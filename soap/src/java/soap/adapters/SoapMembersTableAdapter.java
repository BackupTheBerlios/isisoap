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
package soap.adapters;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import soap.Context;
import soap.model.executionProcess.structure.Project;
import utils.ResourceManager;


public class SoapMembersTableAdapter extends AbstractTableModel
{
    private Vector mColumnName = new Vector();
    private Project mProject;
    
    public SoapMembersTableAdapter()
    {
        ResourceManager resMan = ResourceManager.getInstance();
        mProject = Context.getInstance().getListProjects().getCurrentProject();
        mColumnName.add(resMan.getString("memberTableName"));
        mColumnName.add(resMan.getString("memberTableFirstName"));
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
	
    public int getRowCount()
    {
        return mProject.memberCount();
    }
    public Object getValueAt(int row, int col)
    {
        switch(col)
        {
            case 0 : 
                return mProject.getMember(row).getLastName();
            case 1 :
                return mProject.getMember(row).getFirstName();
        }
        return null;
    }
}
