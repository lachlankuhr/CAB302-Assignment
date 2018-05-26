package csv;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * Test cases for CSVReading class.
 * @author Atrey Gajjar
 */
public class CSVReadingTest {
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void testReadingItemProperties() throws IOException {
		CSVReading csvReader = new CSVReading();
		
		//Accesing .readCSV with object, instead of static, for coverage - other tests check functionality in a static manner
		ArrayList<ArrayList<String>> actual = csvReader.readCSV("." + File.separator + "files" + File.separator + "csv-tests" + File.separator + "item_properties_short.csv");
		ArrayList<ArrayList<String>> expected = new ArrayList<>();
		
		expected.add(new ArrayList<>(Arrays.asList("rice", "2", "3", "225", "300")));
		expected.add(new ArrayList<>(Arrays.asList("beans", "4", "6", "450", "525")));
		expected.add(new ArrayList<>(Arrays.asList("frozen meat", "10", "14", "450", "575", "-14")));
		expected.add(new ArrayList<>(Arrays.asList("frozen vegetable mix", "5", "8", "225", "325", "-12")));
		
		assertEquals(expected, actual);
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void testReadingManifest() throws IOException {
		
		ArrayList<ArrayList<String>> actual = CSVReading.readCSV("." + File.separator + "files" + File.separator + "csv-tests" + File.separator + "manifest.csv");
		ArrayList<ArrayList<String>> expected = new ArrayList<>();

		expected.add(new ArrayList<>(Arrays.asList(">Refrigerated")));
		expected.add(new ArrayList<>(Arrays.asList("ice cream", "100")));
		expected.add(new ArrayList<>(Arrays.asList("cheese", "75")));
		expected.add(new ArrayList<>(Arrays.asList("rice", "90")));
		expected.add(new ArrayList<>(Arrays.asList(">Ordinary")));
		expected.add(new ArrayList<>(Arrays.asList("biscuits", "50")));
		expected.add(new ArrayList<>(Arrays.asList("bread", "10")));
		
		assertEquals(expected, actual);
	}
	
	/**
	 * @author Atrey Gajjar
	 */
	@Test
	public void testReadingSalesLog() throws IOException {
		ArrayList<ArrayList<String>> actual = CSVReading.readCSV("." + File.separator + "files" + File.separator + "csv-tests" + File.separator + "sales_log_short.csv");
		ArrayList<ArrayList<String>> expected = new ArrayList<>();
		
		expected.add(new ArrayList<>(Arrays.asList("rice", "88")));
		expected.add(new ArrayList<>(Arrays.asList("beans", "423")));
		expected.add(new ArrayList<>(Arrays.asList("frozen meat", "220")));
		expected.add(new ArrayList<>(Arrays.asList("frozen vegetable mix", "109")));

		assertEquals(expected, actual);
	}

}