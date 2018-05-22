package delivery;

import static org.junit.Assert.*;

import org.junit.*;

import stock.Item;
import stock.Stock;


public class TruckTest {
	
	Truck truck;
	Stock truckStock;
	
	@Before
	public void before() {
		truckStock = new Stock(); 
	}

	/**
	 * Author: Lachlan Kuhr
	 * @throws DeliveryException 
	 */
	
	@Test
	public void testColdItemCheck() throws DeliveryException {
		truck = new RefrigeratedTruck();
		truck.addCargo(50, new Item("ice", 2.0, 5.0, 225, 325, -10.0));
		assertTrue(truck.coldItemCheck());
	}
	
	/**
	 * Author: Lachlan Kuhr
	 * @throws DeliveryException 
	 */
	
	@Test
	public void testColdItemCheck2() throws DeliveryException {
		truck = new RefrigeratedTruck();
		truck.addCargo(50, new Item("pasta", 3.0, 4.0, 125, 250, null));
		assertTrue(!truck.coldItemCheck());
	}

}
