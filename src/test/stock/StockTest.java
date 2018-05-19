package stock;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StockTest {
	
	Item rice =  new Item("rice", 2, 3, 225, 300, null);
	Item iceCream = new Item("ice cream", 8, 14, 175, 250, (double) -20);
	Stock shopStock;
	
	/**
	 * @author Atrey Gajjar
	 */
	@Before
	public void setup() {
		shopStock = new Stock();
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void addItemTest() {
		assertNull(shopStock.get(rice));
		shopStock.put(rice, 100);	
		assertEquals(shopStock.size(), 1);
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void checkItemTest() {
		shopStock.put(rice,  100);
		assertEquals(shopStock.containsKey(rice), true);
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void checkItemQuantityTest() {
		shopStock.put(rice, 100);
		assertEquals(shopStock.get(rice), Integer.valueOf(100));
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void updateItemQuantityTest() {
		shopStock.put(rice, 300);
		assertEquals(shopStock.get(rice), Integer.valueOf(300));
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void findColdestItemTest1() {
		shopStock.put(rice, 400);
		assertEquals(null, shopStock.findColdestItem());
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void findColdestItemTest2() {
		shopStock.put(rice, 100);
		shopStock.put(iceCream, 200);
		assertEquals(-20.0, shopStock.findColdestItem());		
	}
}
