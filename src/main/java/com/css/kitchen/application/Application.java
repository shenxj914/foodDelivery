package com.css.kitchen.application;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.css.kitchen.couriers.CourierService;
import com.css.kitchen.orders.Order;
import com.css.kitchen.orders.OrderService;
import com.css.kitchen.shelves.ShelfService;

/**
 * Main thread to start the order service, shelf management service and courier service
 */
public class Application implements Runnable {
	//Generated orders from the external, e.g. from the json file.
	private Queue<Order> orders;

	//frequency of the order creation. e.g. two orders per second.
	private int orderFreq;

	private OrderService orderService;
	private ShelfService shelfService;
	private CourierService courierService;


	public static void main(String[] args) {
		Application test = new Application();

		new Thread(test).start();
	    new Thread(test.shelfService).start();
	    new Thread(test.courierService).start();
	}
	
	private void readFromFile()
			{
		JSONParser parser = new JSONParser();
	      try {
	    	 InputStream in = Application.class.getClassLoader().getResourceAsStream("orders.json");
	    	 
	    	 JSONArray jsonArray = (JSONArray)parser.parse(new InputStreamReader(in, "UTF-8"));
	    	 Iterator iterator = jsonArray.iterator();
	    	 orders = new LinkedList<Order>();
	         while (iterator.hasNext()) {
	        	 orders.add(new Order((JSONObject)iterator.next()));
	         }
	         Properties properties = new Properties();
	         ClassLoader loader = Thread.currentThread().getContextClassLoader();
	         InputStream inputStream = loader.getResourceAsStream("config.properties");
	         properties.load(inputStream);

	         orderFreq = Integer.valueOf((String)properties.get("Orderfreq"));
	         
	    	 
	      } catch(Exception e) { //TODO add exception
	         e.printStackTrace();
	         
	      }
	      
	}

	/**
	 * keep produce the order from the order list
	 */
	public void run() {
		while (true) {
			try {
				for (Order order: orders) {
					orderService.OrderReceived(order);
					Thread.sleep(orderFreq);
				}
				
			} catch (Exception e) { //TODO add exception
				e.printStackTrace();
				break;
			}
			
		}
		
	}
	
	public Application() {
		readFromFile();
		BlockingQueue<Order> courierQueue = new LinkedBlockingQueue<Order>();
		BlockingQueue<Order> shelfQueue = new LinkedBlockingQueue<Order>();
		shelfService = new ShelfService(shelfQueue);
		courierService = new CourierService(courierQueue, shelfService);
		orderService = new OrderService(shelfQueue, courierQueue);
		
	}
	
	 

}
