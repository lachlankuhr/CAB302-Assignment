package delivery;

import static org.junit.Assert.*;

import org.junit.Test;

import stock.Item;
import stock.Stock;

public class RefrigeratedTruckTest {
	
	Stock stock;
	Truck refrigeratedTruck;

	@Test
	public void testAddingTooManyItems1() {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 1100);
		try {
			refrigeratedTruck = new RefrigeratedTruck(stock); 
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Added too much cargo.");
		}
	}
	
	@Test
	public void testAddingTooManyItems2() {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, 8.0), 1000);
		try {
			refrigeratedTruck = new RefrigeratedTruck(stock); 
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Added too much cargo.");
		}
	}
	
	@Test 
	public void testcalculateCostOfDelivery1() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, 8.0), 100);
		refrigeratedTruck = new RefrigeratedTruck(stock); 
		assertEquals(refrigeratedTruck.calculateCostOfDelivery(), 1013.03, 2.0);
	}
	
	@Test 
	public void testcalculateCostOfDelivery2() throws DeliveryException {
		stock.put(new Item("chocolate", 5.0, 8.0, 250, 375, -1.0), 200);
		refrigeratedTruck = new RefrigeratedTruck(stock); 
		assertEquals(refrigeratedTruck.calculateCostOfDelivery(), 1114.79, 2.0);
	}
	
	@Test 
	public void testgetCargoQuantity1() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		stock.put(new Item("chocolate", 5.0, 8.0, 250, 375, null), 100);
		refrigeratedTruck = new RefrigeratedTruck(stock); 
		assertEquals(refrigeratedTruck.getCargoQuantity(), 200);
	}
	
	@Test 
	public void testgetCargoQuantity2() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, 8.0), 700);
		refrigeratedTruck = new RefrigeratedTruck(stock); 
		assertEquals(refrigeratedTruck.getCargoQuantity(), 800);
	}

}
