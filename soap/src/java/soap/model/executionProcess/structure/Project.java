/*
 * Created on 8 nov. 2004
 */
package soap.model.executionProcess.structure;

import java.util.Vector;

import soap.model.ModelVisitor;
import soap.model.core.EstimationElement;
import soap.model.core.ModelElement;
import soap.model.executionProcess.structure.user.Supervisor;
import soap.model.extension.SoapProcess;
import soap.model.modelmanagement.IPackage;

/**
 * @author yanagiba
 */
public class Project extends EstimationElement implements IPackage
{
    private Vector mListIteration = new Vector();
    private Supervisor mSupervisor = new Supervisor() ;
    private SoapProcess mProcess = new SoapProcess() ;;

    public Project()
    {
        super() ;
    }
    
    public Project(String name)
    {
        super(name) ;
    }
    
    public SoapProcess getProcess()
    {
        return mProcess ;
    }
    
    public void setProcess(SoapProcess s)
    {
        mProcess = s ;
    }
    
    public void setSupervisor(Supervisor s)
	{
        mSupervisor  = s ;
	}
    
    
    public Supervisor getSupervisor()
	{
	    return mSupervisor ;
	}
    
    public boolean addIteration (Iteration it)
    {
        if (! mListIteration.contains(it))
        {
            mListIteration.add(it) ;
            it.setParent(this);
            return true ;
        }
        return false ;
    }
    
    public boolean removeIteration (Iteration it)
    {
        it.setParent(null);
        return mListIteration.remove(it) ;
    }
    
    public boolean containsIteration(Iteration t)
    {
        return mListIteration.contains(t) ;
    }
    
    
    public void visit(ModelVisitor visitor)
    {
        visitor.visitProject(this);
    }

    public boolean addModelElement(ModelElement e)
    {
        if (e instanceof Iteration)
        {
            return addIteration((Iteration)e) ;
        }
        return false;
    }

    public boolean removeModelElement(ModelElement e)
    {
        if (e instanceof Iteration)
        {
            return removeIteration((Iteration)e);
        }
        return false;
    }

    public boolean containsModelElement(ModelElement e)
    {
        if(e instanceof Iteration)
        {
            return containsIteration((Iteration)e) ;
        }
        return false;
    }


    public ModelElement getModelElement(int i)
    {
        if( i < 0 || i >= mListIteration.size() )
		{
			return null;
		}
		return (Task) mListIteration.get( i );
    }

    public int modelElementCount()
    {
        return mListIteration.size();
    }

}
