package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;

import csv.CSVWriting;
import delivery.DeliveryException;
import delivery.Manifest;
import stock.Stock;
import stock.StockException;
import stock.Store;

/**
 * User interface class for the program. Allows the user to view inventory, item properties, and capital.
 * Provides buttons to set item properties, load sales logs, import and export manifests.
 * Uses the default theme based on what system the program is running on.
 * @author Atrey Gajjar
 */
public class GUI extends JFrame implements ActionListener, Runnable {

	
	/**
	 * Variable declarations:
	 * - Constants for the default size of the GUI, and default file paths
	 * - Object creation for components of the GUI (including panels, buttons, label, etc)
	 * @author Atrey Gajjar
	 */
	private static final long serialVersionUID = -3175409517730839439L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private final String DEFAULT_PROPERTIES_PATH = File.separator + "files" + File.separator + "item_properties.csv";
	
	private JPanel buttonPnl = new JPanel();
	private JPanel storeDataPnl = new JPanel();
	
	private JButton exportBtn;
	private JButton importBtn;
	private JButton itemPropBtn;
	private JButton salesLogBtn;

	private JLabel storeCapitalLbl; 
	private JTable stockDataTbl;
	
	private JFileChooser fileChooser;	
	
	public static void main(String[] args) {
		//Thread safe creation of the GUI window
		SwingUtilities.invokeLater(new GUI());
	}
	
	/**
	 * Entry point for GUI creation. Runs as soon as the program starts from the main method.
	 * @author Atrey Gajjar
	 */
	@Override
	public void run() {
		createGUI();
	}

	/**
	 * Event listening function that handles all button click events.
	 * Opens a file selector on setting item properties, updating sales logs, and importing manifests.
	 * Opens a file saver on exporting manifests. Ensures that a file has been selected, and runs the appropriate code
	 * based on which button was pressed.
	 * @author Atrey Gajjar
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		Object button = evt.getSource();
		int fileChooserReturn = -1;
		String filePath;
		
		if(button == itemPropBtn && JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(this, "This will override current item properties (but retain quantities), are you sure you wish to continue?", "Select item properties", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
			return;
		}
		
		if(button == itemPropBtn || button == salesLogBtn || button == importBtn) {
			fileChooserReturn = fileChooser.showOpenDialog(this);
		}else if(button == exportBtn) {
			fileChooserReturn = fileChooser.showSaveDialog(this);
		}
		
		if(fileChooserReturn == JOptionPane.OK_OPTION) {
			filePath = fileChooser.getSelectedFile().getAbsolutePath();
		}else {
			return;
		}
		
		if(button == itemPropBtn) {
			updateItemProperties(filePath);			
		} else if (button == salesLogBtn) {
			updateSalesLog(filePath);
		} else if (button == importBtn) {
			importManifest(filePath);
		} else if(button == exportBtn) {
			exportManifest(filePath);
		}
		
		//Update the GUI table display to reflect any changes
		((AbstractTableModel) stockDataTbl.getModel()).fireTableDataChanged();
	}
	
	/**
	 * Updates the properties of the items within the store. Loads in the properties from the specified .csv file, providing prompts on success or errors.
	 * @param filePath - Absolute path to .csv file storing item properties. 
	 * @author Atrey Gajjar
	 */
	private void updateItemProperties(String filePath) {
		try {
			Stock.loadInItemProperties(filePath);
			Store.generateStoreInstance().generateInitialStock();
			JOptionPane.showMessageDialog(this, "Successfully updated the item properties!", "Item Properties Updated", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error loading in the properties. Ensure the file path is correct and file is not corrupted.", "Load Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Updates the sales log of the store. Loads in the sales log from the specified .csv file, providing prompts on success or errors.
	 * Additionally, the store capital is updated.
	 * @param filePath - Absolute path to .csv file storing sales log. 
	 * @author Atrey Gajjar
	 */
	private void updateSalesLog(String filePath) {
		try {
			Store.generateStoreInstance().loadSalesLog(filePath);
			storeCapitalLbl.setText("Store Capital: " + Store.generateStoreInstance().getFormattedCapital());
			JOptionPane.showMessageDialog(this, "Successfully imported the sales log file!", "Sales Log Loaded", JOptionPane.INFORMATION_MESSAGE);
		//} catch (StockException e) {
			//optionPane.showMessageDialog(this, "Error loading in the sales log! " + e.getMessage(), "Sales Log Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error loading in the sales log. Ensure the file path is correct and file is not corrupted.", "Load Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	/**
	 * Imports a manifest file, representing a delivery of ordered goods.
	 * @param filePath
	 */
	private void importManifest(String filePath) {
		try {
			Manifest manifest = new Manifest(filePath);
			Store.generateStoreInstance().importManifest(manifest);
			storeCapitalLbl.setText("Store Capital: " + Store.generateStoreInstance().getFormattedCapital());
			JOptionPane.showMessageDialog(this, "Successfully imported the manifest file!", "Manifest Imported", JOptionPane.INFORMATION_MESSAGE);
		} catch (DeliveryException e) {
			JOptionPane.showMessageDialog(this, "Error importing the manifest. " + e.getMessage(), "Manifest Import Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error importing the manifest. Ensure the file path is correct and file is not corrupted.", "Load Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void exportManifest(String filePath) {
		try {
			Stock reorderStock = Store.generateStoreInstance().getReorderStock();
			Manifest manifest = new Manifest(reorderStock);
			CSVWriting.writeManifest(manifest.getManifestCollection(), filePath + ".csv");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error importing the manifest. Ensure the file name is valid and write permissions are available.", "Save Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Creates the GUI. Sets up the initial JFrame, and all panels and components of the GUI.
	 * Also loads up the dialog box to select initial item properties to create the Store.
	 */
	private void createGUI() {
		//Setting up the JFrame
		setSize(WIDTH, HEIGHT);
		setMinimumSize(new Dimension(500, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle(Store.generateStoreInstance().getName() + " Inventory Management Application");
		try {			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			setDefaultLookAndFeelDecorated(true);
		}

		//Creating buttons, label for store capital, and table for store inventory.
		createButtons();
		displayStoreCapital();
		displayStoreStock();
		addPanels();
		this.setVisible(true);
		
		//Dialog box to select initial item properties.
		fileChooser = new JFileChooser("." + File.separator + "files");
		fileChooser.setFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
		initialItemPropertiesSelection();
	}
	
	/**
	 * Displays a dialog box prompting the user to select a file representing initial item properties.
	 * Initial item properties is set to the file selected by the user, or otherwise a default included within the project.
	 */
	private void initialItemPropertiesSelection() {
		int answer = JOptionPane.showConfirmDialog(this, "Select item properties. 'Cancel' uses default.", "Select item properties", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		
		do{
			try {				
				if(answer == JOptionPane.OK_OPTION) {
					int fileChooserReturn = fileChooser.showOpenDialog(this);
					
					if(fileChooserReturn == JOptionPane.OK_OPTION) {
						Stock.loadInItemProperties(fileChooser.getSelectedFile().getAbsolutePath());
						JOptionPane.showMessageDialog(this, "Selected item properties initialised successfully.", "Loaded Properties", JOptionPane.INFORMATION_MESSAGE);
					}else {
						Stock.loadInItemProperties(new File("").getAbsolutePath() + DEFAULT_PROPERTIES_PATH);
						JOptionPane.showMessageDialog(this, "Process cancelled. Using default properties.", "Loaded Properties", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					Stock.loadInItemProperties(new File("").getAbsolutePath() + DEFAULT_PROPERTIES_PATH);
					JOptionPane.showMessageDialog(this, "Using default properties.", "Loaded Properties", JOptionPane.INFORMATION_MESSAGE);
				}
				Store.generateStoreInstance().generateInitialStock();
				((AbstractTableModel) stockDataTbl.getModel()).fireTableDataChanged();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Error loading in the properties! Please try again", "Load Error", JOptionPane.ERROR_MESSAGE);
			}
		}while(Stock.getStockProperties().size() == 0);
		
	}
	
	/**
	 * Sets up the button objects to have event listeners attached to the GUI JFrame, and added to the button panel.
	 * Buttons are laid out to spread evenly across the width of the window.
	 */
	private void createButtons() {
		buttonPnl.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1.0;
	
		exportBtn = new JButton("Export Manifest");
		exportBtn.addActionListener(this);
		buttonPnl.add(exportBtn, constraints);

		importBtn = new JButton("Import Manifest");
		importBtn.addActionListener(this);
		buttonPnl.add(importBtn, constraints);

		itemPropBtn = new JButton("Setup Items");
		itemPropBtn.addActionListener(this);
		buttonPnl.add(itemPropBtn, constraints);

		salesLogBtn = new JButton("Load Sales");
		salesLogBtn.addActionListener(this);
		buttonPnl.add(salesLogBtn, constraints);
	}
	
	/**
	 * Displays the store capital in a currency format on the GUI.	
	 */
	private void displayStoreCapital() {
		storeDataPnl.setLayout(new BorderLayout());
		
		storeCapitalLbl = new JLabel();
		storeCapitalLbl.setText("Store Capital: " + Store.generateStoreInstance().getFormattedCapital());
		storeCapitalLbl.setFont(new Font("SansSerif", Font.BOLD, 24));
		storeCapitalLbl.setHorizontalAlignment(JLabel.CENTER);
		storeDataPnl.add(storeCapitalLbl, BorderLayout.NORTH);
	}
	
	/**
	 * Displays a JTable representing the stock object of the store.
	 * Places this within a scroll pane, to accommodate different lists of items.
	 */
	private void displayStoreStock() {
		Stock inventory = Store.generateStoreInstance().getStock();
		
		stockDataTbl = new JTable();
		stockDataTbl.setModel(new StockTableModel(inventory));
		
		JScrollPane scrollPane = new JScrollPane(stockDataTbl);
		storeDataPnl.add(scrollPane);
	}
	
	/**
	 * Adds the button panel, and store data panel to the GUI.
	 */
	private void addPanels() {
		getContentPane().add(buttonPnl, BorderLayout.NORTH);
		getContentPane().add(storeDataPnl, BorderLayout.CENTER);
	}

}
