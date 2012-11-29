package swingCourier.Views;
/**
 * The panel for all user drawing and input related functions
 */
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import swingCourier.NotePageComponent.NotepageComponent;

public class UserPanel extends JPanel implements ChangeListener{

	private NotepageComponent drawingPanel;
	private PageContainer pages;
	private JToolBar toolBar;
	private JButton btnBack, btnNew, btnDelete, btnNext,btnColor,btnOverview;
	private JRadioButton freeFormRadio, rectangleRadio, ovalRadio;
	private ButtonGroup drawingGroup;
	private JScrollPane pane;
	
	
	public UserPanel() {
		init();
	}
	/**
	 * Initializes and adds all elements and listeners
	 */
	public void init() {
		this.setLayout(new BorderLayout());
		drawingPanel = new NotepageComponent();
		drawingPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				performGesture(e);
			}
		});
		List<NotepageComponent> pageList = new ArrayList<NotepageComponent>();
		
		pageList.add(drawingPanel);
		
		pages = new PageContainer(pageList);
		pane = new JScrollPane(pages);
		//this.add(scroll, BorderLayout.CENTER);
		this.add(pane, BorderLayout.CENTER);
		pages.add(drawingPanel);
		
		toolBar = new JToolBar();
		
		
		btnBack   = new JButton("Back");
		btnBack.setEnabled(false);
		btnBack.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				backCommand();
			}			
		});
		btnNew    = new JButton("New Page");
		btnNew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				NotepageComponent comp = new NotepageComponent();
				comp.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						performGesture(e);
					}
				});			
				pages.newPage(comp);
				btnNext.setEnabled(false);
				btnBack.setEnabled(true);
				btnDelete.setEnabled(true);
				StatusPanel.setStatus("Page: " + pages.getCurPage());
				
			}			
		});
		btnDelete = new JButton("Delete Page");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				pages.deletePage();
				if(pages.getCurPage() <= 0) {
					pages.setCurPage(0);
					btnBack.setEnabled(false);
					btnNext.setEnabled(true);
				} else if (pages.getCurPage() >= pages.getNumPages()-1) {
					pages.setCurPage(pages.getNumPages() - 1);
					btnNext.setEnabled(false);
					btnBack.setEnabled(true);
				}
				
				if(pages.getNumPages() <= 1) {
					btnDelete.setEnabled(false);
					btnBack.setEnabled(false);
					btnNext.setEnabled(false);
				}
				StatusPanel.setStatus("Page: " + pages.getCurPage());
			}			
		});
		btnNext   = new JButton("Next");
		btnNext.setEnabled(false);
		btnNext.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				nextCommand();
			}			
		});
		
		btnOverview   = new JButton("Overview");
		btnOverview.setEnabled(true);
		btnOverview.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				overviewCommand();
			}			
		});
		
		btnColor = new JButton("Color");
		btnColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Color newColor = Color.black;
				 newColor = JColorChooser.showDialog(
	                    UserPanel.this,
	                     "Choose Background Color",
	                     newColor);
				pages.changeColor(newColor);
				btnColor.setBackground(newColor);
				
			}
			
		});
		
		freeFormRadio  = new JRadioButton("Freeform");		
		rectangleRadio = new JRadioButton("Rectangle");		
		ovalRadio      = new JRadioButton("Oval");
		
		freeFormRadio.setSelected(true);
		freeFormRadio.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				changeStrokeType(freeFormRadio.getText());			
			}			
		});
		
		rectangleRadio.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				changeStrokeType(rectangleRadio.getText());			
			}			
		});
		
		ovalRadio.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				changeStrokeType(ovalRadio.getText());			
			}			
		});
		
		drawingGroup = new ButtonGroup();
		drawingGroup.add(freeFormRadio);
		drawingGroup.add(rectangleRadio);
		drawingGroup.add(ovalRadio);
		//Glue added for graceful resizing, grouping related actions as it grows
		toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.LINE_AXIS));
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(btnBack);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(btnNew);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(btnDelete);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(btnNext);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(btnOverview);
		
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(freeFormRadio);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(rectangleRadio);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(ovalRadio);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(btnColor);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(Box.createHorizontalGlue());
		
		this.add(toolBar, BorderLayout.PAGE_END);
	}
	/**
	 * Changes the currently selected stroke type
	 * @param text String value of a particular storke type
	 */
	public void changeStrokeType(String text) {
		NotepageComponent.setStrokeType(text);
	}
	
	
	public void nextCommand() {
		pages.nextPage();
		if(pages.getCurPage() >= pages.getNumPages()-1) {
			pages.setCurPage(pages.getNumPages()-1);
			btnNext.setEnabled(false);
		}
		btnBack.setEnabled(true);
		StatusPanel.setStatus("Page: " + pages.getCurPage());
	}
	
	public void backCommand() {
		pages.prevPage();
		if(pages.getCurPage() <= 0) {
			pages.setCurPage(0);
			btnBack.setEnabled(false);
		}
		btnNext.setEnabled(true);
		StatusPanel.setStatus("Page: " + pages.getCurPage());
	}
	
	public void overviewCommand() {
		if(pages.getOverview()) {
			pages.setOverview(false);
		} else {
			pages.setOverview(true);
		}
		
		pages.repaint();
		//btnOverview.setEnabled(false);
	}
	
	
	@Override
	public void stateChanged(ChangeEvent e) {
		String gesture = pages.getPageList().get(pages.getCurPage()).getGesture();
		
	}
	
	private void performGesture(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Next")) {
			nextCommand();
		} else if (e.getActionCommand().equals("Back")) {
			backCommand();
		} else if(e.getActionCommand().equals("Delete")){
			StatusPanel.setStatus("Deleting");
		} else if(e.getActionCommand().equals("Select")){
			StatusPanel.setStatus("Selecting");
		} else if (e.getActionCommand().equals("None")) {
			StatusPanel.setStatus("Unknown Gesture");
		} else if (e.getActionCommand().equals("List")) {
			StatusPanel.setStatus("List Created");
		}
	}
}
