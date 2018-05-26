package at.frysoft.toyide.ui.texteditor;

import at.frysoft.toyide.Log;
import at.frysoft.toyide.Strings;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;

/**
 * Created by Stefan on 20.05.2018.
 */
public class TextEditor extends JComponent {

    private File currentFile;

    private JLabel lineNumbers;

    private JTextPane textPane;

    private TextEditorDocument document;

    public TextEditor() {
        setLayout(new BorderLayout());

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        for(int i = 1; i <= 20; ++i) {
            sb.append(" ");
            sb.append(i);
            sb.append(" <br>");
        }
        sb.append("</html>");

        lineNumbers = new JLabel(sb.toString(), SwingConstants.RIGHT);
        add(lineNumbers, BorderLayout.WEST);

        textPane = new JTextPane();
        textPane.setEditorKit(new TextEditorKit());
        document = (TextEditorDocument) textPane.getDocument();
        textPane.addCaretListener(document);
        add(new JScrollPane(textPane), BorderLayout.CENTER);

        textPane.getInputMap().put(KeyStroke.getKeyStroke('\t', InputEvent.SHIFT_DOWN_MASK), document.reverseTabAction);

        setVisible(true);
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void loadFile(File file) throws FileNotFoundException {
        if(!file.exists() || file.isDirectory())
            throw new FileNotFoundException(Strings.FILE_NOT_EXIST_OR_DIR);

        try {
            document.readFile(file);
        } catch (IOException | BadLocationException ex) {
            ex.printStackTrace();
        }

        currentFile = file;
        Log.out.println(String.format(Strings.FILE_X_LOADED, file.getAbsoluteFile()));
    }

    public void saveFile(File file) {
        if(file.exists() && file.isDirectory())
            throw new IllegalArgumentException(Strings.FILE_NOT_EXIST_OR_DIR);

        try {
            document.writeFile(file);
        } catch (IOException | BadLocationException ex) {
            ex.printStackTrace();
        }

        currentFile = file;
        Log.out.println(String.format(Strings.FILE_X_SAVED, file.getAbsoluteFile()));
    }

    public void newFile() {
        currentFile = null;
        document.cleanDocument();
    }

}
