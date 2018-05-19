package delivery;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.*;

import delivery.*;
import stock.*;

public class ManifestTest {
	
	Store store = Store.generateStoreInstance();
	private Stock stock;
	
	@Before 
	public void before() {
		Stock.loadInItemProperties("." + File.separator + "files" + File.separator + "item_properties.csv");
		store.generateInitialStock();
		stock = new Stock();
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