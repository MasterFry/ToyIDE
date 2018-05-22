package at.frysoft.toyide.ui.texteditor;

import javax.swing.text.Element;
import javax.swing.text.LabelView;
import java.awt.*;

/**
 * ${FILE_NAME}
 * <p>
 * Created on : 21.05.2018
 * Last update: 21.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class JaggedLabelView extends LabelView {

    public JaggedLabelView(Element elem) {
        super(elem);
    }

    @Override
    public void paint(Graphics g, Shape allocation) {
        super.paint(g, allocation);
        paintJaggedLine(g, allocation);
    }

    public void paintJaggedLine(Graphics g, Shape a) {
        int y = (int)(a.getBounds().getY() + a.getBounds().getHeight());
        int x1 = (int)a.getBounds().getX();
        int x2 = (int)(a.getBounds().getX() + a.getBounds().getWidth());

        Color prevColor = g.getColor();
        g.setColor(Color.RED);

        boolean lineUp = true;
        for(int xi = x1; xi < x2 - 2; xi += 2) {
            if(lineUp) {
                g.drawLine(xi, y - 1, xi + 2, y - 2);
                lineUp = false;
            }else {
                g.drawLine(xi, y - 2, xi + 2, y - 1);
                //g.drawLine(xi + 2, y - 2, xi + 4, y - 1);
                lineUp = true;
            }
        }

        g.setColor(prevColor);
    }

}
