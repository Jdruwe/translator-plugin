package translator.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;

public class TranslatorDialog extends DialogWrapper {

    private TranslatorPanel translatorPanel;

    public TranslatorDialog(String[] languages, @Nullable Project project) {
        super(project);
        translatorPanel = new TranslatorPanel(languages);
        init();
        setTitle("Super Awesome Translator");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return translatorPanel.getContent();
    }

    public TranslatorPanel getTranslatorPanel() {
        return translatorPanel;
    }
}
