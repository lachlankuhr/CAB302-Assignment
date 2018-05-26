package delivery;

import java.io.IOException;
import java.util.*;

import csv.CSVFormatException;
import csv.CSVReading;
import stock.Item;
import stock.Stock;

public class Manifest {
	
	/**
	 * A collection of trucks that 'deliver' ordered items to the store.
	 * @author Atrey Gajjar
	 */
	
	//Collection storing the truck objects.
	private ArrayList<Truck> manifest = new ArrayList<Truck>();

	/** 
	 * Constructor for the manifest during the reorder/export period.
	 * Calls an optimisation method that creates manifest from the stock needing reordering.
	 * @param stock - Stock object containing inventory of store to generate manifest from.
	 * @author Atrey Gajjar
	 */
	public Manifest(Stock reorderStock) {
		calculateOptimisedManifest(reorderStock);
	}
	
	/**
	 * Constructor for manifest during the delivery/import period.
	 * Creates a manifest representation of the information in a .csv file.
	 * @param filePath - File path to .csv file
	 * @throws IOException 
	 * @author Atrey Gajjar
	 */
	public Manifest(String filePath) throws IOException, DeliveryException, CSVFormatException {
		ArrayList<ArrayList<String>> data = CSVReading.readCSV(filePath);
		
		if(!data.get(0).get(0).startsWith(">")) {
			throw new CSVFormatException("File does not match required format. Check manifest file.");
		}
		
		Truck truckHolder = null;
		for(int i = 0; i < data.size(); i++) {
			
			if(data.get(i).size() != 1 && data.get(i).size() != 2) {
				throw new CSVFormatException("File does not match required format. Check manifest file.");
			}
			
			if(data.get(i).get(0).equals(RefrigeratedTruck.MANIFEST_TAG)) {
				truckHolder = new RefrigeratedTruck();
				manifest.add(truckHolder);
			}else if(data.get(i).get(0).equals(OrdinaryTruck.MANIFEST_TAG)) {
				truckHolder = new OrdinaryTruck();
				manifest.add(truckHolder);
			}else if(data.get(i).get(0).startsWith(">")){
				throw new DeliveryException("There was an unkown truck type loaded in. Check manifest file.");
			}else {
				
				String itemName = data.get(i).get(0);
				Item item = Stock.getItem(itemName);

				if(item != null) {					
					truckHolder.addCargo(Integer.valueOf(data.get(i).get(1)), item);
				} else {
					throw new DeliveryException("The manifest contain an item not contained in the currently known item properties. Check manifest file.");
				}
			}
			
		}
	}
	
	/**
	 * Calculates the optimised manifest based on what items need to be reordered.
	 * Limits the number of refrigerated trucks and empty spaces in each truck.
	 * @param reorderStock - Stock object representing what items need to be reordered and their quantities
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
			
	
			int remainingSpace = currentTruck.getMaxCargo() - currentTruck.getCargoQuantity();
			
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
	 * Gets the manifest, represented as a collection of trucks
	 * @return An ArrayList of trucks in the manifest
	 * @author Atrey Gajjar
	 */
	public ArrayList<Truck> getManifestCollection() {
		return manifest;
	}
	
	/**
	 * Calculates the cost of the entire manifest. Includes both the truck delivery costs and item purchasing costs.
	 * @throws DeliveryException 
	 * @return The total cost of receiving the manifest
	 * @author Atrey Gajjar
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
