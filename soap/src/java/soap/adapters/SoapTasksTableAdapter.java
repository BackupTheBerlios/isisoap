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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import soap.Context;
import soap.model.core.EstimationElement;
import soap.model.executionProcess.structure.Task;
import soap.model.executionProcess.structure.Iteration.ListTask;
import soap.model.frontend.SoapMediator;
import soap.model.frontend.event.SoapEvent;
import utils.IndicatorManager;
import utils.Percentage;
import utils.ProjectManager;
import utils.ResourceManager;

public class SoapTasksTableAdapter implements TableModel, SoapMediator.Listener
{
    private ListTask mListTasks;
    private List mColumnName = new ArrayList(); 
    private List mIndicatorsNum = new ArrayList();
    private static ProjectManager pMan = ProjectManager.getInstance(); 
    
    public SoapTasksTableAdapter(ListTask listTasks)
	{
        mListTasks = listTasks;
        ResourceManager resMan = ResourceManager.getInstance();
        mColumnName.add(resMan.getString("taskName"));
        mColumnName.add(resMan.getString("taskStartDate"));
        mColumnName.add(resMan.getString("taskEndDate"));
        mColumnName.add(resMan.getString("taskResponsible"));
        mColumnName.add(resMan.getString("taskPriority"));
        mColumnName.add(resMan.getString("taskEstimatedHours"));
        mColumnName.add(resMan.getString("taskElapsedHours"));
        mColumnName.add(resMan.getString("taskRemainedHours"));
        
        String projectName = Context.getInstance().getListProjects().getCurrentProject().getName();
        
        HashMap indicators = pMan.getIndicatorsName(projectName,ProjectManager.TASK);
        Iterator it = indicators.keySet().iterator();
        while(it.hasNext())
        {
           String key = (String)it.next();
           mIndicatorsNum.add(key);
           mColumnName.add(indicators.get(key));
        }
	}
	
	public int getRowCount() 
	{	
		return mListTasks.modelElementCount();
	}

	public int getColumnCount() 
	{
		return mColumnName.size();
	}


	public String getColumnName(int numColumn) 
	{
		return (String)mColumnName.get(numColumn);
	}

	public Class getColumnClass(int numColumn) 
	{
		if(getRowCount() != 0)
		    return getValueAt(0,numColumn).getClass();
		else
		    return null;
	}

	
	public boolean isCellEditable(int line, int col) 
	{
		return false;
	}

	
	public Object getValueAt(int line, int col) 
	{
	    Task task = (Task)mListTasks.getModelElement(line);
	    String projectName = Context.getInstance().getListProjects().getCurrentProject().getName();
	    switch(col)
	    {
	        case 0 :
	            return task.toString();
	        case 1 :
	            return task.getAttribute(EstimationElement.START_DATE);
	        case 2 :
	            return task.getAttribute(EstimationElement.END_DATE);
	        case 3 :
	            return task.getMember().toString();
	        case 4 :
	            return new Integer(task.getPriority());
	        case 5 :
	            return task.getAttribute(EstimationElement.ESTIMATED_HOURS);
	        case 6 :
	            return task.getAttribute(EstimationElement.ELAPSED_HOURS);
	        case 7 :
	            return task.getAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH);
	    }       
	    for(int i = 8 ; i < mColumnName.size() ; i++)
	    {
	        if(col == i)
	        {
	            String type = pMan.getIndicatorProperty(projectName,(String)mIndicatorsNum.get(i-8),ProjectManager.TYPE);
	            String indicatorValue = IndicatorManager.getInstance().getProperty(projectName,mIndicatorsNum.get(i-8)+"_"+task.getID());
	            if(type.equals(ProjectManager.INTEGER_TYPE))    
	                return new Integer(indicatorValue);
	            else
	                if(type.equals(ProjectManager.FLOAT_TYPE))
	                    return new Float(indicatorValue);
	                else
	                    if(type.equals(ProjectManager.PERCENT_TYPE))
	                        return new Percentage(new Integer(indicatorValue));
	        }
	    }
	    return null;
	}

	public String getIndicatorNum(int col)
	{
	    if(col >= 8 && col < mColumnName.size())
	    {
	        return (String)mIndicatorsNum.get(col - 8);
	    }
	    else
	    {
	        return null;
	    }	    
	}
	
	public void setValueAt(Object obj, int line, int col) 
	{	
	}

	
	public void addTableModelListener(TableModelListener arg0) 
	{	
	}

	
	public void removeTableModelListener(TableModelListener arg0) 
	{
	}
	
	public void modelChanged(SoapEvent event)
	{		
	}
}



