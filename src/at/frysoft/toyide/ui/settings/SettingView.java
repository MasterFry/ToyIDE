package at.frysoft.toyide.ui.settings;

import javax.swing.*;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public abstract class SettingView extends JPanel {

    public abstract String getSettingName();

    public abstract Object getSettingValue() throws InvalidInputException;

}
