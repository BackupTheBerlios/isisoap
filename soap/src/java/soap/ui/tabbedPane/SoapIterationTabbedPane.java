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

import soap.model.core.EstimationElement;
import soap.model.executionProcess.structure.Iteration;
import soap.ui.panel.SoapPanel;


public class SoapIterationTabbedPane extends DefaultCentralTabbedPane 
{
	
	public SoapIterationTabbedPane(Iteration it)
	{
		super();
		
		// Iteration information
		JPanel iterationPanel = new JPanel() ;
		iterationPanel.setBorder(BorderFactory.createEmptyBorder(8,10,10,10)) ;
		iterationPanel.setLayout(new GridLayout(3,2));
		iterationPanel.add(new JLabel("Nom de l'itération : "));
		iterationPanel.add(new JLabel(it.getName()));
		iterationPanel.add(new JLabel("Date de début : "));
		iterationPanel.add(new JLabel(it.getAttribute(EstimationElement.START_DATE).toString()));
		iterationPanel.add(new JLabel("Date de fin : "));
		iterationPanel.add(new JLabel(it.getAttribute(EstimationElement.END_DATE).toString()));
		
		SoapPanel iterationInfoPanel = new SoapPanel();
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyleIteration = BorderFactory.createTitledBorder( loweredetched,"Informations sur l'itération");
		iterationInfoPanel.setBorder(titleStyleIteration);		
		iterationInfoPanel.setLayout(new BorderLayout());
		iterationInfoPanel.add(iterationPanel,BorderLayout.CENTER) ;
		
		// Iteration Realization information
		float consumedBudget = ((float)((Integer)it.getAttribute(EstimationElement.ESTIMATED_HOURS)).intValue()/(float)((Integer)it.getAttribute(EstimationElement.ESTIMATED_HOURS)).intValue())*100;
		
		//System.out.println(it.getEstimatedHours());
		//System.out.println(consumedBudget);
		JPanel realizationPanel = new JPanel() ;
		realizationPanel.setBorder(BorderFactory.createEmptyBorder(8,10,10,10)) ;
		realizationPanel.setLayout(new GridLayout(5,2));
		realizationPanel.add(new JLabel("Charges prévues : "));
		realizationPanel.add(new JLabel(it.getAttribute(EstimationElement.ESTIMATED_HOURS)+" heures"));
		realizationPanel.add(new JLabel("Charges passées : "));
		realizationPanel.add(new JLabel(it.getAttribute(EstimationElement.ELAPSED_HOURS)+" heures"));
		realizationPanel.add(new JLabel("Charges restantes pour finir : "));
		realizationPanel.add(new JLabel(it.getAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH)+" heures"));
		realizationPanel.add(new JLabel("Avancement : "));
		realizationPanel.add(new JLabel(String.valueOf(it.getAdvancement())+" %"));
		realizationPanel.add(new JLabel("Budget consommé : "));
		realizationPanel.add(new JLabel(String.valueOf((int)consumedBudget)+" %"));
		
		
		SoapPanel realizationInfoPanel = new SoapPanel();
		Border loweredetchedRealization = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyleRealization = BorderFactory.createTitledBorder( loweredetchedRealization,"Informations sur la réalisation de l'itération");
		realizationInfoPanel.setBorder(titleStyleRealization);		
		realizationInfoPanel.setLayout(new BorderLayout());
		realizationInfoPanel.add(realizationPanel,BorderLayout.CENTER) ;
		
		// Add to data panel
		
		GridBagLayout gridbag = new GridBagLayout();
	   	GridBagConstraints c = new GridBagConstraints();
        mDataPanel.setLayout(gridbag);
      
		c.weightx = 1.0;
		c.weighty = 0 ;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
				
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(iterationInfoPanel, c);
		mDataPanel.add(iterationInfoPanel) ;
		
		gridbag.setConstraints(realizationInfoPanel, c);
		mDataPanel.add(realizationInfoPanel) ;
		
		c.weighty = 2.0;
		SoapPanel.makeLabel(mDataPanel, " ", gridbag, c);
		
	}
}
