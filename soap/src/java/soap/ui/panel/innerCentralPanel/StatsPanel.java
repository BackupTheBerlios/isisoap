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


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

import soap.Context;
import soap.model.executionProcess.structure.Iteration;
import soap.model.executionProcess.structure.Project;
import soap.model.modelmanagement.IPackage;
import soap.ui.panel.SoapGridbagPanel;
import soap.ui.stats.DataSetFactory;
import soap.ui.stats.OverLaidBarChartFactory;
import utils.ProjectManager;


public class StatsPanel extends InnerCentralPanel
{
    private IPackage mElementParent;
    private ChartPanel mChartPanel = new ChartPanel(null);
    private JCheckBox mCheckBoxTable [];
    
    public StatsPanel(IPackage parentElement)
    {
        mElementParent = parentElement;
        String projectName = Context.getInstance().getListProjects().getCurrentProject().getName();
        if(parentElement instanceof Project)
        {
            mIndicatorsName = ProjectManager.getInstance().getIndicatorsName(projectName,ProjectManager.PROJECT);
        }
        else
        {
            if(parentElement instanceof Iteration)
            {
                mIndicatorsName = ProjectManager.getInstance().getIndicatorsName(projectName,ProjectManager.TASK);
            }
        }
        initUI();
    }
    
    protected void initUI()
    {
        if(mIndicatorsName.size() != 0)
	    {    
	        defaultAddComponent(new JLabel(resMan.getString("statsChooseIndicator")),SoapGridbagPanel.END);
	    
	        JPanel indicatorsPanel = new JPanel();
	        indicatorsPanel.setBackground(Color.WHITE);
	        indicatorsPanel.setLayout(new GridLayout(mIndicatorsName.size(),1));
	        mCheckBoxTable = new JCheckBox [mIndicatorsName.size()];
	        CheckBoxListener listener = new CheckBoxListener();
	        Iterator it = mIndicatorsName.keySet().iterator();
	        for(int i = 0 ; i < mIndicatorsName.size() ; i++)
	        {
	            String key = (String)it.next();
	            mCheckBoxTable[i] = new JCheckBox((String)mIndicatorsName.get(key));
	            mCheckBoxTable[i].setName(key);
	            mCheckBoxTable[i].setPreferredSize(new Dimension(50,20));
	            mCheckBoxTable[i].setBackground(Color.WHITE);
	            mCheckBoxTable[i].addItemListener(listener);
	            indicatorsPanel.add(mCheckBoxTable[i]);
	        }
	        JScrollPane scrollPane = new JScrollPane(indicatorsPanel);
	        scrollPane.setPreferredSize(new Dimension(140,65));
	        defaultAddComponent(scrollPane,SoapGridbagPanel.END,1,0,new Insets(10,0,20,0));
	        
	        // add the chart to a panel...
	        mChartPanel.setPreferredSize(new Dimension(580, 330));
	        defaultAddComponent(new JScrollPane(mChartPanel), SoapGridbagPanel.END);
	    }
    }
    
    private class CheckBoxListener implements ItemListener
	{
	    public void itemStateChanged(ItemEvent event)
        {
	        DefaultCategoryDataset barDataSet = null;
	        DefaultCategoryDataset lineDataSet ;
	        LinkedList lineDataSets = new LinkedList();
	        
	        for(int i = 0 ; i < mCheckBoxTable.length ; i++ )
	        {
	            if(mCheckBoxTable[i].isSelected())
	            {
	                if(mCheckBoxTable[i].getName().equals(ProjectManager.ADVANCEMENT))
	                {
	                    lineDataSet = DataSetFactory.createDatasetAdvancement(mElementParent);
	                    if(lineDataSet != null)
	                        lineDataSets.add(lineDataSet);
	                }
	                else
	                {
	                    if(mCheckBoxTable[i].getName().equals(ProjectManager.CONSUMED_BUDGET))
	                    {
	                        barDataSet = DataSetFactory.addDatasets(DataSetFactory.createDatasetEstimatedBudget(mElementParent),barDataSet);
	                        lineDataSet = DataSetFactory.createDatasetConsumedBudget(mElementParent);
	                        if(lineDataSet != null)
		                        lineDataSets.addFirst(lineDataSet);
	                    }
	                    else
	                    {
	                        if(mCheckBoxTable[i].getName().equals(ProjectManager.REALIZED_FUNCTIONS))
	                        {
	                            barDataSet = DataSetFactory.addDatasets(DataSetFactory.createDatasetEstimatedFunctions(mElementParent),barDataSet);
	                            lineDataSet = DataSetFactory.createDatasetRealizedFunctions(mElementParent);
	                            if(lineDataSet != null)
			                        lineDataSets.addFirst(lineDataSet);
	                        }
	                        else
	                        {
	                            if(mCheckBoxTable[i].getName().equals(ProjectManager.REMAINED_FUNCTIONS))
	                            {
	                                lineDataSet = DataSetFactory.createDatasetRemainedFunctions(mElementParent);
	                                if(lineDataSet != null)
	        	                        lineDataSets.add(lineDataSet);
	                            }
	                        }
	                    }
	                }
	            }
	        }
 
	        if(lineDataSets.size() != 0)
	        {
	            String domainAxisLabel = "";
	            if(mElementParent instanceof Project)
	            {
	                domainAxisLabel = resMan.getString("projectStatsXAxis");
	            }
	            else
	            {
	                if(mElementParent instanceof Iteration)
	                {
	                    domainAxisLabel = resMan.getString("taskStatsXAxis");
	                }
	            }
	            mChartPanel.setChart(OverLaidBarChartFactory.createDualBarAndLineDiagram(resMan.getString("statsDiagramsTitle"),
	                    domainAxisLabel,resMan.getString("statsYAxis"),barDataSet, lineDataSets));
	        }
	        else
	        {
	            mChartPanel.setChart(null);
	        }
        }  
	}
}
