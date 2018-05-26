package delivery;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stock.Item;
import stock.Stock;

public class RefrigeratedTruckTest {
	
	Truck refrigeratedTruck;
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testConstructor() {
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
	public void testConstructorDifferentItems() {
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
		try {
			refrigeratedTruck = new RefrigeratedTruck(); 
			refrigeratedTruck.addCargo(1100, new Item("rice", 2.0, 3.0, 225, 300, null));
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "There was too many items in a refrigerated truck. Check manifest file.");
		}
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testAddingTooManyItems2() {
		try {
			refrigeratedTruck = new RefrigeratedTruck(); 
			refrigeratedTruck.addCargo(100, new Item("rice", 2.0, 3.0, 225, 300, null));
			refrigeratedTruck.addCargo(1000, new Item("asparagus", 2.0, 4.0, 175, 275, 8.0));
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "There was too many items in a refrigerated truck. Check manifest file.");
		}
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testcalculateCostOfDelivery1() throws DeliveryException {
		refrigeratedTruck = new RefrigeratedTruck(); 
		refrigeratedTruck.addCargo(100,new Item("rice", 2.0, 3.0, 225, 300, null));
		refrigeratedTruck.addCargo(100, new Item("asparagus", 2.0, 4.0, 175, 275, 8.0));
		assertEquals(refrigeratedTruck.calculateCostOfDelivery(), 1013.03, 2.0);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testcalculateCostOfDeliveryOppositeOrder() throws DeliveryException {
		refrigeratedTruck = new RefrigeratedTruck(); 
		refrigeratedTruck.addCargo(100,new Item("rice", 2.0, 3.0, 225, 300, null));
		refrigeratedTruck.addCargo(100, new Item("asparagus", 2.0, 4.0, 175, 275, 8.0));
		assertEquals(refrigeratedTruck.calculateCostOfDelivery(), 1013.03, 2.0);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testcalculateCostOfDeliveryJustWarmItems() throws DeliveryException {
		refrigeratedTruck = new RefrigeratedTruck(); 
		refrigeratedTruck.addCargo(100, new Item("rice", 2.0, 3.0, 225, 300, null));
		refrigeratedTruck.addCargo(100, new Item("beans", 4.0, 6.0, 450, 525, 8.0));
		assertEquals(refrigeratedTruck.calculateCostOfDelivery(), 1013.03, 2.0);
	}
	
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testcalculateCostOfDelivery2() throws DeliveryException {
		refrigeratedTruck = new RefrigeratedTruck(); 
		refrigeratedTruck.addCargo(200, new Item("chocolate", 5.0, 8.0, 250, 375, -1.0));
		assertEquals(refrigeratedTruck.calculateCostOfDelivery(), 1114.79, 2.0);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testgetCargoQuantity1() throws DeliveryException {
		refrigeratedTruck = new RefrigeratedTruck(); 
		refrigeratedTruck.addCargo(100, new Item("rice", 2.0, 3.0, 225, 300, null));
		refrigeratedTruck.addCargo(100,new Item("chocolate", 5.0, 8.0, 250, 375, null));
		assertEquals(refrigeratedTruck.getCargoQuantity(), 200);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testgetCargoQuantity2() throws DeliveryException {
		refrigeratedTruck = new RefrigeratedTruck(); 
		refrigeratedTruck.addCargo(100, new Item("rice", 2.0, 3.0, 225, 300, null));
		refrigeratedTruck.addCargo(700, new Item("asparagus", 2.0, 4.0, 175, 275, 8.0));
		assertEquals(refrigeratedTruck.getCargoQuantity(), 800);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testAddingCargo() throws DeliveryException {
		refrigeratedTruck = new RefrigeratedTruck(); 
		refrigeratedTruck.addCargo(50, new Item("rice", 2.0, 3.0, 225, 300, null));
		refrigeratedTruck.addCargo(400, new Item("asparagus", 2.0, 4.0, 175, 275, 8.0));
		Item chips = new Item("chips", 2.0, 4.0, 125, 200, null);
		refrigeratedTruck.addCargo(50, chips);
		assertTrue(refrigeratedTruck.getCargo().containsKey(chips));
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testAddingCargo2() throws DeliveryException {
		refrigeratedTruck = new RefrigeratedTruck(); 
		refrigeratedTruck.addCargo(50, new Item("rice", 2.0, 3.0, 225, 300, null));
		refrigeratedTruck.addCargo(400, new Item("asparagus", 2.0, 4.0, 175, 275, 8.0));
		Item chips = new Item("chips", 2.0, 4.0, 125, 200, null);
		refrigeratedTruck.addCargo(50, chips);
		assertTrue(refrigeratedTruck.getCargo().containsKey(chips));
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testAddingCargo3() throws DeliveryException {
		Item rice = new Item("rice", 2.0, 3.0, 225, 300, null);
		refrigeratedTruck = new RefrigeratedTruck(); 
		refrigeratedTruck.addCargo(50, rice);
		refrigeratedTruck.addCargo(400, new Item("asparagus", 2.0, 4.0, 175, 275, 8.0));
		refrigeratedTruck.addCargo(50, rice);
		assertTrue(refrigeratedTruck.getCargo().containsKey(rice));
		assertTrue(refrigeratedTruck.getCargo().get(rice) == 100);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testFindLowestTemperature() throws DeliveryException {
		RefrigeratedTruck refrigeratedTruck = new RefrigeratedTruck(); 
		refrigeratedTruck.addCargo(50, new Item("rice", 2.0, 3.0, 225, 300, null));
		refrigeratedTruck.addCargo(400, new Item("asparagus", 2.0, 4.0, 175, 275, 8.0));
		Item chips = new Item("chips", 2.0, 4.0, 125, 200, null);
		assertEquals(refrigeratedTruck.getCargo().findColdestItem().getTemperature(), 8.0, 0.1);
	}
	
	@Test
	public void testId() throws DeliveryException {
		refrigeratedTruck = new RefrigeratedTruck();
		assertEquals(refrigeratedTruck.getManifestIdentification(), ">Refrigerated");
	}

}
