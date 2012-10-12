package swingCourier;
/*
 * Main Class for Application
 * Creates and launches JFrame, adding main panels
 */
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import swingCourier.NotePageComponent.BasicNotepageUI;
import swingCourier.Views.ComputationalPanel;
import swingCourier.Views.StatusPanel;
import swingCourier.Views.UserPanel;

public class SwingCourierMain {
	
	public static void main (String args[]) {
		
		UIManager.put("BasicNotepageUI",new BasicNotepageUI());
		JFrame courier = new JFrame("Courier");
		JPanel comp = new ComputationalPanel();
		JPanel user = new UserPanel();
		JPanel status = new StatusPanel();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, comp, user);
		splitPane.setOneTouchExpandable(true);
		Dimension minimumSize = new Dimension(500, 250);
		comp.setMinimumSize(minimumSize);
		
		courier.getContentPane().add(splitPane, BorderLayout.CENTER);
		courier.getContentPane().add(status, BorderLayout.PAGE_END);
		courier.setPreferredSize(new Dimension(1070, 500));
		courier.setMinimumSize(new Dimension(1070, 150));
		courier.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		courier.pack();
		courier.setVisible(true);
	}
	
	
}
