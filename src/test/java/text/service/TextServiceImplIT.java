package text.service;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Rule;
import org.junit.Test;

import io.vertx.core.json.JsonObject;
import text.TextResponse;
import text.repository.InMemoryTextRepository;
import text.repository.TextRepository;
import text.service.analysis.TextAnalyzer;
import text.service.analysis.TextAnalyzerImpl;
import text.source.TextSource;
import text.source.randomtextme.RandomTextMeClient;
import text.source.randomtextme.RandomTextMeResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

/**
 * IT for {@link TextServiceImpl}. Uses the real {@link TextAnalyzer} used in production instead of a dummy, but mocks the remote text source
 *
 * @author antonio
 */
public class TextServiceImplIT {

    private static final String TEST_URL = "http://localhost:8089/api/giberish/p-";

    private final TextSource textSource = new RandomTextMeClient(TEST_URL);
    private final TextAnalyzer textAnalyzer = new TextAnalyzerImpl();
    private final TextRepository textRepository = new InMemoryTextRepository();
    private final TextServiceImpl textService = new TextServiceImpl(textSource, textAnalyzer, textRepository);

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void test1Paragraph1Word() {
        // Given
        stubFor(get(urlEqualTo("/api/giberish/p-1/1-1"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(JsonObject.mapFrom(new RandomTextMeResponse("<p>Spluttered.</p>\r")).encode())));

        // When
        TextResponse actualText = textService.getText(1, 1, 1, 1);

        // Then
        assertEquals("Spluttered", actualText.getMostFrequentWord());
        assertEquals(1, actualText.getAvgParagraphSize(), 0);
    }

    @Test
    public void test1Paragraph2Words() {
        // Given
        stubFor(get(urlEqualTo("/api/giberish/p-1/2-2"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(JsonObject.mapFrom(new RandomTextMeResponse("<p>words words.</p>\r")).encode())));

        // When
        TextResponse actualText = textService.getText(1, 1, 2, 2);

        // Then
        assertEquals("words", actualText.getMostFrequentWord());
        assertEquals(2, actualText.getAvgParagraphSize(), 0);
    }

    @Test
    public void test2Paragraphs1Word() {
        // Given
        stubFor(get(urlEqualTo("/api/giberish/p-2/1-1"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(JsonObject.mapFrom(new RandomTextMeResponse("<p>Amusedly.</p>\r<p>Amusedly.</p>\r")).encode())));

        // When
        TextResponse actualText = textService.getText(2, 2, 1, 1);

        // Then
        assertEquals("Amusedly", actualText.getMostFrequentWord());
        assertEquals(1, actualText.getAvgParagraphSize(), 0);
    }

    @Test
    public void test2Paragraphs2Words() {
        // Given
        stubFor(get(urlEqualTo("/api/giberish/p-2/2-2"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(JsonObject.mapFrom(new RandomTextMeResponse("<p>Amusedly sun.</p>\r<p>Amusedly moon.</p>\r")).encode())));

        // When
        TextResponse actualText = textService.getText(2, 2, 2, 2);

        // Then
        assertEquals("Amusedly", actualText.getMostFrequentWord());
        assertEquals(2, actualText.getAvgParagraphSize(), 0);
    }

}
