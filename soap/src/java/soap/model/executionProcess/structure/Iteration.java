/*
 * Created on 8 nov. 2004
 */
package soap.model.executionProcess.structure;

import java.util.Vector;

import soap.model.ModelVisitor;
import soap.model.core.EstimationElement;
import soap.model.core.ModelElement;
import soap.model.modelmanagement.IPackage;


/**
 * @author yanagiba
 */
public class Iteration extends EstimationElement implements IPackage
{
    private Project mProject ;
    private Vector mList = new Vector() ;
    
    public Iteration()
    {
        super() ;
    }
    
    public Iteration(String name)
    {
        super(name) ;
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
		return (Task) mList.get( i );
    }

    
    public int modelElementCount()
    {
        return mList.size() ;
    }
    
    public class ListTask extends ModelElement implements IPackage
    {
        private Vector mListTask = new Vector() ;
        
        public ListTask(String name)
        {
            super(name);
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
