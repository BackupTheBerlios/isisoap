/*
 * Created on 8 nov. 2004
 */
package soap.model.executionProcess.structure;

import java.util.Vector;

import soap.model.ModelVisitor;
import soap.model.core.EstimationElement;
import soap.model.executionProcess.structure.user.Member;
import soap.model.process.structure.Activity;

/**
 * @author yanagiba
 */
public class Task extends EstimationElement /*implements IPackage*/
{
    private Vector mListArtifactInput = new Vector() ;
    private Vector mListArtifactOutput = new Vector() ;
    private Activity mActivity ;
    private Iteration mIteration ;
    private Member mMember = new Member() ;
    
    private String mState ;
    
    public Task ()
    {
        super() ;
    }
    
    
    public Task(String name)
    {
        super(name) ;
    }
    
    public Task (Activity a)
    {
        super() ;
        mActivity = a ;
    }
    
    public Activity getActivity()
    {
        return mActivity ;
    }
    
    public void setActivity(Activity a)
    {
        mActivity = a ;
    }
    
    public Iteration getIteration()
    {
        return mIteration ;
    }
    
    public void setIteration(Iteration it)
    {
        mIteration = it  ;
    }
    
    public Member getMember()
    {
        return mMember ;
    }
    
    public void setMember(Member m)
    {
        mMember = m ;
    }
    
    public boolean addInputArtifact (Artifact a)
    {
        if (containsInputArtifact(a))
        {
            mListArtifactInput.add(a) ;
            //a.setParent(this);
            return true ;
        }
        return false ;
    }
    
    public boolean removeInputArtifact (Artifact a)
    {
        if (containsInputArtifact(a))
        {
            //a.setParent(null);
            return true ;
        }
        return false ;
    }
    
    public boolean containsInputArtifact (Artifact a)
    {
        return mListArtifactInput.contains(a) ;
    }
    
    public boolean addOutputArtifact (Artifact a)
    {
        if (containsOutputArtifact(a))
        {
            mListArtifactOutput.add(a);
            //a.setParent(this) ;
            return true ;
        }
        return false ;
    }
    
    public boolean removeOutputArtifact (Artifact a)
    {
        if (containsOutputArtifact(a))
        {
            mListArtifactOutput.remove(a) ;
            //a.setParent(null);
            return true ;
        }
        return false ;
    }
    
    public boolean containsOutputArtifact (Artifact a)
    {
        return mListArtifactOutput.contains(a);
    }
    
    /* (non-Javadoc)
     * @see soap.model.spem.core.Element#visit(soap.model.spem.SpemVisitor)
     */
    public void visit(ModelVisitor visitor)
    {
        // TODO Auto-generated method stub

    }

   /* public boolean addModelElement(ModelElement e)
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
    }*/
}
