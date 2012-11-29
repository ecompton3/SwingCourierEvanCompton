package swingCourier.Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import swingCourier.NotePageComponent.NotepageComponent;

public class PageContainer extends JPanel implements ComponentListener{
	private List<NotepageComponent> pageList;
	private int curPage=0;
	private int numPages = 1;
	private Color newColor = new Color(0, 0, 0);
	private boolean overviewMode;
	private int maxWidth;
	private int maxHeight;
	private Timer zoomOutAnimation, zoomInAnimation;
	private double yScale = 1.0;
	private double xScale = 1.0;
	private double xScalar = 0.01;
	private double yScalar = 0.01;
	private int xGoalPos = 0;
	private int yGoalPos = 0;
	private int xMover = 1;
	private int yMover = 1;
	private double yDisScalar = 1;
	private double xDisScalar = 1;
	
	
	
	public PageContainer(List<NotepageComponent> pages) {
		pageList = pages;
		
		zoomOutAnimation = new Timer(100,new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NotepageComponent comp = pageList.get(curPage);
				double curXScale = comp.getXScale(),curYScale = comp.getYScale();
			    int curHOffset = comp.getX(), curVOffset = comp.getY();
					if(curXScale > xScale && (curXScale >= 1.80 * xScale || curXScale <= 1.20 * xScale)) {
						curXScale -= xScalar;
					} else if (curXScale > xScale && (curXScale < 1.80 * xScale && curXScale > 1.20 * xScale)) {
						curXScale -= xScalar * 2;
					}
					if(curYScale > yScale && (curYScale >= 1.80 * yScale || curYScale <= 1.20 * yScale)) {
						curYScale -= yScalar;
					} else if (curYScale > yScale && (curYScale < 1.80 * yScale && curYScale > 1.20 * yScale)) {
						curYScale -= yScalar * 2;
					}
					
					/*if(curHOffset != xScale && (curHOffset >= 1.80 * xScale || curHOffset <= 1.20 * xScale)) {
						curHOffset -= 1;
					} else if (curXScale > xScale && (curXScale < 1.80 * xScale && curXScale > 1.20 * xScale)) {
						curXScale -= xScalar * 2;
					}
					if(curYScale > yScale && (curYScale >= 1.80 * yScale || curYScale <= 1.20 * yScale)) {
						curYScale -= yScalar;
					} else if (curYScale > yScale && (curYScale < 1.80 * yScale && curYScale > 1.20 * yScale)) {
						curYScale -= yScalar * 2;
					}*/
					
					comp.setScaleFactors(curXScale, curYScale);
					//comp.setBounds(curHOffset, curVOffset, comp.getWidth(), comp.getHeight());
					
				if(curXScale <= xScale && curYScale <= yScale) {
					
					zoomOutAnimation.stop();
				}
			}
			
		});
		
		zoomInAnimation = new Timer(100,new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NotepageComponent comp = pageList.get(curPage);
				double curXScale = comp.getXScale(),curYScale = comp.getYScale();
			    int curHOffset = comp.getX(), curVOffset = comp.getY();
					if(curXScale < xScale && (curXScale >= 0.80 * xScale || curXScale <= 0.20 * xScale)) {
						curXScale += xScalar;
					} else if (curXScale < xScale && (curXScale < 0.80 * xScale && curXScale > 0.20 * xScale)) {
						curXScale += xScalar * 2;
					}
					if(curYScale < yScale && (curYScale >= 0.80 * yScale || curYScale <= 0.20 * yScale)) {
						curYScale += yScalar;
					} else if (curYScale < yScale && (curYScale < 0.80 * yScale && curYScale > 0.20 * yScale)) {
						curYScale += yScalar * 2;
					}
					
					
					comp.setScaleFactors(curXScale, curYScale);
					//comp.setBounds(curHOffset, curVOffset, comp.getWidth(), comp.getHeight());
					
				if(curXScale >= xScale && curYScale >= yScale) {
					
					zoomInAnimation.stop();
				}
				
			}
			
		});
		overviewMode = false;
		this.setBackground(Color.cyan);
		this.setLayout(null);
		pageList.get(curPage).setVisible(true);
		resizePages();
		
		layoutPages();
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(this.getWidth() > maxWidth) {
			maxWidth = this.getWidth();
		}
		if(this.getHeight() > maxHeight) {
			maxHeight = this.getHeight();
		}
		this.setMinimumSize(new Dimension(maxWidth,maxHeight));
		this.setPreferredSize(new Dimension(maxWidth,maxHeight));
		this.setMaximumSize(new Dimension(999999999,99999999));
		layoutPages();
	}
	
	@Override
	public void repaint() {
		super.repaint();		
	}
	
	private void layoutPages() {
		int numPages = pageList.size();
		int factor, compHeight, compWidth,index=0; 
		if(overviewMode) {
			factor = findBestFactor(numPages);
			yScale = 1.0/factor;
			xScale = 1.0/(numPages/factor);
			compHeight = this.getHeight()/factor;
			compWidth = this.getWidth()/(numPages/factor);
			
			for(int i = 0; i < factor; i++) {
				
				for(int j = 0; j < numPages/factor; j++) {
					NotepageComponent comp = pageList.get(index);
					if(curPage == index) {
						double curXScale = comp.getXScale(),curYScale = comp.getYScale();
						if(!zoomOutAnimation.isRunning() && curXScale > xScale && curYScale > yScale) {
							xGoalPos = (j*compWidth);
							yGoalPos = (i*compHeight);
							xScalar = Math.abs(curXScale - xScale) / 20.0;
							yScalar = Math.abs(curYScale - yScale) / 20.0;
							xDisScalar = (Math.abs(comp.getX() - xGoalPos) / 20.0);
							yDisScalar = (Math.abs(comp.getY() - yGoalPos) / 20.0);
							zoomOutAnimation.start();
							
						}
						
					} else {
						
						comp.setScaleFactors(xScale, yScale);
						comp.setVisible(true);
						comp.setBounds((j*compWidth), (i*compHeight), comp.getWidth(), comp.getHeight());
						
					}
					
					
					
					index++;
				}
			}
		} else {
			xScale = 1.0;
			yScale = 1.0;
			xGoalPos = 0;
			yGoalPos = 0;
			for(int i = 0; i < numPages; i++) {
				NotepageComponent comp = pageList.get(i);
				int height = this.getHeight();
				int width = this.getWidth();
				
				if(i != curPage) {
					resizePages();
					comp.setVisible(false);
					comp.setScaleFactors(xScale, yScale);
					
					if(comp.getMaxHeight() > height) {
						height = comp.getMaxHeight();
					}
					if(comp.getMaxWidth() > width) {
						width = comp.getMaxWidth();
					}
					
				} else {
					double curXScale = comp.getXScale(),curYScale = comp.getYScale();
					if(!zoomInAnimation.isRunning() && curXScale < xScale && curYScale < yScale) {
						
						xScalar = (Math.abs(curXScale - xScale) / 20.0);
						yScalar = (Math.abs(curYScale - yScale) / 20.0);
						xDisScalar = (Math.abs(comp.getX() - xGoalPos) / 20.0);
						yDisScalar = (Math.abs(comp.getY() - yGoalPos) / 20.0);
						zoomInAnimation.start();
						
					}
				}
				comp.setBounds(0, 0, width, height);
				
				
			}
		}
		
		revalidate();
	}

	private int findBestFactor(int numPages) {
		int factorIndex = 0, bestFactorDiff = 999;
		List<Integer> factors = new ArrayList<Integer>();
		int checkUntil = (int) Math.sqrt(numPages) + 1;
		for(int i = 1; i < checkUntil; i++ ) {
			if(numPages%i == 0) {
				factors.add(new Integer(i));
			}
		}
		
		for(int j = 0; j < factors.size(); j++) {
			int diff = Math.abs(factors.get(j).intValue() - (numPages/factors.get(j).intValue()));
			if(diff < bestFactorDiff) {
				bestFactorDiff = diff;
				factorIndex = j;
			}
		}
		
		return factors.get(factorIndex);
	}

	public void newPage(NotepageComponent comp) {
		numPages++;
		
				
		comp.setColor(newColor);
		pageList.add(comp);
		resizePages();
		this.add(comp);
		resizePages();
		layoutPages();
		pageList.get(curPage).setVisible(false);
		curPage = numPages-1;
		pageList.get(curPage).setVisible(true);
		StatusPanel.setStatus("Page: " + curPage);
	}
	
	public void deletePage() {
		numPages--;
		this.remove(pageList.remove(curPage));
		
		if(curPage != 0) {
			curPage--;
			pageList.get(curPage).setVisible(true);
		} else {
			curPage = 0;
			pageList.get(curPage).setVisible(true);
			
		}
		StatusPanel.setStatus("Page: " + curPage);
	}
	
	public void nextPage() {
		if(curPage >= numPages -1) {
			StatusPanel.setStatus("No Next Page");
			return;
		}
		pageList.get(curPage).setVisible(false);
		
		layoutPages();
		curPage++;
		pageList.get(curPage).setVisible(true);
	}
	
	public void prevPage() {
		if(curPage <= 0) {
			StatusPanel.setStatus("On First Page");
			return;
		}
		pageList.get(curPage).setVisible(false);
		
		layoutPages();
		curPage--;
		pageList.get(curPage).setVisible(true);
	}
	
	public void changeColor(Color c) {
		newColor = c;
		for(int i = 0; i <pageList.size(); i++) {
			pageList.get(i).setColor(newColor);
		}
	}

	public int getCurPage() {
		
		return curPage;
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
		if(width < this.getWidth()) {
			width = this.getWidth();
		}
		if(height < this.getHeight()) {
			height = this.getHeight();
		}
		Dimension d = new Dimension(width,height);
		for(int j = 0; j < pageList.size(); j++) {
			pageList.get(j).setMinimumSize(new Dimension(d));
			pageList.get(j).setPreferredSize(new Dimension(d));
			pageList.get(j).setMaximumSize(new Dimension(9999999,9999999));
			pageList.get(j).setSize(new Dimension(d));
			pageList.get(j).resized(this.getWidth(),this.getHeight());
			pageList.get(j).repaint();
		}
		
		
	}

	public void setCurPage(int i) {
		// TODO Auto-generated method stub
		curPage = i;
	}

	public int getNumPages() {
		// TODO Auto-generated method stub
		return numPages;
	}

	public List<NotepageComponent> getPageList() {
		// TODO Auto-generated method stub
		return pageList;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
		layoutPages();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void setOverview(boolean b) {
		// TODO Auto-generated method stub
		overviewMode = b;
		
	}

	public boolean getOverview() {
		// TODO Auto-generated method stub
		return overviewMode;
	}
	
	
	
}
