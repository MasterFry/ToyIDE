package at.frysoft.toyide.ui.texteditor;

import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

/**
 * ${FILE_NAME}
 * <p>
 * Created on : 21.05.2018
 * Last update: 21.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class TextEditorKit extends StyledEditorKit {

    public TextEditorKit() {
        super();
    }

    @Override
    public ViewFactory getViewFactory() {
        return new TextEditorViewFactory();
    }

}
