package swingCourier.Views;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

public class UserPanel extends JPanel{

	private JPanel drawingPanel;
	private JToolBar toolBar;
	private JButton btnBack, btnNew, btnDelete, btnNext;
	private JRadioButton freeFormRadio, rectangleRadio, ovalRadio;
	private ButtonGroup drawingGroup;
	public UserPanel() {
		init();
	}
	
	public void init() {
		this.setLayout(new BorderLayout());
		drawingPanel = new DrawingPanel();
		this.add(drawingPanel, BorderLayout.CENTER);
		
		toolBar = new JToolBar();
		
		
		btnBack   = new JButton("Back");
		btnNew    = new JButton("New Page");
		btnDelete = new JButton("Delete Page");
		btnNext   = new JButton("Next");
		
		freeFormRadio  = new JRadioButton("Freeform");		
		rectangleRadio = new JRadioButton("Rectangle");		
		ovalRadio      = new JRadioButton("Oval");
		drawingGroup = new ButtonGroup();
		drawingGroup.add(freeFormRadio);
		drawingGroup.add(rectangleRadio);
		drawingGroup.add(ovalRadio);
		toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.LINE_AXIS));
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(btnBack);
		toolBar.add(Box.createRigidArea(new Dimension(15,0)));
		toolBar.add(btnNew);
		toolBar.add(Box.createRigidArea(new Dimension(15,0)));
		toolBar.add(btnDelete);
		toolBar.add(Box.createRigidArea(new Dimension(15,0)));
		toolBar.add(btnNext);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(freeFormRadio);
		toolBar.add(Box.createRigidArea(new Dimension(15,0)));
		toolBar.add(rectangleRadio);
		toolBar.add(Box.createRigidArea(new Dimension(15,0)));
		toolBar.add(ovalRadio);
		toolBar.add(Box.createHorizontalGlue());
		
		this.add(toolBar, BorderLayout.PAGE_END);
	}
}
