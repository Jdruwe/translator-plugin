package translator.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;

public class TranslatorDialogWrapper extends DialogWrapper {

    private TranslatorDialogForm translatorDialogForm;

    public TranslatorDialogWrapper(@Nullable Project project) {
        super(project);
        translatorDialogForm = new TranslatorDialogForm();
        init();
        setTitle("Super Awesome Translator");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return translatorDialogForm.getContent();
    }
}
