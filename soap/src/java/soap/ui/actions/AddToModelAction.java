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

package soap.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import soap.Context;
import soap.adapters.SoapTreeAdapter;
import soap.adapters.SoapTreeNode;
import utils.ResourceManager;

public class AddToModelAction extends AbstractAction
{
	private SoapTreeNode mPrototype;
	
	public AddToModelAction(String label, SoapTreeNode e)
	{
		super(ResourceManager.getInstance().getString(label));
		mPrototype = e;
	}

	public void actionPerformed(ActionEvent e)
	{
	    SoapTreeNode selected = (SoapTreeNode) Context.getInstance().getTopLevelFrame().getProjectTree().getSelectionPath().getLastPathComponent();
	    SoapTreeAdapter model = (SoapTreeAdapter) Context.getInstance().getTopLevelFrame().getProjectTree().getModel();
		JTree tree=Context.getInstance().getTopLevelFrame().getProjectTree();
		
		SoapTreeNode node = (SoapTreeNode)mPrototype.clone();
		
		model.insert( new Object[]{node}, new Object[]{selected} );
		
		try
		{
			tree.setSelectionPath(new TreePath(node.getPath()));
			tree.startEditingAtPath(new TreePath(node.getPath()));
		}
		catch(Exception ex)
		{	
			ex.printStackTrace();
		}
	}
}
