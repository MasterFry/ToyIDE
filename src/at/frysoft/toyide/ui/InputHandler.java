package at.frysoft.toyide.ui;

import at.frysoft.toyide.Log;
import at.frysoft.toyide.Utils;
import at.frysoft.toyide.compiler.ToyCompiler;
import at.frysoft.toyide.ressources.R;
import at.frysoft.toyide.ui.settings.SettingsWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

public class InputHandler implements ActionListener {

    private ToyIdeWindow toyIdeWindow;

    // -----------------------------------------------------------------------------------------------------------------
    protected InputHandler(ToyIdeWindow toyIdeWindow) {
        this.toyIdeWindow = toyIdeWindow;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {

            case ToolBar.LOAD:
                loadFile();
                break;

            case ToolBar.SAVE:
                saveFile();
                break;

            case ToolBar.SAVE_AS:
                saveFileAs();
                break;

            case ToolBar.NEW:
                newFile();
                break;

            case ToolBar.COMPILE:
                saveFile();
                compile();
                break;

            case ToolBar.START:
                start();
                break;

            case ToolBar.C_A_S:
                saveFile();
                compile();
                start();
                break;

            case ToolBar.STEP:
                step();
                break;

            case ToolBar.RUN:
                run();
                break;

            case ToolBar.C_A_R:
                saveFile();
                if(compile()) {
                    start();
                    run();
                }
                break;

            case ToolBar.STOP:
                stop();
                break;

            case ToolBar.SETTINGS:
                new SettingsWindow(toyIdeWindow);
                break;

        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    private File selectFile(String type) {
        File file = new File(R.settings.getString(R.settings.WORKSPACE));

        JFileChooser chooser;
        if(file.exists() && file.isDirectory())
            chooser = new JFileChooser(file);
        else
            chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Toy-Assembler Files", "asm");
        chooser.setFileFilter(filter);

        int returnValue = chooser.showDialog(toyIdeWindow, type);
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    private void loadFile() {
        File file = selectFile(R.strings.OPEN);
        if(file == null)
            return;

        try {
        toyIdeWindow.getTextEditor().loadFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    private void saveFile() {
        File file = toyIdeWindow.getTextEditor().getCurrentFile();
        if(file == null)
            file = selectFile(R.strings.SAVE);

        if(file == null)
            return;

        toyIdeWindow.getTextEditor().saveFile(file);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private void saveFileAs() {
        File file = selectFile(R.strings.SAVE);
        if(file == null)
            return;

        toyIdeWindow.getTextEditor().saveFile(file);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private void newFile() {
        toyIdeWindow.getTextEditor().newFile();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private boolean compile() {
        return ToyCompiler.compile(toyIdeWindow.getTextEditor().getCurrentFile());
    }

    // -----------------------------------------------------------------------------------------------------------------
    private void start() {
        toyIdeWindow.getComputer().reset();

        String dst = Utils.fileNameAsmToToy(toyIdeWindow.getTextEditor().getCurrentFile().getAbsolutePath());
        if(dst == null)
            return;

        toyIdeWindow.getComputer().getBus().getMainMemory().load(new File(dst));
        toyIdeWindow.getComputer().getCpu().start();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private void step() {
        toyIdeWindow.getComputer().getCpu().step();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private void run() {
        (new Thread(() -> toyIdeWindow.getComputer().getCpu().run())).start();
        Log.out.println("Toy Has finished executing.");
    }

    private void stop() {
        toyIdeWindow.getComputer().getCpu().stop();
    }

}
