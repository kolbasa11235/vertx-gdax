package io.vertx.starter;

import java.text.DecimalFormat;
import java.time.Instant;

import com.google.gson.Gson;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.json.JsonObject;

public class WebsocketClient  
{
    public static String passphrase= "nosnevik";
    public static String key = "b0bf5c6f1d5b3bc6d34e3d4765b2f8da";
    public static String	  secret = "Mp6Jx/S9twk+FyGOzyistXSKCeRnHDt1m1D54rG4937JZRylTGnPmS1T4Fr4du52280TYBDrwXgRntRGIwT6Fg==";
    HttpClient client = null;
//	  public static void main(String[] args) throws Exception
//	  {
//		  WebsocketClient wsc = new WebsocketClient();		  
//		  wsc.work(Vertx.vertx());
//	  }
//	  
	  public WebsocketClient()
	  {

		  this.client = Vertx.vertx().createHttpClient();
		  
	  }
	  
	  public void work(Vertx vertx)
	  {
		  RequestOptions ro = new RequestOptions();
		  ro.setSsl(true);
		  ro.setHost("ws-feed.gdax.com");
		  ro.setPort(443);
		  ro.setURI("/");
	  
	  client.websocket(ro, websocket -> {
		  	System.out.println("Connected!");
		  
		  	Subscribe sub = new Subscribe();
		  	sub.setKey(key);
		 	sub.setPassphrase(passphrase);
		    String timestamp = Instant.now().getEpochSecond() + "";
		    sub.setTimestamp(timestamp);
		    String [] prod_ids = { "BTC-USD" };
		 	String [] channels = { "matches" };
			sub.setProduct_ids(prod_ids);
			sub.setChannels(channels);
			sub.setType("subscribe");
			Gson gson = new Gson();
		
			String jsonstr = gson.toJson(sub);
		 	System.out.println("subscribe message " + jsonstr);
	        websocket.writeTextMessage(jsonstr);
	        websocket.frameHandler(frame -> 
		        {
				        String line = frame.textData();
				        JsonObject jo = new JsonObject(line);
			        	  String type = jo.getString("type");
			        	  if( type.equals( "match"))
			        	  {
				        	  String price = jo.getString("price");
				        	  String product = jo.getString("product_id");
				        	  String side = jo.getString("side");
				        	  System.out.println("received from gdax and published to event bus: " + product + " price: " + price + " side: " + side);
				        	  JsonObject jom = new JsonObject();
				        	  DecimalFormat formatter = new DecimalFormat("#0.00");
				        	  Double db = new Double(price);
				        	  String pr = formatter.format(db);
				        	  jom.put(product, pr);
				        	  vertx.eventBus().publish("PRICE", jom);
			        	  }					        
		        });
	});
}
}