package swingCourier.Models;
/**
 * This class represents the model for the Address Table. It contains an Array of Contacts
 */

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class AddressModel extends AbstractTableModel {
	
	private List<Contact> contactArray;
	private final String[] columnNames = Contact.PROPERTIES;
	
	public AddressModel(List contactArray) {
		this.contactArray = contactArray;
		
	}
	
	public AddressModel() {
		contactArray = new ArrayList<Contact>();
		// TODO Remove default stuff
		contactArray.add(new Contact("Evan Compton","(123) 456-7890","123 Fake St, Nowhere GA, USA", "fake@test.net"));
		contactArray.add(new Contact("Person 2","(555) 555-5555","321 Random Rd, Somewhere PA, USA", "fake2@test.net"));
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	@Override
	public int getRowCount() {
		return contactArray.size();
	}

	@Override
	public int getColumnCount() {
		return Contact.PROPERTIES.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(contactArray.size() != 0) {
			String val = Contact.PROPERTIES[columnIndex];
			if(val.equals("Name")) {
				return contactArray.get(rowIndex).getName();
			} else if (val.equals("Phone")) {
				return contactArray.get(rowIndex).getPhone();
			} else if (val.equals("Address")) {
				return contactArray.get(rowIndex).getAddress();
			} else if (val.equals("Email")) {
				return contactArray.get(rowIndex).getEmail();
			} else {
				return null;
			}
			
		}
		
		return null;
		
	}

}
