package translator.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import translator.exception.InvalidOriginException;
import translator.extraction.TranslationFileExtractor;
import translator.model.TranslationFile;
import translator.ui.TranslatorDialogWrapper;

import java.util.List;

public class TranslatorAction extends AnAction {

    private static final String PLUGIN_GROUP_ID = "be_jeroendruwe_translator";

    @Override
    public void actionPerformed(AnActionEvent e) {

        VirtualFile origin = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        try {
            List<TranslationFile> translationFiles = new TranslationFileExtractor(origin).getTranslationFiles();
            showTranslatorDialog(translationFiles, e.getProject());
        } catch (InvalidOriginException ex) {
            Notifications.Bus.notify(new Notification(PLUGIN_GROUP_ID, "Invalid origin", "Only directories are supported.", NotificationType.WARNING));
        }
    }

    private void showTranslatorDialog(List<TranslationFile> translationFiles, Project project) {

        TranslatorDialogWrapper translatorDialogWrapper = new TranslatorDialogWrapper(translationFiles, project);
        translatorDialogWrapper.show();
    }

}
