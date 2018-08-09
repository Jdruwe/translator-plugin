package translator.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import translator.exception.InvalidOriginException;
import translator.exception.NoTranslationFilesFoundException;
import translator.extraction.TranslationFileExtractor;
import translator.model.TranslationFile;
import translator.model.TranslationRequest;
import translator.processing.TranslatorProcessor;

import java.util.List;

public class TranslatorAction extends AnAction {

    private static final String PLUGIN_GROUP_ID = "be_jeroendruwe_translator";

    @Override
    public void actionPerformed(AnActionEvent e) {

        VirtualFile origin = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        try {

            List<TranslationFile> translationFiles = new TranslationFileExtractor(origin).getTranslationFiles();
            TranslatorProcessor translatorProcessor = new TranslatorProcessor(e.getProject(), translationFiles);
            TranslationRequest request = translatorProcessor.getPromptedTranslationRequest();
            handleRequest(translatorProcessor, request);

        } catch (InvalidOriginException ex) {
            notify("Translator - Invalid origin", "Only directories are supported.", NotificationType.WARNING);
        } catch (NoTranslationFilesFoundException e1) {
            notify("Translator - None found", "No translation files found.", NotificationType.WARNING);
        }
    }

    private void handleRequest(TranslatorProcessor translatorProcessor, TranslationRequest request) {

        if (request != null) {
            // Can be null in case of a dialog cancel or close
            translatorProcessor.process(request);
        } else {
            notify("Translator", "Operation has been cancelled.", NotificationType.INFORMATION);
        }
    }

    private void notify(String title, String message, NotificationType notificationType) {
        Notifications.Bus.notify(new Notification(PLUGIN_GROUP_ID, title, message, notificationType));
    }
}
