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

package soap.model.frontend;

import java.io.Serializable;
import java.util.Map;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import soap.Context;
import soap.ListProjects;
import soap.adapters.SoapTreeAdapter;
import soap.adapters.SoapTreeNode;
import soap.model.core.ModelElement;
import soap.model.executionProcess.structure.Artifact;
import soap.model.executionProcess.structure.Iteration;
import soap.model.executionProcess.structure.Project;
import soap.model.executionProcess.structure.Task;
import soap.model.executionProcess.structure.Iteration.ListTask;
import soap.model.extension.SoapListProjects;
import soap.model.frontend.event.SoapEvent;
import soap.model.modelmanagement.IPackage;
import utils.ConfigManager;
import utils.ResourceManager;

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
	    initNewProject(p);
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
	    /*String name = mConfig.getProperty("PackageRole");
	    p.setAttribute(EstimationElement.START_DATE,new SoapDate("22/10/2004"));
	   // p.setStartDate(new SoapDate(22,10,2004));
	    p.setAttribute(EstimationElement.END_DATE,new SoapDate("14/03/2004"));
		p.setSupervisor(new Supervisor("Bernard","Cherbonneau"));
		Iteration it1 = new Iteration(p.getName(),"Itération 1") ;
		it1.setAttribute(EstimationElement.START_DATE,new SoapDate(22,10,2004));
		it1.setAttribute(EstimationElement.END_DATE,new SoapDate(05,11,2004));
		it1.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(40));
		it1.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(47));
		it1.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		Iteration it2 = new Iteration(p.getName(),"Itération 2") ;
		it2.setAttribute(EstimationElement.START_DATE,new SoapDate(05,11,2004));
		it2.setAttribute(EstimationElement.END_DATE, new SoapDate(19,11,2004));
		it2.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(60));
		it2.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(55));
		it2.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		Iteration it3 = new Iteration(p.getName(),"Itération 3") ;
		it3.setAttribute(EstimationElement.START_DATE,new SoapDate(19,11,2004));
		it3.setAttribute(EstimationElement.END_DATE, new SoapDate(10,12,2004));
		it3.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(55));
		it3.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(50));
		it3.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		ListTask listTask1 = it1.new ListTask(p.getName(),"Liste des tâches");
		Member m1 = new Member("SCARAVETTI","Florent");
		Member m2 = new Member("NGO THANH","Trung");
		Member m3 = new Member("CZERNY","Jean");
		Member m4 = new Member("FAUROUX","Claire");
		Task task1 = new Task(p.getName(),"Analyser les besoins ");
		task1.setAttribute(EstimationElement.START_DATE,new SoapDate(22,10,2004));
		task1.setAttribute(EstimationElement.END_DATE, new SoapDate(24,10,2004));
		task1.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(6));
		task1.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(5));
		task1.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task1.setPriority(1);
		task1.setMember(m3);
		listTask1.addTask(task1);
		Task task2 = new Task(p.getName(),"Réaliser le document de vision ");
		task2.setAttribute(EstimationElement.START_DATE,new SoapDate(24,10,2004));
		task2.setAttribute(EstimationElement.END_DATE, new SoapDate(25,10,2004));
		task2.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(2));
		task2.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(4));
		task2.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task2.setPriority(1);
		task2.setMember(m3);
		listTask1.addTask(task2);
		Task task3 = new Task(p.getName(),"Identifier les cas d'utilisation ");
		task3.setAttribute(EstimationElement.START_DATE,new SoapDate(26,10,2004));
		task3.setAttribute(EstimationElement.END_DATE, new SoapDate(29,10,2004));
		task3.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(7));
		task3.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(5));
		task3.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task3.setPriority(1);
		task3.setMember(m1);
		listTask1.addTask(task3);
		Task task4 = new Task(p.getName(),"Réaliser le modèle des cas d'utilisation ");
		task4.setAttribute(EstimationElement.START_DATE,new SoapDate(30,10,2004));
		task4.setAttribute(EstimationElement.END_DATE, new SoapDate(2,11,2004));
		task4.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(5));
		task4.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(4));
		task4.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task4.setPriority(2);
		task4.setMember(m2);
		listTask1.addTask(task4);
		Task task5 = new Task(p.getName(),"Evaluer l'itération ");
		task5.setAttribute(EstimationElement.START_DATE,new SoapDate(3,11,2004));
		task5.setAttribute(EstimationElement.END_DATE, new SoapDate(4,11,2004));
		task5.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(2));
		task5.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(3));
		task5.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task5.setPriority(2);
		task5.setMember(m4);
		listTask1.addTask(task5);
		Task task6 = new Task(p.getName(),"Définir l'itération suivante ");
		task6.setAttribute(EstimationElement.START_DATE,new SoapDate(4,11,2004));
		task6.setAttribute(EstimationElement.END_DATE, new SoapDate(5,11,2004));
		task6.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(2));
		task6.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(3));
		task6.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task6.setPriority(2);
		task6.setMember(m3);
		listTask1.addTask(task6);
		ListTask listTask2 = it2.new ListTask(p.getName(),"Liste des tâches");
		Task task7 = new Task(p.getName(),"Mettre à jour le document de vision ");
		task7.setAttribute(EstimationElement.START_DATE,new SoapDate(5,11,2004));
		task7.setAttribute(EstimationElement.END_DATE, new SoapDate(6,11,2004));
		task7.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(1));
		task7.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(3));
		task7.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task7.setPriority(2);
		task7.setMember(m3);
		listTask2.addTask(task7);
		Task task8 = new Task(p.getName(),"Mettre à jour le modèle des cas d'utilisation ");
		task8.setAttribute(EstimationElement.START_DATE,new SoapDate(5,11,2004));
		task8.setAttribute(EstimationElement.END_DATE, new SoapDate(7,11,2004));
		task8.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(7));
		task8.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(5));
		task8.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task8.setPriority(2);
		task8.setMember(m1);
		listTask2.addTask(task8);
		Task task9 = new Task(p.getName(),"Analyser l'architecture du logiciel");
		task9.setAttribute(EstimationElement.START_DATE,new SoapDate(5,11,2004));
		task9.setAttribute(EstimationElement.END_DATE, new SoapDate(9,11,2004));
		task9.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(11));
		task9.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(13));
		task9.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task9.setPriority(1);
		task9.setMember(m2);
		listTask2.addTask(task9);
		Task task10 = new Task(p.getName(),"Réaliser le document d'architecture");
		task10.setAttribute(EstimationElement.START_DATE,new SoapDate(10,11,2004));
		task10.setAttribute(EstimationElement.END_DATE, new SoapDate(11,11,2004));
		task10.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(4));
		task10.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(5));
		task10.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task10.setMember(m2);
		task10.setPriority(1);
		listTask2.addTask(task10);
		Task task11 = new Task(p.getName(),"Produire une maquette de l’application");
		task11.setAttribute(EstimationElement.START_DATE,new SoapDate(11,11,2004));
		task11.setAttribute(EstimationElement.END_DATE, new SoapDate(16,11,2004));
		task11.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(20));
		task11.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(15));
		task11.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task11.setMember(m1);
		task11.setPriority(1);
		listTask2.addTask(task11);
		Task task12 = new Task(p.getName(),"Evaluer l'itération ");
		task12.setAttribute(EstimationElement.START_DATE,new SoapDate(16,11,2004));
		task12.setAttribute(EstimationElement.END_DATE, new SoapDate(17,11,2004));
		task12.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(3));
		task12.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(2));
		task12.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task12.setMember(m3);
		task12.setPriority(2);
		listTask2.addTask(task12);
		Task task13 = new Task(p.getName(),"Définir l'itération suivante ");
		task13.setAttribute(EstimationElement.START_DATE,new SoapDate(17,11,2004));
		task13.setAttribute(EstimationElement.END_DATE, new SoapDate(19,11,2004));
		task13.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(2));
		task13.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(3));
		task13.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task13.setMember(m4);
		task13.setPriority(2);
		listTask2.addTask(task13);
		
		ListTask listTask3 = it3.new ListTask(p.getName(),"Liste des tâches");
		Task task14 = new Task(p.getName(),"Mettre à jour le modèle des cas d'utilisation");
		task14.setAttribute(EstimationElement.START_DATE,new SoapDate(20,11,2004));
		task14.setAttribute(EstimationElement.END_DATE, new SoapDate(21,11,2004));
		task14.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(3));
		task14.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(2));
		task14.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task14.setPriority(2);
		task14.setMember(m4);
		listTask3.addTask(task14);
		Task task15 = new Task(p.getName(),"Réaliser le référentiel des exigences");
		task15.setAttribute(EstimationElement.START_DATE,new SoapDate(4,12,2004));
		task15.setAttribute(EstimationElement.END_DATE, new SoapDate(5,12,2004));
		task15.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(1));
		task15.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(1));
		task15.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task15.setPriority(2);
		task15.setMember(m1);
		listTask3.addTask(task15);
		Task task16 = new Task(p.getName(),"Mettre à jour le document l'architecture du logiciel");
		task16.setAttribute(EstimationElement.START_DATE,new SoapDate(2,12,2004));
		task16.setAttribute(EstimationElement.END_DATE, new SoapDate(4,12,2004));
		task16.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(4));
		task16.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(3));
		task16.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task16.setPriority(2);
		task16.setMember(m2);
		listTask3.addTask(task16);
		Task task17 = new Task(p.getName(),"Coder le prototype");
		task17.setAttribute(EstimationElement.START_DATE,new SoapDate(20,11,2004));
		task17.setAttribute(EstimationElement.END_DATE, new SoapDate(04,12,2004));
		task17.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(16));
		task17.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(15));
		task17.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task17.setMember(m1);
		task17.setPriority(1);
		listTask3.addTask(task17);
		Task task18 = new Task(p.getName(),"Coder le prototype");
		task18.setAttribute(EstimationElement.START_DATE,new SoapDate(20,11,2004));
		task18.setAttribute(EstimationElement.END_DATE, new SoapDate(04,12,2004));
		task18.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(17));
		task18.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(16));
		task18.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task18.setMember(m2);
		task18.setPriority(1);
		listTask3.addTask(task18);
		Task task19 = new Task(p.getName(),"Analyser les indicateurs");
		task19.setAttribute(EstimationElement.START_DATE,new SoapDate(21,11,2004));
		task19.setAttribute(EstimationElement.END_DATE, new SoapDate(24,11,2004));
		task19.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(5));
		task19.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(6));
		task19.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task19.setMember(m3);
		task19.setPriority(1);
		listTask3.addTask(task19);
		Task task20 = new Task(p.getName(),"Evaluer l'itération ");
		task20.setAttribute(EstimationElement.START_DATE,new SoapDate(05,12,2004));
		task20.setAttribute(EstimationElement.END_DATE, new SoapDate(06,12,2004));
		task20.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(3));
		task20.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(2));
		task20.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task20.setMember(m4);
		task20.setPriority(2);
		listTask3.addTask(task20);
		Task task21 = new Task(p.getName(),"Définir l'itération suivante ");
		task21.setAttribute(EstimationElement.START_DATE,new SoapDate(05,12,2004));
		task21.setAttribute(EstimationElement.END_DATE, new SoapDate(06,12,2004));
		task21.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(2));
		task21.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(3));
		task21.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task21.setMember(m4);
		task21.setPriority(2);
		listTask3.addTask(task21);
		
		
	    insertInModel(new Object[]{p, it1,it2,it3, listTask1,listTask2,listTask3}, 
	            	  new Object[]{listProjects,p,p,p,it1,it2,it3}, null);*/
	    
	    insertInModel(new Object[]{p}, 
	          	  new Object[]{listProjects}, null);
	    
	    loadProject(p);
	    
	}
	
	/**
	 * Load an existing project
	 * @param parent
	 */
	private void loadProject( IPackage parent )
	{
		for( int i = 0; i < parent.modelElementCount(); i++ )
		{	
		    insertInModel( new Object[]{parent.getModelElement(i)} ,new Object[]{parent},null );
		    if( parent.getModelElement(i) instanceof IPackage && !(parent.getModelElement(i) instanceof ListTask))
			{
				loadProject((IPackage)parent.getModelElement(i));
			}
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
	    System.out.println("verifyInsertElementIntoPackage : "+element+" "+ parent);
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
