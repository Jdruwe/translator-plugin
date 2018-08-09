package translator.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;
import java.util.ArrayList;
import java.util.List;

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

    @NotNull
    @Override
    protected List<ValidationInfo> doValidateAll() {

        TranslatorPanel translatorPanel = getTranslatorPanel();
        String translation = translatorPanel.getTranslation();
        String key = translatorPanel.getKey();

        List<ValidationInfo> validationInfoList = new ArrayList<>();

        if (translation.isEmpty()) {
            validationInfoList.add(new ValidationInfo("Translation is required", translatorPanel.getTranslationTextField()));
        }

        if (key.isEmpty()) {
            validationInfoList.add(new ValidationInfo("Key is required", translatorPanel.getKeyTextField()));
        }

        return validationInfoList;
    }
}
