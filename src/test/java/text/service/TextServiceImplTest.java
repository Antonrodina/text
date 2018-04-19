package text.service;

import org.junit.Test;

import java.util.Arrays;

import text.TextResponse;
import text.domain.Text;
import text.repository.InMemoryTextRepository;
import text.repository.TextRepository;
import text.service.analysis.DummyTextAnalyzer;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link TextServiceImpl}. Very simple, since this class simply performs a basic orchestration to other components.
 * We use our dummy implementation of dependencies a la Mockito
 *
 * @author antonio
 */
public class TextServiceImplTest {

    private final DummyTextSource textSource = new DummyTextSource();
    private final DummyTextAnalyzer textAnalyzer = new DummyTextAnalyzer();
    private final TextRepository textRepository = new InMemoryTextRepository();
    private final TextServiceImpl textService = new TextServiceImpl(textSource, textAnalyzer, textRepository);

    @Test
    public void getText() {
        // Given
        textSource.setTextToReturn(new Text(Arrays.asList("irrelevant", "content")));
        textAnalyzer.setTextResponseToReturn(new TextResponse("hyper", 2, 3.33, 5));

        // When
        TextResponse actualText = textService.getText(1, 2, 5, 6);

        // Then
        assertEquals("hyper", actualText.getMostFrequentWord());
        assertEquals(2, actualText.getAvgParagraphSize(), 0);
    }

}