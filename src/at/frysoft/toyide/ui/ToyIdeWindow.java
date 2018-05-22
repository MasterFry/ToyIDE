package at.frysoft.toyide.ui;

import at.frysoft.toyide.ProjectSettings;
import at.frysoft.toyide.ui.texteditor.TextEditor;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Stefan on 20.05.2018.
 */
public class ToyIdeWindow extends JFrame {

    protected Console console;
    protected TextEditor textEditor;

    protected ProjectSettings projectSettings;

    private InputHandler inputHandler;

    public ToyIdeWindow() {
        super("Toy IDE");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        int width = 900;
        int height = 600;

        projectSettings = new ProjectSettings();
        projectSettings.workspace = new File("F:\\Programmieren\\JAVA\\ToyIDE\\testfiles\\");

        inputHandler = new InputHandler(this);
        ToolBar toolBar = new ToolBar(inputHandler);
        add(toolBar, BorderLayout.NORTH);

        textEditor = new TextEditor();
        add(textEditor, BorderLayout.CENTER);

        console = new Console();
        add(console, BorderLayout.SOUTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);
        setSize(width, height);
        setVisible(true);
    }

    public Console getConsole() {
        return console;
    }

}
