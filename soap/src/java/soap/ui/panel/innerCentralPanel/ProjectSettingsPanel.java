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
import java.awt.GridLayout;
import java.awt.Insets;
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

import soap.ui.CentralPanel.SoapProjectCentralPanel;
import soap.ui.panel.SoapGridbagPanel;
import soap.ui.panel.SoapPanel;
import utils.ProjectManager;


public class ProjectSettingsPanel extends ProjectInnerCentralPanel
{

    public ProjectSettingsPanel (SoapProjectCentralPanel parent)
    {
        super(parent);
        initUI();
    }
    
    protected void initUI()
    {
        ProjectManager pMan = ProjectManager.getInstance();
		// Indicators settings
		JPanel indicatorsSettingsPanel [] = new JPanel [mIndicatorsName.size()];
		SoapPanel indSettingsInfoPanel [] = new SoapPanel [mIndicatorsName.size()];
		
		Border loweredetchedIndicators = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyleIndicators [] = new TitledBorder [mIndicatorsName.size()];
		
		Iterator iterator = mIndicatorsName.keySet().iterator();
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
	        
	        titleStyleIndicators [i] = BorderFactory.createTitledBorder( loweredetchedIndicators,(String)mIndicatorsName.get(key));
	        indSettingsInfoPanel[i] = new SoapPanel();
	        indSettingsInfoPanel[i].setBorder(titleStyleIndicators [i]);		
	        indSettingsInfoPanel[i].setLayout(new BorderLayout());
	        indSettingsInfoPanel[i].add(indicatorsSettingsPanel[i],BorderLayout.CENTER) ;
		}
		
	    // add to settings Panel

		for(int i = 0 ; i < mIndicatorsName.size() ; i++)
		{
		    defaultAddComponent(indSettingsInfoPanel[i],SoapGridbagPanel.END, 1.0, 0, new Insets(10,10,10,10));
		}
		addLine(2.0);
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
	            mParent.update();
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
	            mParent.update();
	        }
	    }
	}
}
