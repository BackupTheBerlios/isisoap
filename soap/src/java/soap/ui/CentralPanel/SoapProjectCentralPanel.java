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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import soap.model.core.EstimationElement;
import soap.model.executionProcess.structure.Project;
import soap.ui.panel.SoapPanel;
import utils.IndicatorManager;
import utils.ProjectManager;

public class SoapProjectCentralPanel extends DefaultCentralPanel
{
    private Project mProject; 
    
	public SoapProjectCentralPanel(Project project)
	{
	    super();
	    
	    mProject = project;
		createDataPanel();
		createSettingsPanel();
	}
	
	private void createDataPanel()
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
	
		HashMap indicators = ProjectManager.getInstance().getIndicatorsName(mProject.getName(),ProjectManager.PROJECT);
		if(indicators.size() != 0 && updated)
		{
		    indicatorsPanel.setBorder(BorderFactory.createEmptyBorder(8,10,10,10)) ;
		    indicatorsPanel.setLayout(new GridLayout(indicators.size(),2));
			Iterator iterator = indicators.keySet().iterator();
	        while(iterator.hasNext())
	        {
	           String key = (String)iterator.next();
	           indicatorsPanel.add(new JLabel(indicators.get(key)+" : "));
	           int value = IndicatorManager.getInstance().getPropertyInteger(mProject.getName(),key+"_"+mProject.getID());
	           JLabel valueLabel = new JLabel(value+ " "+ProjectManager.getInstance().getIndicatorProperty(mProject.getName(),key,ProjectManager.UNIT));
	           int seuilMin = ProjectManager.getInstance().getIndicatorPropertyInteger(mProject.getName(),key,ProjectManager.MINLIMIT);
	           int seuilMax = ProjectManager.getInstance().getIndicatorPropertyInteger(mProject.getName(),key,ProjectManager.MAXLIMIT);
	           if(value < seuilMin*0.95 || value > seuilMax*1.05 )
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
	           indicatorsPanel.add(valueLabel);
	        }
			Border loweredetchedIndicators = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder titleStyleIndicators = BorderFactory.createTitledBorder( loweredetchedIndicators,resMan.getString("indicators"));
			indicatorsInfoPanel.setBorder(titleStyleIndicators);		
			indicatorsInfoPanel.setLayout(new BorderLayout());
			indicatorsInfoPanel.add(indicatorsPanel,BorderLayout.CENTER) ;
		}
		
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
	
		if(indicators.size() != 0 && updated)
		{
		    gridbag.setConstraints(indicatorsInfoPanel, c);
			mDataPanel.add(indicatorsInfoPanel) ;
		}
		
		c.weighty = 2.0;
		SoapPanel.makeLabel(mDataPanel, " ", gridbag, c);
	}
	
	private void createSettingsPanel()
	{	
	    ProjectManager pMan = ProjectManager.getInstance();
		// Indicators settings
	    HashMap indicators = ProjectManager.getInstance().getIndicatorsName(mProject.getName(),ProjectManager.PROJECT);
		JPanel indicatorsSettingsPanel [] = new JPanel [indicators.size()];
		SoapPanel indSettingsInfoPanel [] = new SoapPanel [indicators.size()];
		
		Border loweredetchedIndicators = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyleIndicators [] = new TitledBorder [indicators.size()];
		
		Iterator iterator = indicators.keySet().iterator();
	    for(int i = 0 ; iterator.hasNext() ; i++)
	    {
	        String key = (String)iterator.next();
	       
	        indicatorsSettingsPanel[i] = new JPanel();
	        indicatorsSettingsPanel[i].setBorder(BorderFactory.createEmptyBorder(8,10,10,10)) ;
	        indicatorsSettingsPanel[i].setLayout(new GridLayout(2,2));
	        indicatorsSettingsPanel[i].add(new JLabel(resMan.getString("indicatorMinLimit")));
	       
	        if(pMan.getIndicatorProperty(mProject.getName(),key,ProjectManager.TYPE).equals(ProjectManager.PERCENT_TYPE))
	        {
	           indicatorsSettingsPanel[i].add(new IndicatorLimitSlider(key+"_"+ProjectManager.MINLIMIT,0,100,pMan.getIndicatorPropertyInteger(mProject.getName(),key,ProjectManager.MINLIMIT)));
	           indicatorsSettingsPanel[i].add(new JLabel(resMan.getString("indicatorMaxLimit")));
	           indicatorsSettingsPanel[i].add(new IndicatorLimitSlider(key+"_"+ProjectManager.MAXLIMIT,0,100,pMan.getIndicatorPropertyInteger(mProject.getName(),key,ProjectManager.MAXLIMIT)));
	        }
	        else
	        {
	            if(pMan.getIndicatorProperty(mProject.getName(),key,ProjectManager.TYPE).equals(ProjectManager.INTEGER_TYPE))
	            {
	                indicatorsSettingsPanel[i].add(new IndicatorLimitSpinner(key+"_"+ProjectManager.MINLIMIT,new Integer(pMan.getIndicatorProperty(mProject.getName(),key,ProjectManager.MINLIMIT)))); 
	                indicatorsSettingsPanel[i].add(new JLabel(resMan.getString("indicatorMaxLimit")));
	                indicatorsSettingsPanel[i].add(new IndicatorLimitSpinner(key+"_"+ProjectManager.MAXLIMIT,new Integer(pMan.getIndicatorProperty(mProject.getName(),key,ProjectManager.MAXLIMIT))));
	            }
	            else
	            {
	                if(pMan.getIndicatorProperty(mProject.getName(),key,ProjectManager.TYPE).equals(ProjectManager.FLOAT_TYPE))
	                {
	                    indicatorsSettingsPanel[i].add(new IndicatorLimitSpinner(key+"_"+ProjectManager.MINLIMIT,new Float(pMan.getIndicatorProperty(mProject.getName(),key,ProjectManager.MINLIMIT)))); 
		                indicatorsSettingsPanel[i].add(new JLabel(resMan.getString("indicatorMaxLimit")));
		                indicatorsSettingsPanel[i].add(new IndicatorLimitSpinner(key+"_"+ProjectManager.MAXLIMIT,new Float(pMan.getIndicatorProperty(mProject.getName(),key,ProjectManager.MAXLIMIT))));
	                }
	            }
	        }
	        
	        titleStyleIndicators [i] = BorderFactory.createTitledBorder( loweredetchedIndicators,(String)indicators.get(key));
	        indSettingsInfoPanel[i] = new SoapPanel();
	        indSettingsInfoPanel[i].setBorder(titleStyleIndicators [i]);		
	        indSettingsInfoPanel[i].setLayout(new BorderLayout());
	        indSettingsInfoPanel[i].add(indicatorsSettingsPanel[i],BorderLayout.CENTER) ;
		}
		
	    // create settings Panel
	    JPanel settingsPanel = new JPanel();
	    this.addTab(resMan.getString("tabbedPaneSettings"),settingsPanel);
	    
	    // add to settings Panel
	    GridBagLayout gridbag = new GridBagLayout();
	   	GridBagConstraints c = new GridBagConstraints();
        settingsPanel.setLayout(gridbag);
        
        c.weightx = 1.0;
		c.weighty = 0 ;
		c.fill = GridBagConstraints.BOTH ;
		c.insets = new Insets(10,10,10,10);	
		c.gridwidth = GridBagConstraints.REMAINDER;
		
		for(int i = 0 ; i < indicators.size() ; i++)
		{
		    gridbag.setConstraints(indSettingsInfoPanel[i],c);
		    settingsPanel.add(indSettingsInfoPanel[i]);
		}
		c.weighty = 2.0;
		SoapPanel.makeLabel(settingsPanel, " ", gridbag, c);
		
		/*Task t = new Task ("Specification",new SoapDate (10,11,2004), new SoapDate(11,12,2004));
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
		mStatsPanel.add(panel);*/
	}
	
	private class IndicatorLimitSlider extends JSlider
	{
	    public IndicatorLimitSlider (String name, int mimimum, int maximum, int value)
	    {
	        super(mimimum, maximum, value);
	        setMinorTickSpacing(5);
	        setMajorTickSpacing(20);
	        setPaintTicks(true);
	        setPaintLabels(true);
	        setName(name);
	        addChangeListener(new SliderChangeListener());
	    }
	    private class SliderChangeListener implements ChangeListener
	    {
	        public void stateChanged(ChangeEvent event)
	        {
	            JSlider slider = (JSlider)event.getSource();
	            String sliderName = slider.getName();
	            String key = sliderName.substring(0,sliderName.lastIndexOf("_"));
	            int limit = Integer.parseInt(sliderName.substring(sliderName.lastIndexOf("_")+1));
	            ProjectManager.getInstance().setIndicatorPropertyInteger(mProject.getName(),key,limit,slider.getValue());
	        }
	    }
	}
	
	private class IndicatorLimitSpinner extends JSpinner
	{
	    public IndicatorLimitSpinner (String name, Integer value)
	    {
	        super(new SpinnerNumberModel());
	        setName(name);
	        setValue(value);
	        addChangeListener(new SpinnerChangeListener());
	    }
	    
	    public IndicatorLimitSpinner (String name, Float value)
	    {
	        super(new SpinnerNumberModel(value,new Float(0), new Float(999999.99),new Float(0.01)));
	        setName(name);
	        addChangeListener(new SpinnerChangeListener());
	    }
	    
	    private class SpinnerChangeListener implements ChangeListener
	    {
	        public void stateChanged(ChangeEvent event)
	        {
	            JSpinner spinner = (JSpinner)event.getSource();
	            String spinnerName = spinner.getName();
	            String key = spinnerName.substring(0,spinnerName.lastIndexOf("_"));
	            int limit = Integer.parseInt(spinnerName.substring(spinnerName.lastIndexOf("_")+1));
	            ProjectManager.getInstance().setIndicatorProperty(mProject.getName(),key,limit,spinner.getValue().toString());
	        }
	    }
	}
}
