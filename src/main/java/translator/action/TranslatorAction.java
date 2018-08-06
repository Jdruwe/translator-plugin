package translator.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import translator.exception.InvalidOriginException;
import translator.extraction.TranslationFileExtractor;
import translator.ui.TranslatorDialogWrapper;

public class TranslatorAction extends AnAction {

    private static final String PLUGIN_GROUP_ID = "be_jeroendruwe_translator";

    @Override
    public void actionPerformed(AnActionEvent e) {

        VirtualFile origin = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        try {
            new TranslationFileExtractor(origin);
        } catch (InvalidOriginException ex) {
            Notifications.Bus.notify(new Notification(PLUGIN_GROUP_ID, "Invalid origin", "Only directories are supported.", NotificationType.WARNING));
        }

        TranslatorDialogWrapper translatorDialogWrapper = new TranslatorDialogWrapper(e.getProject());
        translatorDialogWrapper.show();
    }
}
