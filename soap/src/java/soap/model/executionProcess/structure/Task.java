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
import soap.model.executionProcess.structure.user.Member;
import soap.model.process.structure.Activity;
import soap.parser.ID;

public class Task extends EstimationElement /*implements IPackage*/
{
    private Vector mListArtifactInput = new Vector() ;
    private Vector mListArtifactOutput = new Vector() ;
    private Activity mActivity ;
    private Iteration mIteration ;
    private int mPriority;
    private Member mMember = new Member() ;
    
    private String mState ;
    
    public Task (String projectName)
    {
        super(projectName) ;
    }
    
    
    public Task(String projectName, String name)
    {
        super(projectName, name) ;
    }
    
    public Task (String projectName, Activity a)
    {
        super(projectName) ;
        mActivity = a ;
    }
    
	public Task (ID id)
	{
		super(id.toString());
		this.setID(id.toString());
	}    
    
    public int getPriority()
    {
        return mPriority;
    }
    
    public void setPriority(int priority)
    {
        mPriority = priority;
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
        if (!containsInputArtifact(a))
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
            mListArtifactInput.remove(a) ;
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
        if (!containsOutputArtifact(a))
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
    
    public boolean equals(Object task)
    {
        return getID() == ((Task)task).getID();
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
