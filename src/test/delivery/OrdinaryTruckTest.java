package delivery;

import static org.junit.Assert.*;

import org.junit.*;

import stock.Item;
import stock.Stock;

public class OrdinaryTruckTest {
	
	Truck ordinaryTruck;
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testConstructor() {
		try {
			new OrdinaryTruck();
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testAddingTooManyItems1() {
		try {
			ordinaryTruck = new OrdinaryTruck(); 
			ordinaryTruck.addCargo(1100, new Item("rice", 2.0, 3.0, 225, 300, null));
			fail();
		} catch (DeliveryException e) {
			assertEquals(e.getMessage(), "Added too much cargo.");
		}
	}
	
	@Test
	public void testAddingTooManyItems2() {
		try {
			ordinaryTruck = new OrdinaryTruck();
			ordinaryTruck.addCargo(100, new Item("rice", 2.0, 3.0, 225, 300, null));
			ordinaryTruck.addCargo(1000, new Item("asparagus", 2.0, 4.0, 175, 275, null));
			fail();
		} catch (DeliveryException e) {
			assertEquals(e.getMessage(), "Added too much cargo.");
		}
	}
	
	@Test
	public void testAddingTooManyItems3() {
		try {
			ordinaryTruck = new OrdinaryTruck();
			ordinaryTruck.addCargo(100, new Item("rice", 2.0, 3.0, 225, 300, null));
			Item asparagus = new Item("asparagus", 2.0, 4.0, 175, 275, null);
			ordinaryTruck.addCargo(200, asparagus);
			ordinaryTruck.addCargo(1000, asparagus);
			fail();
		} catch (DeliveryException e) {
			assertEquals(e.getMessage(), "Added too much cargo.");
		}
	}
	
	
	@Test
	public void addingCoolItem1() {
		try {
			ordinaryTruck = new OrdinaryTruck(); 
			ordinaryTruck.addCargo(1000, new Item("asparagus", 2.0, 4.0, 175, 275, 8.0));
			fail();
		} catch (DeliveryException e) {
			assertEquals(e.getMessage(), "Added cool item to ordinary truck.");
		}
	}
	
	@Test
	public void addingCoolItem2() {
		try {
			ordinaryTruck = new OrdinaryTruck(); 
			ordinaryTruck.addCargo(100, new Item("rice", 2.0, 3.0, 225, 300, null));
			ordinaryTruck.addCargo(800, new Item("asparagus", 2.0, 4.0, 175, 275, 8.0));
			fail();
		} catch (DeliveryException e) {
			assertEquals(e.getMessage(), "Added cool item to ordinary truck.");
		}
	}
	
	
	@Test 
	public void testcalculateCostOfDelivery1() throws DeliveryException {
		ordinaryTruck = new OrdinaryTruck(); 
		ordinaryTruck.addCargo(100, new Item("rice", 2.0, 3.0, 225, 300, null));
		ordinaryTruck.addCargo(500, new Item("asparagus", 2.0, 4.0, 175, 275, null));
		assertEquals(ordinaryTruck.calculateCostOfDelivery(), 900, 2.0);
	}
	
	@Test 
	public void testcalculateCostOfDelivery2() throws DeliveryException {
		ordinaryTruck = new OrdinaryTruck(); 
		ordinaryTruck.addCargo(100, new Item("rice", 2.0, 3.0, 225, 300, null));
		ordinaryTruck.addCargo(100, new Item("chocolate", 5.0, 8.0, 250, 375, null));
		assertEquals(ordinaryTruck.calculateCostOfDelivery(), 800.0, 2.0);
	}
	
	@Test 
	public void testgetCargoQuantity1() throws DeliveryException {
		ordinaryTruck = new OrdinaryTruck(); 
		ordinaryTruck.addCargo(100, new Item("rice", 2.0, 3.0, 225, 300, null));
		ordinaryTruck.addCargo(100, new Item("chocolate", 5.0, 8.0, 250, 375, null));
		assertEquals(ordinaryTruck.getCargoQuantity(), 200);
	}
	
	@Test 
	public void testgetCargoQuantity2() throws DeliveryException {
		ordinaryTruck = new OrdinaryTruck(); 
		ordinaryTruck.addCargo(100, new Item("rice", 2.0, 3.0, 225, 300, null));
		ordinaryTruck.addCargo(700, new Item("chocolate", 5.0, 8.0, 250, 375, null));
		assertEquals(ordinaryTruck.getCargoQuantity(), 800);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testAddingCargo() throws DeliveryException {
		ordinaryTruck = new OrdinaryTruck(); 
		ordinaryTruck.addCargo(50, new Item("rice", 2.0, 3.0, 225, 300, null));
		ordinaryTruck.addCargo(400, new Item("asparagus", 2.0, 4.0, 175, 275, null));
		Item chips = new Item("chips", 2.0, 4.0, 125, 200, null);
		ordinaryTruck.addCargo(50, chips);
		assertTrue(ordinaryTruck.getCargo().containsKey(chips));
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testAddingCargo2() throws DeliveryException {
		ordinaryTruck = new OrdinaryTruck(); 
		ordinaryTruck.addCargo(50, new Item("rice", 2.0, 3.0, 225, 300, null));
		ordinaryTruck.addCargo(400, new Item("asparagus", 2.0, 4.0, 175, 275, null));
		Item chips = new Item("chips", 2.0, 4.0, 125, 200, null);
		ordinaryTruck.addCargo(50, chips);
		assertTrue(ordinaryTruck.getCargo().containsKey(chips));
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testAddingCargo3() throws DeliveryException {
		ordinaryTruck = new OrdinaryTruck(); 
		Item rice = new Item("rice", 2.0, 3.0, 225, 300, null);
		ordinaryTruck.addCargo(50, rice);
		ordinaryTruck.addCargo(400, new Item("asparagus", 2.0, 4.0, 175, 275, null));
		ordinaryTruck.addCargo(50, rice);
		assertTrue(ordinaryTruck.getCargo().containsKey(rice));
		assertTrue(ordinaryTruck.getCargo().get(rice) == 100);
	}
	
	

}
