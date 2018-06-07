package at.frysoft.toyide.ui;

import at.frysoft.toyide.computer.Computer;
import at.frysoft.toyide.computer.cpu.CPU;
import at.frysoft.toyide.computer.cpu.Toy;
import at.frysoft.toyide.ui.computer.ComputerPanel;
import at.frysoft.toyide.ui.texteditor.TextEditor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Stefan on 20.05.2018.
 */
public class ToyIdeWindow extends JFrame {

    private Computer computer;

    private Console console;
    private TextEditor textEditor;
    private ComputerPanel computerPanel;

    private InputHandler inputHandler;

    public ToyIdeWindow() {
        super("Toy IDE");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        int width = 1024;
        int height = 576;

        computer = new Computer(Computer.CPU_STOY);

        inputHandler = new InputHandler(this);
        ToolBar toolBar = new ToolBar(inputHandler);
        add(toolBar, BorderLayout.NORTH);

        textEditor = new TextEditor();
        console = new Console();
        computerPanel = new ComputerPanel(computer);

        JScrollPane cpuPane = new JScrollPane(computerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        cpuPane.getVerticalScrollBar().setUnitIncrement(16);

        JSplitPane sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(textEditor), new JScrollPane(console));
        sp1.setResizeWeight(0.85);
        sp1.setDividerSize(2);
        sp1.setOneTouchExpandable(true);
        sp1.setContinuousLayout(true);

        JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, cpuPane);
        sp2.setResizeWeight(0.75);
        sp2.setDividerSize(2);
        sp2.setOneTouchExpandable(true);
        sp2.setContinuousLayout(true);

        add(sp2, BorderLayout.CENTER);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);
        setSize(width, height);
        setVisible(true);
    }

    public Console getConsole() {
        return console;
    }

    public TextEditor getTextEditor() {
        return textEditor;
    }

    public Computer getComputer() {
        return computer;
    }

}
