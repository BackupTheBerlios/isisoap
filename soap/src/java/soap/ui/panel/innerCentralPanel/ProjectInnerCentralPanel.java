/*
 * SOAP Supervising, Observing, Analysing Projects
 * Copyright (C) 2003-2004 SoapTeam
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
package soap.ui.panel.innerCentralPanel;

import soap.model.executionProcess.structure.Project;
import soap.ui.CentralPanel.SoapProjectCentralPanel;
import utils.ProjectManager;


public class ProjectInnerCentralPanel extends InnerCentralPanel
{
    protected Project mProject;
    protected SoapProjectCentralPanel mParent;
   
    
    public ProjectInnerCentralPanel(SoapProjectCentralPanel parent)
    {
        super();
        mParent = parent;
        mProject = parent.getProject();
        mIndicatorsName = ProjectManager.getInstance().getIndicatorsName(mProject.getName(),ProjectManager.PROJECT);
    }
    
}
