package text.source.randomtextme;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import text.domain.Text;
import text.source.TextSource;

/**
 * @author antonio
 */
public class RandomTextMeClient implements TextSource {

    private static final Client CLIENT = ClientBuilder.newClient();

    private final String baseUrl;

    public RandomTextMeClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Builds the full path using the given params and subsequently makes a get request. The full URL should be as:
     *
     * http://www.randomtext.me/api/giberish/p-3/2-5
     *
     * We don't validate that the paramers don't exceed the max of randomtext.me, because it doesn't throw errors
     */
    @Override
    public Text generateText(int numberParagraphs, int minNumberWordsSentence, int maxNumberWordsSentence) {
        String fullUrl = baseUrl + numberParagraphs + "/" + minNumberWordsSentence + "-" + maxNumberWordsSentence;
        Response response = CLIENT.target(fullUrl).request(MediaType.APPLICATION_JSON).get();
        RandomTextMeResponse randomTextMeResponse = response.readEntity(RandomTextMeResponse.class);
        return parse(randomTextMeResponse.getText());
    }

    /**
     * Assumes that there will be at least one paragraph with one word
     */
    Text parse(String htmlDelimitedParagraphs) {
        // Remove the first <p>, otherwise we have to clean it from the list afterwards
        htmlDelimitedParagraphs = htmlDelimitedParagraphs.substring(3);
        List<String> paragraphs = Arrays.asList(htmlDelimitedParagraphs.split("(<p>)|(\\.</p>\\r<p>)|(\\.</p>\\r)|(\\.</p>\\r)"));
        return new Text(paragraphs);
    }

}
