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

import javax.swing.Icon;

import soap.model.DefaultModelVisitor;
import soap.model.core.Element;
import soap.model.process.structure.Activity;
import soap.model.process.structure.ProcessRole;
import soap.model.process.structure.WorkDefinition;
import soap.model.process.structure.WorkProduct;
import utils.IconManager;

public class IconAssociater extends DefaultModelVisitor
{
	Icon mResult = null;
	IconManager mManager;
	
	/**
	 * @param manager the IconManager
	 */
	public IconAssociater(IconManager manager)
	{
		mManager = manager;
	}
	
	/**
	 * Retrieve the icon associated to the element during the visit
	 */
	public Icon getIcon()
	{
		Icon result = mResult;
		
		mResult = null;
		
		return result;
	}
	
	protected void visitElement(Element element)
	{
		mResult = mManager.getIcon("icons/TreeUnknown.gif");
	}
	
	
	public void visitWorkDefinition( WorkDefinition w )
	{
		mResult = mManager.getIcon("icons/TreeWorkDefinition.gif");
	}
	
	public void visitProduct(WorkProduct product)
	{
		mResult = mManager.getIcon("icons/TreeWorkProduct.gif");
	}

	public void visitRole(ProcessRole role)
	{
		mResult = mManager.getIcon("icons/TreeRole.gif");
	}

	public void visitActivity(Activity activity)
	{
		mResult = mManager.getIcon("icons/TreeActivity.gif");
	}
}
