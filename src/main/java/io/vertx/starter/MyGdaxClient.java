package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class MyGdaxClient extends AbstractVerticle {

	private final Logger logger = LoggerFactory.getLogger(MyGdaxClient.class);
    @Override
    public void start() {
    	WebsocketClient wsc = new WebsocketClient();
    	wsc.work(vertx);
    }

}

