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


package soap.model.process.structure;

import java.util.Vector;

import soap.model.ModelVisitor;
import soap.model.core.ModelElement;
/**
 * @version $Revision: 1.1 $
 */
public class WorkProduct extends ModelElement 
{
	public static final int NO_REFERENCES = 0;
	public static final int REFERENCES_BY_PROVIDED_INTERFACE = 1;
	public static final int REFERENCES_BY_REQUIRED_INTERFACE = 2;
	
	private ProcessPerformer mResponsible;
	private boolean mDeliverable;
	private int mReferences = NO_REFERENCES;
	
	private Vector mInput = new Vector();
	private Vector mOutput = new Vector();
	
	public WorkProduct()
	{
	
	}
	
	public WorkProduct(String name)
	{
		super(name);
	}

	public void visit(ModelVisitor visitor)
	{
		visitor.visitProduct(this);
	}
	
	public void setReferences( int references )
	{
		mReferences = references;
	}
	
	public int getReferences()
	{
		return mReferences;
	}
	
	/**
	 * Set the responsible of the work product
	 *
	 * @param r the responsible
	 */
	public void setResponsible(ProcessPerformer r)
	{
		mResponsible = r;
	}
	
	
	
	/**
	 * Get the responsible of the work product
	 *
	 * @return r the responsible
	 */
	public ProcessPerformer getResponsible()
	{
		return mResponsible;
	}
	
	/**
	 * Add a work definition in input of the work product
	 *
	 * @param w the work definitionto associate
	 * @return true if the work definition can be added, false otherwise
	 */
	public boolean addInputWorkDefinition(WorkDefinition w)
	{
		if(!containsInputWorkDefinition(w))
		{
			mInput.add(w);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Remove a work definition in input of this work product 
	 *
	 * @param w the work definition to remove
	 * @return true if the work definition can be removed, false otherwise
	 */
	public boolean removeInputWorkDefinition(WorkDefinition w)
	{
		if(containsInputWorkDefinition(w))
		{
			mInput.remove(w);
			return true;
		}
		return false;
	}
	
	/**
	 * Check if a Work definition is in input of this work product
	 *
	 * @param w the Work Definition to test
	 * @return true if the Work Definition is in input of this work product, false otherwise
	 */
	public boolean containsInputWorkDefinition(WorkDefinition w)
	{
		return mInput.contains(w);
	}

	/**
	 * Add a work definition in output of the work product
	 *
	 * @param w the work definition to associate
	 * @return true if the work definition can be added, false otherwise
	 */
	public boolean addOutputWorkDefinition(WorkDefinition w)
	{
		if(!containsOutputWorkDefinition(w))
		{
			mOutput.add(w);
			return true;
		}
		
		return false;
	}

	/**
	 * Remove a work definition in output of this work product 
	 *
	 * @param w the work definition to remove
	 * @return true if the work definition can be removed, false otherwise
	 */
	public boolean removeOutputWorkDefinition(WorkDefinition w)
	{
		if(containsOutputWorkDefinition(w))
		{
			mOutput.remove(w);
			return true;
		}
		return false;
	}

	/**
	 * Check if a Work definition is in output of this work product
	 *
	 * @param w the Work Definition to test
	 * @return true if the Work Definition is in output of this work product, false otherwise
	 */
	public boolean containsOutputWorkDefinition(WorkDefinition w)
	{
		return mOutput.contains(w);
	}

	
	public int getInputCount()
	{
		return mInput.size();
	}

	public int getOutputCount()
	{		
		return mOutput.size();
	}

	public WorkDefinition getInput(int i)
	{
		return (WorkDefinition)mInput.get(i);
	}

	public WorkDefinition getOutput(int i)	
	{
		return (WorkDefinition)mOutput.get(i);
	}
	
	
	public int modelElementCount()
	{
		return behaviorCount();
	}

};
