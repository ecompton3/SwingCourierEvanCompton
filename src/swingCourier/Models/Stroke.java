package swingCourier.Models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
/**
 * A stroke object which holds its type, start point, end point, and color
 * @author evan
 *
 */
public class Stroke {

	private int xPos, yPos, xEnd, yEnd;
	private Color color;
	private String type;
	private List<Point> points;
	
	public Stroke(int xPos, int yPos, int xEnd, int yEnd, String type, Color color) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		this.type = type;
		this.color = color;
		points = new ArrayList<Point>();
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getxEnd() {
		return xEnd;
	}

	public void setxEnd(int xEnd) {
		this.xEnd = xEnd;
	}

	public int getyEnd() {
		return yEnd;
	}

	public void setyEnd(int yEnd) {
		this.yEnd = yEnd;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}
	
	public void addPoint(Point p) {
		points.add(p);
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
}
