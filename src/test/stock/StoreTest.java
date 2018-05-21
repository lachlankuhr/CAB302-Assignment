package stock;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
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
	public void loadSalesTest() throws IOException {
		//Manually load up item properties
		Stock.loadInItemProperties("." + File.separator + "files" + File.separator + "item_properties.csv");
		store.generateInitialStock();
		
		//Replace with absolute path to sales_log_0.csv off Blackboard
		//Only include top 5 items in file for testing (rice to nuts inclusive)
		Item beans = Stock.getStockProperties().get(1);
		store.loadSalesLog("." + File.separator + "files" + File.separator + "sales_log_0.csv");
				
		Item rice = Stock.getStockProperties().get(0);
		Item pasta = Stock.getStockProperties().get(2);
		Item biscuits = Stock.getStockProperties().get(3);
		Item nuts = Stock.getStockProperties().get(4);

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
		Stock.loadInItemProperties("." + File.separator + "files" + File.separator + "item_properties.csv");
		store.generateInitialStock();
		
		Item rice = Stock.getStockProperties().get(0);
		Item pasta = Stock.getStockProperties().get(2);
		store.getStock().put(rice, 100);
		store.getStock().put(pasta, 50);
		
		Stock reorderStock = store.getReorderStock();
		
		assertEquals(Integer.valueOf(300), reorderStock.get(rice));
		assertEquals(Integer.valueOf(250), reorderStock.get(pasta));
		
		
	}
}
