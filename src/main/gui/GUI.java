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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

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
	
	public static void main(String[] args) {
		
		Store store = Store.generateStoreInstance();
		
		SwingUtilities.invokeLater(new GUI());
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		createGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == itemPropBtn) {
			int fileChooserReturn = fileChooser.showOpenDialog(this);
			
			if(fileChooserReturn == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				Stock.loadInItemProperties(file.getAbsolutePath());
				Store.generateStoreInstance().generateIntialStock();
				
				((AbstractTableModel) stockDataTbl.getModel()).fireTableDataChanged();;
			}
		}else if(e.getSource() == salesLogBtn) {
			int fileChooserReturn = fileChooser.showOpenDialog(this);
			
			if(fileChooserReturn == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				Store.generateStoreInstance().loadSalesLog(file.getAbsolutePath());
				
				((AbstractTableModel) stockDataTbl.getModel()).fireTableDataChanged();;
			}
		}
		
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
	}
	
	private void createButtons() {
		buttonPnl.setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1.0;
		
		itemPropBtn.addActionListener(this);
		buttonPnl.add(exportBtn, constraints);
		buttonPnl.add(importBtn, constraints);
		buttonPnl.add(itemPropBtn, constraints);
		salesLogBtn.addActionListener(this);
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
		storeDataPnl.add(scrollPane);
	}
	
	private void addPanels() {
		getContentPane().add(buttonPnl, BorderLayout.NORTH);
		getContentPane().add(storeDataPnl, BorderLayout.CENTER);
	}

}
