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

import javax.swing.JPanel;

import soap.server.ConnectionManager;
import soap.ui.dialog.SeveralStepsDialog;
import soap.ui.panel.newProject.IndicatorsPanel;
import soap.ui.panel.newProject.NewProjectPanel;
import utils.ResourceManager;

public class NewProjectAction extends SoapAction
{    
    public NewProjectAction()
	{
		super("fileNew", "icons/NewProject.gif", 'N', Event.CTRL_MASK);
	}

	public void actionPerformed(ActionEvent e)
	{   
	   newProject() ;
	}
	
	public void newProject()
	{
	    ConnectionManager connectManager = ConnectionManager.getConnection() ;
	    if(connectManager.identify())
	    {
	        SeveralStepsDialog cfgDialog = new SeveralStepsDialog (ResourceManager.getInstance().getString("newProject"),ResourceManager.getInstance().getString("createNewProject"),"icons/logoConfig.gif", "icons/NewProject.gif") ;
	        JPanel panel = new NewProjectPanel(cfgDialog) ;
	        cfgDialog.addCenterPanel(panel);
	        
	        panel = new IndicatorsPanel(cfgDialog);
	        cfgDialog.addCenterPanel(panel);
	        cfgDialog.setVisible(true);
	    }
	}
}
