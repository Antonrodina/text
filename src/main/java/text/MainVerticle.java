package text;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import text.handler.ExceptionHandler;
import text.handler.HistoryHandler;
import text.handler.TextHandler;
import text.repository.InMemoryTextRepository;
import text.service.TextServiceImpl;
import text.service.analysis.TextAnalyzerImpl;
import text.source.randomtextme.RandomTextMeClient;

public class MainVerticle extends AbstractVerticle {

    private static final int DEFAULT_PORT = 8080;
    private static final String DEFAULT_RANDOM_TEXT_URI = "http://www.randomtext.me/api/giberish/p-";

    @Override
    public void start() {
        String randomTextBaseUrl = config().getString("randomTextUrl", DEFAULT_RANDOM_TEXT_URI);
        Integer port = config().getInteger("http.port", DEFAULT_PORT);
        Router router = Router.router(vertx);
        router.get("/ping").handler(new Handler<RoutingContext>() {
            @Override
            public void handle(RoutingContext event) {
                event.response().end();
            }
        });
        InMemoryTextRepository textRepository = new InMemoryTextRepository();
        router.get("/betvictor/text")
                .handler(new TextHandler(new TextServiceImpl(new RandomTextMeClient(randomTextBaseUrl), new TextAnalyzerImpl(), textRepository)))
                .failureHandler(new ExceptionHandler());
        router.get("/betvictor/history").handler(new HistoryHandler(textRepository))
                .failureHandler(new ExceptionHandler());

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(port, "localhost");
    }

}
