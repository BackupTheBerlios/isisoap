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

import java.util.ArrayList;
import java.util.Vector;

import soap.model.ModelVisitor;
import soap.model.core.SoapElement;
import soap.model.executionProcess.structure.user.Member;
import soap.model.process.structure.WorkProduct;
import soap.parser.ID;

public class Artifact extends SoapElement
{
    private WorkProduct mWorkProduct ;
    private Vector mListTask = new Vector();
    private ArrayList mMembers = new ArrayList();
    //String mVersion  = "";
    
    public Artifact(String projectName)
    {
        super(projectName);
    }
    
    public Artifact(String projectName, String name)
    {
        super(projectName, name) ;
    }
    
	public Artifact(ID id)
	{
		super(id.toString());
		this.setID(id.toString());
	}
    
    public WorkProduct getWorkProduct()
    {
        return mWorkProduct ;
    }
    
    public void setWorkProduct (WorkProduct w)
    {
        mWorkProduct = w ;
    }
    
    public boolean addTask(Task t)
    {
        if(!containsTask(t))
        {
            mListTask.add(t) ;
            return true;
        }
        return false;
    }
    
    public boolean removeTask (Task t)
    {
        if(containsTask(t))
        {
            mListTask.remove(t);
            return true;
        }
        return false;
    }
    
    public boolean containsTask (Task t)
    {
        return mListTask.contains(t);
    }
    
    /* (non-Javadoc)
     * @see soap.model.spem.core.Element#visit(soap.model.spem.SpemVisitor)
     */
    public void visit(ModelVisitor visitor)
    {
        // TODO Auto-generated method stub

    }

	/**
	 * @return
	 */
	public ArrayList getMembers() {
		return mMembers;
	}

	/**
	 * @param list
	 */
	public void addMember(Member member) {
		mMembers.add(member);
	}

}
