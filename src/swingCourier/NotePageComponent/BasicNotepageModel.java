package swingCourier.NotePageComponent;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import swingCourier.Models.ListItem;
import swingCourier.Models.ListObject;
import swingCourier.Models.Point;
import swingCourier.Models.Recognizer;
import swingCourier.Models.Stroke;
import swingCourier.Models.TextInput;
/**
 * Basic implementation of the NotepageModel
 * @author evan
 *
 */
public class BasicNotepageModel implements NotepageModel {

	private List<Stroke> strokeList = new ArrayList<Stroke>();
	
	private List<ListObject> lists = new ArrayList<ListObject>();

	private List<TextInput> textList = new ArrayList<TextInput>();

	private int initX, initY, curX, curY;
	private boolean drawing, typing, gesturing, selecting;
	public static String strokeType = "Freeform";
	private ChangeListener listener;
	private TextInput curText;
	private Color curColor = new Color(0,0,0);
	private Stroke stroke;
	private Recognizer recognize = new Recognizer();
	private String gesture;
	private Rectangle selectionBounds, listBounds;

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

	public boolean checkIfFreeform() {
		if (strokeType.equals("Freeform")) {
			if(stroke != null) {
				addPoint(stroke.getxEnd(), stroke.getyEnd());
				stroke.setxEnd(curX);
				stroke.setyEnd(curY);
			} else {
				stroke = new Stroke(initX, initY, curX, curY, strokeType, curColor);
			}
			
			return true;
		}
		return false;
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
			if(!textList.contains(curText)) {
				textList.add(curText);
			}
		}	
		curText = null;
		listener.stateChanged(new ChangeEvent(textList));
	}
	
	@Override
	public void addText(TextInput text) {
		if(!textList.contains(text)) {
			textList.add(text);
		}
			
			
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
	public boolean checkGesture() {
		// TODO Auto-generated method stub
		gesture = recognize.checkGesture(stroke.getPoints());
		if(gesture == null) {
			return false;
		}
		if(gesture.equals("Next")) {
			listener.stateChanged(new ChangeEvent(gesture));
		}
		return true;
	}

	@Override
	public void resetStroke() {
		stroke = null;
		
	}
	
	public String getGesture() {
		return gesture;
	}

	@Override
	public void deleteObjects() {
		
		findShapesToDelete(makeFreeformBoundry(stroke));
		
	}

	/**
	 * Creates a bounding box based off some freeform stroke
	 * @param freeform The stroke to create a boundary from
	 * @return The rectangle representation of the boundary box
	 */
	private Rectangle makeFreeformBoundry(Stroke freeform) {
		Rectangle bounds;
		int minX = 999999, maxX = 0, minY = 999999, maxY = 0;
		List<Point> points = freeform.getPoints();
		for(int i = 0; i < points.size(); i++) {
			if(points.get(i).getxPos() < minX) {
				minX = points.get(i).getxPos();
			}
			if(points.get(i).getxPos() > maxX) {
				maxX = points.get(i).getxPos();
			}
			if(points.get(i).getyPos() < minY) {
				minY = points.get(i).getyPos();
			}
			if(points.get(i).getyPos() > maxY) {
				maxY = points.get(i).getyPos();
			}
		}
		if(minX == maxX) {
			maxX++;
		}
		if(minY == maxY) {
			maxY++;
		}
		bounds = new Rectangle(minX,minY,(maxX-minX),(maxY-minY));
		freeform.setBounds(bounds);
		return bounds;
	}
	/**
	 * Determines what shapes to delete based off some boundary box
	 * @param rect The boundary box in which to check
	 */
	private void findShapesToDelete(Rectangle rect) {
		List<Stroke> strokesToDelete = new ArrayList<Stroke>();
		List<TextInput> textToDelete = new ArrayList<TextInput>();
		for(int i = 0; i < strokeList.size(); i++) {
			Stroke curr = strokeList.get(i);
			if(curr.getType().equals("Freeform")) {
				Rectangle freeBound = makeFreeformBoundry(curr);
				if(rect.contains(freeBound)) {
					strokesToDelete.add(curr);
				}
			} else {
				int boundX, boundY, boundHeight, boundWidth;
				if(curr.getxPos() < curr.getxEnd()) {
					boundX = curr.getxPos();
				} else {
					boundX = curr.getxEnd();
				}
				if(curr.getyPos() < curr.getyEnd()) {
					boundY = curr.getyPos();
				} else {
					boundY = curr.getyEnd();
				}
				boundWidth = Math.abs(curr.getxPos() - curr.getxEnd());
				boundHeight = Math.abs(curr.getyPos() - curr.getyEnd());
				Rectangle shapeBound = new Rectangle(boundX,boundY,boundWidth,boundHeight);
				if(rect.contains(shapeBound)) {
					strokesToDelete.add(curr);
				}
			}
		}
		
		for(int j = 0; j < textList.size(); j++) {
			TextInput text = textList.get(j);
			int textWidth = (int) text.getBounds().getWidth();
			int textHeight = (int) text.getBounds().getHeight();
			Rectangle textBound = new Rectangle(text.getxPos(), text.getyPos(),textWidth,textHeight);
			if(rect.contains(textBound)) {
				textToDelete.add(textList.get(j));
			}
		}
		
		for(int k = 0; k < strokesToDelete.size(); k++) {
			strokeList.remove(strokesToDelete.get(k));
		}
		
		for(int m = 0; m < textToDelete.size(); m++) {
			while(textList.remove(textToDelete.get(m)));
		}
		
		
	}
	
	@Override
	public void setSelectMode(boolean b) {
		selecting = b;
		
	}

	@Override
	public boolean getSelectMode() {
		
		return selecting;
	}

	@Override
	public Rectangle getSelectionBounds() {
		// TODO Auto-generated method stub
		return selectionBounds;
	}

	@Override
	public void selectObjects() {
		findShapesToSelect(makeFreeformBoundry(stroke));
		
	}
	/**
	 * Finds out what shapes should be put into select mode based off a boundary
	 * @param rect The selection boundary
	 */
	private void findShapesToSelect(Rectangle rect) {
		// TODO Auto-generated method stub
		selectionBounds = rect;
		for(int i = 0; i < strokeList.size(); i++) {
			Stroke curr = strokeList.get(i);
			if(curr.getType().equals("Freeform")) {
				Rectangle freeBound = makeFreeformBoundry(curr);
				if(rect.contains(freeBound)) {
					curr.setSelected(true);
				}
			} else {
				int boundX, boundY, boundHeight, boundWidth;
				if(curr.getxPos() < curr.getxEnd()) {
					boundX = curr.getxPos();
				} else {
					boundX = curr.getxEnd();
				}
				if(curr.getyPos() < curr.getyEnd()) {
					boundY = curr.getyPos();
				} else {
					boundY = curr.getyEnd();
				}
				boundWidth = Math.abs(curr.getxPos() - curr.getxEnd());
				boundHeight = Math.abs(curr.getyPos() - curr.getyEnd());
				Rectangle shapeBound = new Rectangle(boundX,boundY,boundWidth,boundHeight);
				if(rect.contains(shapeBound)) {
					curr.setSelected(true);
				}
			}
		}
		
		for(int j = 0; j < textList.size(); j++) {
			TextInput text = textList.get(j);
			int textWidth = (int) text.getBounds().getWidth();
			int textHeight = (int) text.getBounds().getHeight();
			Rectangle textBound = new Rectangle(text.getxPos(), text.getyPos(),textWidth,textHeight);
			if(rect.contains(textBound)) {
				text.setSelected(true);
			}
		}
	}

	@Override
	public void moveSelection(int x, int y) {
		// TODO Auto-generated method stub
		for(int i = 0; i < strokeList.size(); i++) {
			Stroke curr = strokeList.get(i);
			if(curr.getSelected()) {
				if(curr.getType().equals("Freeform")) {
					for(int j = 0; j < curr.getPoints().size(); j++) {
						Point p = curr.getPoints().get(j);
						p.setxPos(p.getxPos() + x);
						p.setyPos(p.getyPos() + y);
					}
					curr.setxEnd(curr.getxEnd() + x);
					curr.setyEnd(curr.getyEnd() + y);
				} else {
					curr.setxPos(curr.getxPos() + x);
					curr.setyPos(curr.getyPos() + y);
					curr.setxEnd(curr.getxEnd() + x);
					curr.setyEnd(curr.getyEnd() + y);
				}
			}
		}
		for(int j = 0; j < textList.size(); j++) {
			TextInput curText = textList.get(j);
			if(curText.getSelected()) {
				curText.setxPos(curText.getxPos() + x);
				curText.setyPos(curText.getyPos() + y);
			}
		}
		selectionBounds.x = selectionBounds.x + x;
		selectionBounds.y = selectionBounds.y + y;
	}

	@Override
	public void deselect() {
		// TODO Auto-generated method stub
		for(int i = 0; i < strokeList.size(); i++) {
			strokeList.get(i).setSelected(false);
		}
		for(int j = 0; j < textList.size(); j++) {
			textList.get(j).setSelected(false);
		}
	}
	
	public void listifyObjects() {
		findListObjects(makeFreeformBoundry(stroke));
	}
	
	private void findListObjects(Rectangle rect) {
		Queue<Stroke> listStrokes = new LinkedBlockingQueue<Stroke>();
		listBounds = makeListBounds(rect);
		
		for(int i = 0; i < strokeList.size(); i++) {
			Stroke curr = strokeList.get(i);
			if(curr.getType().equals("Freeform")) {
				Rectangle freeBound = makeFreeformBoundry(curr);
				if(listBounds.contains(freeBound)) {
					strokeList.remove(i);
					listStrokes.add(curr);
					i--;
				}
			} 
		}
		if(listStrokes.size() > 0)
		segmentList(listStrokes);
		
	}
	
	private void segmentList(Queue<Stroke> strokes) {
		ListObject list = new ListObject(listBounds);
		ListItem item = null;
		while(!strokes.isEmpty()) {
			if(item == null) {
				item = new ListItem(strokes.poll());
			} else {
				Stroke curStroke = strokes.peek();
				Rectangle prevBounds = item.getBounds();
				Rectangle curBounds = curStroke.getBounds();
				int xBoundLoc = prevBounds.x + prevBounds.width;
				int yBoundLoc = prevBounds.y + prevBounds.height;
				
				if(curBounds.x - xBoundLoc < 50 ) {
					int width = prevBounds.width + (curBounds.x - xBoundLoc) + curBounds.width;
					int xLoc = prevBounds.x;
					if(curBounds.x < xLoc) {
						xLoc = curBounds.x;
						if(xLoc + curBounds.width > xBoundLoc) {
							width = prevBounds.width + ((xLoc + curBounds.width)-xBoundLoc);
						}
					}
					if(curBounds.y < prevBounds.y) {
						item.addStroke(strokes.poll());						
						if(curBounds.y + curBounds.height > yBoundLoc) {
							
							item.setBounds(new Rectangle(xLoc, curBounds.y, width, curBounds.height) );
						} else {
							item.setBounds(new Rectangle(xLoc, curBounds.y, width, 
									prevBounds.height + (prevBounds.y - curBounds.y)) );
						}
					} else if(curBounds.y <= yBoundLoc && curBounds.y >= prevBounds.y) {
						int yDiff = yBoundLoc - (curBounds.y + curBounds.height);
						double percentIn = (curBounds.y + yDiff * 1.0)/(curBounds.height);
						if(percentIn >= 0.8) {
							item.addStroke(strokes.poll());
							int curYBoundLoc = curBounds.y + curBounds.height;
							if(curYBoundLoc > yBoundLoc) {
								item.setBounds(new Rectangle(xLoc, prevBounds.y, width, 
										prevBounds.height + (curYBoundLoc - yBoundLoc)) );
							} else {
								item.setBounds(new Rectangle(xLoc, prevBounds.y, width, prevBounds.height));
							}
						} else {
							list.add(item);
							item = null;
						}
					} else {
						list.add(item);
						item = null;
					}
				} else {
					strokeList.add(strokes.poll());
					curStroke = strokes.peek();
					curBounds = curStroke.getBounds();
					while(curBounds.x  > xBoundLoc) {
						strokeList.add(strokes.poll());
						curStroke = strokes.peek();
						curBounds = curStroke.getBounds();
					}
					
					list.add(item);
					item = null;
				}
				
			}
			if(strokes.isEmpty()) {
				list.add(item);
			}
		}		
		
		list.normalizeList();
		lists.add(list);
	}
	
	private Rectangle makeListBounds(Rectangle rect) {
		int x      = (int) rect.getX(),
		    y      = (int) rect.getY(),
		    height = (int) rect.getHeight(),
		    maxWidth  = (int) rect.getWidth();
		
		for(int i = 0; i < strokeList.size(); i++) {
			Stroke curr = strokeList.get(i);
			if(curr.getType().equals("Freeform")) {
				Rectangle freeBound = makeFreeformBoundry(curr);
				if(freeBound.getY() > y && freeBound.getY() + freeBound.getHeight() < y + height &&
						freeBound.getX() > x && freeBound.getX() + freeBound.getWidth() > 
						x + maxWidth) {
					maxWidth = (int) (x + freeBound.getX() + freeBound.getWidth());
				}
			} 
		}
		
		return new Rectangle(x,y,maxWidth,height);
	}

	@Override
	public List<ListObject> getLists() {
		// TODO Auto-generated method stub
		return lists;
	}

	@Override
	public void moveElementUp() {
		Rectangle bounds = makeFreeformBoundry(stroke);
		ListObject list = null;
		int index = -1, listIndex = -1;
		for(int i = 0; i < lists.size(); i++) {
			if(lists.get(i).getBounds().contains(bounds)) {
				list = lists.get(i);
				listIndex = i;
			}
		}
		if(list == null) {
			return;
		} else {
			for(int j = 0; j < list.getItems().size(); j++) {
				if(list.getItems().get(j).getBounds().contains(bounds)) {
					index = j;
				}
			}
		}
		if(index == -1 || index == 0) {
			return;
		} else {
			List temp = list.getItems().get(index - 1).getStrokes();
			List temp2 = list.getItems().get(index).getStrokes();
			list.getItems().get(index).setStrokes(temp);
			list.getItems().get(index-1).setStrokes(temp2);
			list.adjustItemStrokes(index, index-1);
			
		}
		list.normalizeList();
		lists.set(listIndex, list);
	}

	@Override
	public void moveElementDown() {
		Rectangle bounds = makeFreeformBoundry(stroke);
		ListObject list = null;
		int index = -1, listIndex = -1;
		for(int i = 0; i < lists.size(); i++) {
			if(lists.get(i).getBounds().contains(bounds)) {
				list = lists.get(i);
				listIndex = i;
			}
		}
		if(list == null) {
			return;
		} else {
			for(int j = 0; j < list.getItems().size(); j++) {
				if(list.getItems().get(j).getBounds().contains(bounds)) {
					index = j;
				}
			}
		}
		if(index == -1 || index == list.getItems().size() - 1) {
			return;
		} else {
			List temp = list.getItems().get(index + 1).getStrokes();
			List temp2 = list.getItems().get(index).getStrokes();
			list.getItems().get(index).setStrokes(temp);
			list.getItems().get(index+1).setStrokes(temp2);
			list.adjustItemStrokes(index+1, index);
			
		}
		list.normalizeList();
		lists.set(listIndex, list);
		
	}

	@Override
	public void deleteListItem() {
		Rectangle bounds = makeFreeformBoundry(stroke);
		ListObject list = null;
		int index = -1, listIndex = -1;
		for(int i = 0; i < lists.size(); i++) {
			if(lists.get(i).getBounds().contains(bounds)) {
				list = lists.get(i);
				listIndex = i;
			}
		}
		if(list == null) {
			return;
		} else {
			for(int j = 0; j < list.getItems().size(); j++) {
				if(list.getItems().get(j).getBounds().contains(bounds)) {
					index = j;
				}
			}
		}
		if(index == -1) {
			return;
		} else {
			/*List temp = list.getItems().get(index + 1).getStrokes();
			List temp2 = list.getItems().get(index).getStrokes();
			list.getItems().get(index).setStrokes(temp);
			list.getItems().get(index+1).setStrokes(temp2);
			list.adjustItemStrokes(index+1, index);*/
			list.getItems().remove(index);
			
		}
		if(list.getItems().size() > 0) {
			list.normalizeList();
			lists.set(listIndex, list);
		} else {
			lists.remove(listIndex);
		}
		
		
	}

}
