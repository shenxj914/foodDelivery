package com.css.kitchen.orders;

import java.util.concurrent.BlockingQueue;

import com.css.kitchen.shelves.ShelfService;

public class OrderService {
	BlockingQueue<Order> shelfQueue;
	BlockingQueue<Order> courtierQueue;

	public void OrderReceived(Order order) {
		System.out.println("Order Received:" + order.getId());
		cook(order);
		try {
			shelfQueue.put(order);
			System.out.println("Order to be picked:" + order.getId());
			courtierQueue.put(order);
		} catch (Exception e) { //TODO add exceptions
			e.printStackTrace();
		}
		
	}

	private void cook(Order order) {
		System.out.println("Order Cooked:" + order.getId());
	}


	public OrderService(BlockingQueue<Order> shelfQueue, BlockingQueue<Order> courtierQueue) {
		this.shelfQueue = shelfQueue;
		this.courtierQueue = courtierQueue;
	}

}
