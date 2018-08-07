package translator.ui;

import javax.swing.*;

public class TranslatorItemForm {

    private JPanel mainPanel;
    private JLabel isoCode;
    private JTextField translation;

    public JComponent getContent() {
        return mainPanel;
    }

    public void setIsoCode(String text) {
        isoCode.setText(text);
    }

    public void setTranslation(String text) {
        translation.setText(text);
    }

    public void show() {
        mainPanel.setVisible(true);
    }

    public void hide() {
        mainPanel.setVisible(false);
    }
}
