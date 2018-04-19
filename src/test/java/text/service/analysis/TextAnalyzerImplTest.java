package text.service.analysis;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import text.TextResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author antonio
 */
public class TextAnalyzerImplTest {

    private final TextAnalyzerImpl analyzer = new TextAnalyzerImpl();

    @Test
    public void getMostFrequentWord_oneWordIsMostFrequent() {
        // Given
        Collection<String> paragraphs = Arrays.asList("I", "I", "am", "a", "big", "egocentric");

        // When
        String actualMostFrequentWord = analyzer.getMostFrequentWord(paragraphs);

        // Then
        assertEquals("I", actualMostFrequentWord);
    }

    @Test
    public void getMostFrequentWord_twoWordsTie() {
        // Given
        Collection<String> paragraphs = Arrays.asList("I", "I", "am", "a", "big", "big", "egocentric");

        // When
        String actualMostFrequentWord = analyzer.getMostFrequentWord(paragraphs);

        // Then
        assertTrue(actualMostFrequentWord.equals("big") || actualMostFrequentWord.equals("I"));
    }

    @Test
    public void splitToWords() {
        // Given
        Collection<String> paragraphs = Arrays.asList("Hello jeez goodness", "Genial ecstatically some");

        // When
        List<String> actualWords = analyzer.splitToWords(paragraphs);

        // Then
        List<String> expectedWords = Arrays.asList("Hello", "jeez", "goodness", "Genial", "ecstatically", "some");
        assertEquals(expectedWords, actualWords);
    }

    @Test
    public void process() {
        // Given
        Collection<String> paragraphs = Arrays.asList("word", "two words", "this para graph has six words");

        // When
        TextResponse actualText = analyzer.process(paragraphs);

        // Then
        assertEquals("words", actualText.getMostFrequentWord());
        assertEquals(3, actualText.getAvgParagraphSize(), 0);
    }

}