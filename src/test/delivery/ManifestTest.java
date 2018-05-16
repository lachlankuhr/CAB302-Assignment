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
		store.generateIntialStock();
	}
	
	/**
	 * Author: Lachlan Kuhr
	 */
	@Test
	public void testWithOneItem() {
		Item rice = Stock.getStockProperties().get(0);
		stock.put(rice, rice.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 825, 0.1);
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
		assertEquals(manifest.calculateCostOfManifest(), 956.25, 0.1);
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
		assertEquals(manifest.calculateCostOfManifest(), 1768.75, 0.1);
	}
	
	/**
	 * Author: Lachlan Kuhr
	 */
	@Test
	public void testWithFourItems() {
		Item rice = Stock.getStockProperties().get(0);
		Item beans = Stock.getStockProperties().get(1);
		Item pasta = Stock.getStockProperties().get(2);
		Item biscuits = Stock.getStockProperties().get(2);
		stock.put(rice, rice.getReorderAmount());
		stock.put(beans, beans.getReorderAmount());
		stock.put(pasta, pasta.getReorderAmount());
		stock.put(biscuits, biscuits.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 912.5, 0.1);
	}
	
	
	/**
	 * Author: Lachlan Kuhr
	 */
	@Test
	public void testWithRoomAndColdTruck() {
		Item rice = Stock.getStockProperties().get(0);
		Item tomat = Stock.getStockProperties().get(10);
		stock.put(rice, rice.getReorderAmount());
		stock.put(tomat, tomat.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 1823, 0.1);
	}
	
	/**
	 * Author: Lachlan Kuhr.
	 */
	@Test
	public void testWithRoomAndTwoColdTrucks() {
		Item rice = Stock.getStockProperties().get(0);
		Item tomat = Stock.getStockProperties().get(10);
		Item tomat2 = Stock.getStockProperties().get(11);
		Item tomat3 = Stock.getStockProperties().get(12);
		Item tomat4 = Stock.getStockProperties().get(13);
		stock.put(rice, rice.getReorderAmount());
		stock.put(tomat, tomat.getReorderAmount());
		stock.put(tomat2, tomat2.getReorderAmount());
		stock.put(tomat3, tomat3.getReorderAmount());
		stock.put(tomat4, tomat4.getReorderAmount());
		Manifest manifest = new Manifest(stock);
		assertEquals(manifest.calculateCostOfManifest(), 1946.02, 0.1);
	}
}
