package com.css.kitchen.couriers;


import java.util.Random;
import java.util.concurrent.BlockingQueue;
import com.css.kitchen.orders.Order;
import com.css.kitchen.shelves.ShelfService;

public class CourierService implements Runnable {
	private BlockingQueue<Order> orderQueue;
	private ShelfService shelfService;
	private Random rand;

	/**
	 * take the order from the shelf and deliver.
	 * @param order
	 * @param id courier id
	 */
	public void deliver(Order order, String id) {
		shelfService.takeOrder(order);
		System.out.println("Order delivered:" + order.getId() + " by: " + id);
	}

	public CourierService(BlockingQueue<Order> orderQueue, ShelfService shelfService) {

		this.shelfService = shelfService;
		this.orderQueue = orderQueue;
		this.rand = new Random();

	}

	public void run() {
		try {
			while (true) {

				Order order = this.orderQueue.take();
				//courier will wait 2-6 seconds to pick up and deliver the order.
				Thread.sleep(2000 + this.rand.nextInt(4000));
				//unlimited couriers, use timestamp to identify the id.
				deliver(order, String.valueOf(System.currentTimeMillis()));
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
