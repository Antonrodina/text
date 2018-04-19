package text.repository;

import java.util.Collection;

import text.TextResponse;

/**
 * @author antonio
 */
public interface TextRepository {

    void addLast(TextResponse textResponse);

    Collection<TextResponse> getAll();
}
