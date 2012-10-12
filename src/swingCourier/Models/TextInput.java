package swingCourier.Models;

import java.awt.Color;
/**
 * A text object which holds its start position, font color, and text
 * @author evan
 *
 */
public class TextInput {
	private String text = "";
	private int xPos, yPos;
	private Color color;

	public TextInput(int x, int y, Color color) {
		xPos = x;
		yPos = y;
		this.color = color;
	}
	
	public TextInput(int x, int y, Color color, String s) {
		xPos = x;
		yPos = y;
		text = s;
	}

	public void addChar(char c) {
		text = text + c;
	}

	public void deletePrev() {
		text = text.substring(0, (text.length() - 2));
	}

	public String getText() {
		return text;
	}

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}
	
	public Color getColor() {
		return color;
	}
}
