package delivery;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.*;

import csv.CSVFormatException;
import delivery.*;
import stock.*;

public class ManifestTest {
	
	Store store;
	private Stock stock;
	Manifest manifest;
	
	@Before 
	public void before() throws IOException {
		stock = new Stock();
		store = Store.generateStoreInstance();
		Stock.loadInItemProperties("." + File.separator + "files" + File.separator + "item_properties.csv");
		store.generateInitialStock();
	}
	
	@After 
	public void after() {
		Store.destoryStore();
		manifest = null;
	}
	
	// Exceptional behavior tests
	
	/**
	 * @author Lachlan Kuhr
	 * @throws DeliveryException 
	 * @throws IOException 
	 */
	@Test
	public void testLoadingManifestWithColdItemInOrdinaryTruck() throws IOException {
		try {
		manifest = new Manifest("." + File.separator + "files" + File.separator + 
				"manifest-tests" + File.separator + 
				"testLoadingManifestWithColdItemInOrdinaryTruck.csv");
		} catch (DeliveryException e) {
			assertEquals("There was at least one cold item in an ordinary truck. Check manifest file.", e.getMessage());
		}
	}
	
	/**
	 * @author Lachlan Kuhr
	 * @throws IOException 
	 */
	@Test
	public void testingLoadingManifestWithTooManyItemsInOrdinaryTruck() throws IOException {
		try {
		manifest = new Manifest("." + File.separator + "files" + File.separator + 
				"manifest-tests" + File.separator + 
				"testingLoadingManifestWithTooManyItemsInOrdinaryTruck.csv");
		} catch (DeliveryException e) {
			assertEquals("There was too many items in an ordinary truck. Check manifest file.", e.getMessage());
		}
	}
	
	/**
	 * @author Lachlan Kuhr
	 * @throws IOException 
	 */
	@Test
	public void testingLoadingManifestWithTooManyItemsInRefrigeratedTruck() throws IOException {
		try {
			manifest = new Manifest("." + File.separator + "files" + File.separator + 
					"manifest-tests" + File.separator + 
					"testingLoadingManifestWithTooManyItemsInRefrigeratedTruck.csv");
			} catch (DeliveryException e) {
				assertEquals("There was too many items in a refrigerated truck. Check manifest file.", e.getMessage());
			}
	}
	
	/**
	 * @author Lachlan Kuhr
	 * @throws IOException 
	 */
	@Test
	public void testLoadingManifestWithUnknownTruckTypeOrdinary() throws IOException {
		try {
			manifest = new Manifest("." + File.separator + "files" + File.separator + 
					"manifest-tests" + File.separator + 
					"testLoadingManifestWithUnknownTruckTypeOrdinary.csv");
			} catch (DeliveryException e) {
				assertEquals("There was an unkown truck type loaded in. Check manifest file.", e.getMessage());
			}
	}
	
	/**
	 * @author Lachlan Kuhr
	 * @throws IOException 
	 */
	@Test
	public void testLoadingManifestWithUnknownTruckTypeRefrigerated() throws IOException {
		try {
			manifest = new Manifest("." + File.separator + "files" + File.separator + 
					"manifest-tests" + File.separator + 
					"testLoadingManifestWithUnknownTruckTypeRefrigerated.csv");
			} catch (DeliveryException e) {
				assertEquals("There was an unkown truck type loaded in. Check manifest file.", e.getMessage());
			}
	}
	
	/**
	 * @author Lachlan Kuhr
	 * @throws IOException 
	 */
	@Test
	public void testLoadingManifestWithNegativeItemQuantities() throws IOException {
		try {
			manifest = new Manifest("." + File.separator + "files" + File.separator + 
					"manifest-tests" + File.separator + 
					"testLoadingManifestWithNegativeItemQuantities.csv");
			} catch (DeliveryException e) {
				assertEquals("There was a negative amount of items in manifest. Check manifest file.", e.getMessage());
			}
	}
	
	/**
	 * @author Lachlan Kuhr
	 * @throws IOException 
	 */
	@Test
	public void testLoadingManifestUnknownItem1() throws IOException {
		try {
			manifest = new Manifest("." + File.separator + "files" + File.separator + 
					"manifest-tests" + File.separator + 
					"testLoadingManifestUnknownItem1.csv");
			} catch (DeliveryException e) {
				assertEquals("The manifest contain an item not contained in the currently known item properties. Check manifest file.", e.getMessage());
			}
	}
	
	/**
	 * @author Lachlan Kuhr
	 * @throws IOException 
	 */
	@Test
	public void testLoadingManifestUnknownItem2() throws IOException {
		try {
			manifest = new Manifest("." + File.separator + "files" + File.separator + 
					"manifest-tests" + File.separator + 
					"testLoadingManifestUnknownItem2.csv");
			} catch (DeliveryException e) {
				assertEquals("The manifest contain an item not contained in the currently known item properties. Check manifest file.", e.getMessage());
			}
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testUsingWrongManifestFile1() throws IOException, StockException, DeliveryException {
		try {
			manifest = new Manifest("." + File.separator + "files" + File.separator + "sales_log_0.csv");
			fail();
		} catch (CSVFormatException e) {
			assertEquals("File does not match required format. Check manifest file.", e.getMessage());
		}
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testUsingWrongManifestFile2() throws IOException, StockException, DeliveryException {
		try {
			manifest = new Manifest("." + File.separator + "files" + File.separator + "item_properties.csv");
			fail();
		} catch (CSVFormatException e) {
			assertEquals("File does not match required format. Check manifest file.", e.getMessage());
		}
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testGettingManifestCollection() {
		manifest = new Manifest(stock);
		assertTrue(manifest.getManifestCollection() instanceof ArrayList<?>);
	}
	
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test 
	public void testConstructor() {
		try {
			new Manifest(stock);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}
	
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testWithOneItem() {
		Item rice = Stock.getItem("rice");
		stock.put(rice, rice.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 1425, 0.1);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testWithTwoItems() {
		Item rice = Stock.getItem("rice");
		Item beans = Stock.getItem("beans");
		stock.put(rice, rice.getReorderAmount());
		stock.put(beans, beans.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 3656.25, 0.1);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testWithThreeItems() {
		Item rice = Stock.getItem("rice");
		Item beans = Stock.getItem("beans");
		Item pasta = Stock.getItem("pasta");
		stock.put(rice, rice.getReorderAmount());
		stock.put(beans, beans.getReorderAmount());
		stock.put(pasta, pasta.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 5218.75, 0.1);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testWithFourItems() {
		Item rice = Stock.getItem("rice");
		Item beans = Stock.getItem("beans");
		Item pasta = Stock.getItem("pasta");
		Item biscuits = Stock.getItem("biscuits");
		stock.put(rice, rice.getReorderAmount());
		stock.put(beans, beans.getReorderAmount());
		stock.put(pasta, pasta.getReorderAmount());
		stock.put(biscuits, biscuits.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 6512.5, 0.1);
	}
	
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testWithRoomAndColdTruck() {
		Item rice = Stock.getItem("rice");
		Item tomat = Stock.getItem("tomatoes");
		stock.put(rice, rice.getReorderAmount());
		stock.put(tomat, tomat.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 1998, 0.1);
	}
	
	/**
	 * @author Lachlan Kuhr
	 */
	@Test
	public void testWithRoomAndTwoColdTrucks() {	
		Item rice = Stock.getItem("rice");
		Item tomat = Stock.getItem("tomatoes");
		Item lettuce = Stock.getItem("lettuce");
		Item grapes = Stock.getItem("grapes");
		Item asparagus = Stock.getItem("asparagus");
		stock.put(rice, rice.getReorderAmount());
		stock.put(tomat, tomat.getReorderAmount());
		stock.put(lettuce, lettuce.getReorderAmount());
		stock.put(grapes, grapes.getReorderAmount());
		stock.put(asparagus, asparagus.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 4811.0282, 0.1);
	}
}