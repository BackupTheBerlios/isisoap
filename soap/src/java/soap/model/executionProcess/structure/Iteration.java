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

package soap.model.executionProcess.structure;

import java.util.Vector;

import soap.model.ModelVisitor;
import soap.model.core.EstimationElement;
import soap.model.core.ModelElement;
import soap.model.core.SoapElement;
import soap.model.modelmanagement.IPackage;

public class Iteration extends EstimationElement implements IPackage
{
    private Project mProject ;
    private Vector mList = new Vector() ;
    
    public Iteration(String projectName)
    {
        super(projectName) ;
    }
    
    public Iteration(String projectName, String name)
    {
        super(projectName, name) ;
    }
    
    public void setProject(Project p)
    {
        mProject = p ;
    }
    
    public Project getProject()
    {
        return mProject ;
    }
    
    public boolean addListTask (ListTask listTask)
    {
        if (! mList.contains(listTask))
        {
            mList.add(listTask) ;
            listTask.setParent(this) ;
            return true ;
        }
        return false ;
    }
    
    public boolean removeListTask(ListTask listTask)
    {
        listTask.setParent(null);
        return mList.remove(listTask) ;
    }
    
    public boolean containsListTask(ListTask listTask)
    {
        return mList.contains(listTask) ;
    }
    
    /* (non-Javadoc)
     * @see soap.model.core.Element#visit(soap.model.ModelVisitor)
     */
    public void visit(ModelVisitor visitor)
    {
        visitor.visitIteration(this);
        
    }

    public boolean addModelElement(ModelElement e)
    {
        if (e instanceof ListTask)
        {
            return addListTask((ListTask)e) ;
        }
        return false;
    }

    public boolean removeModelElement(ModelElement e)
    {
        if (e instanceof ListTask)
        {
            return removeListTask((ListTask)e) ;
        }
        return false;
    }

    
    public boolean containsModelElement(ModelElement e)
    {
        return containsListTask((ListTask )e) ;
    }

   
    public ModelElement getModelElement(int i)
    {
        if( i < 0 || i >= mList.size() )
		{
			return null;
		}
		return (ListTask) mList.get( i );
    }

    
    public int modelElementCount()
    {
        return mList.size() ;
    }
    
    public class ListTask extends SoapElement implements IPackage
    {
        private Vector mListTask = new Vector() ;
        
        public ListTask(String projectName)
        {
            super(projectName);
        }
        
        public ListTask(String projectName, String name)
        {
            super(projectName, name);
        }
        
        public boolean addTask (Task t)
        {
            if (! mListTask.contains(t))
            {
                mListTask.add(t) ;
                t.setParent(this) ;
                return true ;
            }
            return false ;
        }
        
        public boolean removeTask(Task t)
        {
            t.setParent(null);
            return mListTask.remove(t) ;
        }
        
        public boolean containsTask(Task t)
        {
            return mListTask.contains(t) ;
        }
        
        public void visit(ModelVisitor visitor)
        {
            visitor.visitListTask(this);
        }

       
        public boolean addModelElement(ModelElement e)
        {
            if (e instanceof Task)
            {
                return addTask((Task)e) ;
            }
            return false;
        }

        public boolean removeModelElement(ModelElement e)
        {
            if (e instanceof Task)
            {
                return removeTask((Task)e);
            }
            return false;
        }

        public boolean containsModelElement(ModelElement e)
        {
            if(e instanceof Task)
            {
                return containsTask((Task)e) ;
            }
            return false;
        }

        public ModelElement getModelElement(int i)
        {
            if( i < 0 || i >= mListTask.size() )
    		{
    			return null;
    		}
    		return (Task) mListTask.get( i );
        }

        public int modelElementCount()
        {
            return mListTask.size();
        }
    }
}
