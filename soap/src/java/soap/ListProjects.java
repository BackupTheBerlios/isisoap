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
        mlistAllProjects.remove(p);
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
