package text.repository;

import java.util.Collection;

import text.TextResponse;

/**
 * @author antonio
 */
public class InMemoryTextRepository implements TextRepository {

    private final AddOnlyCircularBuffer<TextResponse> buffer = new AddOnlyCircularBuffer<>(10);

    @Override
    public void addLast(TextResponse textResponse) {
        buffer.add(textResponse);
    }

    @Override
    public Collection<TextResponse> getAll() {
        return buffer.getAll();
    }
}
