/*
 * Created on 17 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package soap.ui;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import soap.Context;
import utils.ResourceManager;

/**
 * @author claire
 *
 * TODO  claire
 * */
public class SoapHelp extends JFrame {
	
	private static String HELP_INDEX = "/src/help/index_";
	JEditorPane editor = new JEditorPane();

	/**
	 * @param title
	 * @throws java.awt.HeadlessException
	 */
	public SoapHelp(String title) throws HeadlessException {
		
		String htmlFile = HELP_INDEX+Locale.getDefault().getLanguage()+".html";
		try {
			
			//TODO claire path courant
			java.net.URL helpURL = new URL("file:///T:/IUP Master 1/BE/soap"+htmlFile);
			if (helpURL!=null)
			{
					this.setTitle(ResourceManager.getInstance().getString("helpHelp"));
					this.setSize(new Dimension(700,500));
					this.setLocation(300,0);

					editor.addHyperlinkListener(new HyperlinkListener(){
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
					
					this.add(editorScroll);					
					editor.setPage(helpURL);					
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
