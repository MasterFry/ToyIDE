package at.frysoft.toyide.ui.texteditor;

import at.frysoft.toyide.ressources.R;
import at.frysoft.toyide.ressources.settings.SettingInteger;
import at.frysoft.toyide.ressources.settings.Settings;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.*;

/**
 * Created by Stefan on 20.05.2018.
 */
public class TextEditorDocument extends DefaultStyledDocument implements CaretListener {

    private Highlighter highlighter;

    private int caretPosition;
    private String indent;

    public final ReverseTabAction reverseTabAction;

    public TextEditorDocument(StyleContext styleContext) {
        super(styleContext);
        highlighter = null;

        caretPosition = 0;

        int indentCount = R.settings.getInt(R.settings.INDENT);
        indent = String.format("%" + indentCount + "c", ' ');

        reverseTabAction = new ReverseTabAction();
    }

    public void setHighlighter(Highlighter highlighter) {
        this.highlighter = highlighter;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        str = str.replace("\t", indent);

        super.insertString(offset, str, highlighter.getDefaultStyle());

        if(highlighter != null)
            highlighter.checkForHighlight(offset, str.length());
    }

    @Override
    public void remove(int offset, int length) throws BadLocationException {
        super.remove(offset, length);
        // TODO check for highlight
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        caretPosition = e.getDot();
    }

    public void cleanDocument() {
        try {
            remove(0, getLength());
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    public void readFile(File file) throws IOException, BadLocationException {
        remove(0, getLength());

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while((line = br.readLine()) != null) {
            insertString(getLength(), line + "\n", highlighter.getDefaultStyle());
        }

        br.close();
    }

    public void writeFile(File file) throws IOException, BadLocationException {
        FileWriter fw = new FileWriter(file);

        fw.write(getText(0, getLength()));

        fw.close();
    }

    public void reverseTab() {
        try {
            remove(caretPosition - 4, 4);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    public class ReverseTabAction implements Action {

        @Override
        public Object getValue(String key) {
            return this;
        }

        @Override
        public void putValue(String key, Object value) {
        }

        @Override
        public void setEnabled(boolean b) {
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener listener) {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //reverseTab();
        }

    }

}
