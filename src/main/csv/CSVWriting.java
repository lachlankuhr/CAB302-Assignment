package csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import delivery.Truck;
import stock.Item;
import stock.Stock;

/**
 * Provides writing of CSV functionality. 
 * Only static implementation.
 * @author Lachlan Kuhr
 */


public class CSVWriting {
	
	/**
	 * Static method to write the manifest (collection of trucks) to csv. 
	 * @author Lachlan Kuhr
	 * @param manifest The collection of trucks that will be wrote to a csv.
	 * @param filePath The file directory to which the is to be wrote to. 
	 * @throws IOException Throws IOException upon failure to write file. 
	 */
	
	public static void writeManifest(ArrayList<Truck> manifest, String filePath) throws IOException {
		FileWriter writer = new FileWriter(filePath);
		
		for (Truck truck : manifest) {
			writer.write(truck.getManifestIdentification() + "\n"); // truck label
			Stock truckStock = truck.getCargo(); 
			for (Map.Entry<Item, Integer> cargo : truckStock.entrySet()) {
				writer.write(cargo.getKey().getName().toString() + "," + 
						     cargo.getValue().toString() + "\n"); 
			}
		}
		
		writer.close();

	}
}
