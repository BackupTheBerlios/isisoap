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

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

import soap.Context;
import soap.server.ConnectionManager;

/**
 * @author masahiko
 */
public class UpdateProjectAction extends SoapAction implements  TreeModelListener
{
    public UpdateProjectAction()
    {
    	super("updateProject","icons/UpdateProject.gif", 'U', Event.CTRL_MASK) ;
    	handleChanges();
    }
    
   
    public void actionPerformed(ActionEvent e)
    {
        ConnectionManager connectManager = ConnectionManager.getConnection() ;
        connectManager.updateProject() ;
    }


    /* (non-Javadoc)
     * @see javax.swing.event.TreeModelListener#treeNodesChanged(javax.swing.event.TreeModelEvent)
     */
    public void treeNodesChanged(TreeModelEvent e)
    {
        handleChanges();
    }


    /* (non-Javadoc)
     * @see javax.swing.event.TreeModelListener#treeNodesInserted(javax.swing.event.TreeModelEvent)
     */
    public void treeNodesInserted(TreeModelEvent e)
    {
        handleChanges();
    }


    /* (non-Javadoc)
     * @see javax.swing.event.TreeModelListener#treeNodesRemoved(javax.swing.event.TreeModelEvent)
     */
    public void treeNodesRemoved(TreeModelEvent e)
    {
        handleChanges();
    }


    /* (non-Javadoc)
     * @see javax.swing.event.TreeModelListener#treeStructureChanged(javax.swing.event.TreeModelEvent)
     */
    public void treeStructureChanged(TreeModelEvent e)
    {
        handleChanges() ;
    }
    
    private void handleChanges()
    {
        if (Context.getInstance().getListProjects().getCurrentProject() == null)
    	{
    	    setEnabled(false);
    	}
    	else
    	{
    	    setEnabled(true);
    	}
    }
    
    public void setListener()
    {
        Context.getInstance().getTopLevelFrame().getProjectTree().getModel().addTreeModelListener(this);
    }

}
