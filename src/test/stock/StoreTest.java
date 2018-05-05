package stock;

import static org.junit.Assert.*;

import org.junit.Test;

public class StoreTest {

	Store store = Store.generateStoreInstance();
	
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
	public void loadSalesTest() {
		//Manually load up item properties
		Stock.loadInItemProperties("C:\\Temp\\CAB302\\item_properties.csv");
		store.generateIntialStock();
		//Replace with absolute path to sales_log_0.csv off Blackboard
		//Only include top 5 items in file for testing (rice to nuts inclusive)
		store.loadSalesLog("C:\\Temp\\CAB302\\sales_log_0.csv");
				
		Item rice = Stock.getStockProperties().get(0);
		Item beans = Stock.getStockProperties().get(1);
		Item pasta = Stock.getStockProperties().get(2);
		Item biscuits = Stock.getStockProperties().get(3);
		Item nuts = Stock.getStockProperties().get(4);
				
		assertEquals(Integer.valueOf(88), store.getStock().get(rice));
		assertEquals(Integer.valueOf(423), store.getStock().get(beans));
		assertEquals(Integer.valueOf(43), store.getStock().get(pasta));
		assertEquals(Integer.valueOf(394), store.getStock().get(biscuits));
		assertEquals(Integer.valueOf(36), store.getStock().get(nuts));

	}

}
