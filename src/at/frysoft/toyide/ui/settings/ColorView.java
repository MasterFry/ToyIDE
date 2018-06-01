package at.frysoft.toyide.ui.settings;

import at.frysoft.toyide.ressources.settings.SettingColor;
import at.frysoft.toyide.ressources.settings.SettingId;
import at.frysoft.toyide.ressources.settings.SettingInteger;

import javax.swing.*;
import java.awt.*;

/**
 * Created on : 01.06.2018
 * Last update: 01.06.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class ColorView extends SettingView {

    public final SettingColor setting;

    private JTextField textField;

    public ColorView(SettingColor setting) {
        this.setting = setting;

        setLayout(new GridLayout(1, 2));

        add(new JLabel(setting.id.name));

        textField = new JTextField(String.format("#%06X", setting.getValue().getRGB() & 0xFFFFFF));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        add(textField);

        setVisible(true);
    }

    @Override
    public SettingId getSettingId() {
        return setting.id;
    }

    @Override
    public Object getSettingValue() throws InvalidInputException {
        try {
            String text = textField.getText();

            if(text.length() != 7 || text.charAt(0) != '#')
                throw new InvalidInputException(setting.id.name + " format is: #XXXXXX");

            return (Integer.parseInt(text.substring(1)) | 0xFF000000);

        } catch(NumberFormatException ex) {
            throw new InvalidInputException(setting.id.name + " must be an Integer Value");
        }
    }

}
