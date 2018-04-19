package text.source.randomtextme;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import text.domain.Text;

import static org.junit.Assert.assertEquals;

/**
 * @author antonio
 */
public class RandomTextMeClientTest {

    private final RandomTextMeClient randomTextMeClient = new RandomTextMeClient(null);

    @Test
    public void parse_oneParagraphOneWord() {
        // Given
        String testString = "<p>As.</p>\r";

        // When
        Text actualText = randomTextMeClient.parse(testString);

        // Then
        Collection<String> expectedParagraphs = Collections.singletonList("As");
        assertEquals(expectedParagraphs, actualText.getParagraphs());
    }

    @Test
    public void parse_oneParagraphMultipleWords() {
        // Given
        String testString = "<p>By gaudily warthog consciously.</p>\r<p>";

        // When
        Text actualText = randomTextMeClient.parse(testString);

        // Then
        Collection<String> expectedParagraphs = Collections.singletonList("By gaudily warthog consciously");
        assertEquals(expectedParagraphs, actualText.getParagraphs());
    }

    @Test
    public void parse_twoParagraphs() {
        // Given
        String testString = "<p>Hello jeez goodness.</p>\r<p>Genial ecstatically some.</p>\r";

        // When
        Text actualText = randomTextMeClient.parse(testString);

        // Then
        Collection<String> expectedParagraphs = Arrays.asList("Hello jeez goodness", "Genial ecstatically some");
        assertEquals(expectedParagraphs, actualText.getParagraphs());
    }
}