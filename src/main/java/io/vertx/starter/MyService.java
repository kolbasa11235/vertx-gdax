package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class MyService
{

    public static void main(String[] args) {
      Vertx vertx = Vertx.vertx();

      // Deploy MyGdaxClient and MyHttpServer

      vertx.deployVerticle(new MyGdaxClient());
      vertx.deployVerticle(new MyEventBus());
      vertx.deployVerticle(new MyHttpServer());
    }
}