/*
 * Created on 26 oct. 2004
 */
package soap.ui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;



import soap.ui.SoapMenuBar;
//import soap.ui.SoapToolBar;
import soap.ui.SoapProjectTree;

import utils.IconManager;
import utils.ResourceManager;

import soap.MainFrameInterface;

/**
 * @author yanagiba
 */
public class SoapFrame extends JFrame implements MainFrameInterface
{
	private JTree mTree;
	private String mFilePath;
	private JSplitPane mFrame_split = new JSplitPane();
    
    
    public SoapFrame()
    {
        	super(ResourceManager.getInstance().getString("Title"));
        	mTree = new SoapProjectTree();
        	init();
    }
    
    public SoapFrame(JTree tree)
	{
		super(ResourceManager.getInstance().getString("Title"));
		mTree = tree;
		init();	
	}
    
    private void init()
	{
		addWindowListener(new SoapFrameListener());
		
		mFrame_split.setOneTouchExpandable(true);
		mFrame_split.setDividerLocation(150);		
		mFrame_split.add(new JScrollPane(mTree), JSplitPane.LEFT);
		JPanel welcomePanel = new JPanel() ;
		/*{
		    public void paint(Graphics g)
		    {
				 super.paint(g);
				 Image Background = IconManager.getInstance().getImageResource("icons/logo.gif");
				 g.drawImage(Background, 0, 0, this);
				 repaint();
			};
		};*/
		mFrame_split.add( welcomePanel, JSplitPane.RIGHT);
		
		this.setJMenuBar(new SoapMenuBar());
		getContentPane().add(new SoapToolBar(), BorderLayout.NORTH);
		getContentPane().add(mFrame_split, BorderLayout.CENTER);
		Rectangle r = getGraphicsConfiguration().getBounds();
		setBounds(r.x+10, r.y+10, r.width*5/6, r.height*5/6);
	}
    
    public void setWelcomePanel()
    {
        JPanel welcomePanel = new JPanel() ;
        mFrame_split.add( welcomePanel, JSplitPane.RIGHT);
    }
    
    public void openCentralPanel(JTabbedPane panel)
    {
    	mFrame_split.add(new JScrollPane(panel), JSplitPane.RIGHT);
    	mFrame_split.setDividerLocation(200);
    }
    public JTree getProjectTree()
    {
        return mTree ;
    }
    
    public String getFilePath()
	{
		return mFilePath;
	}

	public void setFilePath(String filePath)
	{
		mFilePath = filePath;
		if(filePath == null || filePath.equals(""))
		{
			setTitle(ResourceManager.getInstance().getString("Title"));
		}
		else
		{
			setTitle(ResourceManager.getInstance().getString("Title")
			+" - "+filePath);
		}
	}
	
	private class SoapFrameListener extends WindowAdapter
	{
		/**
		 * Allows to intercept the close event to call the QuitAction
		 */
		public void windowClosing(WindowEvent e)
		{
		    System.exit(0) ;
			//Context.getInstance().getAction("Quit").actionPerformed(null);
		}
	}
    
}
