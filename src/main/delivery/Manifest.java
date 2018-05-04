package delivery;

import java.util.*;

import stock.Stock;

public class Manifest {
	
	/**
	 * A collection of trucks.
	 */
	
	private AbstractCollection<Truck> manifest; // List of trucks
	
	/** 
	 * The constructor for the manifest.
	 * 	@param stock - 
	 */
	
	public Manifest(Stock stock) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	/**
	 * Generates manifest for trucks to be delivered. 
	 * Manifest is a list of refrigerated and ordinary trucks with their respective cargo items and quantities.
	 */ 
	
	public void generateManifest() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	/**
	 * Calculates the optimised manifest. 
	 */
	
	public void calculateOptimisedManifest() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	
	/**
	 * Gets the number of refrigerated trucks in manifest.
	 * @return Number of refrigerated trucks in manifest
	 */
	
	public int getNumberOfRefrigeratedTrucks() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	/**
	 * Gets the number of cold trucks in manifest.
	 * @return Number of cold trucks in manifest
	 */
	
	public int getNumberOfOrdinaryTrucks() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
}
