package at.frysoft.toyide.ui.texteditor;

import javax.swing.text.*;
import java.awt.*;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Highlighter {

    private HighlightSettings settings;

    private TextEditorDocument document;

    private int offset;

    public Highlighter(TextEditorDocument document) {
        this.document = document;
        offset = 0;
    }

    private boolean isSeparate(int offset, int length, String str) {
        if(offset == 0 || (offset + length) == str.length())
            return false;

        char start = str.charAt(offset - 1);
        char end = str.charAt(offset + length);

        return (
                (
                 start < 33 ||
                 start == '(' ||
                 start == ')' ||
                 start == ',' ||
                 start == ';'
                ) && (
                 end < 33 ||
                 end == '(' ||
                 end == ')' ||
                 end == ',' ||
                 end == ';'
                )
        );
    }

    private void highlight(int offset, int length, int styleIndex) {
        document.setCharacterAttributes(this.offset + offset, length,
                                        settings.getStyle(styleIndex), false);
/*
        if(style == commentStyle)
            document.setCharacterAttributes(this.offset + offset, length, errorStyle, false);
            */
    }

    private void highlightComment(int offset) {
        int index = document.indexOf('\n', offset);
        if(index == -1)
            index = document.getLength();

        int length = index - offset;
        highlight(offset, length, HighlightSettings.DEFAULT_STYLE_COMMENT);
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
                    highlight(numberStart, numberLength, HighlightSettings.DEFAULT_STYLE_NUMBER);
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
