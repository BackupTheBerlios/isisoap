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

import soap.model.DefaultSoapElementVisitor;
import soap.model.executionProcess.structure.Iteration;
import soap.model.executionProcess.structure.Project;
import soap.model.executionProcess.structure.Iteration.ListTask;
import soap.model.extension.SoapListProjects;
import soap.ui.tabbedPane.SoapCentralTabbedPane;
import soap.ui.tabbedPane.SoapIterationTabbedPane;
import soap.ui.tabbedPane.SoapListProjectsTabbedPane;
import soap.ui.tabbedPane.SoapProjectTabbedPane;
import soap.ui.tabbedPane.SoapTasksTabbedPane;


public class PanelAssociater extends DefaultSoapElementVisitor
{

	SoapCentralTabbedPane mCentralPanel = null;
	
	public PanelAssociater()
	{
	}
	
	/**
	 * Retrieve the central panel associated to the element during the visit
	 */
	public SoapCentralTabbedPane getCentralPanel()
	{
		SoapCentralTabbedPane centralPanel = mCentralPanel;
		
		mCentralPanel = null;
		
		return centralPanel;
	}
	
	public void visitProject (Project p)
	{
		mCentralPanel = new SoapProjectTabbedPane(p);
	}	
	
	
	public void visitSoapListProjects (SoapListProjects listProjects)
	{
	    mCentralPanel = new SoapListProjectsTabbedPane(listProjects);
	}
	
	public void visitIteration(Iteration i)
	{
		mCentralPanel = new SoapIterationTabbedPane(i);
	}
	public void visitListTask(ListTask listTask)
    {
	    mCentralPanel = new SoapTasksTabbedPane(listTask);
    }
}