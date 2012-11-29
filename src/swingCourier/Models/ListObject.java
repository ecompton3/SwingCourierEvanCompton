package swingCourier.Models;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class ListObject {
	private List<ListItem> listItems;
	private Rectangle listBounds;
	
	public ListObject(Rectangle bounds) {
		listBounds = bounds;
		listItems = new ArrayList<ListItem>();
	}
	
	public void add(ListItem l) {
		listItems.add(l);
	}
	
	public Rectangle getBounds() {
		return listBounds;
	}
	
	public List<ListItem> getItems() {
		return listItems;
	}
	
	public void normalizeList() {
		int xPos      = 999999,
			yPos      = 999999,
			maxWidth  = 0, 
			maxHeight = 0;
		for(int i = 0; i < listItems.size(); i++) {
			ListItem listItem = listItems.get(i);
			Rectangle bounds = listItem.getBounds();
			if(bounds.x < xPos) {
				xPos = bounds.x;
			}
			
			if(bounds.width > maxWidth) {
				maxWidth = bounds.width;
			}
			if(bounds.height > maxHeight) {
				maxHeight = bounds.height;
			}
		}
		for(int j = 0; j < listItems.size(); j++) {
			ListItem listItem = listItems.get(j);
			Rectangle bounds = listItem.getBounds();
			int xChange, yChange;
			if(j == 0) {
				yPos = bounds.y;
			} else {
				yPos = listItems.get(j - 1).getBounds().y + listItems.get(j - 1).getBounds().height;
			}
			xChange = Math.abs(bounds.x - xPos) * -1;
			
				yChange = yPos - bounds.y;
			
			listItem.setBounds(new Rectangle(xPos,yPos,maxWidth,maxHeight));
			
			listItem.transformStroke(xChange, yChange);
		}
		updateListBounds();
	}

	private void updateListBounds() {
		int xPos, yPos, width, height;
		xPos = listItems.get(0).getBounds().x;
		yPos = listItems.get(0).getBounds().y;
		width = listItems.get(0).getBounds().width;
		height = (listItems.get(listItems.size()-1).getBounds().y + listItems.get(listItems.size()-1).getBounds().height) - yPos;
		listBounds = new Rectangle(xPos,yPos,width,height);
		
	}

	public void adjustItemStrokes(int indexDown, int indexUp) {
		int yChange = Math.abs(listItems.get(indexDown).getBounds().y - listItems.get(indexUp).getBounds().y);
		listItems.get(indexDown).transformStroke(0, yChange);
		listItems.get(indexUp).transformStroke(0, -yChange);
		
	}
}
