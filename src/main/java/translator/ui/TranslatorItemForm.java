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
}
