package text.service;

import text.domain.Text;
import text.source.TextSource;

/**
 * Dummy source; returns some previously specified value.
 *
 * @author antonio
 */
public class DummyTextSource implements TextSource {

    private Text textToReturn;

    @Override
    public Text generateText(int numberParagraphs, int minNumberWordsSentence, int maxNumberWordsSentence) {
        return textToReturn;
    }

    public void setTextToReturn(Text textToReturn) {
        this.textToReturn = textToReturn;
    }
}
