package delivery;

import static org.junit.Assert.*;

import org.junit.*;

import delivery.*;
import stock.*;

public class ManifestTest {
	
	private Manifest manifest; 
	
	@Before
	public void Before() {
		Stock stock = new Stock();
		stock.put(new Item("rice", 2, 3, 225, 300, null), 50);
		stock.put(new Item("rice", 2, 3, 225, 300, null), 50);
		stock.put(new Item("rice", 2, 3, 225, 300, null), 50);
		stock.put(new Item("rice", 2, 3, 225, 300, null), 50);
		stock.put(new Item("rice", 2, 3, 225, 300, null), 50);
		stock.put(new Item("rice", 2, 3, 225, 300, null), 50);
		stock.put(new Item("rice", 2, 3, 225, 300, null), 50);
		stock.put(new Item("rice", 2, 3, 225, 300, null), 50);
		stock.put(new Item("rice", 2, 3, 225, 300, null), 50);
		
		manifest = new Manifest(stock); 
	}

	@Test
	public void TestConstructorOfManifest() {
		fail();
	}

}
