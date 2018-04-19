package text.handler;

import org.junit.Test;

import javax.ws.rs.BadRequestException;

/**
 * @author antonio
 */
public class TextHandlerTest {

    private final TextHandler textHandler = new TextHandler(null);

    @Test(expected = BadRequestException.class)
    public void validateArguments_givenNotPresent_throwsException() {
        // When
        textHandler.validateParamsArePresent(null, "1", "2", "3");
    }

    @Test(expected = BadRequestException.class)
    public void validateArguments_givenNotNumber_throwsException() {
        // When
        textHandler.validateParamsAreIntegers("a", "1", "2", "3");
    }

    @Test
    public void validateArguments_givenAllNumbers_pass() {
        // When
        textHandler.validateParamsAreIntegers("1", "1", "2", "3");
    }

    @Test(expected = BadRequestException.class)
    public void validateArguments_givenNotPositive_throwsException() {
        // When
        textHandler.validateParamValuesAreValid(0, 1, 1, 4);
    }

    @Test(expected = BadRequestException.class)
    public void validateArguments_givenStartParagraphHigherThanEnd_throwsException() {
        // When
        textHandler.validateParamValuesAreValid(2, 1, 1, 4);
    }

    @Test
    public void validateArguments_givenAllValid_pass() {
        // When
        textHandler.validateParamValuesAreValid(1, 1, 1, 4);
    }
}