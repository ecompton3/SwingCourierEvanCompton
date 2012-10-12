package swingCourier.NotePageComponent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import swingCourier.Models.Point;
import swingCourier.Models.Stroke;
import swingCourier.Models.TextInput;
/**
 * Basic implementation of the NotepageModel
 * @author evan
 *
 */
public class BasicNotepageModel implements NotepageModel {

	private List<Stroke> strokeList = new ArrayList<Stroke>();

	private List<TextInput> textList = new ArrayList<TextInput>();

	private int initX, initY, curX, curY;
	private boolean drawing, typing, gesturing;
	public static String strokeType = "Freeform";
	private ChangeListener listener;
	private TextInput curText;
	private Color curColor = new Color(0,0,0);
	private Stroke stroke;

	@Override
	public void removeChangeListener(ChangeListener listener) {
		this.listener = null;
	}

	@Override
	public void addChangeListener(ChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void setCurrentX(int x) {
		curX = x;
		listener.stateChanged(new ChangeEvent(strokeList));
	}

	@Override
	public void setCurrentY(int y) {
		curY = y;
		listener.stateChanged(new ChangeEvent(strokeList));
	}

	@Override
	public void setInitialX(int x) {
		initX = x;
	}

	@Override
	public void setInitialY(int y) {
		initY = y;
	}

	@Override
	public void addStroke(int finalX, int finalY) {
		if(strokeType.equals("Freeform") && stroke != null) {
			stroke.setxEnd(finalX);
			stroke.setyEnd(finalY);
			strokeList.add(stroke);
			stroke = null;
		} else {
			strokeList.add(new Stroke(initX, initY, finalX, finalY, strokeType,curColor));
		}
		
		listener.stateChanged(new ChangeEvent(strokeList));
	}

	public void checkIfFreeform() {
		if (strokeType.equals("Freeform")) {
			if(stroke != null) {
				addPoint(stroke.getxEnd(), stroke.getyEnd());
				stroke.setxEnd(curX);
				stroke.setyEnd(curY);
			} else {
				stroke = new Stroke(initX, initY, curX, curX, strokeType, curColor);
			}
			
			
		}
	}

	private void addPoint(int curX2, int curY2) {
		stroke.addPoint(new Point(curX2, curY2));
		
	}

	@Override
	public List<Stroke> getStrokeList() {
		return strokeList;
	}

	@Override
	public Stroke getCurrentStroke() {
		Stroke curStroke = new Stroke(initX, initY, curX, curY, strokeType, curColor);
		if (drawing) {
			if(strokeType.equals("Freeform")) {
				return stroke;
			} else {
				return curStroke;
			}
			
		} else {
			return null;
		}

	}

	public static void setStrokeType(String text) {
		strokeType = text;
	}

	@Override
	public void stillDrawing(boolean draw) {
		drawing = draw;
	}

	@Override
	public void typing(boolean type) {
		typing = type;
	}

	@Override
	public void addText() {
		if(curText !=null) {
			textList.add(curText);
		}		
		listener.stateChanged(new ChangeEvent(textList));
	}
	
	@Override
	public void addText(TextInput text) {
		
			textList.add(text);
			
		listener.stateChanged(new ChangeEvent(textList));
	}
	
	@Override
	public List<TextInput> getTextList() {
		return textList;
	}

	@Override
	public void startNewText(int x, int y) {
		curText = new TextInput(x, y, curColor);

	}

	@Override
	public void addNewChar(char c) {
		if (typing) {
			curText.addChar(c);
		}
		listener.stateChanged(new ChangeEvent(curText));
	}

	@Override
	public TextInput getCurText() {
		return curText;
	}
	
	@Override
	public void setCurText(TextInput text) {
		curText = text;
	}

	@Override
	public void setColor(Color newColor) {
		curColor = newColor;
	}
	
	public Color getColor() {
		return curColor;
	}

	@Override
	public void setGestureMode(boolean b) {
		gesturing = b;
		
	}

	@Override
	public boolean getGestureMode() {
		// TODO Auto-generated method stub
		return gesturing;
	}

	@Override
	public void checkGesture(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetStroke() {
		stroke = null;
		
	}
	

}
