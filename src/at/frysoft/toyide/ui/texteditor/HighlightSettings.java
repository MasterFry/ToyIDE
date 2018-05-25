package at.frysoft.toyide.ui.texteditor;

import at.frysoft.toyide.Strings;

import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class HighlightSettings {

    private Vector<Style> styles;

    private HashMap<Integer, String[]> keywordGroups;

    public HighlightSettings() {
    }

    public int addStyle(Style style) {
        styles.add(style);
        return (styles.size() - 1);
    }

    public void addKeywordGroup(String[] kwg, Style style) {
        keywordGroups.put(addStyle(style), kwg);
    }

    public Style getStyle(int index) {
        return styles.get(index);
    }

    public Set<Map.Entry<Integer, String[]>> getKeyWordGroups() {
        return keywordGroups.entrySet();
    }

    public static int DEFAULT_STYLE;
    public static int DEFAULT_STYLE_NUMBER;
    public static int DEFAULT_STYLE_COMMENT;
    public static int DEFAULT_STYLE_ERROR;

    public static HighlightSettings createDefault(StyleContext styleContext) {
        HighlightSettings settings = new HighlightSettings();
        String[] keyWords;
        Style defaultStyle, style;

        defaultStyle = styleContext.addStyle("DEFAULT", null);
        defaultStyle.addAttribute(StyleConstants.FontFamily, "Consolas");
        defaultStyle.addAttribute(StyleConstants.FontSize, 12);
        StyleConstants.setBold(defaultStyle, false);
        StyleConstants.setItalic(defaultStyle, false);
        DEFAULT_STYLE = settings.addStyle(defaultStyle);

        style = styleContext.addStyle("COLOR_ORANGE", defaultStyle);
        style.addAttribute(StyleConstants.Foreground, Color.ORANGE.darker());
        StyleConstants.setBold(style, true);
        DEFAULT_STYLE_NUMBER = settings.addStyle(style);

        style = styleContext.addStyle("COLOR_GRAY", defaultStyle);
        style.addAttribute(StyleConstants.Foreground, Color.GRAY);
        DEFAULT_STYLE_COMMENT = settings.addStyle(style);

        style = styleContext.addStyle("ERROR", defaultStyle);
        style.addAttribute("ERROR", true);
        DEFAULT_STYLE_ERROR = settings.addStyle(style);

        keyWords = new String[]{
                Strings.COMPILER_INSTRUCTION_ORG,
                Strings.COMPILER_INSTRUCTION_DW,
                Strings.COMPILER_INSTRUCTION_DUP
        };
        style = styleContext.addStyle("COLOR_RED", defaultStyle);
        style.addAttribute(StyleConstants.Foreground, Color.RED);
        StyleConstants.setBold(style, true);
        settings.addKeywordGroup(keyWords, style);

        keyWords = new String[] {
                Strings.INSTRUCTION_HLT,
                Strings.INSTRUCTION_ADD,
                Strings.INSTRUCTION_SUB,
                Strings.INSTRUCTION_AND,
                Strings.INSTRUCTION_XOR,
                Strings.INSTRUCTION_SHL,
                Strings.INSTRUCTION_SHR,
                Strings.INSTRUCTION_LDA,
                Strings.INSTRUCTION_LD,
                Strings.INSTRUCTION_ST,
                Strings.INSTRUCTION_LDI,
                Strings.INSTRUCTION_STI,
                Strings.INSTRUCTION_BZ,
                Strings.INSTRUCTION_BP,
                Strings.INSTRUCTION_JR,
                Strings.INSTRUCTION_JL
        };
        style = styleContext.addStyle("COLOR_BLUE", defaultStyle);
        style.addAttribute(StyleConstants.Foreground, Color.BLUE);
        StyleConstants.setBold(style, true);
        settings.addKeywordGroup(keyWords, style);

        style = styleContext.addStyle("COLOR_GREEN", defaultStyle);
        style.addAttribute(StyleConstants.Foreground, Color.GREEN.darker());
        StyleConstants.setBold(style, true);
        settings.addKeywordGroup(Strings.REGISTER_NAMES, style);

        return settings;
    }
}
