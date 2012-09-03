package swingCourier.Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Stack;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

public class BrowserPanel extends JPanel{
	 private JEditorPane ed;
	 private JTextField txtURL;
	 private Stack<String> backURLs;
	 private Stack<String> fwdURLs;
	 private JButton btnBack, btnFwd;
	public BrowserPanel() {
		init();
		
	}
	
	private void init() {
		
		this.setMinimumSize(new Dimension(350,400));
		        String initialURL = "http://www.java.com/";
		       backURLs = new Stack<String>();
		       fwdURLs = new Stack<String>();
		        JLabel lblURL = new JLabel("URL");
		        txtURL = new JTextField(initialURL, 20);
		        JButton btnBrowse = new JButton("Browse");
		        btnBack = new JButton("");
		        btnBack.setToolTipText("Back");
		        btnBack.setIcon(new ImageIcon("images/arrowLeft.png"));
		        btnBack.setEnabled(false);
		        btnFwd = new JButton("");
		        btnFwd.setIcon(new ImageIcon("images/arrowRight.png"));
		        btnFwd.setToolTipText("Forward");
		        btnFwd.setEnabled(false);

		        JToolBar bar = new JToolBar();
		        bar.setLayout(new BoxLayout(bar, BoxLayout.LINE_AXIS));
		        bar.add(Box.createRigidArea(new Dimension(15,0)));
		        bar.add(btnBack);
		        bar.add(Box.createRigidArea(new Dimension(15,0)));
		        bar.add(btnFwd);
		        bar.add(Box.createRigidArea(new Dimension(15,0)));
		        bar.add(lblURL);
		        bar.add(Box.createRigidArea(new Dimension(15,0)));
		        bar.add(txtURL);
		        bar.add(Box.createRigidArea(new Dimension(15,0)));
		        bar.add(btnBrowse);
		        bar.add(Box.createRigidArea(new Dimension(15,0)));

		        try {
		            ed = new JEditorPane(initialURL);
		            ed.setEditable(false);

		            ed.addHyperlinkListener(new HyperlinkListener() {

		                public void hyperlinkUpdate(HyperlinkEvent e) {
		                    linkListener(e);
		                }
		            });

		            btnBrowse.addActionListener(
		                    new ActionListener() {

		                        public void actionPerformed(ActionEvent e) {
		                            addressListener();
		                        }
		                    });
		            btnBack.addActionListener(
		                    new ActionListener() {

		                        public void actionPerformed(ActionEvent e) {
		                        	backListener();
		                        }
		                    });
		            btnFwd.addActionListener(
		                    new ActionListener() {

		                        public void actionPerformed(ActionEvent e) {
		                        	fwdListener();
		                        }
		                    });
		            
		            txtURL.addKeyListener(new KeyAdapter() {
		            	public void keyReleased(KeyEvent e) {
		            		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		                    {
		                      addressListener();
		                    }
		                }
		            });
		            JScrollPane sp = new JScrollPane(ed);
		            sp.setPreferredSize(new Dimension(350, 400));
		            this.setLayout(new BorderLayout());
		            
		            this.add(bar, BorderLayout.PAGE_START);
		            this.add(sp, BorderLayout.CENTER);

		            
		        } catch (IOException ex) {
		        	invalidURL();
		        }
		    }
	
	private void linkListener(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            JEditorPane pane = (JEditorPane) e.getSource();
            if (e instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
                HTMLDocument doc = (HTMLDocument) pane.getDocument();
                doc.processHTMLFrameHyperlinkEvent(evt);
                backURLs.push(txtURL.getText());
                btnBack.setEnabled(true);
                fwdURLs.empty();
                btnFwd.setEnabled(false);
                txtURL.setText(evt.getTarget());
               
            } else {
                try {
                    pane.setPage(e.getURL());
                    backURLs.push(txtURL.getText());
                    fwdURLs.empty();
                    btnFwd.setEnabled(false);
                    btnBack.setEnabled(true);
                    txtURL.setText("http://"+ e.getURL().getHost() + e.getURL().getFile());
                } catch (Throwable t) {
                	invalidURL();
                }
            }
        }
	}
	
	private void addressListener() {
		try {
			 backURLs.push(txtURL.getText());
			 fwdURLs.empty();
			 btnFwd.setEnabled(false);
            ed.setPage(txtURL.getText().trim());
            btnBack.setEnabled(true);
        } catch (IOException ex) {
        	invalidURL();
        }
	}
	
	private void backListener() {
		try {
    		btnFwd.setEnabled(true);
    		fwdURLs.push(txtURL.getText());
    		String url = backURLs.pop();
            ed.setPage(new URL(url.trim()));
    		txtURL.setText(url);
            if(backURLs.size() < 1) {
            	btnBack.setEnabled(false);
            }
            
        } catch (IOException ex) {
        	invalidURL();
        }
	}
	
	private void fwdListener() {
		try {
    		btnBack.setEnabled(true);
    		backURLs.push(txtURL.getText());
    		String url = fwdURLs.pop();
            ed.setPage(new URL(url));
    		txtURL.setText(url);
            if(fwdURLs.size() < 1) {
            	btnFwd.setEnabled(false);
            }
            
        } catch (IOException ex) {
        	invalidURL();
        }
	}
	
	private void invalidURL() {
		backURLs.pop();
		JFrame frame = (JFrame) SwingUtilities.getRoot(this);
		JOptionPane.showMessageDialog(frame,
    		    "Your URL is not valid",
    		    "Invalid URL",
    		    JOptionPane.ERROR_MESSAGE);
	}
	
		

}
