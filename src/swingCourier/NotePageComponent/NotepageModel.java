package swingCourier.NotePageComponent;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.event.ChangeListener;

import swingCourier.Models.Stroke;
import swingCourier.Models.TextInput;
/**
 * The interface for all notepage models
 * @author evan
 *
 */
public interface NotepageModel {
	/**
	 * Removes the current change listener
	 * @param listener
	 */
	public void removeChangeListener(ChangeListener listener);
	/**
	 * Adds a change listener to the model
	 * @param listener
	 */
	public void addChangeListener(ChangeListener listener);
	/**
	 * sets the current x position of the mouse
	 * @param x
	 */
	public void setCurrentX(int x);
	/**
	 * sets the current y position of the mouse
	 * @param y
	 */
	public void setCurrentY(int y);
	/**
	 * sets an initial value for the x position
	 * @param x
	 */
	public void setInitialX(int x);
	/**
	 * sets an initial value for the y position
	 * @param y
	 */
	public void setInitialY(int y);
	/**
	 * Adds a stroke that ends at some final x,y point	
	 * @param finalX
	 * @param finalY
	 */
	public void addStroke(int finalX, int finalY);
	/**
	 * Checks if the current stroke type is Freeform
	 */
	public boolean checkIfFreeform();
	/**
	 * Gets the list of stroke objects
	 * @return
	 */
	public List<Stroke> getStrokeList();
	/**
	 * getsthe current stroke
	 * @return
	 */
	public Stroke getCurrentStroke();
	/**
	 * checks if a stroke is still in process
	 * @param b
	 */
	public void stillDrawing(boolean b);
	/**
	 * checks if the notepage should still capture key events
	 * @param type
	 */
	public void typing(boolean type);
	/**
	 * adds the current TextInput object to the list of text input objects
	 */
	public void addText();
	/**
	 * Gets the list of text
	 * @return
	 */
	public List<TextInput> getTextList();
	/**
	 * Starts a new TextInput at a x,y point
	 * @param x
	 * @param y
	 */
	public void startNewText(int x, int y);
	/**
	 * adds a new character to the currently being typed text
	 * @param c
	 */
	public void addNewChar(char c);
	/**
	 * Gets the current TextInput object
	 * @return
	 */
	public TextInput getCurText();
	/**
	 * Adds a new text object to the text list
	 * @param text
	 */
	public void addText(TextInput text);
	/**
	 * Sets the current TextInput object to some new TextInput
	 * @param text
	 */
	public void setCurText(TextInput text);
	/**
	 * sets the current stroke color of the notepage
	 * @param newColor
	 */
	public void setColor(Color newColor);
	/**
	 * gets the current stroke color of the notepage
	 * @return
	 */
	public Color getColor();
	/**
	 * Turns on gesture mode
	 * @param b
	 */
	public void setGestureMode(boolean b);
	/**
	 * Checks if user is gesturing
	 * @return
	 */
	public boolean getGestureMode();
	/**
	 * Determines if a gesture ws drawn
	 * @return true if a matched gesture was fired, otherwise false
	 */
	public boolean checkGesture();
	/**
	 * Resets the current stroke to null
	 */
	public void resetStroke();
	/**
	 * Returns the matched gesture
	 * @return A String representing the gesture
	 */
	public String getGesture();
	/**
	 * Deletes objects from the page
	 */
	public void deleteObjects();
	/**
	 * Turns on the selection mode
	 * @param b
	 */
	public void setSelectMode(boolean b);
	/**
	 * Checks if in selection mode
	 * @return
	 */
	public boolean getSelectMode();
	/**
	 * Gets the boundries of the selected area
	 * @return A Rectangle representing the bounds area
	 */
	public Rectangle getSelectionBounds();
	/**
	 * Puts objects in the selection area in selection mode
	 */
	public void selectObjects();
	/**
	 * Translates objects based on change in mouse direction
	 * @param x Change in x direction
	 * @param y Change in y direction
	 */
	public void moveSelection(int x, int y);
	/**
	 * Deselects all objects
	 */
	public void deselect();
	
	
}
