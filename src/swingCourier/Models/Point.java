package swingCourier.Models;

public class Point {
	private int xPos;
	private int yPos;
	private final char N = 'N';
	private final char NE = 'B';
	private final char E = 'E';
	private final char SE = 'C';
	private final char S = 'S';
	private final char SW = 'D';
	private final char W = 'W';
	private final char NW = 'A';
	private final char EQUAL = 'J';
	
	public Point(int x, int y) {
		xPos = x;
		yPos = y;
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
	
	public char comparePoint(Point prev) {
		if(xPos == prev.getxPos() && yPos < prev.yPos) {
			return N;
		} else if(xPos > prev.getxPos() && yPos < prev.yPos) {
			return NE;
		} else if(xPos > prev.getxPos() && yPos == prev.getyPos()) {
			return E;
		} else if(xPos > prev.getxPos() && yPos > prev.yPos) {
			return SE;
		} else if(xPos == prev.getxPos() && yPos > prev.yPos) {
			return S;
		} else if(xPos < prev.getxPos() && yPos > prev.yPos) {
			return SW;
		} else if(xPos < prev.getxPos() && yPos == prev.getyPos()) {
			return W;
		} else if(xPos < prev.getxPos() && yPos < prev.yPos) {
			return NW;
		}
		
		
		
		return 'J';
	}

}
