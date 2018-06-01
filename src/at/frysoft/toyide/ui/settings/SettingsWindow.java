package at.frysoft.toyide.ui.settings;

import at.frysoft.toyide.Log;
import at.frysoft.toyide.ressources.R;
import at.frysoft.toyide.ressources.settings.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class SettingsWindow extends JFrame implements ActionListener, WindowListener {

    private static final String BUTTON_OK = "OK";
    private static final String BUTTON_CANCEL = "Cancel";
    private static final String BUTTON_APPLY = "Apply";

    private Window parent;

    private SettingView[] settingViews;

    public SettingsWindow(Window parent) {
        super("Toy IDE Settings");
        this.parent = parent;
        parent.setEnabled(false);
        //parent.setFocusable(false);

        int width = 400;
        int height = 400;

        setLayout(new BorderLayout());
        addWindowListener(this);
        JButton b;

        // -------------------------------------------------------------------------------------------------------------
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0f;
        gbc.insets = new Insets(1, 5, 1, 5);

        Setting[] settings = R.settings.getSettings();
        settingViews = new SettingView[settings.length];

        for(int i = 0; i < settings.length; ++i) {
            settingViews[i] = createSettingView(settings[i]);
            settingsPanel.add(settingViews[i], gbc);
            ++gbc.gridy;
        }

        add(settingsPanel, BorderLayout.CENTER);

        // -------------------------------------------------------------------------------------------------------------
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        b = new JButton(BUTTON_OK);
        b.addActionListener(this);
        bottomPanel.add(b);

        b = new JButton(BUTTON_CANCEL);
        b.addActionListener(this);
        bottomPanel.add(b);

        b = new JButton(BUTTON_APPLY);
        b.addActionListener(this);
        bottomPanel.add(b);

        add(bottomPanel, BorderLayout.SOUTH);

        // -------------------------------------------------------------------------------------------------------------
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);
        setSize(width, height);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {

            case BUTTON_OK:
                applySettings();
                // No break.
            case BUTTON_CANCEL:
                setVisible(false);
                dispose();
                parent.setEnabled(true);
                parent.requestFocus();
                break;

            case BUTTON_APPLY:
                applySettings();
                break;

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        parent.setEnabled(true);
        parent.requestFocus();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    private void applySettings() {
        for(SettingView v : settingViews) {
            try {
                R.settings.set(v.getSettingId(), v.getSettingValue());
            } catch (InvalidInputException e) {
                Log.err.println("Invalid setting for " + v.getSettingId().name);
            }
        }

        R.settings.saveSettings();
    }

    private SettingView createSettingView(Setting setting) {
        if(setting instanceof SettingString) {
            return new StringView((SettingString) setting);

        }else if(setting instanceof SettingInteger) {
            return new IntegerView((SettingInteger) setting);

        }else if(setting instanceof SettingColor) {
            return new ColorView((SettingColor) setting);

        }

        return null;
    }

}
