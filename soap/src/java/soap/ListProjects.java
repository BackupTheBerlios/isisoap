/*
 * Created on 1 nov. 2004
 */
package soap;

import java.util.Vector;


import soap.adapters.SoapTreeAdapter;
import soap.model.executionProcess.structure.Project;
import soap.model.extension.SoapListProjects;
import soap.model.frontend.SoapMediator;
import utils.ConfigManager;

/**
 * @author yanagiba
 */
public class ListProjects
{
    private static final ListProjects mInstance = new ListProjects() ;
    private SoapListProjects mSoapListProjects = new SoapListProjects(ConfigManager.getInstance().getProperty("Project")) ;
    private Vector mlistAllProjects = new Vector();
    
    public ListProjects()
    {
    }
    
    public static ListProjects getInstance()
	{
		return mInstance ;
	}
    /**
     * @return Returns the mSoapListProcess.
     */
    public SoapListProjects getListSoapProjects()
    {
        return mSoapListProjects;
    }
    
    public void addProject(Project p)
    {
        mlistAllProjects.add(p) ;
        SoapMediator.getInstance().addProject(p) ;
    }
    
    public void closeProject(Project p)
    {
        	SoapMediator.getInstance().closeProject(p) ;
    }
    
    public Project getCurrentProject()
    {
        return SoapMediator.getInstance().getCurrentProject(this) ;
    }
	
	public Vector getListAllProjets()
	{
	    return mlistAllProjects ;
	}
	
	public Project getProject(String projectName)
	{
	    if ( existProject(projectName) )
	    {
	        for (int i = 0; i < mlistAllProjects.size(); i++) 
		    {
	            Project project = (Project)mlistAllProjects.elementAt(i) ;
	            if(project.getName().equals(projectName)) 
	            {
	                return project ;
		       	}
		    }
	    }
	    return null ;
	}
	
	
	public boolean existProject(Project p)
	{
	    return mlistAllProjects.contains(p) ;
	}
	
	public boolean existProject(String projectName)
	{	    
	    for (int i = 0; i < mlistAllProjects.size(); i++) 
	    {
	       if(((Project)mlistAllProjects.elementAt(i)).getName().equals(projectName)) 
	       {
	           return true ;
	       }
	    }
	    return false ;
	}
	
	public void setListeners()
	{
	    SoapTreeAdapter treeAdapt = (SoapTreeAdapter)Context.getInstance().getTopLevelFrame().getProjectTree().getModel();
	    SoapMediator.getInstance().addSoapMediatorListener(treeAdapt) ;
	}
	public void clearListeners()
	{
	    SoapTreeAdapter treeAdapt = (SoapTreeAdapter)Context.getInstance().getTopLevelFrame().getProjectTree().getModel();
	    SoapMediator.getInstance().removeSoapMediatorListener(treeAdapt) ;
	}
}
