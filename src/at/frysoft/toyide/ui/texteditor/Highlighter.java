package at.frysoft.toyide.ui.texteditor;

import javafx.util.Pair;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Style;
import java.util.Vector;

/**
 * Highlighter.java
 * <p>
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class Highlighter {

    private HighlightSettings settings;

    private TextEditorDocument document;

    public Highlighter(TextEditorDocument document, HighlightSettings settings) {
        this.document = document;
        this.settings = settings;
    }

    public void setSettings(HighlightSettings settings) {
        this.settings = settings;
    }

    public Style getDefaultStyle() {
        return settings.getStyle(HighlightSettings.DEFAULT_STYLE);
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

    private void highlight(int offset, int length, Style style) {
        document.setCharacterAttributes(offset, length, style, false);
/*
        if(style == commentStyle)
            document.setCharacterAttributes(this.offset + offset, length, errorStyle, false);
            */
    }

    private void highlight(int offset, int length, int styleIndex) {
        highlight(offset, length, settings.getStyle(styleIndex));
    }

    private void restoreDefaults(int offset, int length) {
        document.setCharacterAttributes(
                offset,
                length,
                settings.getStyle(HighlightSettings.DEFAULT_STYLE),
                true
       );
    }

    private void highlightKeyword(int offset, String word) {
        Style style;
        for(KeywordGroup kwg : settings.getKeyWordGroups()) {
            style = kwg.getStyle();

            for(int i = 0; i < kwg.getKeywordCount(); ++i) {
                if(kwg.getKeyword(i).equals(word)) {
                    highlight(offset, word.length(), style);
                    return;
                }
            }
        }
        restoreDefaults(offset, word.length());
    }

    private boolean isNumber(String word) {
        if(word.length() < 3 || word.charAt(0) != '0' || word.charAt(1) != 'x')
            return false;

        try {
            Integer.parseInt(word.substring(2), 16);
        }catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    private void highlightParagraph(Element paragraph) {
        String line;
        int offset = paragraph.getStartOffset();

        try {
            line = document.getText(paragraph.getStartOffset(), paragraph.getEndOffset() - paragraph.getStartOffset());
        } catch (BadLocationException e) {
            e.printStackTrace();
            return;
        }

        int commentStart = line.indexOf(';');
        if(commentStart != -1) {
            highlight(commentStart + offset, line.length() - commentStart, HighlightSettings.DEFAULT_STYLE_COMMENT);
            line = line.substring(0, commentStart);
        }

        if(line.isEmpty())
            return;

        if(line.charAt(line.length() - 1) == '\n')
            line = line.substring(0, line.length() - 1);

        String[] words = line.split(" ");

        for(String word : words) {
            if(!word.isEmpty()) {
                if(isNumber(word))
                    highlight(offset, word.length(), HighlightSettings.DEFAULT_STYLE_NUMBER);
                else
                    highlightKeyword(offset, word);
            }
            offset += word.length() + 1;
        }


    }

    public void checkForHighlight(int offset, int length) throws BadLocationException {
        Element paragraphStart = document.getParagraphElement(offset);
        Element paragraphEnd = document.getParagraphElement(offset + length - 1);

        if(paragraphStart.getStartOffset() != paragraphEnd.getStartOffset()) {
            highlightParagraph(paragraphEnd);

            if(paragraphStart.getEndOffset() != paragraphEnd.getStartOffset()) {
                Element paragraph;
                offset = paragraphStart.getEndOffset() + 1;

                while (offset < paragraphEnd.getStartOffset()) {
                    paragraph = document.getParagraphElement(offset);
                    highlightParagraph(paragraph);
                    offset = paragraph.getEndOffset() + 1;
                }
            }
        }

        highlightParagraph(paragraphStart);
    }

}
