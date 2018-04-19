package text.service;

import java.util.ArrayList;
import java.util.Collection;

import text.TextResponse;
import text.repository.TextRepository;
import text.service.analysis.TextAnalyzer;
import text.source.TextSource;
import text.domain.Text;

/**
 * @author antonio
 */
public class TextServiceImpl implements TextService {

    private final TextSource textSource;
    private final TextAnalyzer textAnalyzer;
    private final TextRepository textRepository;

    public TextServiceImpl(TextSource textSource, TextAnalyzer textAnalyzer, TextRepository textRepository) {
        this.textSource = textSource;
        this.textAnalyzer = textAnalyzer;
        this.textRepository = textRepository;
    }

    @Override
    public TextResponse getText(int startNumberOfParagraphs, int endNumberOfParagraphs, int minNumberWords, int maxNumberWords) {
        Collection<String> allParagraphs = new ArrayList<>();
        for (int i = startNumberOfParagraphs; i <= endNumberOfParagraphs; i++) {
            Text text = textSource.generateText(i, minNumberWords, maxNumberWords);
            allParagraphs.addAll(text.getParagraphs());
        }
        TextResponse textResponse = textAnalyzer.process(allParagraphs);
        textRepository.addLast(textResponse);
        return textResponse;
    }
}
