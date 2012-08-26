package swingCourier.Views;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ComputationalPanel extends JPanel{
	
	private JTabbedPane courierTabs;
	private JPanel browser, address;
	public ComputationalPanel() {
		init();
	}
	
	private void init() {
		courierTabs = new JTabbedPane();
		browser = new BrowserPanel();
		address = new AddressPanel();
		courierTabs.addTab("Browser", browser);
		courierTabs.addTab("Address", address);
		this.add(courierTabs);
	}

}
