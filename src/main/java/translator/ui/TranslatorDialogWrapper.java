package translator.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import translator.model.TranslationFile;

import javax.swing.JComponent;
import java.util.List;

public class TranslatorDialogWrapper extends DialogWrapper {

    private TranslatorDialogForm translatorDialogForm;

    public TranslatorDialogWrapper(List<TranslationFile> translationFiles, @Nullable Project project) {
        super(project);
        translatorDialogForm = new TranslatorDialogForm(translationFiles);
        init();
        setTitle("Super Awesome Translator");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return translatorDialogForm.getContent();
    }
}
