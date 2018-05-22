package at.frysoft.toyide.ui.texteditor;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Highlighter {

    private KeywordGroup[] keywordGroups;

    private Style numberStyle;
    private Style commentStyle;

    private TextEditorDocument document;

    private int offset;

    public Highlighter(TextEditorDocument document) {
        this.document = document;
        keywordGroups = new KeywordGroup[0];
        numberStyle = null;
        offset = 0;
    }

    public void setNumberStyle(Style numberStyle) {
        this.numberStyle = numberStyle;
    }

    public void setCommentStyle(Style commentStyle) {
        this.commentStyle = commentStyle;
    }

    public void addKeywordGroup(KeywordGroup keywordGroup) {
        KeywordGroup[] kwgs = new KeywordGroup[keywordGroups.length + 1];
        System.arraycopy(keywordGroups, 0, kwgs, 0, keywordGroups.length);
        kwgs[keywordGroups.length] = keywordGroup;
        keywordGroups = kwgs;
    }

    private boolean isSeparate(int offset, int length, String str) {
        if(offset == 0 || (offset + length) == str.length())
            return false;

        char start = str.charAt(offset - 1);
        char end = str.charAt(offset + length);

        return (
                (
                 start < 33 ||
                 start == ' ' ||
                 start == '(' ||
                 start == ')' ||
                 start == ',' ||
                 start == ';'
                ) && (
                 end < 33 ||
                 end == ' ' ||
                 end == '(' ||
                 end == ')' ||
                 end == ',' ||
                 end == ';'
                )
        );
    }

    private void highlight(int offset, int length, Style style) {
        document.setCharacterAttributes(this.offset + offset, length, style, false);
    }

    private void highlightComment(int offset) {
        if(commentStyle == null)
            return;

        int index = document.indexOf('\n', offset);
        if(index == -1)
            index = document.getLength();

        int length = index - offset;
        highlight(offset, length, commentStyle);
    }

    private void highlightKeyword(String str, String keyword, Style style) {
        int pos = -1;
        int length = keyword.length();

        while((pos = str.indexOf(keyword, pos + 1)) != -1) {
            if(isSeparate(pos, length, str))
                highlight(pos, length, style);
        }
    }

    private void highlightNumbers(String str) {
        if(numberStyle == null)
            return;

        str = str.toUpperCase();

        char c;
        int numberStart = -1;
        int numberLength = 0;
        boolean hexadecimal = false;

        for(int i = 0; i < str.length(); ++i) {
            c = str.charAt(i);

            if(c >= '0' && c <= '9') {
                if(numberLength == 0)
                    numberStart = i;

                ++numberLength;
                continue;
            }

            if(c == 'X' && numberLength == 1) {
                ++numberLength;
                hexadecimal = true;
                continue;
            }

            if(hexadecimal && c >= 'A' && c <= 'F') {
                ++numberLength;
                continue;
            }

            if(numberLength != 0) {
                if(isSeparate(numberStart, numberLength, str))
                    highlight(numberStart, numberLength, numberStyle);
                numberLength = 0;
                hexadecimal = false;
            }
        }
    }

    public void checkForHighlight(int offset, int length) throws BadLocationException {
        offset -= 5;
        if(offset < 0)
            offset = 0;
        this.offset = offset;

        length += 5;
        if(document.getLength() < offset + length)
            length = document.getLength() - offset;

        String str = document.getText(offset, length);

        int commentStart = str.indexOf(';');
        if(commentStart != -1) {
            str = str.substring(0, commentStart);
            highlightComment(commentStart);
        }

        Style style;
        for(KeywordGroup kwg : keywordGroups) {
            style = kwg.getStyle();

            for(int i = 0; i < kwg.getKeywordCount(); ++i) {
                highlightKeyword(str, kwg.getKeyword(i), style);
            }
        }

        highlightNumbers(str);
    }

}
