package translator.processing;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.util.WaitForProgressToShow;
import org.jetbrains.annotations.NotNull;
import translator.exception.InvalidFileException;
import translator.exception.InvalidFormatException;
import translator.exception.TranslationException;
import translator.model.TranslationFile;
import translator.model.TranslationRequest;
import translator.ui.TranslatorDialog;
import translator.ui.TranslatorPanel;
import translator.util.TranslateUtil;

import java.io.IOException;
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
                TranslationRequest request = new TranslationRequest(p.getLanguage(), p.getKey(), p.getTranslation(), p.isAlphabetizeRequested());
                translationRequestRef.set(request);
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
                .distinct()
                .toArray(String[]::new);
    }


    public void process(TranslationRequest request) {

        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Translating", false) {

            @Override
            public void run(@NotNull ProgressIndicator indicator) {

                indicator.setFraction(0);

                try {

                    int numberOfTranslations = translationFiles.size();
                    for (int i = 0; i < numberOfTranslations; i++) {

                        TranslationFile translationFile = translationFiles.get(i);
                        String translation = getTranslation(request, translationFile.getIsoCode());
                        String newContent = translationFile.addTranslation(request.getKey(), translation, request.isAlphabetizeRequested());
                        updateTranslationFile(translationFile, newContent);

                        indicator.setFraction(((double) i + 1) / numberOfTranslations);
                    }

                } catch (TranslationException | IOException e) {
                    showErrorMessage("Something went wrong while translating");
                } catch (InvalidFileException e) {
                    showErrorMessage("Some files became invalid while translating");
                } catch (InvalidFormatException e) {
                    showErrorMessage(e.getMessage());
                }
            }
        });
    }

    private String getTranslation(TranslationRequest request, String isoCode) throws TranslationException {

        if (request.getLanguage().equalsIgnoreCase(isoCode)) {
            return request.getTranslation();
        } else {
            return TranslateUtil.translate(request.getTranslation(), request.getLanguage(), isoCode);
        }
    }

    private void showErrorMessage(String message) {

        ApplicationManager.getApplication().invokeLater(
                () -> Messages.showErrorDialog(project, message, "Translating"));

    }

    private void updateTranslationFile(TranslationFile translationFile, String newContent) {

        ApplicationManager.getApplication().invokeLater(() -> {
            try {
                WriteAction.run(() -> {
                    VfsUtil.saveText(translationFile.getVirtualFile(), newContent);
                });
            } catch (IOException e) {
                Messages.showErrorDialog(project, "Something went wrong while saving the translation", "Translating");
            }
        });
    }
}
