/*
 * Created on 25 nov. 2004
 */
package soap.ui.tabbedPane;

import java.awt.BorderLayout;

import soap.model.executionProcess.structure.Iteration.ListTask;
import soap.ui.SoapTasksTable;

/**
 * @author SCARAVETTI Florent
 */
public class SoapTasksTabbedPane extends DefaultCentralTabbedPane
{
    public SoapTasksTabbedPane(ListTask listTask)
    {
        SoapTasksTable table = new SoapTasksTable(listTask);
		mDataPanel.setLayout(new BorderLayout());
		mDataPanel.add(table.getTableHeader(),BorderLayout.NORTH);
		mDataPanel.add(table,BorderLayout.CENTER);
    }
}
