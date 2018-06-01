package at.frysoft.toyide.ui.cpu;

import at.frysoft.toyide.ressources.R;
import at.frysoft.toyide.ressources.settings.Settings;
import at.frysoft.toyide.toy.Memory;
import at.frysoft.toyide.toy.MemoryEvent;
import at.frysoft.toyide.toy.MemoryListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created on : 31.05.2018
 * Last update: 31.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class MemoryPanel extends JPanel implements MemoryListener {

    private Font font;

    private JTextField[] dataValues;

    private Color defaultColor;
    private Color highlightColor;
    private JTextField highlighted;

    private LineBorder markedBorder;
    private JTextField marked;

    public MemoryPanel(Memory memory) {
        memory.addMemoryListener(this);

        setLayout(new GridBagLayout());

        font = new Font(R.settings.getString(R.settings.FONT_FAMILY), Font.PLAIN, R.settings.getInt(R.settings.FONT_SIZE));
        dataValues = new JTextField[memory.size()];
        highlighted = null;
        marked = null;

        JTextField tf;
        Dimension dataSize = new Dimension((int)(font.getSize() * 2.6), font.getSize());

        for(int i = 0; i < dataValues.length; ++i) {
            tf = new JTextField(String.format("%04X", memory.read(i)));
            tf.setFont(font);
            tf.setPreferredSize(dataSize);
            tf.setBorder(null);
            tf.setEditable(false);
            dataValues[i] = tf;
        }

        if(dataValues.length != 0)
            defaultColor = dataValues[0].getBackground();
        highlightColor = new Color(178, 255, 89);

        markedBorder = new LineBorder(Color.RED, 2);

        setVisible(true);
    }

    protected void componentResized() {
        removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridheight = 1;
        gbc.ipadx = 5;
        gbc.ipady = 5;

        Dimension addrSize = new Dimension((int)(font.getSize() * 2.0), font.getSize());
        Dimension dataSize = new Dimension((int)(font.getSize() * 2.6), font.getSize());

        int wordsPerRow = (getWidth() - 20 - addrSize.width) / (gbc.ipadx + dataSize.width);
        if(wordsPerRow >= 16) wordsPerRow = 16;
        else if(wordsPerRow >= 8) wordsPerRow = 8;
        else if(wordsPerRow >= 4) wordsPerRow = 4;
        else if(wordsPerRow >= 2) wordsPerRow = 2;
        else if(wordsPerRow <= 0) wordsPerRow = 1;

        int addr = 0x00;
        gbc.gridy = 0;

        while(addr < dataValues.length) {

            gbc.gridx = 0;
            gbc.gridwidth = 1;
            add(createJTextField(String.format("%02X:", addr), addrSize), gbc);

            gbc.gridwidth = 2;
            for(int i = 0; i < wordsPerRow; ++i) {
                gbc.gridx = 1 + i * 2;
                add(dataValues[addr], gbc);
                ++addr;

                if(addr >= dataValues.length)
                    break;
            }

            ++gbc.gridy;
        }
    }

    private JTextField createJTextField(String text, Dimension minSize) {
        JTextField tf = new JTextField(text);
        tf.setFont(font);
        tf.setPreferredSize(minSize);
        tf.setBorder(null);
        tf.setEditable(false);
        return tf;
    }

    @Override
    public void onMemoryChanged(MemoryEvent e) {
        if(highlighted != null)
            highlighted.setBackground(defaultColor);

        highlighted = dataValues[e.getAddress()];
        highlighted.setText(String.format("%04X", e.getValue()));
        highlighted.setBackground(highlightColor);
    }

    @Override
    public void onMemoryReset() {
        for(JTextField tf : dataValues) {
            tf.setText("0000");
        }
    }

    @Override
    public void onMemoryImageLoaded(MemoryEvent e) {
        for(int i = 0; i < dataValues.length; ++i) {
            dataValues[i].setText(String.format("%04X", e.getMemory().read(i)));
        }
    }

    public void markValue(int address) {
        if(address < 0 || address >= dataValues.length)
            throw new IllegalArgumentException("Address out of bounds: " + address);

        if(marked != null)
            marked.setBorder(null);

        marked = dataValues[address];
        marked.setBorder(markedBorder);
    }

}
