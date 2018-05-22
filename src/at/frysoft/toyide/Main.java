package at.frysoft.toyide;

import at.frysoft.toyide.compiler.ToyCompiler;
import at.frysoft.toyide.toy.Toy;
import at.frysoft.toyide.ui.ToyIdeWindow;

import javax.swing.*;
import java.util.Scanner;

public class Main {

    public static final String PATH = "F:\\Programmieren\\JAVA\\ToyIDE\\testfiles\\";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ignore) {
        }

        ToyIdeWindow wnd = new ToyIdeWindow();
        Log.setConsole(wnd.getConsole());

        Log.out.println("Standard out test");
        Log.err.println("Standard err test");
/*
        Toy toy = new Toy();

        toy.load(PATH + "add.toy");

        Scanner scan = new Scanner(System.in);

        do {
            Log.out.print("> ");
            scan.nextLine();

            toy.print();
        } while(toy.step());

        toy.print();
        Log.out.println("Toy Has finished executing.");
        */

        //ToyCompiler tc = new ToyCompiler();
        //tc.compile(PATH + "add.asm", PATH + "test_add.toy");

    }

}
