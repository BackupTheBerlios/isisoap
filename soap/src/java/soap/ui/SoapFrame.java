/*
 * SOAP Supervising, Observing, Analysing Projects
 * Copyright (C) 2003-2004 SOAPteam
 * 
 *
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package soap.ui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

import soap.MainFrameInterface;
import utils.ResourceManager;


public class SoapFrame extends JFrame implements MainFrameInterface
{
	private JTree mTree;
	private String mFilePath;
	private JSplitPane mFrame_split = new JSplitPane();
    
    
    public SoapFrame()
    {
        	super(ResourceManager.getInstance().getString("Title"));
        	mTree = new SoapProjectTree();
        	initUI();
    }
    
    public SoapFrame(JTree tree)
	{
		super(ResourceManager.getInstance().getString("Title"));
		mTree = tree;
		initUI();	
	}
    
    private void initUI()
	{
		addWindowListener(new SoapFrameListener());
		
		mFrame_split.setOneTouchExpandable(true);
		mFrame_split.setDividerLocation(150);		
		mFrame_split.add(new JScrollPane(mTree), JSplitPane.LEFT);
		JTabbedPane welcomePanel = new JTabbedPane() ;
		mFrame_split.add( welcomePanel, JSplitPane.RIGHT);
		
		this.setJMenuBar(new SoapMenuBar());
		getContentPane().add(new SoapToolBar(), BorderLayout.NORTH);
		getContentPane().add(mFrame_split, BorderLayout.CENTER);
		Rectangle r = getGraphicsConfiguration().getBounds();
        Double x = new Double (r.getX()+(r.getWidth()-r.width*5/6)/2) ;
        Double y = new Double (r.getY()+(r.getHeight()- r.height*5/6)/2) ;
		setBounds(x.intValue(), y.intValue(), r.width*5/6, r.height*5/6);
	}
    
    public void setWelcomePanel()
    {
        JPanel welcomePanel = new JPanel() ;
        mFrame_split.add( welcomePanel, JSplitPane.RIGHT);
    }
    
    public void openCentralPanel(JTabbedPane panel)
    {
    	mFrame_split.add(panel, JSplitPane.RIGHT);
    	mFrame_split.setDividerLocation(200);
    }
    public JTree getProjectTree()
    {
        return mTree ;
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
