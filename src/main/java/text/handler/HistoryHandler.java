package text.handler;

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import text.TextResponse;
import text.repository.TextRepository;

/**
 * @author antonio
 */
public class HistoryHandler implements Handler<RoutingContext> {

    private final TextRepository textRepository;

    public HistoryHandler(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    @Override
    public void handle(RoutingContext context) {
        Collection<TextResponse> responses = textRepository.getAll();
        context.response().putHeader("content-type", MediaType.APPLICATION_JSON).end(Json.encode(responses));
    }
}
