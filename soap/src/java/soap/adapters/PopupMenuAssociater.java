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
import soap.model.extension.SoapProcess;
import soap.model.process.structure.Activity;
import soap.model.process.structure.ProcessRole;
import soap.model.process.structure.WorkDefinition;
import soap.model.process.structure.WorkProduct;

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
	
/*	protected void visitElement(Element element) 
	{
	   // System.out.println("go");
	    if (element instanceof SoapProcess)
	        visitSoapProcess((SoapProcess)element) ;
	}*/
	
	public void visitProject (Project p)
	{
	    mResult.add(msContext.getAction("CloseNoHotKey")) ;
	}
	
	public void visitSoapProcess(SoapProcess process)
	{
	    mResult.add(msContext.getAction("ProjectProperties"));
	}
	
		
	public void visitProduct(WorkProduct product)
	{
		mResult.add(msContext.getAction("TreeAddWorkProductState"));
		
		super.visitProduct(product);
	}
	
	public void visitRole(ProcessRole role)
	{
		super.visitRole(role);
	}

	public void visitActivity(Activity activity)
	{
		super.visitActivity(activity);
	}
		
	public void visitWorkDefinition(WorkDefinition w)
	{
		if( w instanceof WorkDefinition )
		{
			WorkDefinition aw = (WorkDefinition) w;
			mResult.add(msContext.getAction("TreeAddActivity"));
			
			/*if( aw.canAddActivityDiagram() )
			{
				mResult.add(msContext.getAction("TreeAddActivityDiagram"));
			}
			if( aw.canAddFlowDiagram() )
			{
				mResult.add(msContext.getAction("TreeAddFlowDiagram"));
			}*/
		}
		
		super.visitWorkDefinition(w);
	}
	
	
	
}
