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

import javax.swing.JPopupMenu;

import soap.Context;
import soap.model.DefaultSoapElementVisitor;
import soap.model.executionProcess.structure.Project;

/**
 * This visitor associate an element with an his popupmenu.
 *
 */

public class PopupMenuAssociater extends DefaultSoapElementVisitor
{
	private static final Context msContext = Context.getInstance();
	private JPopupMenu mResult = new JPopupMenu();
	
	/**
	 * Retrieve the icon associated to the element during the visit
	 */
	public JPopupMenu getMenu()
	{
		JPopupMenu result = mResult;
		
		mResult = new JPopupMenu();
		
		return result;
	}
	
	
	public void visitProject (Project p)
	{
	    mResult.add(msContext.getAction("CloseNoHotKey")) ;
	}	
	
}
