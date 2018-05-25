package at.frysoft.toyide.ui.texteditor;

import javax.swing.text.Document;
import javax.swing.text.StyleContext;
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

    private Highlighter highlighter;
    private TextEditorDocument document;

    public TextEditorKit() {
        super();
    }

    @Override
    public Document createDefaultDocument() {
        StyleContext styleContext = new StyleContext();
        document = new TextEditorDocument(styleContext);

        highlighter = new Highlighter(document);
        document.setHighlighter(highlighter);

        return document;
    }

    @Override
    public ViewFactory getViewFactory() {
        return new TextEditorViewFactory();
    }

}
