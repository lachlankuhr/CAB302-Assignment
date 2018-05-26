package stock;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import csv.CSVFormatException;
import delivery.DeliveryException;
import delivery.Manifest;

public class StoreTest {
	
	Store store;
	
	@Before 
	public void before() throws IOException {
		store = Store.generateStoreInstance();
		Stock.loadInItemProperties("." + File.separator + "files" + File.separator + "item_properties.csv");
	}
	
	@After 
	public void after() {
		Store.destoryStore();
	}
	
	@Test
	public void getNameTest() {
		assertEquals(store.getName(), "SuperMart");
	}
	
	@Test
	public void initialCapitalTest() {
		assertEquals(store.getCapital(), 100000, 1e-5);
	}
	
	@Test
	public void capitalFormattingTest() {
		assertEquals(store.getFormattedCapital(), "$100,000.00");
	}
	
	@Test
	public void singletonTest() {
		Store newStore = Store.generateStoreInstance();
		assertEquals(store, newStore);
	}
	
	@Test
	public void initialStockTest() {
		assertEquals(store.getStock(), new Stock());
	}
	
	@Test
	public void loadSalesTest() throws IOException, StockException {
		//Only include top 5 items in file for testing (rice to nuts inclusive)
		store.generateInitialStock();
				
		Item rice = Stock.getItem("rice");
		Item beans = Stock.getItem("beans");
		Item pasta = Stock.getItem("pasta");
		Item biscuits = Stock.getItem("biscuits");
		Item nuts = Stock.getItem("nuts");
		
		store.getStock().put(rice, 88);
		store.getStock().put(beans, 423);
		store.getStock().put(pasta, 43);
		store.getStock().put(biscuits, 394);
		store.getStock().put(nuts, 36);

		store.loadSalesLog("." + File.separator + "files" + File.separator + "sales-tests" + File.separator + "sales_log_primary.csv");

		assertEquals(Integer.valueOf(0), store.getStock().get(rice));
		assertEquals(Integer.valueOf(0), store.getStock().get(beans));
		assertEquals(Integer.valueOf(0), store.getStock().get(pasta));
		assertEquals(Integer.valueOf(0), store.getStock().get(biscuits));
		assertEquals(Integer.valueOf(0), store.getStock().get(nuts));
	}
	
	@Test
	public void importManifestTest() {
		store.generateInitialStock();
		Stock simulatedStock = new Stock();
		
		Item rice = Stock.getItem("rice");
		Item beans = Stock.getItem("beans");
		Item pasta = Stock.getItem("pasta");
		Item biscuits = Stock.getItem("biscuits");
		Item nuts = Stock.getItem("nuts");
		
		simulatedStock.put(rice, 300);
		simulatedStock.put(beans, 525);
		simulatedStock.put(pasta, 250);
		simulatedStock.put(biscuits, 575);
		simulatedStock.put(nuts, 250);
		
		Manifest manifest = new Manifest(simulatedStock);
		store.importManifest(manifest);
		
		assertEquals(Integer.valueOf(300), store.getStock().get(rice));
		assertEquals(Integer.valueOf(525), store.getStock().get(beans));
		assertEquals(Integer.valueOf(250), store.getStock().get(pasta));
		assertEquals(Integer.valueOf(575), store.getStock().get(biscuits));
		assertEquals(Integer.valueOf(250), store.getStock().get(nuts));

		//Delivery cost - $1975.00
		//Item cost - $5850.0
		assertEquals(92175.0, store.getCapital(), 0.1);
	}
	
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void getReorderStockBeforePointTest() {
		store.generateInitialStock();
		
		Item rice = Stock.getItem("rice");
		Item pasta = Stock.getItem("pasta");
		store.getStock().put(rice, 100);
		store.getStock().put(pasta, 50);
		
		Stock reorderStock = store.getReorderStock();
		
		assertEquals(Integer.valueOf(300), reorderStock.get(rice));
		assertEquals(Integer.valueOf(250), reorderStock.get(pasta));
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void getReorderStockAtPointTest() {
		store.generateInitialStock();
		Item rice = Stock.getItem("rice");
		store.getStock().put(rice, 225);
		
		Stock reorderStock = store.getReorderStock();
		
		assertEquals(Integer.valueOf(300), reorderStock.get(rice));
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void getReorderStockAbovePointTest() {
		store.generateInitialStock();
		Item rice = Stock.getItem("rice");
		store.getStock().put(rice, 300);
		
		Stock reorderStock = store.getReorderStock();
		
		//Null as the reorder stock shouldn't include any rice
		assertEquals(null, reorderStock.get(rice));
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void changePropertiesTest() throws IOException {
		store.generateInitialStock();
		Item rice = Stock.getItem("rice");
		store.getStock().put(rice, 225);
		
		assertEquals(2.0, rice.getManufacturingCost(), 0.1);
		assertEquals(Integer.valueOf(225), store.getStock().get(rice));
		assertEquals(Integer.valueOf(0), store.getStock().get(Stock.getItem("beans")));
		
		Stock.loadInItemProperties("." + File.separator + "files" + File.separator + "property-tests" + File.separator + "item_properties_increased_prices.csv");
		store.generateInitialStock();
		Item newRice = Stock.getItem("rice");
		
		assertEquals(20.0, newRice.getManufacturingCost(), 0.1);
		assertEquals(Integer.valueOf(225), store.getStock().get(newRice));
		assertEquals(Integer.valueOf(0), store.getStock().get(Stock.getItem("beans")));
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void changePropertiesLessItemsTest() throws IOException{
		store.generateInitialStock();
		Item rice = Stock.getItem("rice");
		store.getStock().put(rice, 225);
		Item chocolate = Stock.getItem("chocolate");
		store.getStock().put(chocolate, 100);
		
		assertEquals(24, Stock.getStockProperties().size());
		assertEquals(Integer.valueOf(225), store.getStock().get(rice));
		assertEquals(Integer.valueOf(100), store.getStock().get(chocolate));
		assertEquals(Integer.valueOf(0), store.getStock().get(Stock.getItem("bread")));
		
		Stock.loadInItemProperties("." + File.separator + "files" + File.separator + "property-tests" + File.separator + "item_properties_less.csv");
		store.generateInitialStock();
		
		Item newRice = Stock.getItem("rice");
		assertNull(newRice);
		Item newChocolate = Stock.getItem("chocolate");
	
		assertEquals(3, Stock.getStockProperties().size());
		assertEquals(Integer.valueOf(100), store.getStock().get(newChocolate));
		assertEquals(Integer.valueOf(0), store.getStock().get(Stock.getItem("bread")));
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void changePropertiesMoreItemsTest() throws IOException{
		store.generateInitialStock();
		Item rice = Stock.getItem("rice");
		store.getStock().put(rice, 225);
		
		assertEquals(24, Stock.getStockProperties().size());
		assertEquals(Integer.valueOf(225), store.getStock().get(rice));
		assertEquals(Integer.valueOf(0), store.getStock().get(Stock.getItem("bread")));
		
		Stock.loadInItemProperties("." + File.separator + "files" + File.separator + "property-tests" + File.separator + "item_properties_more.csv");
		store.generateInitialStock();
		
		Item newRice = Stock.getItem("rice");

		assertEquals(26, Stock.getStockProperties().size());
		assertEquals(Integer.valueOf(0), store.getStock().get(Stock.getItem("dry item extra")));
		assertEquals(Integer.valueOf(0), store.getStock().get(Stock.getItem("cold item extra")));
		assertEquals(Integer.valueOf(225), store.getStock().get(newRice));
		assertEquals(Integer.valueOf(0), store.getStock().get(Stock.getItem("bread")));
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void loadSalesWithNegativeQuantityTest() throws IOException{
		try {
			store.generateInitialStock();
			store.loadSalesLog("." + File.separator + "files" + File.separator + "sales-tests" + File.separator + "sales_log_negative.csv");
			fail();
		} catch (StockException e) {
			assertEquals("There was a negative number of items sold. Check sales log file.", e.getMessage());
		}
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void loadSalesAddingUnknownItemTest() throws IOException {		
		try {
			store.generateInitialStock();
			store.loadSalesLog("." + File.separator + "files" + File.separator + "sales-tests" + File.separator +  "sales_log_unknown_item.csv");
			fail();
		} catch (StockException e) {
			assertEquals("There was an unknown item sold. Check sales log file.", e.getMessage());
		}
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void soldMoreThanStockTest() throws IOException{
		try {
			store.generateInitialStock();
			Item rice = Stock.getItem("rice");
			store.getStock().put(rice, 10);
			store.loadSalesLog("." + File.separator + "files" + File.separator + "sales-tests" + File.separator + "sales_log_oversold.csv");
			fail();
		} catch (StockException e) {
			assertEquals("Sold more quantity than in the store. Check sales log file.", e.getMessage());
		}
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void incorrectQuantitySalesTest() throws IOException{
		try {
			store.generateInitialStock();
			store.loadSalesLog("." + File.separator + "files" + File.separator + "sales-tests" + File.separator + "sales_log_corrupted_quantities.csv");
			fail();
		} catch (StockException e) {
			assertEquals("Unable to parse sale quantities. Check sales log file.", e.getMessage());
		}
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void missingValuesSalesTest() throws IOException{
		try {
			store.generateInitialStock();
			store.loadSalesLog("." + File.separator + "files" + File.separator + "sales-tests" + File.separator + "sales_log_missing_values.csv");
			fail();
		} catch (StockException e) {
			assertEquals("Missing item name or quantity. Check sales log file.", e.getMessage());
		}
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void testUsingWrongSalesFile1() throws IOException, StockException {
		try {
			store.generateInitialStock();
			store.loadSalesLog("." + File.separator + "files" + File.separator + "initial_export.csv");
			fail();
		} catch (CSVFormatException e) {
			assertEquals("File does not match required format. Check sales log file.", e.getMessage());
		}
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void testUsingWrongSalesFile2() throws IOException, StockException {
		try {
			store.generateInitialStock();
			store.loadSalesLog("." + File.separator + "files" + File.separator + "item_properties.csv");
			fail();
		} catch (CSVFormatException e) {
			assertEquals("File does not match required format. Check sales log file.", e.getMessage());
		}
	}
}