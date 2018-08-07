package translator.ui;

import translator.model.TranslationFile;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.List;

public class TranslatorDialogForm {

    private List<TranslationFile> translationFiles;

    private JPanel mainPanel;
    private JPanel contentPanel;
    private JButton translateButton;
    private JTextField translationKey;
    private JPanel actionPanel;

    private List<TranslatorItemForm> translatorItems;

    public TranslatorDialogForm(List<TranslationFile> translationFiles) {
        this.translationFiles = translationFiles;
        addTranslations();
        setListeners();
    }

    public JComponent getContent() {
        return mainPanel;
    }

    private void createUIComponents() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
    }

    private void addTranslations() {

        translatorItems = new ArrayList<>();

        for (TranslationFile translationFile : translationFiles) {
            TranslatorItemForm translatorItemForm = new TranslatorItemForm();
            translatorItemForm.setIsoCode(translationFile.getIsoCode());
            translatorItemForm.hide();
            translatorItems.add(translatorItemForm);
            contentPanel.add(translatorItemForm.getContent());
        }
    }

    private void showTranslations() {
        for (TranslatorItemForm translatorItemForm : translatorItems) {
            translatorItemForm.show();
        }
    }

    private void resetTranslations() {
        for (TranslatorItemForm translatorItemForm : translatorItems) {
            translatorItemForm.hide();
        }
    }

    private void setListeners() {
        translateButton.addActionListener(e -> {
            showTranslations();
        });
    }
}
