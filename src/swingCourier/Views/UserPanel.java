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

import swingCourier.NotePageComponent.NotepageComponent;

public class UserPanel extends JPanel{

	private NotepageComponent drawingPanel;
	private JPanel pages;
	private JToolBar toolBar;
	private JButton btnBack, btnNew, btnDelete, btnNext,btnColor;
	private JRadioButton freeFormRadio, rectangleRadio, ovalRadio;
	private ButtonGroup drawingGroup;
	private int numPages = 1, curPage = 0;
	private List<NotepageComponent> pageList;
	private Color newColor = new Color(0, 0, 0);
	public UserPanel() {
		init();
	}
	/**
	 * Initializes and adds all elements and listeners
	 */
	public void init() {
		this.setLayout(new BorderLayout());
		drawingPanel = new NotepageComponent();
		pageList = new ArrayList<NotepageComponent>();
		
		pageList.add(drawingPanel);
		
		pages = new JPanel();
		JScrollPane scroll = new JScrollPane(pages);
		this.add(scroll, BorderLayout.CENTER);
		pages.setLayout(new BorderLayout());
		pages.add(drawingPanel, BorderLayout.CENTER);
		
		toolBar = new JToolBar();
		
		
		btnBack   = new JButton("Back");
		btnBack.setEnabled(false);
		btnBack.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				pageList.get(curPage).setVisible(false);
				resizePages();
				curPage--;
				pageList.get(curPage).setVisible(true);
				if(curPage <= 0) {
					curPage = 0;
					btnBack.setEnabled(false);
				}
				btnNext.setEnabled(true);
				StatusPanel.setStatus("Page: " + curPage);
			}			
		});
		btnNew    = new JButton("New Page");
		btnNew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				numPages++;
				NotepageComponent comp = new NotepageComponent();			
				comp.setColor(newColor);
				pageList.add(comp);
				resizePages();
				pages.add(comp, BorderLayout.CENTER);
				pageList.get(curPage).setVisible(false);
				curPage = numPages-1;
				pageList.get(curPage).setVisible(true);
				btnNext.setEnabled(false);
				btnBack.setEnabled(true);
				btnDelete.setEnabled(true);
				StatusPanel.setStatus("Page: " + curPage);
				
			}			
		});
		btnDelete = new JButton("Delete Page");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				numPages--;
				pages.remove(pageList.remove(curPage));
				
				if(curPage != 0) {
					curPage--;
					pageList.get(curPage).setVisible(true);
				} else {
					curPage = 0;
					pageList.get(curPage).setVisible(true);
					
				}
				
				if(curPage <= 0) {
					curPage = 0;
					btnBack.setEnabled(false);
					btnNext.setEnabled(true);
				} else if (curPage >= numPages-1) {
					curPage = numPages - 1;
					btnNext.setEnabled(false);
					btnBack.setEnabled(true);
				}
				
				if(numPages <= 1) {
					btnDelete.setEnabled(false);
					btnBack.setEnabled(false);
					btnNext.setEnabled(false);
				}
				StatusPanel.setStatus("Page: " + curPage);
			}			
		});
		btnNext   = new JButton("Next");
		btnNext.setEnabled(false);
		btnNext.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				pageList.get(curPage).setVisible(false);
				resizePages();
				curPage++;
				pageList.get(curPage).setVisible(true);
				if(curPage >= numPages-1) {
					curPage = numPages-1;
					btnNext.setEnabled(false);
				}
				btnBack.setEnabled(true);
				StatusPanel.setStatus("Page: " + curPage);
			}			
		});
		
		btnColor = new JButton("Color");
		btnColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newColor = JColorChooser.showDialog(
	                    UserPanel.this,
	                     "Choose Background Color",
	                     newColor);
				for(int i = 0; i <pageList.size(); i++) {
					pageList.get(i).setColor(newColor);
				}
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
	/**
	 * Resizes all pages to be the maximum page size generated
	 */
	public void resizePages() {
		int width = 0, height = 0;
		for(int i = 0; i < pageList.size(); i++) {
			if(width < pageList.get(i).getMaxWidth()) {
				width = pageList.get(i).getMaxWidth();
			}
			if(height < pageList.get(i).getMaxHeight()) {
				height = pageList.get(i).getMaxHeight();
			}
		}
		Dimension d = new Dimension(width,height);
		for(int j = 0; j < pageList.size(); j++) {
			pageList.get(j).setMinimumSize(new Dimension(d));
			pageList.get(j).setPreferredSize(new Dimension(d));
			pageList.get(j).setMaximumSize(new Dimension(9999999,9999999));
			pageList.get(j).setSize(new Dimension(d));
			pageList.get(j).resized(pages.getWidth(),pages.getHeight());
			pageList.get(j).repaint();
		}
		
		
	}
}
