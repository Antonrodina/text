package text.service;

import text.TextResponse;

/**
 * @author antonio
 */
public interface TextService {

    TextResponse getText(int startNumberOfParagraphs, int endNumberOfParagraphs, int minNumberWords, int maxNumberWords);
}
