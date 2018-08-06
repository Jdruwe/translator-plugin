package translator.ui;

import translator.model.TranslationFile;

import javax.swing.*;
import java.util.List;

public class TranslatorDialogForm {

    private List<TranslationFile> translationFiles;

    private JPanel mainPanel;
    private JPanel contentPanel;

    public TranslatorDialogForm(List<TranslationFile> translationFiles) {
        this.translationFiles = translationFiles;
    }

    public JComponent getContent() {
        return mainPanel;
    }

    private void createUIComponents() {

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));

        for(TranslationFile translationFile: translationFiles){
            TranslatorItemForm translatorItemForm = new TranslatorItemForm();
            translatorItemForm.setIsoCode(translationFile.getVirtualFile().getName());
            contentPanel.add(translatorItemForm.getContent());
        }


    }
}
