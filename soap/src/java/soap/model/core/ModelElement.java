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


package soap.model.core;

import java.util.HashMap;
import java.util.Vector;

import soap.model.modelmanagement.IPackage;


public abstract class ModelElement extends Element
{
    private IPackage mParent;
    private Vector mBehavior = new Vector();
    
    private HashMap mAttributes = new HashMap();

	public ModelElement(String name)
	{
		super(name);
	}

	public ModelElement()
	{
		
	}
	
	/**
	 * Get the number of state machines associated to the model element
	 *
	 * @return the number of state machines
	 */
	public int behaviorCount()
	 {
		return mBehavior.size();
	 }

	/**
	 * Get the containing package of the model element
	 *
	 * @return the package containing this model element
	 */
	public IPackage getParent()
	{
		return mParent;
	}
	
	/**
	 * Set the containing package of the model element
	 *
	 * @param parent the package
	 */
	public void setParent(IPackage parent)
	{
		mParent = parent;
	}
	
	/**
	 * Set the name of the element
	 *
	 * @param name the element name
	 */
	public void setName(String name)
	{
		super.setName(name);
	}

	public boolean equals (Object o)
	{
	    return this.getName().equals(((ModelElement)o).getName());
	}
	public Object getAttribute(int attributeType)
	{
	    return mAttributes.get(new Integer(attributeType));
	}
	public void setAttribute(int attributeType, Object attribute)
	{
	    mAttributes.put(new Integer(attributeType), attribute);
	}
	
}
