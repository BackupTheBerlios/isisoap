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

package soap.ui.CentralPanel;


import java.util.Vector;

import javax.swing.JScrollPane;

import soap.model.executionProcess.structure.Project;
import soap.ui.panel.innerCentralPanel.InnerCentralPanel;
import soap.ui.panel.innerCentralPanel.ProjectDataPanel;
import soap.ui.panel.innerCentralPanel.ProjectSettingsPanel;
import soap.ui.panel.innerCentralPanel.ProjectUsersPanel;
import soap.ui.panel.innerCentralPanel.StatsPanel;

public class SoapProjectCentralPanel extends DefaultCentralPanel
{
    private Project mProject; 
    private Vector mListPanel = new Vector();

	public SoapProjectCentralPanel(Project project)
	{
	    super();
	    
	    mProject = project;
	    
	    ProjectDataPanel dataPanel = new ProjectDataPanel(this);
	    StatsPanel statsPanel = new StatsPanel(mProject);
	    ProjectSettingsPanel settingsPanel = new ProjectSettingsPanel(this);
	    ProjectUsersPanel usersPanel = new ProjectUsersPanel(this);
	    addTab(resMan.getString("tabbedPaneData"),new JScrollPane(dataPanel));
		addTab(resMan.getString("tabbedPaneStats"),new JScrollPane(statsPanel));
	    addTab(resMan.getString("tabbedPaneSettings"),new JScrollPane(settingsPanel));
	    addTab(resMan.getString("tabbedPaneUsers"),new JScrollPane(usersPanel));
	    mListPanel.add(dataPanel);
	    mListPanel.add(statsPanel);
	    mListPanel.add(settingsPanel);
	    mListPanel.add(usersPanel);
	}
	
	public Project getProject()
	{
	    return mProject;
	}
	
	public void update()
	{
	    for(int i = 0 ; i < mListPanel.size() ; i++)
	    {
	        ((InnerCentralPanel)mListPanel.get(i)).update();
	    }
	}	
}