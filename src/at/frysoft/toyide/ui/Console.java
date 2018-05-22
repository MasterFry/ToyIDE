package at.frysoft.toyide.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Console extends JComponent {

    private JTextPane textPane;
    private DefaultStyledDocument document;

    public final Style styleColorRed;

    public Console() {
        setLayout(new BorderLayout());

        StyleContext styleContext = new StyleContext();
        document = new DefaultStyledDocument(styleContext);

        textPane = new JTextPane(document);
        textPane.setEditable(false);
        add(new JScrollPane(textPane), BorderLayout.CENTER);

        styleColorRed = styleContext.addStyle("COLOR_RED", null);
        styleColorRed.addAttribute(StyleConstants.Foreground, Color.RED);

        setVisible(true);
    }

    public void addText(String text, AttributeSet attributeSet) {
        try {
            document.insertString(document.getLength(), text, attributeSet);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private class ConsoleDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }

    }

}
