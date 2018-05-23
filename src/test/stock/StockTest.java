package stock;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class StockTest {
	
	Item rice; 
	Item iceCream; 
	Item colderIceCream;
	Stock shopStock;
	
	/**
	 * @author Atrey Gajjar
	 */
	@Before
	public void setup() {
		shopStock = new Stock();
		rice =  new Item("rice", 2, 3, 225, 300, null);
		iceCream = new Item("ice cream", 8, 14, 175, 250, (double) -20);
		colderIceCream = new Item("ice cream super cold", 8, 14, 175, 250, (double) -30);
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
		assertEquals(rice, shopStock.findColdestItem());
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void findColdestItemTest2() {
		shopStock.put(rice, 100);
		shopStock.put(iceCream, 200);
		assertEquals(iceCream, shopStock.findColdestItem());		
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void findColdestItemTest3() {
		shopStock.put(rice, 100);
		shopStock.put(iceCream, 200);
		shopStock.put(colderIceCream, 200);
		assertEquals(colderIceCream, shopStock.findColdestItem());	
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void negativeItemNumberTest() {
		try {
			shopStock.put(rice, -100);
			fail();
		} catch (StockException e) {
			assertEquals("Adding negative quantity to an item", e.getMessage());
		}
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void addingUnknownItem() throws IOException {
		Stock.loadInItemProperties(File.separator + "files" + File.separator + "item_properties.csv");
		
		try {
			Item pizza = new Item("pizza", 5, 10, 250, 300, (double) -5);
			shopStock.put(pizza, 100);			
		} catch (StockException e) {
			assertEquals("Unknown item being added to stock", e.getMessage());
		}
	}	
}
