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

import java.io.Serializable;

import soap.Identity;
import soap.model.ModelVisitor;

/**
 * Base class for all the elements : 
 * - process
 * - execution process
 * 
 * Specify an abstract method to allow visitors
 * See the visitor design pattern for more informations
 */
public abstract class Element implements Serializable, Identity
{
    private static int msNoNameCounter = 0;
	protected static int mNoID = 0;

	private String mName;
	private String mID;
	
	/**
	 * Create a new element
	 *
	 * @param name the new element name
	 */
	public Element(String name)
	{
		mName = name;
		mID=String.valueOf(mNoID++);
	}


	/**
	 * Create a new element with a default name
	 */
	public Element()
	{
		this("noname"+msNoNameCounter++);
		mID=String.valueOf(mNoID++);
	}

	public void resetName()
	{
		setName("noname"+msNoNameCounter++);
	}

	/**
	 * Allow the elements to be visited
	 *
	 * @param visitor the visitor which need an access
	 */
	public abstract void visit(ModelVisitor visitor);

	
	/**
	 * Get the name of the element
	 *
	 * @return the element name
	 */
	public String getName()
	{
		return mName;
	}


	/**
	 * Set the name of the element
	 *
	 * @param name the element name
	 */
	public void setName(String name)
	{
		mName = name;
	}

	/**
	 * Get the unique identifier of this element
	 *
	 * @return the ID of the element
	 */
	public String getID()
	{
		return mID;
	}

	protected void setID(String id)
	{
		mID=id;
	}

	/**
	 * Gives a string representation of the element
	 *
	 * @return string representation
	 */
	public String toString()
	{
		return mName;
	}

};
