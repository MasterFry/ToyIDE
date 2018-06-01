package at.frysoft.toyide.ui.texteditor;

import at.frysoft.toyide.Log;
import at.frysoft.toyide.ressources.R;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Stefan on 20.05.2018.
 */
public class TextEditor extends JComponent {

    private File currentFile;

    private JTextPane textPane;

    private TextEditorDocument document;

    public TextEditor() {
        setLayout(new BorderLayout());

        textPane = new JTextPane();
        textPane.setEditorKit(new TextEditorKit());
        document = (TextEditorDocument) textPane.getDocument();
        textPane.addCaretListener(document);

        JScrollPane sp = new JScrollPane(textPane);
        sp.setRowHeaderView(new TextLineNumber(textPane));
        add(sp, BorderLayout.CENTER);

        textPane.getInputMap().put(KeyStroke.getKeyStroke('\t', InputEvent.SHIFT_DOWN_MASK), document.reverseTabAction);

        setVisible(true);
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void loadFile(File file) throws FileNotFoundException {
        if(!file.exists() || file.isDirectory())
            throw new FileNotFoundException(R.strings.file.NOT_EXIST_OR_IS_DIR);

        try {
            document.readFile(file);
        } catch (IOException | BadLocationException ex) {
            ex.printStackTrace();
        }

        currentFile = file;
        Log.out.println(R.strings.file.LOADED(file));
    }

    public void saveFile(File file) {
        if(file.exists() && file.isDirectory())
            throw new IllegalArgumentException(R.strings.file.NOT_EXIST_OR_IS_DIR);

        try {
            document.writeFile(file);
        } catch (IOException | BadLocationException ex) {
            ex.printStackTrace();
        }

        currentFile = file;
        Log.out.println(R.strings.file.SAVED(file));
    }

    public void newFile() {
        currentFile = null;
        document.cleanDocument();
    }

}
