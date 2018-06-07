package at.frysoft.toyide.ui.computer;

import at.frysoft.toyide.computer.memory.IOListener;
import at.frysoft.toyide.computer.memory.Input;
import at.frysoft.toyide.ressources.R;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class InputPanel extends JPanel implements IOListener {

    private Font font;

    private JTextField[] dataValues;

    private LineBorder markedBorder;
    private int markedIndex;

    private Input input;

    public InputPanel(Input input) {
        input.addIOListener(this);

        setLayout(new GridBagLayout());

        font = new Font(R.settings.getString(R.settings.FONT_FAMILY), Font.PLAIN, R.settings.getInt(R.settings.FONT_SIZE));

        Dimension dataSize = new Dimension((int)(font.getSize() * 2.6), font.getSize());
        dataValues = new JTextField[]{ createJTextField("END", dataSize) };

        markedBorder = new LineBorder(Color.RED, 2);
        markedIndex = 0;

        setVisible(true);
    }

    protected void componentResized() {
        removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridheight = 1;
        gbc.ipadx = 5;
        gbc.ipady = 5;

        Dimension dataSize = new Dimension((int)(font.getSize() * 2.6), font.getSize());
        int wordsPerRow = (getWidth() - 20) / (gbc.ipadx + dataSize.width);

        for(int i = 0; i < dataValues.length; ++i) {
            gbc.gridx = 0;
            gbc.gridwidth = 1;

            for(int j = 0; j < wordsPerRow && i < dataValues.length; ++j, ++i) {
                gbc.gridx = 1 + j * 2;
                add(dataValues[i], gbc);
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
    public void onRead(int readValue) {
        dataValues[markedIndex].setBorder(null);
        dataValues[++markedIndex].setBorder(markedBorder);
    }

    @Override
    public void onWrite(int writeValue) {
    }

    @Override
    public void onReset() {
        if(markedIndex == 0)
            return;

        dataValues[markedIndex].setBorder(null);
        dataValues[0].setBorder(markedBorder);
    }

    @Override
    public void onClear() {
        Dimension dataSize = new Dimension((int)(font.getSize() * 2.6), font.getSize());
        dataValues = new JTextField[]{ createJTextField("END", dataSize) };
    }

    @Override
    public void onLoad(int[] data) {
        if(dataValues.length != data.length) {
            dataValues = new JTextField[data.length + 1];

            Dimension dataSize = new Dimension((int)(font.getSize() * 2.6), font.getSize());
            for(int i = 0; i < data.length; ++i)
                dataValues[i] = createJTextField(String.format("%04X", data[i]), dataSize);
            dataValues[data.length] = createJTextField("END", dataSize);

        }else {
            for(int i = 0; i < data.length; ++i)
                dataValues[i].setText(String.format("%04X", data[i]));
        }
    }

}
