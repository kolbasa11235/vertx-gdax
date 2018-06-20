package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class MyEventBus extends AbstractVerticle 
{
	private final Logger logger = LoggerFactory.getLogger(MyHttpServer.class);
    @Override
    public void start() {
            vertx.eventBus().consumer("PRICE", message -> {
            	{
                    String key = message.body().toString();
                    logger.info("received from event bus last price: " + key);
            	}
            });

    }
    
    
    
  }