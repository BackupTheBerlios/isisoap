/*
 * Created on 13 nov. 2004
 */
package soap.ui.tabbedPane;

import javax.swing.JPanel;

/**
 * @author yanagiba
 */
public class DefaultCentralTabbedPane extends SoapCentralTabbedPane
{
    protected JPanel mDataPanel  = new JPanel() ;
	protected JPanel mStatsPanel = new JPanel() ;
		
	public DefaultCentralTabbedPane()
	{
	    addTab(resMan.getString("tabbedPaneData"),mDataPanel);
		addTab(resMan.getString("tabbedPaneStats"),mStatsPanel);
	}
	
}
