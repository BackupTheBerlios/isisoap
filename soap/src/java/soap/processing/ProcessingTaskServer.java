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

package soap.processing;


import java.io.IOException;

import soap.server.ConnectionManager;
import soap.server.TaskServerAttributes;
import soap.server.ConnectionManager.ConnectionServer;
import soap.ui.dialog.ProcessingTaskServerDialog;
import utils.MonitoredTaskBase;
import utils.ResourceManager;


/**
 * Monitor used to perform task beetween and the server
 *
 */

public class ProcessingTaskServer extends MonitoredTaskBase 
{  
    private ProcessingTaskServerDialog mTaskDialog = null;
    
    private TaskServerAttributes mTaskAttribute ;
    private boolean mStatus;
            
    public ProcessingTaskServer (TaskServerAttributes taskAttr) 
    {
        mTaskAttribute = taskAttr ;
    }
    
    protected Object processingTask()
    {
        launch();
		return null;
    }
    
    public void setTask( ProcessingTaskServerDialog taskDialog )
	{
        mTaskDialog = taskDialog;
	}
    
    /**
     * start performing server task
     *
     */
    protected void launch()
	{
        
	    if(mTaskAttribute.getTypeOfTask().equals(ConnectionManager.CONNECTION))
	    {
	        connect() ;
	        return ;
	    }
	    
	    if(mTaskAttribute.getTypeOfTask().equals(ConnectionServer.IMPORT))
	    {
	        importXML() ;
	        return ;
	    }  
		
	}
    
    /**
	 * connect to the the server with the attributes contained in the TaskServerAttributes.
	 * 
	 * @return the code associated 
	 */
    private boolean connect()  
    {	
        mStatus = true;
        if (mTaskAttribute.getLogin().equals("" ) && mTaskAttribute.getPassword().equals(""))
        {
            mStatus = true ;
            print(ResourceManager.getInstance().getString("connectionEstablished"));
            return true;
        }
        else
        {
            mStatus = false ;
            print(ResourceManager.getInstance().getString("loginMistaken"));
            return false ;
        }
    } 
    
    /**
     * import the xml from the server.
	 * 
	 * @return the code associated 
	 */
    private int importXML()
    {
        return 0 ;
    }
    
    /**
     * create the user.
	 * 
	 * @return the code associated 
	 */
    public boolean createUsers()
    {
        
        return true ;
    }
     
    /**
     * get the result of the task.
	 * 
	 * @return return the result of the task
	 */
    public boolean getResultTask()
    {
        return mStatus ;
    }
    /**
	 * Print a new message to the TaskMonitorDialog
	 * 
	 * @param msg
	 */
	protected void print( String msg )
	{
		setMessage(msg);
		if( mTaskDialog != null )
		{
			mTaskDialog.forceRefresh();
		}
	}   
}
