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
	 * 
	 */
	private static final long serialVersionUID = -3175409517730839439L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	private JPanel buttonPnl = new JPanel();
	private JPanel storeDataPnl = new JPanel();
	
	private JButton exportBtn = new JButton("Export Manifest");
	private JButton importBtn = new JButton("Import Manifest");
	private JButton itemPropBtn = new JButton("Set Items");
	private JButton salesLogBtn = new JButton("Load Sales");

	private JLabel storeCapitalLbl = new JLabel(); 
	private JTable stockDataTbl = new JTable();
	
	private JFileChooser fileChooser = new JFileChooser();
	private JOptionPane optionPane = new JOptionPane();
	
	private final String DEFAULT_PROPERTIES_PATH = "\\files\\item_properties.csv";
	
	public static void main(String[] args) {
		
		Store store = Store.generateStoreInstance();
		
		SwingUtilities.invokeLater(new GUI());
	}
	
	/**
	 * Entry point for GUI creation.
	 */
	@Override
	public void run() {
		createGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object button = e.getSource();
		if(button == itemPropBtn || button == salesLogBtn || button == importBtn) {
			String filePath = fileSelectingAction();
			
			if(filePath != null) {
				if(button == itemPropBtn) {
					Stock.loadInItemProperties(filePath);
				}else if(button == salesLogBtn) {
					Store.generateStoreInstance().loadSalesLog(filePath);
				}else if(button == importBtn) {
					Manifest manifest = new Manifest(filePath);
					//Store.generateStoreInstance().importManifest(manifest);
				}
				
				((AbstractTableModel) stockDataTbl.getModel()).fireTableDataChanged();
			}
		}
		
	}
	
	private String fileSelectingAction() {
		int fileChooserReturn = fileChooser.showOpenDialog(this);
		File file = null;
		if(fileChooserReturn == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		
		return (file != null ? file.getAbsolutePath() : null);
	}
	
	private void createGUI() {
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultLookAndFeelDecorated(true);
		setLayout(new BorderLayout());
		setTitle(Store.generateStoreInstance().getName() + " Inventory Management Application");


		createButtons();
		displayStoreCapital();
		displayStoreStock();
		addPanels();
		
		this.setVisible(true);
		initialItemPropertiesSelection();
	}
	
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
	
	private void displayStoreCapital() {
		storeDataPnl.setLayout(new BorderLayout());
		storeCapitalLbl.setText("Store Capital: " + Store.generateStoreInstance().getFormattedCapital());
		storeCapitalLbl.setFont(new Font("SansSerif", Font.BOLD, 24));
		storeCapitalLbl.setHorizontalAlignment(JLabel.CENTER);
		storeDataPnl.add(storeCapitalLbl, BorderLayout.NORTH);
	}
	
	private void displayStoreStock() {
		Stock inventory = Store.generateStoreInstance().getStock();
		stockDataTbl.setModel(new StockTableModel(inventory));
		
		JScrollPane scrollPane = new JScrollPane(stockDataTbl);
		storeDataPnl.add(scrollPane);
	}
	
	private void addPanels() {
		getContentPane().add(buttonPnl, BorderLayout.NORTH);
		getContentPane().add(storeDataPnl, BorderLayout.CENTER);
	}

}
