package at.frysoft.toyide.ui.texteditor;

import at.frysoft.toyide.Log;
import at.frysoft.toyide.Strings;
import at.frysoft.toyide.compiler.tuple.CompilerInstruction;
import at.frysoft.toyide.toy.Instruction;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;

/**
 * Created by Stefan on 20.05.2018.
 */
public class TextEditor extends JComponent {

    private File currentFile;

    private JLabel lineNumbers;

    private JTextPane textPane;
    private TextEditorDocument document;

    public final Style styleColorRed;
    public final Style styleColorBlue;
    public final Style styleColorGreen;
    public final Style styleColorOrange;
    public final Style styleColorGray;
    public final Style defaultStyle;

    private Highlighter highlighter;

    public TextEditor() {
        setLayout(new BorderLayout());

        StyleContext styleContext = new StyleContext();
        document = new TextEditorDocument(styleContext);
        highlighter = new Highlighter(document);
        document.setHighlighter(highlighter);

        defaultStyle = styleContext.addStyle("DEFAULT", null);
        defaultStyle.addAttribute(StyleConstants.FontFamily, "Consolas");
        defaultStyle.addAttribute(StyleConstants.FontSize, 12);
        StyleConstants.setBold(defaultStyle, false);
        StyleConstants.setItalic(defaultStyle, false);


        styleColorRed = styleContext.addStyle("COLOR_RED", defaultStyle);
        styleColorRed.addAttribute(StyleConstants.Foreground, Color.RED);
        StyleConstants.setBold(styleColorRed, true);

        styleColorBlue = styleContext.addStyle("COLOR_BLUE", defaultStyle);
        styleColorBlue.addAttribute(StyleConstants.Foreground, Color.BLUE);
        StyleConstants.setBold(styleColorBlue, true);

        styleColorGreen = styleContext.addStyle("COLOR_GREEN", defaultStyle);
        styleColorGreen.addAttribute(StyleConstants.Foreground, Color.GREEN.darker());
        StyleConstants.setBold(styleColorGreen, true);

        styleColorOrange = styleContext.addStyle("COLOR_ORANGE", defaultStyle);
        styleColorOrange.addAttribute(StyleConstants.Foreground, Color.ORANGE.darker());
        StyleConstants.setBold(styleColorOrange, true);
        highlighter.setNumberStyle(styleColorOrange);

        styleColorGray = styleContext.addStyle("COLOR_GRAY", defaultStyle);
        styleColorGray.addAttribute(StyleConstants.Foreground, Color.GRAY);
        highlighter.setCommentStyle(styleColorGray);

        addKeyWords();

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        for(int i = 1; i <= 20; ++i)
            sb.append(" " + i + " <br>");
        sb.append("</html>");

        lineNumbers = new JLabel(sb.toString(), SwingConstants.RIGHT);
        add(lineNumbers, BorderLayout.WEST);

        textPane = new JTextPane(document); // StyledEditorKit
        textPane.setEditorKit(new TextEditorKit());
        add(new JScrollPane(textPane), BorderLayout.CENTER);

        setVisible(true);
    }

    public File getCurrentFile() {
        return currentFile;
    }

    private void cleanDocument() {
        try {
            document.remove(0, document.getLength());
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    public void loadFile(File file) throws FileNotFoundException {
        if(!file.exists() || file.isDirectory())
            throw new FileNotFoundException(Strings.FILE_NOT_EXIST_OR_DIR);

        cleanDocument();

        FileReader fr = new FileReader(file);

        char[] buffer = new char[1024];
        int bred;

        try {
            while((bred = fr.read(buffer)) != -1) {
                document.insertString(document.getLength(), new String(buffer, 0, bred), defaultStyle);
            }

            fr.close();
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            highlighter.checkForHighlight(0, document.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        currentFile = file;
        Log.out.println(String.format(Strings.FILE_X_LOADED, file.getAbsoluteFile()));
    }

    public void saveFile(File file) {
        if(file.exists() && file.isDirectory())
            throw new IllegalArgumentException(Strings.FILE_NOT_EXIST_OR_DIR);

        try {
            FileWriter fw = new FileWriter(file);

            try {
                fw.write(document.getText(0, document.getLength()));
            } catch (BadLocationException e) {
                e.printStackTrace();
            }

            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        currentFile = file;
        Log.out.println(String.format(Strings.FILE_X_SAVED, file.getAbsoluteFile()));
    }

    public void newFile() {
        currentFile = null;
        cleanDocument();
    }

    private void addKeyWords() {
        KeywordGroup kwg;

        kwg = new KeywordGroup(styleColorRed);
        kwg.setKeywords(
                Strings.COMPILER_INSTRUCTION_ORG,
                Strings.COMPILER_INSTRUCTION_DW,
                Strings.COMPILER_INSTRUCTION_DUP
        );
        highlighter.addKeywordGroup(kwg);

        kwg = new KeywordGroup(styleColorBlue);
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
        highlighter.addKeywordGroup(kwg);

        kwg = new KeywordGroup(styleColorGreen);
        kwg.setKeywords(Strings.REGISTER_NAMES);
        highlighter.addKeywordGroup(kwg);
    }

}
