package stock;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	public void loadSalesTest() throws IOException {
		//Only include top 5 items in file for testing (rice to nuts inclusive)
		store.generateInitialStock();
		store.loadSalesLog("." + File.separator + "files" + File.separator + "sales_log_0.csv");
				
		Item rice = Stock.getItem("rice");
		Item beans = Stock.getItem("beans");
		Item pasta = Stock.getItem("pasta");
		Item biscuits = Stock.getItem("biscuits");
		Item nuts = Stock.getItem("nuts");

		assertEquals(Integer.valueOf(-88), store.getStock().get(rice));
		assertEquals(Integer.valueOf(-423), store.getStock().get(beans));
		assertEquals(Integer.valueOf(-43), store.getStock().get(pasta));
		assertEquals(Integer.valueOf(-394), store.getStock().get(biscuits));
		assertEquals(Integer.valueOf(-36), store.getStock().get(nuts));
	}
	
	/**
	 * @author Atrey Gajjar
	 * @throws IOException 
	 */
	@Test
	public void getReorderStockTest() throws IOException {
		store.generateInitialStock();
		
		Item rice = Stock.getItem("rice");
		Item pasta = Stock.getItem("pasta");
		store.getStock().put(rice, 100);
		store.getStock().put(pasta, 50);
		
		Stock reorderStock = store.getReorderStock();
		
		assertEquals(Integer.valueOf(300), reorderStock.get(rice));
		assertEquals(Integer.valueOf(250), reorderStock.get(pasta));
		
		
	}
}