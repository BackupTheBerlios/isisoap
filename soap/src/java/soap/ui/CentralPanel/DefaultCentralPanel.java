/*
 * Created on 13 nov. 2004
 */
package soap.ui.CentralPanel;

import javax.swing.JPanel;

/**
 *
 */
public class DefaultCentralPanel extends SoapCentralPanel
{
    protected JPanel mDataPanel  = new JPanel() ;
	protected JPanel mStatsPanel = new JPanel() ;
		
	public DefaultCentralPanel()
	{
	    addTab(resMan.getString("tabbedPaneData"),mDataPanel);
		addTab(resMan.getString("tabbedPaneStats"),mStatsPanel);
	}
	
}
