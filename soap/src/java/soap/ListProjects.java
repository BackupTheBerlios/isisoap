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
package soap;

import java.util.Vector;

import soap.adapters.SoapTreeAdapter;
import soap.model.executionProcess.structure.Project;
import soap.model.extension.SoapListProjects;
import soap.model.frontend.SoapMediator;
import utils.ConfigManager;

/**
 * Class used to contains the list of all the projects
 * It is implemented as a singleton.
 * 
 */

public class ListProjects
{
    private static final ListProjects mInstance = new ListProjects() ;
    private SoapListProjects mSoapListProjects = new SoapListProjects(ConfigManager.getInstance().getProperty("Project")) ;
    private Vector mlistAllProjects = new Vector();
    private Project mCurrentProject = null;
    
    public ListProjects(){};
    
    /**
	 * Allows to retrieve the ListProjects instance
	 * @return the unique instance of ListProjects
	 */
    public static ListProjects getInstance()
	{
		return mInstance ;
	}
    
    /**
     * Allows to retrieve the the working project list
     * @return Returns the the working project lis.
     * 
     */
    public SoapListProjects getListSoapProjects()
    {
        return mSoapListProjects;
    }
    
    /**
	 * Allows to add a  project to the list
	 *
	 */
    public void addProject(Project p)
    {
        mlistAllProjects.add(p) ;
        SoapMediator.getInstance().addProject(p) ;
    }
    
    /**
	 * Allows to remove a project from the list
	 *
	 */
    public void removeProject(Project p)
    {
        mlistAllProjects.remove(p);
        SoapMediator.getInstance().closeProject(p) ;
    }
    
    /**
	 * Allows to get the current project selected
	 * @return returns the current project
	 *
	 */
    public Project getCurrentProject()
    {
        return mCurrentProject ;
    }
	
    /**
	 * Allows to set the current project selected
	 *
	 */
    public void setCurrentProject(Project p)
    {
        mCurrentProject = p;
    }
    
    /**
	 * Allows to get all the existing projects
	 * @return returns the list of all existing project
	 * 
	 */
	public Vector getListAllProjets()
	{
	    return mlistAllProjects ;
	}
	
	 /**
	 * Allows to get a project in the list of the working project
	 * identify by his name
	 *
	 */
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
	
	/**
     * Test if a project exists
     * @return Returns true if the project exists
     *
     */

	public boolean existProject(Project p)
	{
	    return mlistAllProjects.contains(p) ;
	}
	
	/**
     * Test if a project exists
     * @return Returns true if the project exists
     *
     */
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
