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

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import soap.model.executionProcess.structure.Iteration.ListTask;
import soap.ui.SoapTasksTable;

public class SoapTasksCentralPanel extends DefaultCentralPanel
{
    
    public SoapTasksCentralPanel(ListTask listTask)
    {
        SoapTasksTable table = new SoapTasksTable(listTask);
        JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(table.getTableHeader(),BorderLayout.NORTH);
		tablePanel.add(table,BorderLayout.CENTER);
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new BorderLayout());
		dataPanel.add(tablePanel, BorderLayout.CENTER);
		addTab(resMan.getString("tabbedPaneData"),new JScrollPane(dataPanel));
    }
}