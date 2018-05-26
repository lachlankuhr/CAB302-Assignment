package stock;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

import csv.CSVFormatException;

/**
 * Test cases for Stock class
 * @author Atrey Gajjar
 */
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
	public void quantityCountTest1() {
		shopStock.put(rice, 200);
		shopStock.put(iceCream, 34);
		assertEquals(234, shopStock.getStockQuantity());
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void quantityCountTest2() {
		shopStock.put(rice, 15676);
		shopStock.put(iceCream, 5676);
		shopStock.put(colderIceCream, 20);
		assertEquals(21372, shopStock.getStockQuantity());
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void cargoCostDryOnlyTest() {
		shopStock.put(rice, 200);
		assertEquals(400.0, shopStock.calculateCostOfCargo(), 0.1);
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void cargoCostBothTypesTest() {
		shopStock.put(rice, 150);
		shopStock.put(iceCream, 650);
		shopStock.put(colderIceCream, 20);
		assertEquals(5660.0, shopStock.calculateCostOfCargo(), 0.1);
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
	public void loadPropertiesBaseTest() throws IOException, CSVFormatException {
		Stock.loadInItemProperties("." + File.separator + "files" + File.separator + "property-tests" + File.separator + "item_properties_base.csv");
		
		Item chocolate = Stock.getItem("chocolate");
		Item bread = Stock.getItem("bread");
		Item mushroom = Stock.getItem("mushrooms");
		Item tomatoes = Stock.getItem("tomatoes");
		
		assertNotNull(chocolate);
		assertNotNull(bread);
		assertNotNull(mushroom);
		assertNotNull(tomatoes);
		
		assertEquals("chocolate", chocolate.getName());
		assertEquals(5.0, chocolate.getManufacturingCost(), 0.1);
		assertEquals(8.0, chocolate.getSellPrice(), 0.1);
		assertEquals(250, chocolate.getReorderPoint());
		assertEquals(375, chocolate.getReorderAmount());
		assertNull(chocolate.getTemperature());
		
		assertEquals("bread", bread.getName());
		assertEquals(2.0, bread.getManufacturingCost(), 0.1);
		assertEquals(3.0, bread.getSellPrice(), 0.1);
		assertEquals(125, bread.getReorderPoint());
		assertEquals(200, bread.getReorderAmount());
		assertNull(bread.getTemperature());
		
		assertEquals("mushrooms", mushroom.getName());
		assertEquals(2.0, mushroom.getManufacturingCost(), 0.1);
		assertEquals(4.0, mushroom.getSellPrice(), 0.1);
		assertEquals(200, mushroom.getReorderPoint());
		assertEquals(325, mushroom.getReorderAmount());
		assertEquals(Double.valueOf(10.0), mushroom.getTemperature());
		
		assertEquals("tomatoes", tomatoes.getName());
		assertEquals(1.0, tomatoes.getManufacturingCost(), 0.1);
		assertEquals(2.0, tomatoes.getSellPrice(), 0.1);
		assertEquals(325, tomatoes.getReorderPoint());
		assertEquals(400, tomatoes.getReorderAmount());
		assertEquals(Double.valueOf(10.0), tomatoes.getTemperature());
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void testUsingWrongPropertiesFile1() throws IOException, StockException {
		try {
			Stock.loadInItemProperties("." + File.separator + "files" + File.separator + "initial_export.csv");
			fail();
		} catch (CSVFormatException e) {
			assertEquals("File does not match required format. Check item properties file.", e.getMessage());
		}
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void testUsingWrongPropertiesFile2() throws IOException, StockException {
		try {
			Stock.loadInItemProperties("." + File.separator + "files" + File.separator + "sales_log_0.csv");
			fail();
		} catch (CSVFormatException e) {
			assertEquals("File does not match required format. Check item properties file.", e.getMessage());
		}
	}
}
