package stock;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {

	Item item;
	
	/**
	 * @author Atrey Gajjar
	 */
	@Before
	public void initialiseItems() {
		item = new Item("rice", 2, 3, 225, 300, null);		
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void getNameTest() {
		assertEquals(item.getName(), "rice");
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void getManufacturingCostTest() {
		assertEquals(item.getManufacturingCost(), 2, 1e-15);
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void getSellPriceTest() {
		assertEquals(item.getSellPrice(), 3, 1e-15);
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void getReorderPointTest() {
		assertEquals(item.getReorderPoint(), 225);
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void getReorderAmountTest() {
		assertEquals(item.getReorderAmount(), 300);
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void getTemperatureTest() {
		//Testing dry good
		assertNull(item.getTemperature());
		
		//Test frozen good
		Item frozenItem = new Item("ice cream", 8, 14, 175, 250, (double) -20);
		assertEquals(frozenItem.getTemperature(), Double.valueOf(-20));
	}

}
