package delivery;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import delivery.*;
import stock.*;

public class ManifestTest {
	
	private Manifest manifest; 
	private Stock stock = new Stock();
	private Store store = Store.generateStoreInstance();
	
	@Before 
	public void before() {
		stock = new Stock();

	}
	
	@Test
	public void testConstructorOfManifest() {
		stock.put(new Item("rice", 2.0, 3.0, 225, 375, null), 10);
		try {
			manifest = new Manifest(stock);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testManifestPrice1() {
		
	}

}
