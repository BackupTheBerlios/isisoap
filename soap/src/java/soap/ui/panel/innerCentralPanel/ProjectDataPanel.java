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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import soap.model.core.EstimationElement;
import soap.ui.CentralPanel.SoapProjectCentralPanel;
import soap.ui.panel.SoapGridbagPanel;
import soap.ui.panel.SoapPanel;
import utils.IndicatorManager;
import utils.ProjectManager;


public class ProjectDataPanel extends ProjectInnerCentralPanel
{
    
    private Vector mListLabels;
    
    public ProjectDataPanel(SoapProjectCentralPanel parent)
    {
        super(parent);
        mListLabels = new Vector();
        initUI();
    }
    
    protected void initUI()
    {
        // Project informations
        boolean updated = mProject.getAttribute(EstimationElement.START_DATE) != null;
	    
		JPanel projectPanel = new JPanel() ;
		projectPanel.setBorder(BorderFactory.createEmptyBorder(8,10,10,10)) ;
		if(updated)
		    projectPanel.setLayout(new GridLayout(4,2));
		else
		    projectPanel.setLayout(new GridLayout(2,2));
		
		projectPanel.add(new JLabel(resMan.getString("projectName")));
		projectPanel.add(new JLabel(mProject.getName()));
		if(updated)
		{
		    projectPanel.add(new JLabel(resMan.getString("projectStartDate")));
		    projectPanel.add(new JLabel(mProject.getAttribute(EstimationElement.START_DATE).toString()));
		    projectPanel.add(new JLabel(resMan.getString("projectEndDate")));
			projectPanel.add(new JLabel(mProject.getAttribute(EstimationElement.END_DATE).toString()));
		}
		projectPanel.add(new JLabel(resMan.getString("projectLastUpdate")));
		projectPanel.add(new JLabel("14/01/05"));
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
		supervisorPanel.add(new JLabel(mProject.getSupervisor().getLastName()));
		supervisorPanel.add(new JLabel(resMan.getString("supervisorFirstName")));
		supervisorPanel.add(new JLabel(mProject.getSupervisor().getFirstName()));
		
		JPanel supervisorInfoPanel = new JPanel();
		TitledBorder titleStyleSupervisor = BorderFactory.createTitledBorder( loweredetched,resMan.getString("supervisorInformation"));
		supervisorInfoPanel.setBorder(titleStyleSupervisor);
		supervisorInfoPanel.setLayout(new BorderLayout());
		supervisorInfoPanel.add(supervisorPanel) ;
	
		// Indicators
		JPanel indicatorsPanel = new JPanel() ;
		SoapPanel indicatorsInfoPanel = new SoapPanel();
	
		if(mIndicatorsName.size() != 0 && updated)
		{
		    indicatorsPanel.setBorder(BorderFactory.createEmptyBorder(8,10,10,10)) ;
		    indicatorsPanel.setLayout(new GridLayout(mIndicatorsName.size(),2));
			Iterator iterator = mIndicatorsName.keySet().iterator();
	        while(iterator.hasNext())
	        {
	           String key = (String)iterator.next();
	           indicatorsPanel.add(new JLabel(mIndicatorsName.get(key)+" : "));
	           int value = IndicatorManager.getInstance().getPropertyInteger(mProject.getName(),key+"_"+mProject.getID());
	           JLabel valueLabel = new JLabel(value+ " "+ProjectManager.getInstance().getIndicatorProperty(mProject.getName(),key,ProjectManager.UNIT));
	           mListLabels.add(valueLabel);
	           indicatorsPanel.add(valueLabel);
	        }
	        updateIndicatorsLabels();
			Border loweredetchedIndicators = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder titleStyleIndicators = BorderFactory.createTitledBorder( loweredetchedIndicators,resMan.getString("indicators"));
			indicatorsInfoPanel.setBorder(titleStyleIndicators);		
			indicatorsInfoPanel.setLayout(new BorderLayout());
			indicatorsInfoPanel.add(indicatorsPanel,BorderLayout.CENTER) ;
		}
		
		// Add to data panel
		defaultAddComponent(projectInfoPanel,SoapGridbagPanel.END);
		defaultAddComponent(supervisorInfoPanel, SoapGridbagPanel.END);
		if(mIndicatorsName.size() != 0 && updated)
		{
		    defaultAddComponent(indicatorsInfoPanel, SoapGridbagPanel.END);
		}
		addLine(2.0);
    }
    
    public void update()
    {
        updateIndicatorsLabels();
    }
    
    private void updateIndicatorsLabels ()
	{
	    Iterator iterator = mIndicatorsName.keySet().iterator();
	    for(int i = 0 ; i < mListLabels.size() ; i++)
	    {
	        String key = (String)iterator.next();
	        int seuilMin = ProjectManager.getInstance().getIndicatorPropertyInteger(mProject.getName(),key,ProjectManager.MINLIMIT);
	        int seuilMax = ProjectManager.getInstance().getIndicatorPropertyInteger(mProject.getName(),key,ProjectManager.MAXLIMIT);
	        int value = IndicatorManager.getInstance().getPropertyInteger(mProject.getName(),key+"_"+mProject.getID());
	        if(value < seuilMin*0.95 || value > seuilMax*1.05 )
	        {	
	            ((JLabel)mListLabels.get(i)).setForeground(Color.RED);
	        }
	        else	
	        {
	            if(value < seuilMin || value > seuilMax)
	            {
	                ((JLabel)mListLabels.get(i)).setForeground(new Color(255,160,0));
	            }
	            else
	            {
	                ((JLabel)mListLabels.get(i)).setForeground(Color.BLACK);
	            }
	        }
        }
	}
}
