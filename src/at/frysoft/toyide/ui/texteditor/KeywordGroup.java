package at.frysoft.toyide.ui.texteditor;

import javax.swing.text.Style;

/**
 * Created by Stefan on 20.05.2018.
 */
public class KeywordGroup {

    private String[] keywords;

    private Style style;

    public KeywordGroup(Style style) {
        this.style = style;
        keywords = null;
    }

    public void setKeywords(String... keywords) {
        this.keywords = new String[keywords.length];
        for(int i = 0; i < keywords.length; ++i) {
            this.keywords[i] = keywords[i];
        }
    }

    public int getKeywordCount() {
        return keywords.length;
    }

    public String getKeyword(int index) {
        return keywords[index];
    }

    public Style getStyle() {
        return style;
    }

}
