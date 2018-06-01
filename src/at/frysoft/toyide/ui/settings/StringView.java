package at.frysoft.toyide.ui.settings;

import at.frysoft.toyide.ressources.settings.SettingId;
import at.frysoft.toyide.ressources.settings.SettingString;

import javax.swing.*;
import java.awt.*;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class StringView extends SettingView {

    public final SettingString setting;

    private JTextField textField;

    public StringView(SettingString setting) {
        this.setting = setting;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 1.0f;

        gbc.gridx = 0;
        gbc.gridwidth = 1;
        add(new JLabel(setting.id.name), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        textField = new JTextField(setting.getValue());
        textField.setHorizontalAlignment(JTextField.RIGHT);
        add(textField, gbc);

        setVisible(true);
    }

    @Override
    public SettingId getSettingId() {
        return setting.id;
    }

    @Override
    public Object getSettingValue() {
        return textField.getText();
    }

}
