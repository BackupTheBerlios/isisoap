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

package soap.ui.tabbedPane;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

import soap.model.core.EstimationElement;
import soap.model.executionProcess.structure.Project;
import soap.ui.panel.SoapPanel;
import utils.ResourceManager;
import utils.SoapDate;

public class SoapProjectTabbedPane extends DefaultCentralTabbedPane
{
    
	public SoapProjectTabbedPane(Project project)
	{
	    super();
	    
		JPanel settingsPanel = new JPanel();
		addTab(ResourceManager.getInstance().getString("tabbedPaneSettings"),settingsPanel);
		
	
		
		// Project informations
		JPanel projectPanel = new JPanel() ;
		projectPanel.setBorder(BorderFactory.createEmptyBorder(8,10,10,10)) ;
		projectPanel.setLayout(new GridLayout(4,2));
		projectPanel.add(new JLabel(resMan.getString("projectName")));
		projectPanel.add(new JLabel(project.getName()));
		projectPanel.add(new JLabel("Date de début :"));
		projectPanel.add(new JLabel(project.getAttribute(EstimationElement.START_DATE).toString()));
		projectPanel.add(new JLabel("Date de fin :"));
		projectPanel.add(new JLabel(project.getAttribute(EstimationElement.END_DATE).toString()));
		projectPanel.add(new JLabel("Dernière mise à jour :"));
		projectPanel.add(new JLabel("09/12/04"));
		System.getProperties() ;
		SoapPanel projectInfoPanel = new SoapPanel();
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyleProject = BorderFactory.createTitledBorder( loweredetched,resMan.getString("projectInformation"));
		projectInfoPanel.setBorder(titleStyleProject);		
		projectInfoPanel.setLayout(new BorderLayout());
		projectInfoPanel.add(projectPanel) ;
		
		//Supervisor informations
		JPanel supervisorPanel = new JPanel() ;
		supervisorPanel.setBorder(BorderFactory.createEmptyBorder(8,10,10,10)) ;
		supervisorPanel.setLayout(new GridLayout(2,2));
		supervisorPanel.add(new JLabel(resMan.getString("supervisorLastName")));
		supervisorPanel.add(new JLabel(project.getSupervisor().getLastName()));
		supervisorPanel.add(new JLabel(resMan.getString("supervisorFirstName")));
		supervisorPanel.add(new JLabel(project.getSupervisor().getFirstName()));
		
		JPanel supervisorInfoPanel = new JPanel();
		TitledBorder titleStyleSupervisor = BorderFactory.createTitledBorder( loweredetched,resMan.getString("supervisorInformation"));
		supervisorInfoPanel.setBorder(titleStyleSupervisor);
		supervisorInfoPanel.setLayout(new BorderLayout());
		supervisorInfoPanel.add(supervisorPanel) ;
		// Add to data panel
		
		GridBagLayout gridbag = new GridBagLayout();
	   	GridBagConstraints c = new GridBagConstraints();
        mDataPanel.setLayout(gridbag);
      
		c.weightx = 1.0;
		c.weighty = 0 ;
		c.fill = GridBagConstraints.BOTH ;
		c.insets = new Insets(10,10,10,10);
				
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(projectInfoPanel, c);
		mDataPanel.add(projectInfoPanel) ;
		

		c.weighty = 0.0;
		c.gridwidth = GridBagConstraints.REMAINDER ;
		c.insets = new Insets(10,10,10,10);
		gridbag.setConstraints(supervisorInfoPanel, c);
		mDataPanel.add(supervisorInfoPanel) ;
	
		c.weighty = 2.0;
		SoapPanel.makeLabel(mDataPanel, " ", gridbag, c);
		
		Task t = new Task ("Specification",new SoapDate (10,11,2004), new SoapDate(11,12,2004));
		Task t2 = new Task ("Plans de test",new SoapDate(15,11,2004),new SoapDate(25,11,2004));
		Task t3 = new Task ("Conception",new SoapDate(11,12,2004),new SoapDate(12,01,2005));
		Task t4 = new Task ("Données de test",new SoapDate(12,01,2005),new SoapDate(25,01,2005));
		Task t5 = new Task ("Codage",new SoapDate(12,01,2005),new SoapDate(13,02,2005));
		Task t6 = new Task ("Test du produit",new SoapDate(13,02,2005),new SoapDate(14,03,2005));
		TaskSeries ts = new TaskSeries ("Activité critique");
		ts.add(t);
		ts.add(t3);
		ts.add(t5);
		ts.add(t6);
		TaskSeries ts2 = new TaskSeries ("Activité simple");
		ts2.add(t2);
		ts2.add(t4);
		TaskSeriesCollection tsc = new TaskSeriesCollection();
		tsc.add(ts);
		tsc.add(ts2);
		ChartPanel panel = new ChartPanel(ChartFactory.createGanttChart( "Diagramme de Gantt",  "","",  tsc, true, true, true));
		mStatsPanel.add(panel);
	}
}
