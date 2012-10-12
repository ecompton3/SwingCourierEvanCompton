package swingCourier.NotePageComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * The core controller of the notepage component. This is the class that gets instantiated when using this component
 * @author evan
 *
 */
public class NotepageComponent extends JComponent implements ChangeListener {
    NotepageModel model;
    int maxWidth = 0, maxHeight = 0;
    public NotepageComponent() {
    	
        setModel(new BasicNotepageModel());
        updateUI();
        this.setFocusable(true);
        
    }
    public void setModel(NotepageModel m) {
        if (model != null) 
           model.removeChangeListener(this);
        model = m;
        model.addChangeListener(this);
    }
    public NotepageModel getModel() {
        return model;
    }
    public void setUI(BasicNotepageUI ui) { super.setUI(ui); }
    public void updateUI() {
        setUI((BasicNotepageUI) new BasicNotepageUI());
        invalidate();
    }
    public String getUIClassID() { return "BasicNotepageUI"; }
    
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    }
    
	@Override
	public void stateChanged(ChangeEvent e) {
		
		if(this.getWidth() > maxWidth) {
			maxWidth = this.getWidth();
		}
		if(this.getHeight() > maxHeight) {
			maxHeight = this.getHeight();
		}
		this.setMinimumSize(new Dimension(maxWidth,maxHeight));
		this.setPreferredSize(new Dimension(maxWidth,maxHeight));
		this.setMaximumSize(new Dimension(999999999,99999999));
		repaint();
	}
	
	public static void setStrokeType(String text) {
		BasicNotepageModel.setStrokeType(text);
	}
	
	public int getMaxWidth() {
		return maxWidth;
	}
	
	public int getMaxHeight() {
		return maxHeight;
	}
	
	public void resized(int width, int height) {
		if(maxWidth == 0) {
			maxWidth = width;
		}
		if(maxHeight == 0) {
			maxHeight = height;
		}
		stateChanged(new ChangeEvent(this));
	}
	public void setColor(Color newColor) {
		// TODO Auto-generated method stub
		model.setColor(newColor);
	}
	
	
	
}