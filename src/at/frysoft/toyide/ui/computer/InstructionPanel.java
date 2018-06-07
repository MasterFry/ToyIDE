package at.frysoft.toyide.ui.computer;

import at.frysoft.toyide.ressources.R;
import at.frysoft.toyide.computer.cpu.CPU;
import at.frysoft.toyide.computer.cpu.CpuEvent;
import at.frysoft.toyide.computer.cpu.CpuListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created on : 31.05.2018
 * Last update: 31.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class InstructionPanel extends JPanel implements CpuListener {

    private JTextField instructionCode;
    private JTextField instructionInformation;

    public InstructionPanel(CPU cpu) {
        cpu.addCpuListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridy = 0;

        Font font = new Font(R.settings.getString(R.settings.FONT_FAMILY), Font.PLAIN, R.settings.getInt(R.settings.FONT_SIZE));

        gbc.gridx = 0;
        gbc.weightx = 0.2;
        instructionCode = new JTextField("");
        instructionCode.setFont(font);
        instructionCode.setBorder(null);
        instructionCode.setEditable(false);
        add(instructionCode, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        instructionInformation = new JTextField("...");
        instructionInformation.setFont(font);
        instructionInformation.setBorder(null);
        instructionInformation.setEditable(false);
        add(instructionInformation, gbc);

        setVisible(true);
    }

    @Override
    public void onInstructionChanged(CpuEvent e) {
        instructionCode.setText(String.format("%04X", e.getInstruction().get()));
        instructionInformation.setText(e.getInstruction().getDescription());
    }

    @Override
    public void onInstructionExecuted(CpuEvent e) {
    }

}
