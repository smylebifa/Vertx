package org.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

public class Main extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(request ->{
            HttpServerResponse response = request.response();
            response.putHeader("content-type","text/plain");

            vertx.eventBus().send("greet", "world", (r) -> {
                if (r.succeeded()){
                    response.end((String) r.result().body());
                }   else {
                    response.end(r.cause().toString());
                }
            });
        });
        server.listen(8080);
    }

    public static void main(String[] args) {
        Vertx.clusteredVertx(new VertxOptions(), event -> event.result().deployVerticle(new Main()));
    }
}

