package delivery;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stock.Item;
import stock.Stock;

public class RefrigeratedTruckTest {
	
	Stock stock;
	Truck refrigeratedTruck;
	
	@Before
	public void before() {
		stock = new Stock();
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testConstructor() {
		try {
			new RefrigeratedTruck(stock);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}
	
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testConstructorDifferentItems() {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 300);
		try {
			new RefrigeratedTruck(stock);
			assertEquals(true, true);
		} catch (Exception e) {
			fail();
		}
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testConstructorWithNoParameters() {
		try {
			new RefrigeratedTruck();
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testAddingTooManyItems1() {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 1100);
		try {
			refrigeratedTruck = new RefrigeratedTruck(stock); 
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Added too much cargo.");
		}
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
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
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testcalculateCostOfDelivery1() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, 8.0), 100);
		refrigeratedTruck = new RefrigeratedTruck(stock); 
		assertEquals(refrigeratedTruck.calculateCostOfDelivery(), 1013.03, 2.0);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testcalculateCostOfDeliveryOppositeOrder() throws DeliveryException {
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, 8.0), 100);
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		refrigeratedTruck = new RefrigeratedTruck(stock); 
		assertEquals(refrigeratedTruck.calculateCostOfDelivery(), 1013.03, 2.0);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testcalculateCostOfDeliveryJustWarmItems() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		stock.put(new Item("beans", 4.0, 6.0, 450, 525, 8.0), 100);
		refrigeratedTruck = new RefrigeratedTruck(stock); 
		assertEquals(refrigeratedTruck.calculateCostOfDelivery(), 1013.03, 2.0);
	}
	
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testcalculateCostOfDelivery2() throws DeliveryException {
		stock.put(new Item("chocolate", 5.0, 8.0, 250, 375, -1.0), 200);
		refrigeratedTruck = new RefrigeratedTruck(stock); 
		assertEquals(refrigeratedTruck.calculateCostOfDelivery(), 1114.79, 2.0);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testgetCargoQuantity1() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		stock.put(new Item("chocolate", 5.0, 8.0, 250, 375, null), 100);
		refrigeratedTruck = new RefrigeratedTruck(stock); 
		assertEquals(refrigeratedTruck.getCargoQuantity(), 200);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testgetCargoQuantity2() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, 8.0), 700);
		refrigeratedTruck = new RefrigeratedTruck(stock); 
		assertEquals(refrigeratedTruck.getCargoQuantity(), 800);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testAddingCargo() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 50);
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, 8.0), 400);
		refrigeratedTruck = new RefrigeratedTruck(stock); 
		Item chips = new Item("chips", 2.0, 4.0, 125, 200, null);
		refrigeratedTruck.addCargo(50, chips);
		assertTrue(refrigeratedTruck.getCargo().containsKey(chips));
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testAddingCargo2() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 50);
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, 8.0), 400);
		refrigeratedTruck = new RefrigeratedTruck(stock); 
		Item chips = new Item("chips", 2.0, 4.0, 125, 200, null);
		refrigeratedTruck.addCargo(50, chips);
		assertTrue(refrigeratedTruck.getCargo().containsKey(chips));
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testFindLowestTemperature() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 50);
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, 8.0), 400);
		refrigeratedTruck = new RefrigeratedTruck(stock); 
		Item chips = new Item("chips", 2.0, 4.0, 125, 200, null);
		refrigeratedTruck.addCargo(50, chips);
	}
	
	@Test
	public void testId() {
		assertEquals(refrigeratedTruck.getManifestIdentification(), ">Refrigerated");
	}

}
