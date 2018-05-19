package delivery;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import delivery.*;
import stock.*;

public class ManifestTest {
	
	Store store = Store.generateStoreInstance();
	private Stock stock = new Stock();
	
	@Before 
	public void before() {
		Stock.loadInItemProperties("./files/item_properties.csv");
		store.generateInitialStock();
	}
	
	/**
	 * Author: Lachlan Kuhr
	 */
	@Test
	public void testWithOneItem() {
		Item rice = Stock.getStockProperties().get(0);
		stock.put(rice, rice.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 1425, 0.1);
	}
	
	/**
	 * Author: Lachlan Kuhr
	 */
	@Test
	public void testWithTwoItems() {
		Item rice = Stock.getStockProperties().get(0);
		Item beans = Stock.getStockProperties().get(1);
		stock.put(rice, rice.getReorderAmount());
		stock.put(beans, beans.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 3656.25, 0.1);
	}
	
	/**
	 * Author: Lachlan Kuhr
	 */
	@Test
	public void testWithThreeItems() {
		Item rice = Stock.getStockProperties().get(0);
		Item beans = Stock.getStockProperties().get(1);
		Item pasta = Stock.getStockProperties().get(2);
		stock.put(rice, rice.getReorderAmount());
		stock.put(beans, beans.getReorderAmount());
		stock.put(pasta, pasta.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 5218.75, 0.1);
	}
	
	/**
	 * Author: Lachlan Kuhr
	 */
	@Test
	public void testWithFourItems() {
		Item rice = Stock.getStockProperties().get(0);
		Item beans = Stock.getStockProperties().get(1);
		Item pasta = Stock.getStockProperties().get(2);
		Item biscuits = Stock.getStockProperties().get(3);
		stock.put(rice, rice.getReorderAmount());
		stock.put(beans, beans.getReorderAmount());
		stock.put(pasta, pasta.getReorderAmount());
		stock.put(biscuits, biscuits.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 6512.5, 0.1);
	}
	
	
	/**
	 * Author: Lachlan Kuhr
	 */
	@Test
	public void testWithRoomAndColdTruck() {
		Item rice = Stock.getStockProperties().get(0);
		Item tomat = Stock.getStockProperties().get(9);
		stock.put(rice, rice.getReorderAmount());
		stock.put(tomat, tomat.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 1998, 0.1);
	}
	
	/**
	 * Author: Lachlan Kuhr
	 */
	@Test
	public void testWithRoomAndTwoColdTrucks() {	
		Item rice = Stock.getStockProperties().get(0);
		Item tomat = Stock.getStockProperties().get(9);
		Item lettuce = Stock.getStockProperties().get(10);
		Item grapes = Stock.getStockProperties().get(11);
		Item asparagus = Stock.getStockProperties().get(12);
		stock.put(rice, rice.getReorderAmount());
		stock.put(tomat, tomat.getReorderAmount());
		stock.put(lettuce, lettuce.getReorderAmount());
		stock.put(grapes, grapes.getReorderAmount());
		stock.put(asparagus, asparagus.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 4811.0282, 0.1);
	}
}