package text.handler;

import javax.ws.rs.BadRequestException;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * This handler takes care of converting known user errors to 400s
 *
 * @author antonio
 */
public class ExceptionHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext context) {
        Throwable failure = context.failure();
        if (failure instanceof BadRequestException) {
            context.response().setStatusCode(400).setStatusMessage(failure.getMessage()).end();
        } else {
            context.response().setStatusCode(500).setStatusMessage("Something went wrong").end();
        }
    }
}
