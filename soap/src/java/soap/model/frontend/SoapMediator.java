/*
 * APES is a Process Engineering Software
 * Copyright (C) 2003-2004 IPSquad
 * team@ipsquad.tuxfamily.org
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
package soap.model.frontend;

import java.io.Serializable;
import java.util.Map;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import soap.adapters.SoapTreeAdapter;
import soap.adapters.SoapTreeNode;
import soap.model.frontend.SoapMediator;
import soap.model.frontend.event.SoapEvent;

import soap.Context;
import soap.ListProjects;

import soap.model.core.EstimationElement;
import soap.model.core.ModelElement;
import soap.model.executionProcess.structure.Artifact;
import soap.model.executionProcess.structure.Iteration;
import soap.model.executionProcess.structure.Iteration.ListTask;
import soap.model.executionProcess.structure.Project;
import soap.model.executionProcess.structure.Task;
import soap.model.executionProcess.structure.user.Supervisor;
import soap.model.extension.SoapListProjects;

import soap.model.modelmanagement.IPackage;
import utils.ConfigManager;
import utils.ResourceManager;
import utils.SoapDate;

/**
 * 
 * @version $Revision: 1.1 $
 */
public class SoapMediator implements Serializable
{
	private static final SoapMediator mInstance = new SoapMediator();
	
	private ResourceManager mResource = ResourceManager.getInstance();
	private ConfigManager mConfig = ConfigManager.getInstance();
	
	private Vector mListeners = new Vector();
	
	private SoapMediator() { }
	
	/**
	 * Return the unique instance of the ApesMediator
	 * @return the unique instance of the ApesMediator
	 */
	public static SoapMediator getInstance()
	{
		return mInstance;
	}
	
	
	/**
	 * Set a new process
	 * @param ap the process
	 */
	public void addProject(Project p)
	{
		if(p.modelElementCount() > 0)
		{
		}
		else
		{
		    initNewProject(p);
		}
	}
	
	public void closeProject(Project p)
	{
	    SoapTreeNode selected = (SoapTreeNode) Context.getInstance().getTopLevelFrame().getProjectTree().getSelectionPath().getLastPathComponent();
	    
	    if (selected.getUserObject() == p)
	    {
	        SoapTreeAdapter treeAdapt = (SoapTreeAdapter) Context.getInstance().getTopLevelFrame().getProjectTree().getModel();
		    treeAdapt.close(selected) ;
	    }
	    
	}
	
	public Project getCurrentProject(ListProjects list)
    {
        SoapTreeNode node =(SoapTreeNode)Context.getInstance().getTopLevelFrame().getProjectTree().getLastSelectedPathComponent() ;
         TreeNode tabTreeNode [] = node.getPath() ;
         if (tabTreeNode.length >= 2)
         {
             return list.getProject(((SoapTreeNode)tabTreeNode[1]).getName()) ;
         }
        return null ;
    }
	
	
	/**
	 * Create a new process
	 * @param ap
	 */
	private void initNewProject(Project p )
	{
	    SoapListProjects listProjects = Context.getInstance().getListProjects().getListSoapProjects() ;
	    String name = mConfig.getProperty("PackageRole");
	    p.setStartDate(new SoapDate("22/10/2004"));
	   // p.setStartDate(new SoapDate(22,10,2004));
		p.setEndDate(new SoapDate("14/03/2004"));
		p.setSupervisor(new Supervisor("Bernard","Cherbonneau"));
		Iteration it1 = new Iteration("Itération 1") ;
		it1.setStartDate(new SoapDate(22,10,2004));
		it1.setEndDate(new SoapDate(05,11,2004));
		it1.setElapsedHours(40);
		it1.setEstimatedHours(47);
		it1.setRemainedHoursToFinish(0);
		Iteration it2 = new Iteration("Itération 2") ;
		it2.setStartDate(new SoapDate(05,11,2004));
		it2.setEndDate(new SoapDate(19,11,2004));
		it2.setElapsedHours(60);
		it2.setEstimatedHours(55);
		it2.setRemainedHoursToFinish(0);
		ListTask listTask1 = it1.new ListTask("Liste des tâches");
		listTask1.addTask(new Task("Analyser les besoins "));
		listTask1.addTask(new Task("Réaliser le document de vision "));
		listTask1.addTask(new Task("Identifier les cas d'utilisation "));
		listTask1.addTask(new Task("Réaliser le modèle des cas d'utilisation "));
		listTask1.addTask(new Task("Evaluer l'itération "));
		listTask1.addTask(new Task("Définir l'itération suivante "));
		ListTask listTask2 = it2.new ListTask("Liste des tâches");
		listTask2.addTask(new Task("Mettre à jour le document de vision "));
		listTask2.addTask(new Task("Mettre à jour le modèle des cas d'utilisation "));
		listTask2.addTask(new Task("Produire une maquette de l’application"));
		listTask2.addTask(new Task("Analyser l'architecture du logiciel"));
		listTask2.addTask(new Task("Réaliser le document d'architecture"));
		listTask2.addTask(new Task("Evaluer l'itération "));
		listTask2.addTask(new Task("Définir l'itération suivante "));
		
	    insertInModel(new Object[]{p, it1,it2, listTask1,listTask2}, 
	            	  new Object[]{listProjects,p,p,it1,it2}, null);
	}
	
	/**
	 * Load an existing process
	 * @param parent
	 */
	private void loadProject( IPackage parent )
	{
		for( int i = 0; i < parent.modelElementCount(); i++ )
		{	
			insertInModel( new Object[]{parent.getModelElement(i)} ,new Object[]{parent},null );
		}
	}
	
	/**
	 * Adds a listener for the Mediator event posted after the model changes
	 * 
	 * @param l the listener to add
	 * @return true if the listener can be added, false otherwise
	 */
	public boolean addSoapMediatorListener( Listener l )
	{
		if( !mListeners.contains( l ) )
		{
			mListeners.add( l );
			return true;
		}
		return false;
	}
	
	/**
	 * Removes a listener previously added with addApesMediatorListener
	 * 
	 * @param l the listener to add
	 * @return true if the listener can be added, false otherwise
	 */
	public boolean removeSoapMediatorListener( Listener l )
	{
		return mListeners.remove( l );
	}

	/**
	 * Clear all current ApesMediator listeners
	 */
	public void clearListeners()
	{
		mListeners.clear();
	}
	
	/**
	 * Clear all listeners and all registered diagrams
	 */
	public void clearAll()
	{
		mListeners.clear();
	}
	

	/**
	 * Notify all listeners that have registered interest for
	 * notification on this event type.  
	 * 
	 * @param e the event
	 */
	protected void fireModelChanged( SoapEvent e )
	{
		for( int i = 0; i < mListeners.size(); i++ )
		{
			((Listener) mListeners.get( i )).modelChanged( e );
		}
	}
	
	public void insertInModel (Object[] elements, Object[] parents,Map extras)
	{
	    if( parents == null || elements == null || elements.length != parents.length)
			throw new IllegalArgumentException("ApesMediator.insert : elements and parents length was different!");
	    SoapEvent event = createInsertEvent(elements, parents, extras) ;
	    if (event != null)
	    {
	        fireModelChanged( event );
	    }
	}
	
	public SoapEvent createInsertEvent(Object[] elements,	Object[] parents,Map extras)
	{
	    SoapEvent e = null ;	  
	    for (int i = 0; i < parents.length; i++)
	    {
	        /*{
	            return null ;
		    }
	        if ( ((IPackage)parents[i]).containsModelElement((ModelElement)elements[i]) )
	        {
	            if(Debug.enabled) Debug.print("duplicate Element "+ ((ModelElement)elements[i]).getName());
	            return null ;
	        }*/
	        if (! verifyInsertElementIntoPackage((ModelElement)elements[i], (IPackage)parents[i]))
	        {
	            	//elements.
	        } 
	   }
	   e = new SoapEvent(elements, null,parents ,extras) ;
	   return e ;
	}

	public boolean verifyInsertElementIntoPackage (ModelElement element, IPackage parent)
	{    
	    if (!(element instanceof IPackage))
	    {
	        if (parent instanceof SoapListProjects && ! (element instanceof Project) ||
	                parent instanceof Project && ! (element instanceof Iteration) ||
	                	parent instanceof Iteration && ! (element instanceof Task) ||
	                		parent instanceof Task && ! (element instanceof Artifact))
	        {
	            return false ;
	        }
	    }
	    
	    parent.addModelElement(element) ;
	    return true ;
	}

	
	/**
	 * Removes elements from the model
	 *  
	 * @param elements the elements to removed
     * @param extras a stored map wich will be returned by the ApesModelEvent
	 */
	public void removeFromModel(Object[] elements, Map extras)
	{
		//if(Debug.enabled) Debug.print( "\n(ApesMediator) -> removeFromModel ");
	    SoapEvent event = createRemovedEvent(elements, extras) ;
	    if (event != null)
	    {
	        fireModelChanged( event );
	    }
	}
	
	public SoapEvent createRemovedEvent (Object[] elements,Map extras)
	{
	    SoapEvent event = null ;
	    /*for (int i = 0; i < elements.length ; i++)
	    {
	        
	    }*/
	    return event ;
	}
	/**
	 * Defines the interface for an object that listens to changes in a ApesMediator
	 */
	public static interface Listener
	{
		public void modelChanged(SoapEvent e );
	}
}
