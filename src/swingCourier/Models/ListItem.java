package swingCourier.Models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class ListItem {
	List<Stroke> strokes;
	Rectangle bounds, originalBounds;

	public ListItem(Stroke s) {
		strokes = new ArrayList<Stroke>();
		strokes.add(s);
		originalBounds = null;
		bounds = s.getBounds();
	}
	
	
	
	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public List<Stroke> getStrokes() {
		return strokes;
	}
	
	public void setStrokes(List<Stroke> s) {
		strokes = s;
	}
	
	public void addStroke(Stroke s) {
		strokes.add(s);
	}
	
	public void drawStroke(Graphics2D g2) {
		for(int i = 0; i < strokes.size(); i++) {
			Stroke stroke = strokes.get(i);
			g2.setColor(stroke.getColor());
			int width = stroke.getxEnd() - stroke.getxPos();
			int height = stroke.getyEnd() - stroke.getyPos();
			List<Point> points = stroke.getPoints();
				for(int j = 0; j < points.size(); j++) {
	    			if (j == points.size() - 1) {
	    				g2.drawLine(points.get(j).getxPos(), points.get(j).getyPos(), stroke.getxEnd(), stroke.getyEnd());
	    			} else if(j > 1){
	    				g2.drawLine(points.get(j-1).getxPos(), points.get(j-1).getyPos(), points.get(j).getxPos(), points.get(j).getyPos());
	    			}
	    		}
		}
		Color c = g2.getColor();
		g2.setColor(Color.MAGENTA);
		g2.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		g2.setColor(c);
			
			
	
	}
	
	public void transformStroke(int xChange, int yChange) {
		for(int i = 0; i < strokes.size(); i++) {
			Stroke stroke = strokes.get(i);
			
			
			for(int j = 0; j < stroke.getPoints().size(); j++) {
				Point p = stroke.getPoints().get(j);
				p.setyPos(p.getyPos() + yChange);
				p.setxPos(p.getxPos() + xChange);
			}
			stroke.setxEnd(stroke.getxEnd() + xChange);
			stroke.setyEnd(stroke.getyEnd() + yChange);
		}	
	}

	public void setOriginalBounds(Rectangle rect) {
		originalBounds = rect;
	}

	public Rectangle getOriginalBounds() {
		// TODO Auto-generated method stub
		return originalBounds;
	}
}
