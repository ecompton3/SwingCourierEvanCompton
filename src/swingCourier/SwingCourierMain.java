package swingCourier;

import javax.swing.JFrame;
import javax.swing.JPanel;

import swingCourier.Views.ComputationalPanel;
import swingCourier.Views.DrawingPanel;
import java.awt.BorderLayout;

public class SwingCourierMain {
	
	public static void main (String args[]) {
		JFrame courier = new JFrame("Courier");
		JPanel comp = new ComputationalPanel();
		JPanel drawing = new DrawingPanel();
		
		courier.getContentPane().add(comp,BorderLayout.LINE_START);
		courier.getContentPane().add(drawing,BorderLayout.LINE_END);
		courier.setSize(500, 500);
		courier.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		courier.pack();
		courier.setVisible(true);
	}
}
