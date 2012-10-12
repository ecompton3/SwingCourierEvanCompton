package swingCourier.NotePageComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;

import swingCourier.Models.Point;
import swingCourier.Models.Stroke;
import swingCourier.Models.TextInput;
/**
 * Basic UI delegate for the notepage component. Captures mouse and key events.
 * @author evan
 *
 */
public class BasicNotepageUI extends NotepageUI implements MouseListener, MouseMotionListener, KeyListener{
	 public static final String UI_CLASS_ID = "BasicNotepageUI";
	private NotepageComponent page;
	private Graphics2D g2;
	
    public static ComponentUI createUI(JComponent c) {
        return new BasicNotepageUI();
    }
    public void installUI(JComponent c) {
    	page = (NotepageComponent) c;
    	

		c.setLayout(new BorderLayout());
		c.setBorder(new EmptyBorder(1, 1, 1, 1));
    	page.addMouseListener(this); // we’ll handle mouse events for the Notepage component
    	page.addMouseMotionListener(this);
    	page.addKeyListener(this);
    }
        
    
    public void uninstallUI(JComponent c) {
    	
        ((NotepageComponent) c).removeMouseListener(this);
    }
    
   
    public void paint(Graphics g, JComponent c) {
        // do painting for the component here!
    	g2 = (Graphics2D) g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	drawBackground(g2);
    	
    	drawStrokes(g2);
    	g2.setColor(Color.black);
    	drawText(g2);
    }
    /**
     * Draws the red/white paper background
     * @param g2 The current Graphics 2D instance
     */
    public void drawBackground(Graphics2D g2) {
    	int width = page.getWidth();
    	int height = page.getHeight();
    	int lineY = 30;
    	g2.setColor(Color.white);
    	g2.fillRect(0, 0, width, height);
    	g2.setColor(Color.red);
    	g2.drawLine(20, 0, 20, height);
    	do {
    		g2.drawLine(0, lineY, width, lineY);
    		lineY += 30;
    	}while(lineY <= height - 30);
    }
    /**
     * Draws the stored and current stroke
     * @param g2 The current graphics 2D instance
     */
    public void drawStrokes(Graphics2D g2) {
    	List<Stroke> strokes = page.getModel().getStrokeList();
    	int width;
		int height;
    	for(int i = 0; i < strokes.size(); i++) {
    		Stroke stroke = strokes.get(i);
    		g2.setColor(stroke.getColor());
    		width = stroke.getxEnd() - stroke.getxPos();
    		height = stroke.getyEnd() - stroke.getyPos();
    		if(stroke.getType().equals("Freeform")) {
    			List<Point> points = stroke.getPoints();
    			for(int j = 0; j < points.size(); j++) {
    				if(j == 0) {
    					g2.drawLine(stroke.getxPos(), stroke.getyPos(), points.get(j).getxPos(), points.get(j).getyPos());
    					System.out.println("Stroke " + i + " Ypos: " + stroke.getyPos());
    				} else if (j == points.size() - 1) {
    					g2.drawLine(points.get(j).getxPos(), points.get(j).getyPos(), stroke.getxEnd(), stroke.getyEnd());
    				} else {
    					g2.drawLine(points.get(j-1).getxPos(), points.get(j-1).getyPos(), points.get(j).getxPos(), points.get(j).getyPos());
    				}
    			}
    		} else if (stroke.getType().equals("Rectangle")) {
    			
    			if(width < 0 && height < 0) {
    				g2.drawRect(stroke.getxEnd(), stroke.getyEnd(), Math.abs(width), Math.abs(height));
    			} else if (width < 0 && height >= 0) {
    				g2.drawRect(stroke.getxEnd(), stroke.getyPos(), Math.abs(width), Math.abs(height));
    			} else if (width >= 0 && height < 0) {
    				g2.drawRect(stroke.getxPos(), stroke.getyEnd(), Math.abs(width), Math.abs(height));
    			} else if (width >= 0 && height >= 0) {
    				g2.drawRect(stroke.getxPos(), stroke.getyPos(), Math.abs(width), Math.abs(height));
    			}
    		} else if (stroke.getType().equals("Oval")) {
    			if(width < 0 && height < 0) {
    				g2.drawOval(stroke.getxEnd(), stroke.getyEnd(), Math.abs(width), Math.abs(height));
    			} else if (width < 0 && height >= 0) {
    				g2.drawOval(stroke.getxEnd(), stroke.getyPos(), Math.abs(width), Math.abs(height));
    			} else if (width >= 0 && height < 0) {
    				g2.drawOval(stroke.getxPos(), stroke.getyEnd(), Math.abs(width), Math.abs(height));
    			} else if (width >= 0 && height >= 0) {
    				g2.drawOval(stroke.getxPos(), stroke.getyPos(), Math.abs(width), Math.abs(height));
    			}
    		}
    	}
    	Stroke curStroke = page.getModel().getCurrentStroke();
    	
    	if(curStroke != null) {
    		g2.setColor(curStroke.getColor());
    		width = curStroke.getxEnd() - curStroke.getxPos();
    		height = curStroke.getyEnd() - curStroke.getyPos();
    		if(curStroke.getType().equals("Freeform")) {
    			
    			List<Point> points = curStroke.getPoints();
    			for(int i = 0; i < points.size(); i++) {
    				if(i == 0) {
    					g2.drawLine(curStroke.getxPos(), curStroke.getyPos(), points.get(i).getxPos(), points.get(i).getyPos());
    				} else if (i == points.size() - 1) {
    					g2.drawLine(points.get(i).getxPos(), points.get(i).getyPos(), curStroke.getxEnd(), curStroke.getyEnd());
    				} else {
    					g2.drawLine(points.get(i-1).getxPos(), points.get(i-1).getyPos(), points.get(i).getxPos(), points.get(i).getyPos());
    				}
    			}
    		} else if (curStroke.getType().equals("Rectangle")) {
    			if(width < 0 && height < 0) {
    				g2.drawRect(curStroke.getxEnd(), curStroke.getyEnd(), Math.abs(width), Math.abs(height));
    			} else if (width < 0 && height >= 0) {
    				g2.drawRect(curStroke.getxEnd(), curStroke.getyPos(), Math.abs(width), Math.abs(height));
    			} else if (width >= 0 && height < 0) {
    				g2.drawRect(curStroke.getxPos(), curStroke.getyEnd(), Math.abs(width), Math.abs(height));
    			} else if (width >= 0 && height >= 0) {
    				g2.drawRect(curStroke.getxPos(), curStroke.getyPos(), Math.abs(width), Math.abs(height));
    			}
			
    		} else if (curStroke.getType().equals("Oval")) {
    			if(width < 0 && height < 0) {
    				g2.drawOval(curStroke.getxEnd(), curStroke.getyEnd(), Math.abs(width), Math.abs(height));
    			} else if (width < 0 && height >= 0) {
    				g2.drawOval(curStroke.getxEnd(), curStroke.getyPos(), Math.abs(width), Math.abs(height));
    			} else if (width >= 0 && height < 0) {
    				g2.drawOval(curStroke.getxPos(), curStroke.getyEnd(), Math.abs(width), Math.abs(height));
    			} else if (width >= 0 && height >= 0) {
    				g2.drawOval(curStroke.getxPos(), curStroke.getyPos(), Math.abs(width), Math.abs(height));
    			}
    		}
    	}
    }
    /**
     * Draws all stored text objects and the current text, checking for wrapping as needed
     * @param g2 The current graphics2D instance
     */
    public void drawText(Graphics2D g2) {
    	List<TextInput> textList = page.getModel().getTextList();
    	String textToDraw;
    	Font font = new Font("SansSerif",Font.PLAIN,14);
		page.setFont(font);
		FontMetrics metric = g2.getFontMetrics(font);
    	for(int i = 0; i < textList.size(); i++) {
    		TextInput text = textList.get(i);
    		textToDraw = text.getText();    
    		g2.setColor(text.getColor());
    		Rectangle2D bounds = metric.getStringBounds(text.getText(), g2);
    		if(checkWrap(text.getxPos(), bounds)) {
    			
    			wrapText(text, metric,g2);   			
    		} else {
    			g2.drawString(textToDraw, text.getxPos(), text.getyPos());
    		}
    	}
    	TextInput curText = page.getModel().getCurText();
    	
    	if(curText != null) {
    		g2.setColor(curText.getColor());
    		textToDraw = curText.getText();    		
    		Rectangle2D bounds = metric.getStringBounds(curText.getText(), g2);
    		if(checkWrap(curText.getxPos(), bounds)) {
    			
    			wrapText(curText, metric,g2);   			
    		} else {
    			g2.drawString(textToDraw, curText.getxPos(), curText.getyPos());
    		}
    		
    		
    	}
    }
    /**
     * Checks if a text's bounds exist beyond the edge of the page
     * @param xPos The xPos of a piece of text
     * @param bounds The rectangle boundary of a piece of text
     */
    public boolean checkWrap(int xPos, Rectangle2D bounds) {
    	if((xPos + bounds.getWidth()) > page.getWidth()) {
    		return true;
    	}    	
    	return false;
    }
    /**
     * Recursively wraps text until all lines do not wrap
     * @param text The text to be wrapped
     * @param metric The FontMetric of the current font
     * @param g2 The current graphics2D instance
     */
    public void wrapText(TextInput text, FontMetrics metric, Graphics2D g2) {
    	int spaceIndex = text.getText().lastIndexOf(' ');
    	int oldSpace = -1;
    	if(spaceIndex > 0) {
    		Rectangle2D bounds = metric.getStringBounds(text.getText().substring(0, spaceIndex), g2);
        	while(spaceIndex !=-1 && oldSpace != spaceIndex && checkWrap(text.getxPos(),bounds)){
        		oldSpace = spaceIndex;
        		spaceIndex = text.getText().substring(0, spaceIndex).lastIndexOf(' ');
        		if(spaceIndex > 0)
        		bounds = metric.getStringBounds(text.getText().substring(0, spaceIndex), g2);
        	}
        	if(spaceIndex > 0) {
        		g2.drawString(text.getText().substring(0, spaceIndex), text.getxPos(), text.getyPos());
        	} else {
        		g2.drawString(text.getText(), text.getxPos(), text.getyPos());
        	}
        	if(text.getText().substring(spaceIndex+1).length() > 0) {
        		int offset = text.getyPos() + metric.getHeight() + 2;
        		wrapText(new TextInput(text.getxPos(), offset, text.getColor(),text.getText().substring(spaceIndex+1)), metric, g2);
        	}
    	} else {
    		g2.drawString(text.getText(),text.getxPos(),text.getyPos());
    	}
    	
    	
    	
    }
    
	@Override
	public void mouseClicked(MouseEvent e) {
		page.getModel().typing(false);
		page.getModel().addText();
		page.getModel().startNewText(e.getX(), e.getY());
		page.getModel().typing(true);
		page.requestFocusInWindow();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			page.getModel().setInitialX(e.getX());
			page.getModel().setInitialY(e.getY());
			page.getModel().typing(false);
			page.getModel().addText();
			page.getModel().setGestureMode(false);
		} else if(e.getButton() == MouseEvent.BUTTON3) {
			page.getModel().setInitialX(e.getX());
			page.getModel().setInitialY(e.getY());
			page.getModel().typing(false);
			page.getModel().addText();
			page.getModel().setGestureMode(true);
		}
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(page.getModel().getGestureMode()) {
			page.getModel().checkGesture(e.getX(),e.getY());
			page.getModel().stillDrawing(false);
			page.getModel().resetStroke();
		} else{
			page.getModel().addStroke(e.getX(),e.getY());
			page.getModel().stillDrawing(false);
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
   
	@Override
	public void mouseDragged(MouseEvent e) {
		
				page.getModel().setCurrentX(e.getX());
				page.getModel().setCurrentY(e.getY());
				page.getModel().checkIfFreeform();
				page.getModel().stillDrawing(true);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		//if(e.getKeyCode())
		//System.out.println(e.getKeyChar());
		page.getModel().addNewChar(e.getKeyChar());
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e.getKeyCode());
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
