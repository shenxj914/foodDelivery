package com.css.kitchen.shelves;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import com.css.kitchen.orders.Order;

public class ShelfService implements Runnable {
	BlockingQueue<Order> orderQueue;
	HashMap<String, Set<Order>> shelves;
	public final static String HOT = "hot";
	public final static String COLD = "cold";
	public final static String FROZEN = "frozen";
	public final static String OVERFLOW = "overflow";
	
	
	public ShelfService(BlockingQueue<Order> orderQueue) {
		this.orderQueue = orderQueue;
		//TODO move size to constants.
		Set<Order> hot = new HashSet<Order>();
		Set<Order> cold = new HashSet<Order>();
		Set<Order> frozen = new HashSet<Order>();
		Set<Order> overflow = new HashSet<Order>();
		this.shelves = new HashMap<String, Set<Order>>();
		//TODO move to constants
		this.shelves.put(HOT, hot);
		this.shelves.put(COLD, cold);
		this.shelves.put(FROZEN, frozen);
		this.shelves.put(OVERFLOW, overflow);
		
	}

	/**
	 * for courier to take the order from the shelf.
	 * @param order
	 */
	public void takeOrder(Order order) {
		if (shelves.get(order.getTemp()).contains(order)) {
			shelves.get(order.getTemp()).remove(order);
		} else if (shelves.get(OVERFLOW).contains(order)) {
			shelves.get(OVERFLOW).remove(order);
		}
		
	}

	/**
	 * get the cooked order and put to the right shelf
	 */
	public void run() {
		try {
			while (true) {
				Order order = this.orderQueue.take();
				while (!putOnShelf(order)) {
					rearrange(); //rearrange the shelf and discard order if needed.
				}
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * try to put to the corresponding shelf first. e.g hot -> hot shelf
	 * if corresponding shelf full, put to the overflow shelf.
	 * @param order
	 * @return false if both corresponding shelf and  overflow shelf are full
	 */
	public boolean putOnShelf(Order order) {
		if (shelves.get(order.getTemp()).size() == 10) {
			if (shelves.get(OVERFLOW).size() == 10)
				return false;
			System.out.println("Order " + order.getId() + " on the shelf: overflow");
			shelves.get(OVERFLOW).add(order);
		} else {
			System.out.println("Order " + order.getId() + " on the shelf: " + order.getTemp());
			shelves.get(order.getTemp()).add(order);
			
		}
		
		return true;
	}


	/**
	 * overflow shelf is full. move some orders to the corresponding shelf if not full.
	 * if not possible, discard one order.
	 */
	public void rearrange() {
		Set<Order> overflow = shelves.get(OVERFLOW);
		Set<Order> toBeRemoved = new HashSet<>();
		for (Order order: overflow) {
			if (shelves.get(order.getTemp()).size() < 10) {
				System.out.println("Order " + order.getId() + " moved to the shelf: " + order.getTemp());
				shelves.get(order.getTemp()).add(order);
				toBeRemoved.add(order);
			}
		}

		overflow.removeAll(toBeRemoved);
		
		//discard the first order
		if (overflow.size() == 10) {
			Order rand = overflow.iterator().next();
			System.out.println("Order " + rand.getId() + " discarded ");
			overflow.remove(rand);
		}
		shelves.put(OVERFLOW, overflow);
	}
}
