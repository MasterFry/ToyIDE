package at.frysoft.toyide.ui.texteditor;

import javax.swing.text.*;

/**
 * Created by Stefan on 20.05.2018.
 */
public class TextEditorDocument extends DefaultStyledDocument {

    private Highlighter highlighter;

    public TextEditorDocument(StyleContext styleContext) {
        super(styleContext);
        highlighter = null;
    }

    public void setHighlighter(Highlighter highlighter) {
        this.highlighter = highlighter;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offset, str, a);

        if(highlighter != null)
            highlighter.checkForHighlight(offset, str.length());
    }

    @Override
    public void remove(int offset, int length) throws BadLocationException {
        super.remove(offset, length);
    }

    public int indexOf(char c, int offset) {
        int remainingLength = getLength() - offset;
        int length = 128;
        int index;

        try {
            while(remainingLength != 0) {
                if (length > remainingLength)
                    length = remainingLength;

                remainingLength -= length;

                String str = null;
                    str = getText(offset, length);
                index = str.indexOf(c);

                if (index != -1)
                    return (offset + index);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        return -1;
    }

}
