package translator.processing;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Ref;
import com.intellij.util.WaitForProgressToShow;
import org.jetbrains.annotations.NotNull;
import translator.exception.TranslationException;
import translator.model.TranslationFile;
import translator.model.TranslationRequest;
import translator.ui.TranslatorDialog;
import translator.ui.TranslatorPanel;
import translator.util.TranslateUtil;

import java.util.List;

public class TranslatorProcessor {

    private Project project;
    private List<TranslationFile> translationFiles;

    public TranslatorProcessor(Project project, List<TranslationFile> translationFiles) {
        this.project = project;
        this.translationFiles = translationFiles;
    }

    public TranslationRequest getPromptedTranslationRequest() {

        final Ref<TranslationRequest> translationRequestRef = Ref.create();

        runAboveAll(() -> {

            String[] languages = getLanguages(translationFiles);

            TranslatorDialog translatorDialog = new TranslatorDialog(languages, project);
            translatorDialog.show();

            if (translatorDialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
                TranslatorPanel p = translatorDialog.getTranslatorPanel();
                TranslationRequest request = new TranslationRequest(p.getLanguage(), p.getKey(), p.getTranslation());
                translationRequestRef.set(request);

            } else {
                // TODO: handle this error?
            }
        });

        return translationRequestRef.get();

    }

    private static void runAboveAll(@NotNull final Runnable runnable) {

        ProgressIndicator progressIndicator = ProgressManager.getInstance().getProgressIndicator();
        if (progressIndicator != null && progressIndicator.isModal()) {
            WaitForProgressToShow.runOrInvokeAndWaitAboveProgress(runnable);
        } else {
            Application app = ApplicationManager.getApplication();
            app.invokeAndWait(runnable, ModalityState.any());
        }
    }

    private String[] getLanguages(List<TranslationFile> translationFiles) {

        return translationFiles.stream()
                .map(TranslationFile::getIsoCode)
                .toArray(String[]::new);
    }


    public void process(TranslationRequest request) {

        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Translating", false) {

            @Override
            public void run(@NotNull ProgressIndicator indicator) {

                indicator.setFraction(0);

                try {

                    StringBuilder stringBuilder = new StringBuilder();

                    int numberOfTranslations = translationFiles.size();
                    for (int i = 0; i < numberOfTranslations; i++) {
                        TranslationFile translationFile = translationFiles.get(i);
                        stringBuilder.append(TranslateUtil.translate(request.getTranslation(), request.getLanguage(), translationFile.getIsoCode()));
                        stringBuilder.append("\n");
                        indicator.setFraction(((double) i + 1) / numberOfTranslations);
                    }

                    ApplicationManager.getApplication().invokeLater(
                            () -> Messages.showInfoMessage(project, stringBuilder.toString(), "Translating"));

                } catch (TranslationException e) {
                    e.printStackTrace();
                    ApplicationManager.getApplication().invokeLater(
                            () -> Messages.showErrorDialog(project, "Something went wrong while translating", "Translating"));
                }
            }
        });

    }
}
