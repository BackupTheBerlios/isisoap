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
import soap.ui.CentralPanel.SoapCentralPanel;
import soap.ui.CentralPanel.SoapIterationCentralPanel;
import soap.ui.CentralPanel.SoapListProjectsCentralPanel;
import soap.ui.CentralPanel.SoapProjectCentralPanel;
import soap.ui.CentralPanel.SoapTasksCentralPanel;

/**
 * This visitor associate an element with his CentralPanelAssociater
 *
 */


public class CentralPanelAssociater extends DefaultSoapElementVisitor
{

	SoapCentralPanel mCentralPanel = null;
	
	public CentralPanelAssociater()
	{
	}
	
	/**
	 * Retrieve the central panel associated to the element during the visit
	 */
	public SoapCentralPanel getCentralPanel()
	{
		SoapCentralPanel centralPanel = mCentralPanel;
		
		mCentralPanel = null;
		
		return centralPanel;
	}
	
	public void visitProject (Project p)
	{
		mCentralPanel = new SoapProjectCentralPanel(p);
	}	
	
	
	public void visitSoapListProjects (SoapListProjects listProjects)
	{
	    mCentralPanel = new SoapListProjectsCentralPanel(listProjects);
	}
	
	public void visitIteration(Iteration i)
	{
		mCentralPanel = new SoapIterationCentralPanel(i);
	}
	public void visitListTask(ListTask listTask)
    {
	    mCentralPanel = new SoapTasksCentralPanel(listTask);
    }
}
