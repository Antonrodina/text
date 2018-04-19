package text;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import text.source.randomtextme.RandomTextMeResponse;
import text.util.ExcludeTimesTextResponseMatcher;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(VertxUnitRunner.class)
public class MainVerticleTest {

    private static final String WIREMOCK_URL = "http://localhost:8089/api/giberish/p-";

    private final Client client = ClientBuilder.newClient();

    private Vertx vertx;
    private int port;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Before
    public void setUp(TestContext context) throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            port = socket.getLocalPort();
        }
        DeploymentOptions options = new DeploymentOptions()
                .setConfig(new JsonObject().put("http.port", port).put("randomTextUrl", WIREMOCK_URL));

        vertx = Vertx.vertx();
        vertx.deployVerticle(MainVerticle.class.getName(), options, context.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void serverIsStarted() {
        // When
        Response response = client.target("http://localhost:" + port + "/ping").request(MediaType.APPLICATION_JSON).get();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void badRequestIsConvertedTo400() {
        // When
        Response response = client.target("http://localhost:" + port + "/betvictor/text").request(MediaType.APPLICATION_JSON).get();
        assertEquals(400, response.getStatus());
    }

    @Test
    public void text_1Paragraph() {
        // Given
        stubFor(get(urlEqualTo("/api/giberish/p-1/1-1"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(JsonObject.mapFrom(new RandomTextMeResponse("<p>you me you.</p>\r")).encode())));

        // When
        Response response = client.target("http://localhost:" + port + "/betvictor/text?p_start=1&p_end=1&w_count_min=1&w_count_max=1")
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, response.getStatus());
        TextResponse actualResponse = response.readEntity(TextResponse.class);
        assertEquals("you", actualResponse.getMostFrequentWord());
        assertEquals(3.0, actualResponse.getAvgParagraphSize(), 0);
    }

    @Test
    public void history_getsLast10Elements() {
        // Given
        stubFor(get(urlEqualTo("/api/giberish/p-1/1-1"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(JsonObject.mapFrom(new RandomTextMeResponse("<p>you me you.</p>\r")).encode())));
        stubFor(get(urlEqualTo("/api/giberish/p-2/1-1"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(JsonObject.mapFrom(new RandomTextMeResponse("<p>you.</p><p>sun sun moon.</p>\n")).encode())));
        stubFor(get(urlEqualTo("/api/giberish/p-2/100-100"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(JsonObject.mapFrom(new RandomTextMeResponse("<p>this should not be in history.</p>\r")).encode())));

        // When
        // This first request will be erased from the history by the subsequent 10 requests
        client.target("http://localhost:" + port + "/betvictor/text?p_start=2&p_end=2&w_count_min=100&w_count_max=100")
                .request(MediaType.APPLICATION_JSON)
                .get();
        // Request 9 times the first endpoint, then 1 time the second
        for (int i = 0; i < 9; i++) {
            client.target("http://localhost:" + port + "/betvictor/text?p_start=1&p_end=1&w_count_min=1&w_count_max=1")
                    .request(MediaType.APPLICATION_JSON)
                    .get();
        }
        client.target("http://localhost:" + port + "/betvictor/text?p_start=2&p_end=2&w_count_min=1&w_count_max=1")
                .request(MediaType.APPLICATION_JSON)
                .get();

        Response response = client.target("http://localhost:" + port + "/betvictor/history").request(MediaType.APPLICATION_JSON).get();
        assertEquals(200, response.getStatus());
        List<TextResponse> actualResponses = response.readEntity(new GenericType<List<TextResponse>>() {
        });
        assertEquals(10, actualResponses.size());
        // We expect this to be 9 times in the response list
        TextResponse expectedResponse1 = new TextResponse("you", 3, 0, 0);
        // And this 1 time
        TextResponse expectedResponse2 = new TextResponse("sun", 2, 0, 0);

        Collection<Matcher<? super TextResponse>> textResponseMatchers = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            textResponseMatchers.add(new ExcludeTimesTextResponseMatcher(expectedResponse1));
        }
        textResponseMatchers.add(new ExcludeTimesTextResponseMatcher(expectedResponse2));
        assertThat(actualResponses, new IsIterableContainingInAnyOrder<>(textResponseMatchers));
    }

}