package org.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.Scanner;

class GreetVerticle extends AbstractVerticle {
    String name;

    public GreetVerticle(String name){
        this.name = name;
    }

    public GreetVerticle() {}

    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer("greet").handler(event ->
                event.reply("Hello "+ event.body() + " from " + name));
    }

    public static void main(String[] args) {

        final String name = new Scanner(System.in).nextLine();

        Vertx.clusteredVertx(new VertxOptions(), (event ->
                event.result().deployVerticle(new GreetVerticle(name))));
    }

}
