package at.frysoft.toyide.ui.cpu;

import at.frysoft.toyide.toy.CPU;
import at.frysoft.toyide.toy.CpuEvent;
import at.frysoft.toyide.toy.CpuListener;
import at.frysoft.toyide.toy.Memory;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created on : 31.05.2018
 * Last update: 31.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class CpuPanel extends JPanel implements ComponentListener, CpuListener {

    private InstructionPanel instructionPanel;
    private MemoryPanel registerPanel;
    private MemoryPanel memoryPanel;

    public CpuPanel(CPU cpu) {
        cpu.addCpuListener(this);

        setLayout(new GridBagLayout());
        addComponentListener(this);

        LineBorder border = new LineBorder(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0f;
        gbc.gridx = 0;

        gbc.gridy = 0;
        instructionPanel = new InstructionPanel(cpu);
        instructionPanel.setBorder(new TitledBorder(border, "Instruction"));
        add(instructionPanel, gbc);

        gbc.gridy = 1;
        registerPanel = new MemoryPanel(cpu.getRegister());
        registerPanel.setBorder(new TitledBorder(border, "Register"));
        add(registerPanel, gbc);

        gbc.gridy = 2;
        memoryPanel = new MemoryPanel(cpu.getMemory());
        memoryPanel.setBorder(new TitledBorder(border, "Memory"));
        add(memoryPanel, gbc);

        setVisible(true);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        registerPanel.componentResized();
        memoryPanel.componentResized();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void onInstructionChanged(CpuEvent e) {
        memoryPanel.markValue(e.getPc());
    }

    @Override
    public void onInstructionExecuted(CpuEvent e) {
    }

}
