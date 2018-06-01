package at.frysoft.toyide.ui.settings;

import at.frysoft.toyide.ressources.settings.SettingId;

import javax.swing.*;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public abstract class SettingView extends JPanel {

    public abstract SettingId getSettingId();

    public abstract Object getSettingValue() throws InvalidInputException;

}
