package text.handler;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import text.TextResponse;
import text.service.TextService;

/**
 * @author antonio
 */
public class TextHandler implements Handler<RoutingContext> {

    private final TextService textService;

    public TextHandler(TextService textService) {
        this.textService = textService;
    }

    @Override
    public void handle(RoutingContext context) {
        String startNumberOfParagraphsAsString = context.request().getParam("p_start");
        String endNumberOfParagraphsString = context.request().getParam("p_end");
        String minNumberWordsString = context.request().getParam("w_count_min");
        String maxNumberWordsString = context.request().getParam("w_count_max");
        validateParamsArePresent(startNumberOfParagraphsAsString, endNumberOfParagraphsString, minNumberWordsString, maxNumberWordsString);
        validateParamsAreIntegers(startNumberOfParagraphsAsString, endNumberOfParagraphsString, minNumberWordsString, maxNumberWordsString);

        int startNumberOfParagraphs = Integer.valueOf(startNumberOfParagraphsAsString);
        int endNumberOfParagraphs = Integer.valueOf(endNumberOfParagraphsString);
        int minNumberWords = Integer.valueOf(minNumberWordsString);
        int maxNumberWords = Integer.valueOf(maxNumberWordsString);
        validateParamValuesAreValid(startNumberOfParagraphs, endNumberOfParagraphs, minNumberWords, maxNumberWords);

        TextResponse textResponse = textService.getText(startNumberOfParagraphs, endNumberOfParagraphs, minNumberWords, maxNumberWords);
        context.response().putHeader("content-type", MediaType.APPLICATION_JSON).end(Json.encode(textResponse));
    }

    void validateParamsArePresent(String startNumberOfParagraphsAsString, String endNumberOfParagraphsString, String minNumberWordsString, String maxNumberWordsString) {
        validateNotNull(startNumberOfParagraphsAsString, "p_start");
        validateNotNull(endNumberOfParagraphsString, "p_end");
        validateNotNull(minNumberWordsString, "w_count_min");
        validateNotNull(maxNumberWordsString, "w_count_max");
    }

    private void validateNotNull(String param, String paramName) {
        if (param == null) {
            throw new BadRequestException("Param " + paramName + " was null");
        }
    }

    void validateParamsAreIntegers(String startNumberOfParagraphsAsString, String endNumberOfParagraphsString, String minNumberWordsString, String maxNumberWordsString) {
        validateIsInteger(startNumberOfParagraphsAsString, "p_start");
        validateIsInteger(endNumberOfParagraphsString, "p_end");
        validateIsInteger(minNumberWordsString, "w_count_min");
        validateIsInteger(maxNumberWordsString, "w_count_max");
    }

    private void validateIsInteger(String paramValue, String paramName) {
        try {
            Integer.valueOf(paramValue);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Parameter " + paramName + " is not a number: " + paramValue);
        }
    }

    void validateParamValuesAreValid(int startNumberOfParagraphs, int endNumberOfParagraphs, int minNumberWords, int maxNumberWords) {
        validateIsPositive(startNumberOfParagraphs, "p_start");
        validateIsPositive(endNumberOfParagraphs, "p_end");
        validateIsPositive(minNumberWords, "w_count_min");
        validateIsPositive(maxNumberWords, "w_count_max");
        validateNumbersAreNotDecreasing(startNumberOfParagraphs, endNumberOfParagraphs);
        validateNumbersAreNotDecreasing(minNumberWords, maxNumberWords);
    }

    /**
     * Validates that the first given integer is not higher than the second
     */
    private void validateNumbersAreNotDecreasing(int i1, int i2) {
        if (i1 > i2) {
            throw new BadRequestException("start parameters can't be higher than their pair");
        }
    }

    private void validateIsPositive(int i, String paramName) {
        if (i <= 0) {
            throw new BadRequestException("Parameter " + paramName + " is not a positive: " + i);
        }
    }
}
