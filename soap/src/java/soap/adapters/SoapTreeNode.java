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

package soap.adapters;

import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;

import soap.Identity;

/**
 * class representing a node of a tree
 *
 */


public class SoapTreeNode extends DefaultMutableTreeNode implements Identity, Serializable
{
	
	public SoapTreeNode(Object userObject, boolean isLeaf)
	{
		super(userObject);		
	}


	public SoapTreeNode(Object userObject, SoapTreeNode parent)
	{
		super(userObject);
		setParent(parent);
	}
	

	public String getName()
	{
		return getUserObject().toString();
	}
	
	public String toString()
	{
		return getName();
	}
	
	public String getID()
	{
		if( getUserObject() instanceof Identity )
		{
			return ((Identity)getUserObject()).getID();
		}
		return null;
	}	
}
