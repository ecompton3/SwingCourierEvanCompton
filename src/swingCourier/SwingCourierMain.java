package swingCourier;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import swingCourier.Views.ComputationalPanel;
import swingCourier.Views.UserPanel;

public class SwingCourierMain {
	
	public static void main (String args[]) {
		JFrame courier = new JFrame("Courier");
		JPanel comp = new ComputationalPanel();
		JPanel user = new UserPanel();
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, comp, user);
		splitPane.setOneTouchExpandable(true);
		Dimension minimumSize = new Dimension(500, 250);
		comp.setMinimumSize(minimumSize);
		
		courier.getContentPane().add(splitPane, BorderLayout.CENTER);
		courier.setPreferredSize(new Dimension(1050, 500));
		courier.setMinimumSize(new Dimension(1050, 500));
		courier.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		courier.pack();
		courier.setVisible(true);
	}
}
