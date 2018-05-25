package csv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import delivery.DeliveryException;
import delivery.OrdinaryTruck;
import delivery.RefrigeratedTruck;
import delivery.Truck;
import stock.Item;

public class CSVWritingTest {

	@Test
	public void testWritingManifest1() throws DeliveryException, IOException {
		Truck truck1 = new OrdinaryTruck();
		Truck truck2 = new RefrigeratedTruck();
		
		truck1.addCargo(77, new Item("rice", 2.0, 3.0, 225, 300, null));
		truck2.addCargo(78, new Item("rice", 2.0, 3.0, 225, 300, 7.0));
		
		ArrayList<Truck> manifest = new ArrayList<Truck>();
		manifest.add(truck1);
		manifest.add(truck2);
		
		//Using object for static method solely for coverage - other test uses static method to test both.
		//.writeManifest method can be called statically.
		CSVWriting csvWriter = new CSVWriting();
		csvWriter.writeManifest(manifest, "." + File.separator + "files" + File.separator + "manifest-tests" + File.separator + "manifest-writing-tests" + File.separator + "manifest_test_writing1.csv");
		// check to see if generated correctly ["worked on my computer"]
		// passing of this test doesn't mean it is implemented correctly 
		// need to check if file looks correct by looking at the above directory. 
	}
	
	@Test
	public void testWritingManifest2() throws DeliveryException, IOException {
		Truck truck1 = new OrdinaryTruck();
		Truck truck2 = new RefrigeratedTruck();
		
		truck1.addCargo(77, new Item("rice", 2.0, 3.0, 225, 300, null));
		truck1.addCargo(500, new Item("biscuits", 2.0, 5.0, 450, 575, null));
		truck2.addCargo(78, new Item("rice", 2.0, 3.0, 225, 300, 7.0));
		truck2.addCargo(77, new Item("chicken", 2.0, 3.0, 225, 300, 7.0));
		truck2.addCargo(7, new Item("beef", 2.0, 3.0, 225, 300, 7.0));
		truck2.addCargo(77, new Item("chips", 2.0, 3.0, 225, 300, 7.0));
		
		ArrayList<Truck> manifest = new ArrayList<Truck>();
		manifest.add(truck1);
		manifest.add(truck2);
		
		//Using static access to method .writeManifest - other test tests object access.
		CSVWriting.writeManifest(manifest, "." + File.separator + "files" + File.separator + "manifest-tests" + File.separator + "manifest-writing-tests" + File.separator + "manifest_test_writing2.csv");
		// check to see if generated correctly ["worked on my computer"]
		// passing of this test doesn't mean it is implemented correctly 
		// need to check if file looks correct by looking at the above directory. 
	}


}
