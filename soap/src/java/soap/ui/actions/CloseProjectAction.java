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

package soap.ui.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;

import soap.Context;
import soap.ListProjects;
/**
 * Close the current project
 *
 * @version $Revision: 1.1 $
 */
public class CloseProjectAction extends SoapAction
{
    public static int NO_HOT_KEY = 0 ;
    
	public CloseProjectAction()
	{
		super("fileClose", "icons/CloseProject.gif", 'W', Event.CTRL_MASK);
	}

	public CloseProjectAction(int NO_HOT_KEY)
	{
		super("fileClose", "icons/CloseProject.gif");
	}
	
	public void actionPerformed(ActionEvent e)
	{
	    closeProject() ;
	}
	
	public void closeProject()
	{        
	    ListProjects listProject = Context.getInstance().getListProjects() ;
	    listProject.closeProject(listProject.getCurrentProject());
	}
}
