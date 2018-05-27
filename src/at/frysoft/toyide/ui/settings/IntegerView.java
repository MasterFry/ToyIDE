package at.frysoft.toyide.ui.settings;

import at.frysoft.toyide.settings.SettingInteger;

import javax.swing.*;
import java.awt.*;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class IntegerView extends SettingView {

    public final SettingInteger setting;

    private JTextField textField;

    public IntegerView(SettingInteger setting) {
        this.setting = setting;

        setLayout(new GridLayout(1, 2));

        add(new JLabel(setting.name));

        textField = new JTextField("" + setting.getValue());
        add(textField);

        setVisible(true);
    }

    @Override
    public String getSettingName() {
        return setting.name;
    }

    @Override
    public Object getSettingValue() throws InvalidInputException {
        try {
            return Integer.parseInt(textField.getText());
        } catch(NumberFormatException ex) {
            throw new InvalidInputException(setting.name + " must be an Integer Value");
        }
    }

}
