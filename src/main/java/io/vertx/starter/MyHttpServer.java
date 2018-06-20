package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class MyHttpServer extends AbstractVerticle 
{
	private final Logger logger = LoggerFactory.getLogger(MyHttpServer.class);
	boolean responded  = false;
    // Implement Http server
    @Override
    public void start() {
    	System.out.println("START");
    	HttpServerOptions options = new HttpServerOptions();
    	options.setHost("127.0.0.1");
        vertx.createHttpServer(options)
                .requestHandler(httpRequest -> 
                {
                	handleHttpRequest(httpRequest);
                }
                ).listen(8080);
    }

    String key =  "nothing yet";
    private void handleHttpRequest(final HttpServerRequest httpRequest) 
    {
        vertx.eventBus().consumer("PRICE", message -> {
        	{
                key = message.body().toString();
                logger.info("received by http server from event bus last price: " + key);
        	}
        });
    	
        try {
        	httpRequest.response().end(key);
        } catch ( IllegalStateException ise)
        {
        	logger.error(ise.getMessage());
        }
    	
        

    }
    
  }