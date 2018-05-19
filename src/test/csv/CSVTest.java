package csv;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import delivery.DeliveryException;
import delivery.OrdinaryTruck;
import delivery.RefrigeratedTruck;
import delivery.Truck;
import stock.Item;
import stock.Stock;

public class CSVTest {

	@Test
	public void test() throws DeliveryException, IOException {
		Stock stock1 = new Stock();
		Stock stock2 = new Stock();
		stock1.put(new Item("rice", 2.0, 3.0, 225, 300, null), 77);
		stock2.put(new Item("rice", 2.0, 3.0, 225, 300, 7.0), 78);
		Truck truck1 = new OrdinaryTruck(stock1);
		Truck truck2 = new RefrigeratedTruck(stock2);
		
		ArrayList<Truck> manifest = new ArrayList<Truck>();
		manifest.add(truck1);
		manifest.add(truck2);
		
		CSVWriting.writeManifest(manifest, "C:\\Temp\\CAB302\\test.csv");
		// check to see if generated correctly ["worked on my computer" :p ]
	}

}
