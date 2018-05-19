package delivery;

import static org.junit.Assert.*;

import org.junit.*;

import stock.Item;
import stock.Stock;

public class OrdinaryTruckTest {
	
	Stock stock;
	Truck ordinaryTruck;
	
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
			new OrdinaryTruck(stock);
			assertTrue(true);
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
			new OrdinaryTruck();
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
			new OrdinaryTruck(stock);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testAddingTooManyItems1() {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 1100);
		try {
			ordinaryTruck = new OrdinaryTruck(stock); 
		} catch (DeliveryException e) {
			assertEquals(e.getMessage(), "Added too much cargo.");
		}
	}
	
	@Test
	public void testAddingTooManyItems2() {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, null), 1000);
		try {
			ordinaryTruck = new OrdinaryTruck(stock); 
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Added too much cargo.");
		}
	}
	
	@Test
	public void addingCoolItem1() {
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, 8.0), 1000);
		try {
			ordinaryTruck = new OrdinaryTruck(stock); 
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Added cool item to ordinary truck.");
		}
	}
	
	@Test
	public void addingCoolItem2() {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, 8.0), 800);
		try {
			ordinaryTruck = new OrdinaryTruck(stock); 
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Added cool item to ordinary truck.");
		}
	}
	
	
	@Test 
	public void testcalculateCostOfDelivery1() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, null), 500);
		ordinaryTruck = new OrdinaryTruck(stock); 
		assertEquals(ordinaryTruck.calculateCostOfDelivery(), 900, 2.0);
	}
	
	@Test 
	public void testcalculateCostOfDelivery2() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		stock.put(new Item("chocolate", 5.0, 8.0, 250, 375, null), 100);
		ordinaryTruck = new OrdinaryTruck(stock); 
		assertEquals(ordinaryTruck.calculateCostOfDelivery(), 800.0, 2.0);
	}
	
	@Test 
	public void testgetCargoQuantity1() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		stock.put(new Item("chocolate", 5.0, 8.0, 250, 375, null), 100);
		ordinaryTruck = new OrdinaryTruck(stock); 
		assertEquals(ordinaryTruck.getCargoQuantity(), 200);
	}
	
	@Test 
	public void testgetCargoQuantity2() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 100);
		stock.put(new Item("chocolate", 5.0, 8.0, 250, 375, null), 700);
		ordinaryTruck = new OrdinaryTruck(stock); 
		assertEquals(ordinaryTruck.getCargoQuantity(), 800);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testAddingCargo() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 50);
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, null), 400);
		ordinaryTruck = new OrdinaryTruck(stock); 
		Item chips = new Item("chips", 2.0, 4.0, 125, 200, null);
		ordinaryTruck.addCargo(50, chips);
		assertTrue(ordinaryTruck.getCargo().containsKey(chips));
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testAddingCargo2() throws DeliveryException {
		stock.put(new Item("rice", 2.0, 3.0, 225, 300, null), 50);
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, null), 400);
		ordinaryTruck = new OrdinaryTruck(stock); 
		Item chips = new Item("chips", 2.0, 4.0, 125, 200, null);
		ordinaryTruck.addCargo(50, chips);
		assertTrue(ordinaryTruck.getCargo().containsKey(chips));
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testAddingCargo3() throws DeliveryException {
		Item rice = new Item("rice", 2.0, 3.0, 225, 300, null);
		stock.put(rice, 50);
		stock.put(new Item("asparagus", 2.0, 4.0, 175, 275, null), 400);
		ordinaryTruck = new OrdinaryTruck(stock); 
		ordinaryTruck.addCargo(50, rice);
		assertTrue(ordinaryTruck.getCargo().containsKey(rice));
		assertTrue(ordinaryTruck.getCargo().get(rice) == 100);
	}
	
	

}
