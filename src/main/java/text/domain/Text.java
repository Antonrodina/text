package text.domain;

import java.util.Collection;

/**
 * Represents text as a collection of paragraphs
 *
 * Immutable
 *
 * @author antonio
 */
public class Text {

    private final Collection<String> paragraphs;

    public Text(Collection<String> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public Collection<String> getParagraphs() {
        return paragraphs;
    }
}
