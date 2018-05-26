package at.frysoft.toyide.ui.texteditor;

import at.frysoft.toyide.Strings;

import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Vector;

public class HighlightSettings {

    private Vector<Style> styles;

    private Vector<KeywordGroup> keywordGroups;

    public HighlightSettings() {
        styles = new Vector<>();
        keywordGroups = new Vector<>();
    }

    public int addStyle(Style style) {
        styles.add(style);
        return (styles.size() - 1);
    }

    public void addKeywordGroup(KeywordGroup keywordGroup) {
        keywordGroups.add(keywordGroup);
    }

    public Style getStyle(int index) {
        return styles.get(index);
    }

    public Vector<KeywordGroup> getKeyWordGroups() {
        return keywordGroups;
    }

    public static int DEFAULT_STYLE;
    public static int DEFAULT_STYLE_NUMBER;
    public static int DEFAULT_STYLE_COMMENT;
    public static int DEFAULT_STYLE_ERROR;

    public static HighlightSettings createDefault(StyleContext styleContext) {
        HighlightSettings settings = new HighlightSettings();
        KeywordGroup kwg;
        Style defaultStyle, style;

        // Default Style
        defaultStyle = styleContext.addStyle("DEFAULT", null);
        //defaultStyle.addAttribute(StyleConstants.FontFamily, "Consolas");
        StyleConstants.setFontFamily(defaultStyle, "Monospaced");
        StyleConstants.setFontSize(defaultStyle, 12);
        StyleConstants.setBold(defaultStyle, false);
        StyleConstants.setItalic(defaultStyle, false);
        DEFAULT_STYLE = settings.addStyle(defaultStyle);

        // Numbers
        style = styleContext.addStyle("NUMBER", defaultStyle);
        style.addAttribute(StyleConstants.Foreground, Color.ORANGE.darker());
        StyleConstants.setBold(style, true);
        DEFAULT_STYLE_NUMBER = settings.addStyle(style);

        // Comments
        style = styleContext.addStyle("COMMENT", defaultStyle);
        style.addAttribute(StyleConstants.Foreground, Color.GRAY);
        DEFAULT_STYLE_COMMENT = settings.addStyle(style);

        // Syntax Errors
        style = styleContext.addStyle("ERROR", defaultStyle);
        style.addAttribute("ERROR", true);
        DEFAULT_STYLE_ERROR = settings.addStyle(style);

        // ToyCompiler Instructions
        style = styleContext.addStyle("COMPILER_INSTRUCTION", defaultStyle);
        style.addAttribute(StyleConstants.Foreground, new Color(200, 140, 255));
        StyleConstants.setBold(style, true);
        kwg = new KeywordGroup(style);
        kwg.setKeywords(
                Strings.COMPILER_INSTRUCTION_ORG,
                Strings.COMPILER_INSTRUCTION_DW,
                Strings.COMPILER_INSTRUCTION_DUP
        );
        settings.addKeywordGroup(kwg);

        // Instructions
        style = styleContext.addStyle("INSTRUCTION", defaultStyle);
        style.addAttribute(StyleConstants.Foreground, Color.BLUE);
        StyleConstants.setBold(style, true);
        kwg = new KeywordGroup(style);
        kwg.setKeywords(
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
        );
        settings.addKeywordGroup(kwg);

        // Registers
        style = styleContext.addStyle("REGISTER", defaultStyle);
        style.addAttribute(StyleConstants.Foreground, Color.GREEN.darker());
        StyleConstants.setBold(style, true);
        kwg = new KeywordGroup(style);
        kwg.setKeywords(Strings.REGISTER_NAMES);
        settings.addKeywordGroup(kwg);

        return settings;
    }
}
