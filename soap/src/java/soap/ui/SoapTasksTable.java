/*
 * Created on 20 nov. 2004
 */
package soap.ui;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import soap.adapters.SoapTasksTableAdapter;
import soap.adapters.SoapTasksTableSorter;
import soap.model.modelmanagement.IPackage;

/**
 * @author SCARAVETTI Florent
 */
public class SoapTasksTable extends JTable
{
    public SoapTasksTable(IPackage listTasks)
    {
        super(new SoapTasksTableSorter(new SoapTasksTableAdapter(listTasks)));
        ((SoapTasksTableSorter)getModel()).setTableHeader(getTableHeader());
    }
}
