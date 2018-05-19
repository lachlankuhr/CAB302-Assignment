package csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import delivery.Truck;
import stock.Item;
import stock.Stock;

public class CSVWriting {
	
	public static void writeManifest(ArrayList<Truck> manifest, String filePath) throws IOException {
		FileWriter writer;
		
		writer = new FileWriter(filePath);
		
		for (Truck truck : manifest) {
			writer.write(truck.getManifestIdentification() + "\n");
			Stock truckStock = truck.getCargo(); 
			for (Map.Entry<Item, Integer> cargo : truckStock.entrySet()) {
				writer.write(cargo.getKey().getName().toString() + "," + 
						     cargo.getValue().toString() + "\n");
			}
		}
		
		writer.close();

	}
}
