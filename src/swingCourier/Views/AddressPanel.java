package swingCourier.Views;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import swingCourier.Models.AddressModel;

public class AddressPanel extends JPanel{
	private JTable addressTable;
	private JTextArea addressBox;
	private TableModel addressModel;
	public AddressPanel() {
		init();
	}
	
	private void init() {
		this.setLayout(new BorderLayout());
		setupTable();		
		JScrollPane tablePane = new JScrollPane(addressTable);
		addressBox = new JTextArea();
		addressBox.setMinimumSize(new Dimension(200, 100));
		addressBox.setPreferredSize(new Dimension(200, 100));
		JScrollPane textPane = new JScrollPane(addressBox);
		textPane.setMinimumSize(new Dimension(200,100));
		this.add(tablePane, BorderLayout.CENTER);
		this.add(textPane, BorderLayout.PAGE_END);
	}
	
	private void setupTable() {
		addressModel = new AddressModel();
		addressTable = new JTable(addressModel);
		ListSelectionModel selectionModel = addressTable.getSelectionModel();
		selectionModel.addListSelectionListener( new ListSelectionListener() {
			// Handler for list selection changes
		 	public void valueChanged( ListSelectionEvent event )
		 	{
		 		// See if this is a valid table selection
				if( event.getSource() == addressTable.getSelectionModel() && event.getFirstIndex() >= 0 ) {
					rowSelectListener();
				}
		 	}
		});
	}
	
	public void setTableModel(TableModel model) {
		addressModel = model;
		setupTable();
	}
	
	public void rowSelectListener() {
		// Get the data model for this table
		TableModel model = (TableModel)addressTable.getModel();
		String info = "";
		for(int i = 0; i < model.getColumnCount(); i++) {
			info = info + (String)model.getValueAt(
					addressTable.getSelectedRow(),
					i ) + "\n";
		}

		// Display the selected item
		addressBox.setText(info);
	}

}
