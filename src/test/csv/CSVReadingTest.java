package csv;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;


public class CSVReadingTest {
	
	@Test
	public void testReadingItemProperties() throws IOException {
		CSVReading.readCSV("." + File.separator + "files" + File.separator + "item_properties.csv");
	}
	
	@Test
	public void testReadingManifest() throws IOException {
		CSVReading.readCSV("." + File.separator + "files" + File.separator + "manifest.csv");
	}
	
	@Test
	public void testReadingSalesLog() throws IOException {
		CSVReading.readCSV("." + File.separator + "files" + File.separator + "sales_log_0.csv");
	}

}