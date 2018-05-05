package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

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
	
	
	public static void main(String[] args) {
		Stock.loadInItemProperties("C:\\Temp\\CAB302\\item_properties.csv");
		
		Store store = Store.generateStoreInstance();
		store.generateIntialStock();
		
		SwingUtilities.invokeLater(new GUI());
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		createGUI();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void createGUI() {
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultLookAndFeelDecorated(true);
		setLayout(new BorderLayout());
	
		createButtons();
		displayStoreCapital();
		displayStoreStock();
		
		addPanels();

		this.setVisible(true);
	}
	
	private void createButtons() {
		buttonPnl.setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1.0;
		
		buttonPnl.add(exportBtn, constraints);
		buttonPnl.add(importBtn, constraints);
		buttonPnl.add(itemPropBtn, constraints);
		buttonPnl.add(salesLogBtn, constraints);
	}
	
	private void displayStoreCapital() {
		storeDataPnl.setLayout(new BorderLayout());
		storeCapitalLbl.setText("Store Capital: TBC");
		storeCapitalLbl.setFont(new Font("SansSerif", Font.BOLD, 24));
		storeCapitalLbl.setHorizontalAlignment(JLabel.CENTER);
		storeDataPnl.add(storeCapitalLbl, BorderLayout.NORTH);
	}
	
	private void displayStoreStock() {
		Stock inventory = Store.generateStoreInstance().getStock();
		stockDataTbl.setModel(new StockTableModel(inventory));
		
		JScrollPane scrollPane = new JScrollPane(stockDataTbl);
		scrollPane.setBackground(Color.BLACK);
		storeDataPnl.add(scrollPane);
	}
	
	private void addPanels() {
		getContentPane().add(buttonPnl, BorderLayout.NORTH);
		getContentPane().add(storeDataPnl, BorderLayout.CENTER);
	}

}
