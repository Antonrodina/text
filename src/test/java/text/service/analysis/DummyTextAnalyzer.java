package text.service.analysis;

import java.util.Collection;

import text.TextResponse;

/**
 * @author antonio
 */
public class DummyTextAnalyzer implements TextAnalyzer {

    private TextResponse textResponseToReturn;

    @Override
    public TextResponse process(Collection<String> paragraphs) {
        return textResponseToReturn;
    }

    public void setTextResponseToReturn(TextResponse textResponseToReturn) {
        this.textResponseToReturn = textResponseToReturn;
    }
}
