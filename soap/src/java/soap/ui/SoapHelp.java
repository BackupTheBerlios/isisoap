/*
 * Created on 17 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package soap.ui;

import java.awt.Dimension;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import soap.Context;
import utils.ConfigManager;
import utils.ResourceManager;

/**
 * @author claire
 *
 * TODO  claire
 * */
public class SoapHelp extends  JFrame  
{
	
	JEditorPane editor = new JEditorPane();

	/**
	 * @param title
	 * @throws java.awt.HeadlessException
	 */
	public SoapHelp()
	{
	    super(ResourceManager.getInstance().getString("helpHelp"));
	    initUI();
	}
	
	protected void initUI()
	{
		try 
		{
			URL helpURL = getClass().getResource("/" + ConfigManager.getInstance().getProperty("HelpFile"));
			if (helpURL!=null)
			{
					editor.addHyperlinkListener(new HyperlinkListener()
					{
						public void hyperlinkUpdate(HyperlinkEvent e) {
							if (e.getEventType()==	HyperlinkEvent.EventType.ACTIVATED){
								try {
									editor.setPage(e.getURL());
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}
					});
					editor.setEditable(false);
					JScrollPane editorScroll = new JScrollPane(editor);
					editorScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					editorScroll.setPreferredSize(new Dimension(250,145));
					editorScroll.setMinimumSize(new Dimension(10,10));
					editorScroll.setVisible(true);					
					
					this.getContentPane().add(editorScroll);					
					editor.setPage(helpURL);
					SoapFrame parent = (SoapFrame)Context.getInstance().getTopLevelFrame();
					this.setSize(new Dimension(700,500));
					this.setLocation(parent.getX()+(parent.getWidth()-this.getWidth())/2,parent.getY()+(parent.getHeight()-this.getHeight())/2);
					this.setVisible(true);
		
			}else{
				System.out.println("URL nulle");}
		
			} catch (MalformedURLException e) {
			e.printStackTrace();
			}
			catch (IOException e2) {
				e2.printStackTrace();
				System.err.println("read a bad URL");
			}
	}
}
