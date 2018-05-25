package at.frysoft.toyide.ui.texteditor;

import javax.swing.text.*;

/**
 * ${FILE_NAME}
 * <p>
 * Created on : 21.05.2018
 * Last update: 21.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class TextEditorViewFactory implements ViewFactory {

    @Override
    public View create(Element elem) {
        String name = elem.getName();

        switch(name) {

            case AbstractDocument.ContentElementName: {
                AttributeSet attributes = elem.getAttributes();

                if(attributes.containsAttribute("ERROR", true))
                    return new JaggedLabelView(elem);

                return new LabelView(elem);
            }

            case AbstractDocument.ParagraphElementName: return new ParagraphView(elem);
            case AbstractDocument.SectionElementName: return new BoxView(elem, View.Y_AXIS);
            case StyleConstants.ComponentElementName: return new ComponentView(elem);
            case StyleConstants.IconElementName: return new IconView(elem);

        }

        return null;
    }

}
