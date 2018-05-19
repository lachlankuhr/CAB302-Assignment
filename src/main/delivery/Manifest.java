package delivery;

import java.util.*;

import csv.CSVReading;
import stock.Item;
import stock.Stock;

public class Manifest {
	
	/**
	 * A collection of trucks.
	 */
	
	private ArrayList<Truck> manifest = new ArrayList<Truck>(); // List of trucks
	private final String REFRIGERATED_TRUCK_STRING = ">Refrigerated";
	private final String ORDINARY_TRUCK_STRING = ">Ordinary";
	private int numRefrigerated = 0;
	private int numOrdinary = 0;
	/** 
	 * The constructor for the manifest.
	 * 	@param stock - Stock object containing inventory of store to generate manifest from
	 */
	
	public Manifest(Stock reorderStock) {
		calculateOptimisedManifest(reorderStock);
	}
	
	/**
	 * Constructor for manifest that creates a manifest representation of a .csv file
	 * @param filePath - File path to .csv file
	 */
	public Manifest(String filePath) {
		ArrayList<ArrayList<String>> data = CSVReading.readCSV(filePath);
		
		Truck truckHolder = null;
		for(int i = 0; i < data.size(); i++) {
			
			if(data.get(i).get(0).equals(REFRIGERATED_TRUCK_STRING)) {
				truckHolder = new RefrigeratedTruck();
				numRefrigerated++;
				manifest.add(truckHolder);
			}else if(data.get(i).get(0).equals(ORDINARY_TRUCK_STRING)) {
				truckHolder = new OrdinaryTruck();
				numOrdinary++;
				manifest.add(truckHolder);
			}else {
				//Should really change how item properties are kept
				String itemName = data.get(i).get(0);
				ArrayList<Item> itemProperties = Stock.getStockProperties();
				for(int j = 0; j < itemProperties.size(); j++) {
					Item item = itemProperties.get(j);
					if(item.getName().equals(itemName)) {
						truckHolder.getCargo().put(item, Integer.valueOf(data.get(i).get(1)));
					}
				}

			}
			
		}
	}
	
	/**
	 * Calculates the optimised manifest. 
	 * @author Atrey Gajjar
	 */
	
	private void calculateOptimisedManifest(Stock reorderStock) {
		
		Truck currentTruck = null;
		
		while(reorderStock.size() > 0) {

			Item coldestItem = reorderStock.findColdestItem();
			
			if(currentTruck == null) {
				if(coldestItem.getTemperature() == null) {
					currentTruck = new OrdinaryTruck();
				}else {
					currentTruck = new RefrigeratedTruck();
				}
			}
			
	
			int remainingSpace = currentTruck.MAX_CARGO - currentTruck.getCargoQuantity();
			
			if(remainingSpace > 0) {
				int itemQuantity = reorderStock.get(coldestItem);
				if(remainingSpace >= itemQuantity) {
					currentTruck.getCargo().put(coldestItem, itemQuantity);
					reorderStock.remove(coldestItem);
				}else {
					currentTruck.getCargo().put(coldestItem, remainingSpace);
					reorderStock.put(coldestItem, itemQuantity - remainingSpace);
				}
			}else {
				manifest.add(currentTruck);
				currentTruck = null;
			}
		}
		
		manifest.add(currentTruck);
		
	}
	
	
	/**
	 * Gets the number of refrigerated trucks in manifest.
	 * @return Number of refrigerated trucks in manifest
	 */
	
	public int getNumberOfRefrigeratedTrucks() {
		return numRefrigerated;
	}
	
	/**
	 * Gets the number of cold trucks in manifest.
	 * @return Number of cold trucks in manifest
	 */
	
	public int getNumberOfOrdinaryTrucks() {
		return numOrdinary;
	}
	
	public ArrayList<Truck> getManifestCollection() {
		return manifest;
	}
	
	/**
	 * Calculates the cost of the entire manifest. 
	 */
	
	public double calculateCostOfManifest() {
		
		double cost = 0;
		for (Truck truck : manifest) {
			cost += truck.calculateCostOfDelivery();
			cost += truck.getCargo().calculateCostOfCargo();
		}
		return cost;
	}
	
}
