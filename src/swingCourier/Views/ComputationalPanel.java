package swingCourier.Views;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ComputationalPanel extends JPanel{
	
	private JTabbedPane courierTabs;
	
	public ComputationalPanel() {
		init();
	}
	
	private void init() {
		courierTabs = new JTabbedPane();
		this.add(courierTabs);
	}

}
