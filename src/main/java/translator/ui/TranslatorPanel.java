package translator.ui;

import com.intellij.openapi.util.text.StringUtil;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TranslatorPanel {

    private JTextField translationTextField;
    private JPanel mainPanel;
    private JComboBox languageComboBox;
    private JTextField keyTextField;

    public TranslatorPanel(String[] languages) {
        fillLanguagesComboBox(languages);
        setPlaceholder(translationTextField, "Translation");
        setPlaceholder(keyTextField, "Key");
    }

    public JComponent getContent() {
        return mainPanel;
    }

    private void fillLanguagesComboBox(String[] languages) {
        DefaultComboBoxModel<String> languagesModel = new DefaultComboBoxModel<>(languages);
        languageComboBox.setModel(languagesModel);
    }

    private void setPlaceholder(JTextField textField, String placeholder) {
        TextPrompt translationTextPrompt = new TextPrompt(placeholder, textField);
        translationTextPrompt.changeAlpha(0.5f);
    }

    public String getLanguage() {
        return StringUtil.notNullize(String.valueOf(languageComboBox.getSelectedItem()));
    }


    public String getTranslation() {
        return StringUtil.notNullize(translationTextField.getText());
    }


    public String getKey() {
        return StringUtil.notNullize(keyTextField.getText());
    }

    public JTextField getTranslationTextField() {
        return translationTextField;
    }

    public JTextField getKeyTextField() {
        return keyTextField;
    }
}
