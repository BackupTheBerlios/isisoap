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
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import soap.model.core.EstimationElement;
import soap.model.executionProcess.structure.Task;
import soap.model.executionProcess.structure.Iteration.ListTask;
import soap.model.frontend.SoapMediator;
import soap.model.frontend.event.SoapEvent;
import utils.Percentage;

public class SoapTasksTableAdapter implements TableModel, SoapMediator.Listener
{
    private ListTask mListTasks;
    private List mColumnName = new ArrayList(); 
    
    public SoapTasksTableAdapter(ListTask listTasks)
	{
        mListTasks = listTasks;
        mColumnName.add("Nom");
        mColumnName.add("Date de début");
        mColumnName.add("Date de fin");
        mColumnName.add("Priorité");
        mColumnName.add("Charges estimées");
        mColumnName.add("Charges passées");
        mColumnName.add("Charges restantes pour finir");
		mColumnName.add("Avancement");
		mColumnName.add("Responsable");
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
	    switch(col)
	    {
	        case 0 :
	            return task.toString();
	        case 1 :
	            return task.getAttribute(EstimationElement.START_DATE);
	        case 2 :
	            return task.getAttribute(EstimationElement.END_DATE);
	        case 3 :
	            return new Integer(task.getPriority());
	        case 4 :
	            return task.getAttribute(EstimationElement.ESTIMATED_HOURS);
	        case 5 :
	            return task.getAttribute(EstimationElement.ELAPSED_HOURS);
	        case 6 :
	            return task.getAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH);
	        case 7 :
	            return new Percentage(new Integer(task.getAdvancement()));
	        case 8 :
	            return task.getMember().toString();
	    }
	    return null;
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



