package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import delivery.Manifest;
import stock.Stock;
import stock.Store;

public class GUI extends JFrame implements ActionListener, Runnable {

	/**
	 * Variable declarations:
	 * - Constants for the default size of the GUI, and default file paths
	 * - Object creation for components of the GUI (including panels, buttons, label, etc)
	 */
	private static final long serialVersionUID = -3175409517730839439L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private final String DEFAULT_PROPERTIES_PATH = "\\files\\item_properties.csv";
	
	private JPanel buttonPnl = new JPanel();
	private JPanel storeDataPnl = new JPanel();
	
	private JButton exportBtn = new JButton("Export Manifest");
	private JButton importBtn = new JButton("Import Manifest");
	private JButton itemPropBtn = new JButton("Set Items");
	private JButton salesLogBtn = new JButton("Load Sales");

	private JLabel storeCapitalLbl = new JLabel(); 
	private JTable stockDataTbl = new JTable();
	
	private JFileChooser fileChooser = new JFileChooser(".\\files");
	private JOptionPane optionPane = new JOptionPane();
	
	
	public static void main(String[] args) {
		//Thread safe creation of the GUI window
		SwingUtilities.invokeLater(new GUI());
	}
	
	/**
	 * Entry point for GUI creation. Runs as soon as the program starts.
	 */
	@Override
	public void run() {
		createGUI();
	}

	/**
	 * Event listening function that handles all button click events.
	 * Opens a file selector on setting item properties, updating sales logs, and importing manifests.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object button = e.getSource();
		if(button == itemPropBtn || button == salesLogBtn || button == importBtn) {
			String filePath = fileSelectingAction();
			
			//If the user has selected a file, execute the relevant action associated with the button.
			if(filePath != null) {
				if(button == itemPropBtn) {
					Stock.loadInItemProperties(filePath);
				}else if(button == salesLogBtn) {
					Store.generateStoreInstance().loadSalesLog(filePath);
					storeCapitalLbl.setText("Store Capital: " + Store.generateStoreInstance().getFormattedCapital());
				}else if(button == importBtn) {
					Manifest manifest = new Manifest(filePath);
					Store.generateStoreInstance().importManifest(manifest);
					storeCapitalLbl.setText("Store Capital: " + Store.generateStoreInstance().getFormattedCapital());
				}
				
				//Update the GUI table display to reflect these changes
				((AbstractTableModel) stockDataTbl.getModel()).fireTableDataChanged();
			}
		}
		
	}
	
	/**
	 * Helper method for opening a file selecting dialog and selecting a file.
	 * @return The absolute file path of the selected file or {@code null} if not selected
	 */
	private String fileSelectingAction() {
		int fileChooserReturn = fileChooser.showOpenDialog(this);
		File file = null;
		if(fileChooserReturn == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		
		return (file != null ? file.getAbsolutePath() : null);
	}
	
	/**
	 * Creates the GUI. Sets up the initial JFrame, and all panels and components of the GUI.
	 * Also loads up the dialog box to select initial item properties to create the Store.
	 */
	private void createGUI() {
		//Setting up the JFrame
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultLookAndFeelDecorated(true);
		setLayout(new BorderLayout());
		setTitle(Store.generateStoreInstance().getName() + " Inventory Management Application");

		//Creating buttons, label for store capital, and table for store inventory.
		createButtons();
		displayStoreCapital();
		displayStoreStock();
		addPanels();
		this.setVisible(true);
		
		//Dialog box to select initial item properties.
		initialItemPropertiesSelection();
	}
	
	/**
	 * Displays a dialog box prompting the user to select a file representing initial item properties.
	 * Initial item properties is set to the file selected by the user, or otherwise a default included within the project.
	 */
	private void initialItemPropertiesSelection() {
		String title = "Select item properties";
		String message = "Select item properties. 'Cancel' uses default.";
		int answer = optionPane.showConfirmDialog(this, message, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if(answer == JOptionPane.OK_OPTION) {
			String filePath = fileSelectingAction();
			if(filePath != null) {
				Stock.loadInItemProperties(filePath);
			}else {
				Stock.loadInItemProperties(new File("").getAbsolutePath() + DEFAULT_PROPERTIES_PATH);
			}
		} else {
			Stock.loadInItemProperties(new File("").getAbsolutePath() + DEFAULT_PROPERTIES_PATH);
		}
		Store.generateStoreInstance().generateIntialStock();
		((AbstractTableModel) stockDataTbl.getModel()).fireTableDataChanged();
	}
	
	/**
	 * Sets up the button objects to have event listeners attached to the GUI JFrame, and added to the button panel.
	 * Buttons are laid out to spread evenly across the width of the window.
	 */
	private void createButtons() {
		buttonPnl.setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1.0;
		
		exportBtn.addActionListener(this);
		buttonPnl.add(exportBtn, constraints);
		importBtn.addActionListener(this);
		buttonPnl.add(importBtn, constraints);
		itemPropBtn.addActionListener(this);
		buttonPnl.add(itemPropBtn, constraints);
		salesLogBtn.addActionListener(this);
		buttonPnl.add(salesLogBtn, constraints);
	}
	
	/**
	 * Displays the store capital in a currency format on the GUI.	
	 */
	private void displayStoreCapital() {
		storeDataPnl.setLayout(new BorderLayout());
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
