package swingCourier;

import javax.swing.JFrame;
import javax.swing.JPanel;

import swingCourier.Views.ComputationalPanel;
import swingCourier.Views.DrawingPanel;

public class SwingCourierMain {
	
	public static void main (String args[]) {
		JFrame courier = new JFrame("Courier");
		JPanel comp = new ComputationalPanel();
		JPanel drawing = new DrawingPanel();
		
		courier.getContentPane().add(comp);
		courier.getContentPane().add(drawing);
		
		courier.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		courier.pack();
		courier.setVisible(true);
	}
}
