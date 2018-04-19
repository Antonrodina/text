package text.service.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import text.TextResponse;

/**
 * @author antonio
 */
public class TextAnalyzerImpl implements TextAnalyzer {

    @Override
    public TextResponse process(Collection<String> paragraphs) {
        long startProcessingTime = System.currentTimeMillis();
        Collection<String> words = splitToWords(paragraphs);
        String mostFrequentWord = getMostFrequentWord(words);
        long totalProcessingTime = System.currentTimeMillis() - startProcessingTime;
        double avgParagraphSize = (double) words.size() / paragraphs.size();
        double avgParagraphProcessingTime = (double) totalProcessingTime / paragraphs.size();
        return new TextResponse(mostFrequentWord, avgParagraphSize, avgParagraphProcessingTime, totalProcessingTime);
    }

    String getMostFrequentWord(Collection<String> words) {
        Set<Map.Entry<String, Integer>> set = words
                .stream()
                .collect(Collectors.toMap(word -> word, word -> 1, Integer::sum))
                .entrySet();
        return Collections.max(set, (a, b) -> Integer.compare(a.getValue(), b.getValue())).getKey();
    }

    List<String> splitToWords(Collection<String> paragraphs) {
        List<String> allWords = new ArrayList<>();
        for (String paragraph : paragraphs) {
            List<String> words = Arrays.asList(paragraph.split(" "));
            allWords.addAll(words);
        }
        return allWords;
    }
}
