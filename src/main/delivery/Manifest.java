package delivery;

import java.io.IOException;
import java.util.*;

import csv.CSVFormatException;
import csv.CSVReading;
import stock.Item;
import stock.Stock;

/**
 * A collection of trucks representing the items needed for reordering, and how they are stored in various trucks.
 * @author Atrey Gajjar
 */
public class Manifest {
	
	
	//Collection storing the truck objects.
	private ArrayList<Truck> manifest = new ArrayList<Truck>();

	/** 
	 * Constructor for the manifest during the reorder/export period.
	 * Calls an optimisation method that creates manifest from the stock needing reordering.
	 * @param stock - Stock object containing inventory of store to generate manifest from.
	 * @throws DeliveryException When stock does not require reordering based off its quantity and reorder points
	 * @author Atrey Gajjar
	 */
	public Manifest(Stock reorderStock) throws DeliveryException {
		if(reorderStock.size() == 0) {
			throw new DeliveryException("All items are stocked appropriately, hence a manifest cannot be created.");
		}
		calculateOptimisedManifest(reorderStock);
	}
	
	/**
	 * Constructor for manifest during the delivery/import period.
	 * Creates a manifest representation of the information in a .csv file.
	 * @param filePath - File path to .csv file
	 * @throws IOException When there is an error reading from file path specified
	 * @throws DeliveryException When contents of manifest file are incorrect.
	 * @throws CSVFormatException When file doesn't meet format of a manifest file, not allowing it to parse properly.
	 * @author Atrey Gajjar
	 */
	public Manifest(String filePath) throws IOException, DeliveryException, CSVFormatException {
		ArrayList<ArrayList<String>> data = CSVReading.readCSV(filePath);
		
		//If the first entry isn't a possible truck tag, file cannot be a manifest file
		if(!data.get(0).get(0).startsWith(">")) {
			throw new CSVFormatException("File does not match required format. Check manifest file.");
		}
		
		//Create a truck to place items into
		Truck truckHolder = null;
		
		for(int i = 0; i < data.size(); i++) {
			//If any entry is not 1 or 2 values long, cannot be a manifest file
			if(data.get(i).size() != 1 && data.get(i).size() != 2) {
				throw new CSVFormatException("File does not match required format. Check manifest file.");
			}
			
			//Create a new truck depending on which tag gets matched and add it to the manifest
			if(data.get(i).get(0).equals(RefrigeratedTruck.MANIFEST_TAG)) {
				truckHolder = new RefrigeratedTruck();
				manifest.add(truckHolder);
			}else if(data.get(i).get(0).equals(OrdinaryTruck.MANIFEST_TAG)) {
				truckHolder = new OrdinaryTruck();
				manifest.add(truckHolder);
			}else if(data.get(i).get(0).startsWith(">")){
				//If there is a truck tag, but doesn't match ordinary or refrigerated trucks
				throw new DeliveryException("There was an unkown truck type loaded in. Check manifest file.");
			}else {
				
				//Find item from stock through and place it in the cargo of a truck. Throws error if the item cannot be found or quantity is negative.
				String itemName = data.get(i).get(0);
				Item item = Stock.getItem(itemName);
				int quantity = Integer.valueOf(data.get(i).get(1));
				
				if(quantity < 0) {
					throw new DeliveryException("There was a negative amount of items in manifest. Check manifest file.");
				}else {}
				
				if(item != null) {					
					truckHolder.addCargo(quantity, item);
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
		
		//Truck variable to store a truck that is currently being filled up
		Truck currentTruck = null;
		
		//Loop until all reordering items are placed into a truck
		while(reorderStock.size() > 0) {

			//Find coldest item in the stock - need to fill from coldest to warmed for optimisation purposes
			Item coldestItem = reorderStock.findColdestItem();
			
			//Create a truck if there is no truck being filled up currently, type depends on whether there are cold items in the reorder stock still
			if(currentTruck == null) {
				if(coldestItem.getTemperature() == null) {
					currentTruck = new OrdinaryTruck();
				}else {
					currentTruck = new RefrigeratedTruck();
				}
			}
			
			
			int remainingSpace = currentTruck.getMaxCargo() - currentTruck.getCargoQuantity();
			
			if(remainingSpace > 0) {
				//If there is still space in the truck put the current coldest item in it
				//If space, put all of the item in and remove it from the reordering stock, else just put as much as can fit.
				int itemQuantity = reorderStock.get(coldestItem);
				if(remainingSpace >= itemQuantity) {
					currentTruck.getCargo().put(coldestItem, itemQuantity);
					reorderStock.remove(coldestItem);
				}else {
					currentTruck.getCargo().put(coldestItem, remainingSpace);
					reorderStock.put(coldestItem, itemQuantity - remainingSpace);
				}
			}else {
				//Once truck is full add it to the manifest, and clear the current truck so it can be created in the next iteration
				manifest.add(currentTruck);
				currentTruck = null;
			}
		}
		
		//Add the last truck as it will not be added in the loop
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
