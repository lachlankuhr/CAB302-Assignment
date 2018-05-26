package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import stock.Item;
import stock.Stock;

/**
 * An extension of the AbstractTableModel to customise the table for the format required in this GUI.
 * Provides information about the item properties, as well as the current quantities available in the store, based on the order provided in the item properties file.
 * @author Atrey Gajjar
 */
@SuppressWarnings("serial")
public class StockTableModel extends AbstractTableModel {
	
	//Fields for storing row and column information for the table
	private String[] columnNames = {"Name", "Quantity", "Manufacturing Cost ($)", "Sell Price ($)", "Reorder Point", "Reorder Amount", "Temperature (\u00b0C)"};
	private Stock data;
	
	/**
	 * Constructor for creating a StockTableModel object.
	 * @param data - Inventory of the store with the item as key, and its quantity as value.
	 * @author Atrey Gajjar
	 */
	public StockTableModel(Stock data) {
		this.data = data;
	}
	
	/**
	 * Gets the number of columns to be shown in the table. Equivalent to amount of properties to be shown.
	 * @return The number of columns
	 * @author Atrey Gajjar
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/**
	 * Gets the number of entries to display in the table. Equivalent to number of items in the store.
	 * @return The number of rows
	 * @author Atrey Gajjar
	 */
	@Override
	public int getRowCount() {
		return data.size();
	}

	/**
	 * Gets the value to show at a certain cell in the table.
	 * @param rowIndex - The row to get the value from (a particular item entry).
	 * @param columnIndex - The column to get the value from (the property of item being considered).
	 * @return The value held at rowIndex and columnIndex
	 * @author Atrey Gajjar
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		//Indexable data structure of stock properties to use with numerical row index.
		ArrayList<Item> list = new ArrayList<Item>(Stock.getStockProperties().values());
		Item item = list.get(rowIndex);
		
		Object result = null;
		
		//Gets the appropriate value based on which property is to be shown in the column.
		switch(columnIndex) {
			case 0:
				result = item.getName();
				break;
			//Quantity
			case 1:
				result = data.get(item);
				break;
			case 2:
				result = item.getManufacturingCost();
				break;
			case 3:
				result = item.getSellPrice();
				break;
			case 4:
				result = item.getReorderPoint();
				break;
			case 5:
				result = item.getReorderAmount();
				break;
			case 6:
				result = item.getTemperature();
				break;
		}
		
		
		return result;
	}
	
	/**
	 * Gets the appropriate headings for the columns, from the private field {@link columnNames} specified in the class.
	 * @param columnIndex - The column to get the heading for
	 * @return The value of the column heading
	 * @author Atrey Gajjar
	 */
	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

}
