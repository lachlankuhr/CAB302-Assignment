package delivery;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import delivery.*;
import stock.*;

public class ManifestTest {
	
	private Manifest manifest; 
	private Stock<Item> stock;
	private ArrayList<Truck> manifestResult;
	
	@Before
	public void Before() {
		manifest = new Manifest(stock);
		manifestResult = manifest.calculateOptimisedManifest();
	}

	@Test
	public void testConstructorOfManifest() {
		assertEquals(manifest, new Manifest(stock));
	}
	
	@Test
	public void testManifestGeneration() {
		assertEquals(manifestResult.get(1).getCargo().get(
				new Item("ice cream", 8.0, 14.0, 175, 250, -20.0)).intValue(),
				100);
		
	}

}
