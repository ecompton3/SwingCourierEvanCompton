package swingCourier.Views;
/**
 * Status Bar for displaying current page 
 */
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel{

	private static JLabel status;
	
	public StatusPanel() {
		init();
	}
	/**
	 * Initializes and adds all elements and listeners
	 */
	private void init() {
		status = new JLabel("Status Bar");
		this.add(status);
	}
	
	public static void setStatus(String msg) {
		status.setText(msg);
	}
}
