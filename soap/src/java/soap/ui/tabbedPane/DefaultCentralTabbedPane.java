/*
 * Created on 13 nov. 2004
 */
package soap.ui.tabbedPane;

import javax.swing.JPanel;

import utils.ResourceManager;

/**
 * @author yanagiba
 */
public class DefaultCentralTabbedPane extends SoapCentralTabbedPane
{
    protected JPanel mDataPanel  = new JPanel() ;
	protected JPanel mStatsPanel = new JPanel() ;
		
	public DefaultCentralTabbedPane()
	{
	    addTab(ResourceManager.getInstance().getString("tabbedPaneData"),mDataPanel);
		addTab(ResourceManager.getInstance().getString("tabbedPaneStats"),mStatsPanel);
	}
	
}
