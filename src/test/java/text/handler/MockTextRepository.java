package text.handler;

import java.util.ArrayList;
import java.util.Collection;

import text.TextResponse;
import text.repository.TextRepository;

/**
 * Implementation for which we can set the data we need
 *
 * @author antonio
 */
// TODO delete
public class MockTextRepository implements TextRepository {

    private Collection<TextResponse> data = new ArrayList<>();

    @Override
    public void addLast(TextResponse textResponse) {
        data.add(textResponse);
    }

    @Override
    public Collection<TextResponse> getAll() {
        return data;
    }
}
