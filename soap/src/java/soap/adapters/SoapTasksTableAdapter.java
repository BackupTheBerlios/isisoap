/*
 * Created on 20 nov. 2004
 */
package soap.adapters;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import soap.model.frontend.SoapMediator;
import soap.model.frontend.event.SoapEvent;
import soap.model.modelmanagement.IPackage;

/**
 * @author SCARAVETTI Florent
 */
public class SoapTasksTableAdapter implements TableModel, SoapMediator.Listener
{
    private IPackage mListTasks;
    private List mColumnName = new ArrayList(); 
    
    public SoapTasksTableAdapter(IPackage listTasks)
	{
        mListTasks = listTasks;
        mColumnName.add("Nom");
        mColumnName.add("test");
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
		return String.class;
	}

	
	public boolean isCellEditable(int line, int col) 
	{
		return false;
	}

	
	public Object getValueAt(int line, int col) 
	{
	    if(col == 0)
	    {
	        return mListTasks.getModelElement(line).toString();
	    }
	    return "test"+line;
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



