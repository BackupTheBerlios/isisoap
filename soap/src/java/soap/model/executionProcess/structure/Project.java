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
import soap.model.executionProcess.structure.user.Member;
import soap.model.executionProcess.structure.user.Supervisor;
import soap.model.extension.SoapProcess;
import soap.model.modelmanagement.IPackage;

public class Project extends EstimationElement implements IPackage
{
    public static int ESTIMATED_FUNCTIONALITIES = 5; 
    public static int REALIZED_FUNCTIONALITIES = 6;
    private Vector mListIteration = new Vector();
    private Supervisor mSupervisor = new Supervisor() ;
    private SoapProcess mProcess = new SoapProcess() ;
    private Vector mListMember = new Vector () ;
    
    public Project(String name)
    {
        super(name, name) ;
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
        addMember(s);
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
		return (Iteration) mListIteration.get( i );
    }

    public int modelElementCount()
    {
        return mListIteration.size();
    }
    
    public boolean addMember (Member m)
    {
        if (! mListMember.contains(m))
        {
            mListMember.add(m) ;
            return true ;
        }
        return false ;
    }
    
    public boolean removeMember (Member m)
    {
        return mListMember.remove(m) ;
    }
    
    public boolean containsMember(Member m)
    {
        return mListMember.contains(m) ;
    }
    
    public Member getMember(int i)
    {
        return (Member)mListMember.get(i);
    }
    
    public int memberCount()
    {
        return mListMember.size();
    }

}
