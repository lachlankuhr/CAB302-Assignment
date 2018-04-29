package stock;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {

	Item item;
	
	//Initialising an item for testing
	@Before
	public void initialiseItems() {
		item = new Item("rice", 2, 3, 225, 300, null);		
	}
	
	@Test
	public void getNameTest() {
		assertEquals(item.getName(), "rice");
	}
	
	@Test
	public void getManufacturingCostTest() {
		assertEquals(item.getManufacturingCost(), 2);
	}
	
	@Test
	public void getSellPriceTest() {
		assertEquals(item.getSellPrice(), 3);
	}
	
	@Test
	public void getReorderPointTest() {
		assertEquals(item.getReorderPoint(), 225);
	}
	
	@Test
	public void getReorderAmountTest() {
		assertEquals(item.getReorderAmount(), 300);
	}
	
	@Test
	public void getTemperatureTest() {
		//Testing dry good
		assertNull(item.getTemperature());
		
		//Test frozen good
		Item frozenItem = new Item("ice cream", 8, 14, 175, 250, (double) -20);
		assertEquals(frozenItem.getTemperature(), -20);
	}

}
