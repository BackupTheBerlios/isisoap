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

package soap.ui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import soap.adapters.SoapTasksTableAdapter;
import soap.adapters.SoapTasksTableSorter;
import soap.model.executionProcess.structure.Iteration.ListTask;
import utils.Percentage;

public class SoapTasksTable extends JTable
{
    public static final int COLUMN_WIDTH = 150;
    
    public SoapTasksTable(ListTask listTasks)
    {
        super(new SoapTasksTableSorter(new SoapTasksTableAdapter(listTasks)));
        ((SoapTasksTableSorter)getModel()).setTableHeader(getTableHeader());
        setDefaultRenderer(Percentage.class, new PercentageRenderer());
        setDefaultRenderer(Integer.class, new IntegerRenderer());
        setPreferredSize(new Dimension(COLUMN_WIDTH*getColumnCount(), getRowCount()*getRowHeight()));
    }
    
    private class PercentageRenderer extends DefaultTableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column)
        {
              JLabel label = (JLabel)super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
              label.setText(value+" %");
              label.setHorizontalAlignment(SwingConstants.CENTER);
              return label;
        }
    }
    private class IntegerRenderer extends DefaultTableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column)
        {
              JLabel label = (JLabel)super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
              label.setHorizontalAlignment(SwingConstants.CENTER);
              return label;
        }
    }
}