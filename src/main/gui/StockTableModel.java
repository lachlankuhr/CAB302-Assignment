package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import stock.Item;
import stock.Stock;
import stock.Store;

public class StockTableModel extends AbstractTableModel {
	
	private String[] columnNames = {"Name", "Quantity", "Manufacturing Cost ($)", "Sell Price ($)", "Reorder Point", "Reorder Amount", "Temperature (°C)"};
	private Stock data;
	
	public StockTableModel(Stock data) {
		this.data = data;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		
		ArrayList<Item> list = new ArrayList<Item>(Stock.getStockProperties().values());
		Item item = list.get(rowIndex);
		
		Object result = null;
		
		switch(columnIndex) {
			case 0:
				result = item.getName();
				break;
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
	
	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

}
