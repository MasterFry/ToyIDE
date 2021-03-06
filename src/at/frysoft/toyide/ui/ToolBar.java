package at.frysoft.toyide.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Stefan on 21.05.2018.
 */
public class ToolBar extends JPanel {

    public static final String LOAD     = "Load";
    public static final String SAVE     = "Save";
    public static final String SAVE_AS  = "SaveAs";
    public static final String NEW      = "New";
    public static final String COMPILE  = "Compile";
    public static final String START    = "Start";
    public static final String C_A_S    = "Compile'n'start";
    public static final String STEP     = "Step";
    public static final String RUN      = "Run";
    public static final String C_A_R    = "Compile'n'run";
    public static final String STOP     = "Stop";
    public static final String SETTINGS = "Settings";

    public ToolBar(ActionListener actionListener) {
        JButton b;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 10;
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;

        String[] buttonNames = {
            LOAD, SAVE, SAVE_AS, NEW, COMPILE, START,
            C_A_S, STEP, RUN, C_A_R, STOP, SETTINGS
        };

        for(String buttonName : buttonNames) {
            b = new JButton(buttonName);
            b.addActionListener(actionListener);
            add(b);
            ++gbc.gridx;
        }

        setVisible(true);
    }

}
