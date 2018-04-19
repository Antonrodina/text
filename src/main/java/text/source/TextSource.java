package text.source;

import text.domain.Text;

/**
 * @author antonio
 */
public interface TextSource {

    Text generateText(int numberParagraphs, int minNumberWordsSentence, int maxNumberWordsSentence);
}
