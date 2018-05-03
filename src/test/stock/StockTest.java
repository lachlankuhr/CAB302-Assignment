package stock;

import static org.junit.Assert.*;

import org.junit.Test;

public class StockTest {

	Item rice =  new Item("rice", 2, 3, 225, 300, null);
	Stock shopStock = new Stock();
	
	
	@Test
	public void addItemTest() {
		assertNull(shopStock.get(rice));
		shopStock.put(rice, 100);	
		assertEquals(shopStock.size(), 1);
	}
	
	@Test
	public void checkItemTest() {
		assertEquals(shopStock.containsKey(rice), true);
	}
	
	@Test
	public void checkItemQuantityTest() {
		assertEquals(shopStock.get(rice), Integer.valueOf(100));
	}
	
	@Test
	public void updateItemQuantityTest() {
		shopStock.put(rice, 300);
		assertEquals(shopStock.get(rice), Integer.valueOf(300));
	}
	
}
