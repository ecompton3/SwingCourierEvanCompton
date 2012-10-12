package swingCourier.Views;
/**
 * Panel that holds all computational functions like the browser and address book
 */
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ComputationalPanel extends JPanel{
	
	private JTabbedPane courierTabs;
	private JPanel browser, address;
	public ComputationalPanel() {
		init();
	}
	/**
	 * Initializes and adds all elements and listeners
	 */
	private void init() {
		this.setLayout(new BorderLayout());
		courierTabs = new JTabbedPane();
		browser = new BrowserPanel();
		address = new AddressPanel();
		courierTabs.addTab("Browser", browser);
		courierTabs.addTab("Address", address);
		this.add(courierTabs, BorderLayout.CENTER);
	}

}
