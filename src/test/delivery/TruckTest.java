package delivery;

import static org.junit.Assert.*;

import org.junit.*;

import stock.Item;
import stock.Stock;

/**
 * Test cases for the ordinary truck class. 
 * @author Lachlan Kuhr
 */

public class TruckTest {
	
	Truck truck;
	Stock truckStock;
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Before
	public void before() {
		truckStock = new Stock(); 
	}

	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testColdItemCheck() throws DeliveryException {
		truck = new RefrigeratedTruck();
		truck.addCargo(50, new Item("ice", 2.0, 5.0, 225, 325, -10.0));
		assertTrue(truck.coldItemCheck());
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testColdItemCheck2() throws DeliveryException {
		truck = new RefrigeratedTruck();
		truck.addCargo(50, new Item("pasta", 3.0, 4.0, 125, 250, null));
		assertTrue(!truck.coldItemCheck());
	}
}
