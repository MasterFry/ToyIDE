package at.frysoft.toyide.ui.texteditor;

import at.frysoft.toyide.ressources.R;
import at.frysoft.toyide.ressources.settings.Settings;

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
        StyleConstants.setFontFamily(defaultStyle, R.settings.getString(R.settings.FONT_FAMILY));
        StyleConstants.setFontSize(defaultStyle, R.settings.getInt(R.settings.FONT_SIZE));
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
                R.strings.compiler.instruction.ORG,
                R.strings.compiler.instruction.DW,
                R.strings.compiler.instruction.DUP
        );
        settings.addKeywordGroup(kwg);

        // Instructions
        style = styleContext.addStyle("INSTRUCTION", defaultStyle);
        style.addAttribute(StyleConstants.Foreground, Color.BLUE);
        StyleConstants.setBold(style, true);
        kwg = new KeywordGroup(style);
        kwg.setKeywords(
                R.strings.toy.instruction.HLT,
                R.strings.toy.instruction.ADD,
                R.strings.toy.instruction.SUB,
                R.strings.toy.instruction.AND,
                R.strings.toy.instruction.XOR,
                R.strings.toy.instruction.SHL,
                R.strings.toy.instruction.SHR,
                R.strings.toy.instruction.LDA,
                R.strings.toy.instruction.LD,
                R.strings.toy.instruction.ST,
                R.strings.toy.instruction.LDI,
                R.strings.toy.instruction.STI,
                R.strings.toy.instruction.BZ,
                R.strings.toy.instruction.BP,
                R.strings.toy.instruction.JR,
                R.strings.toy.instruction.JL,

                R.strings.toy.instruction.PUSH,
                R.strings.toy.instruction.POP,
                R.strings.toy.instruction.CALL,
                R.strings.toy.instruction.RET
        );
        settings.addKeywordGroup(kwg);

        // Registers
        style = styleContext.addStyle("REGISTER", defaultStyle);
        style.addAttribute(StyleConstants.Foreground, Color.GREEN.darker());
        StyleConstants.setBold(style, true);
        kwg = new KeywordGroup(style);
        kwg.setKeywords(R.strings.toy.REGISTER_NAMES);
        settings.addKeywordGroup(kwg);

        return settings;
    }
}
