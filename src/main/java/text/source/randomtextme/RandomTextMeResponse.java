package text.source.randomtextme;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author antonio
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RandomTextMeResponse {

    @JsonProperty("text_out")
    private String text;

    @JsonCreator
    public RandomTextMeResponse(@JsonProperty("text_out") String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
