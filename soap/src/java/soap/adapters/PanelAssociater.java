/*
 * Created on 31 oct. 2004
 */
package soap.adapters;

import soap.model.DefaultSoapElementVisitor;
import soap.model.executionProcess.structure.Iteration;
import soap.model.executionProcess.structure.Project;
import soap.model.executionProcess.structure.Iteration.ListTask;
import soap.model.extension.SoapListProjects;
import soap.ui.tabbedPane.SoapCentralTabbedPane;
import soap.ui.tabbedPane.SoapIterationTabbedPane;
import soap.ui.tabbedPane.SoapListProjectsTabbedPane;
import soap.ui.tabbedPane.SoapProjectTabbedPane;
import soap.ui.tabbedPane.SoapTasksTabbedPane;



/**
 * @author SCARAVETTI Florent
 */
public class PanelAssociater extends DefaultSoapElementVisitor
{

	SoapCentralTabbedPane mCentralPanel = null;
	
	public PanelAssociater()
	{
	}
	
	/**
	 * Retrieve the central panel associated to the element during the visit
	 */
	public SoapCentralTabbedPane getCentralPanel()
	{
		SoapCentralTabbedPane centralPanel = mCentralPanel;
		
		mCentralPanel = null;
		
		return centralPanel;
	}
	
	public void visitProject (Project p)
	{
		mCentralPanel = new SoapProjectTabbedPane(p);
	}	
	
	
	public void visitSoapListProjects (SoapListProjects listProjects)
	{
	    mCentralPanel = new SoapListProjectsTabbedPane(listProjects);
	}
	
	public void visitIteration(Iteration i)
	{
		mCentralPanel = new SoapIterationTabbedPane(i);
	}
	public void visitListTask(ListTask listTask)
    {
	    mCentralPanel = new SoapTasksTabbedPane(listTask);
    }
}
