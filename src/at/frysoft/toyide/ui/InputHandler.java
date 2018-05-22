package at.frysoft.toyide.ui;

import at.frysoft.toyide.Log;
import at.frysoft.toyide.Utils;
import at.frysoft.toyide.compiler.ToyCompiler;
import at.frysoft.toyide.toy.Toy;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Stefan on 21.05.2018.
 */
public class InputHandler implements ActionListener {

    private ToyIdeWindow toyIdeWindow;

    protected InputHandler(ToyIdeWindow toyIdeWindow) {
        this.toyIdeWindow = toyIdeWindow;
    }

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

            case ToolBar.RUN:
                run();
                break;

            case ToolBar.C_A_R:
                saveFile();
                compile();
                run();
                break;

        }
    }

    private File selectFile() {
        JFileChooser chooser;

        if(toyIdeWindow.projectSettings.workspace == null)
            chooser = new JFileChooser();
        else
            chooser = new JFileChooser(toyIdeWindow.projectSettings.workspace);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Toy-Assembler Files", "asm");
        chooser.setFileFilter(filter);

        int returnValue = chooser.showOpenDialog(toyIdeWindow);
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    private void loadFile() {
        File file = selectFile();
        if(file == null)
            return;

        try {
        toyIdeWindow.textEditor.loadFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveFile() {
        File file = toyIdeWindow.textEditor.getCurrentFile();
        if(file == null)
            file = selectFile();

        if(file == null)
            return;

        toyIdeWindow.textEditor.saveFile(file);
    }

    private void saveFileAs() {
        File file = selectFile();
        if(file == null)
            return;

        toyIdeWindow.textEditor.saveFile(file);
    }

    private void newFile() {
        toyIdeWindow.textEditor.newFile();
    }

    private void compile() {
        ToyCompiler tc = new ToyCompiler();
        tc.compile(toyIdeWindow.textEditor.getCurrentFile());
    }

    private void run() {
        Toy toy = new Toy();

        String dst = Utils.fileNameAsmToToy(toyIdeWindow.textEditor.getCurrentFile().getAbsolutePath());
        if(dst == null)
            return;
        toy.load(dst);

        while(toy.step())
            toy.print();
        toy.print();

        toy.print();
        Log.out.println("Toy Has finished executing.");
    }

}
