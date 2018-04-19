package text.util;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import text.TextResponse;

/**
 * A matcher for {@link TextResponse} that doesn't consider that time fields, only the most frequent word and average paragraph size
 *
 * @author antonio
 */
public class ExcludeTimesTextResponseMatcher extends BaseMatcher<TextResponse> {

    private final TextResponse expectedValue;

    public ExcludeTimesTextResponseMatcher(TextResponse expectedValue) {
        this.expectedValue = expectedValue;
    }

    @Override
    public boolean matches(Object item) {
        if (!(item instanceof TextResponse)) {
            return false;
        }

        TextResponse that = (TextResponse) item;

        if (!expectedValue.getMostFrequentWord().equals(that.getMostFrequentWord())) {
            return false;
        }
        if (expectedValue.getAvgParagraphSize() != that.getAvgParagraphSize()) {
            return false;
        }

        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue("{" +
                "mostFrequentWord='" + expectedValue.getMostFrequentWord() + '\'' +
                ", avgParagraphSize=" + expectedValue.getAvgParagraphSize() +
                '}');
    }
}
