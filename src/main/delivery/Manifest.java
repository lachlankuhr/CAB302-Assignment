package delivery;

import java.io.IOException;
import java.util.*;

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
	public Manifest(String filePath) throws IOException, DeliveryException {
		ArrayList<ArrayList<String>> data = CSVReading.readCSV(filePath);
		
		Truck truckHolder = null;
		for(int i = 0; i < data.size(); i++) {
			
			if(data.get(i).get(0).equals(RefrigeratedTruck.MANIFEST_TAG)) {
				truckHolder = new RefrigeratedTruck();
				manifest.add(truckHolder);
			}else if(data.get(i).get(0).equals(OrdinaryTruck.MANIFEST_TAG)) {
				truckHolder = new OrdinaryTruck();
				manifest.add(truckHolder);
			}else {
				String itemName = data.get(i).get(0);
				Item item = Stock.getItem(itemName);

				truckHolder.addCargo(Integer.valueOf(data.get(i).get(1)), item);
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
