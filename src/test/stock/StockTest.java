package stock;

import static org.junit.Assert.*;

import org.junit.Test;

public class StockTest {

	Item rice =  new Item("rice", 2, 3, 225, 300, null);
	Stock shopStock = new Stock();
	
	
	@Test
	public void addItemTest() {
		shopStock.put(rice, 100);
		
		assertEquals(shopStock.size(), 1);
	}
	
	@Test
	public void itemQuantityTest() {
		assertEquals(shopStock.get(rice), Double.valueOf(100));
	}

}
