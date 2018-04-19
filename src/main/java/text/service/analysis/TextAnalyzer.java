package text.service.analysis;

import java.util.Collection;

import text.TextResponse;

/**
 * @author antonio
 */
public interface TextAnalyzer {

    TextResponse process(Collection<String> paragraphs);
}
