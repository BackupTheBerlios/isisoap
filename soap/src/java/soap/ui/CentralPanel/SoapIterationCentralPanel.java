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
import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import soap.Context;
import soap.model.core.EstimationElement;
import soap.model.executionProcess.structure.Iteration;
import soap.ui.panel.SoapGridbagPanel;
import soap.ui.panel.SoapPanel;
import soap.ui.panel.innerCentralPanel.StatsPanel;
import utils.IndicatorManager;
import utils.ProjectManager;
import utils.ResourceManager;


public class SoapIterationCentralPanel extends DefaultCentralPanel 
{
	public SoapIterationCentralPanel(Iteration it)
	{
		super();

		ResourceManager resMan = ResourceManager.getInstance();
		// Iteration information
		JPanel iterationPanel = new JPanel() ;
		iterationPanel.setBorder(BorderFactory.createEmptyBorder(8,10,10,10)) ;
		iterationPanel.setLayout(new GridLayout(3,2));
		iterationPanel.add(new JLabel(resMan.getString("iterationName")));
		iterationPanel.add(new JLabel(it.getName()));
		iterationPanel.add(new JLabel(resMan.getString("iterationStartDate")));
		iterationPanel.add(new JLabel(it.getAttribute(EstimationElement.START_DATE).toString()));
		iterationPanel.add(new JLabel(resMan.getString("iterationEndDate")));
		iterationPanel.add(new JLabel(it.getAttribute(EstimationElement.END_DATE).toString()));
		
		SoapPanel iterationInfoPanel = new SoapPanel();
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyleIteration = BorderFactory.createTitledBorder( loweredetched,resMan.getString("iterationInformation"));
		iterationInfoPanel.setBorder(titleStyleIteration);		
		iterationInfoPanel.setLayout(new BorderLayout());
		iterationInfoPanel.add(iterationPanel,BorderLayout.CENTER) ;
		
		// Iteration Realization information
		JPanel realizationPanel = new JPanel() ;
		realizationPanel.setBorder(BorderFactory.createEmptyBorder(8,10,10,10)) ;
		realizationPanel.setLayout(new GridLayout(3,2));
		realizationPanel.add(new JLabel(resMan.getString("iterationEstimatedHours")));
		realizationPanel.add(new JLabel(it.getAttribute(EstimationElement.ESTIMATED_HOURS)+" "+resMan.getString("hours")));
		realizationPanel.add(new JLabel(resMan.getString("iterationElapsedHours")));
		realizationPanel.add(new JLabel(it.getAttribute(EstimationElement.ELAPSED_HOURS)+" "+resMan.getString("hours")));
		realizationPanel.add(new JLabel(resMan.getString("iterationRemainedHours")));
		realizationPanel.add(new JLabel(it.getAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH)+" "+resMan.getString("hours")));
		
		SoapPanel realizationInfoPanel = new SoapPanel();
		Border loweredetchedRealization = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyleRealization = BorderFactory.createTitledBorder( loweredetchedRealization,resMan.getString("iterationRealizationInformation"));
		realizationInfoPanel.setBorder(titleStyleRealization);		
		realizationInfoPanel.setLayout(new BorderLayout());
		realizationInfoPanel.add(realizationPanel,BorderLayout.CENTER) ;
		
		//Indicators
		JPanel iteratorsPanel = new JPanel() ;
		SoapPanel iteratorsInfoPanel = new SoapPanel();
		String projectName = Context.getInstance().getListProjects().getCurrentProject().getName();
		HashMap indicators = ProjectManager.getInstance().getIndicatorsName(projectName,ProjectManager.ITERATION);
		if(indicators.size() != 0)
		{
		    iteratorsPanel.setBorder(BorderFactory.createEmptyBorder(8,10,10,10)) ;
		    iteratorsPanel.setLayout(new GridLayout(indicators.size(),2));
			Iterator iterator = indicators.keySet().iterator();
	        while(iterator.hasNext())
	        {
	           String key = (String)iterator.next();
	           iteratorsPanel.add(new JLabel(indicators.get(key)+" : "));
	           int value = IndicatorManager.getInstance().getPropertyInteger(projectName,key+"_"+it.getID());
	           JLabel valueLabel = new JLabel(value+ " "+ProjectManager.getInstance().getIndicatorProperty(projectName,key,ProjectManager.UNIT));
	           int seuilMin = ProjectManager.getInstance().getIndicatorPropertyInteger(projectName,key,ProjectManager.MINLIMIT);
	           int seuilMax = ProjectManager.getInstance().getIndicatorPropertyInteger(projectName,key,ProjectManager.MAXLIMIT);
	           if(value < seuilMin*0.95 || value > seuilMax*1.05)
	           {
	               valueLabel.setForeground(Color.RED);
	           }
	           else
	           {
	               if(value < seuilMin || value > seuilMax)
	               {
	                   valueLabel.setForeground(Color.MAGENTA);
	               }
	           }
	           iteratorsPanel.add(valueLabel);
	        }
			Border loweredetchedIterators = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder titleStyleIterators = BorderFactory.createTitledBorder( loweredetchedIterators,resMan.getString("indicators"));
			iteratorsInfoPanel.setBorder(titleStyleIterators);		
			iteratorsInfoPanel.setLayout(new BorderLayout());
			iteratorsInfoPanel.add(iteratorsPanel,BorderLayout.CENTER) ;
		}
		
		// Add to data panel
        SoapGridbagPanel dataPanel = new SoapGridbagPanel();
      		
		dataPanel.defaultAddComponent(iterationInfoPanel, SoapGridbagPanel.END) ;
		dataPanel.defaultAddComponent(realizationInfoPanel, SoapGridbagPanel.END) ;
		
		if(indicators.size() != 0)
		{
			dataPanel.defaultAddComponent(iteratorsInfoPanel, SoapGridbagPanel.END) ;
		}
		dataPanel.addLine(2.0);
		addTab(resMan.getString("tabbedPaneData"),dataPanel);
		addTab(resMan.getString("tabbedPaneStats"),new JScrollPane(new StatsPanel(it)));
	}
}
