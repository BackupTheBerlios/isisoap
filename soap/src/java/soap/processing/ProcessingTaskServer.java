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
import java.net.HttpURLConnection;

import soap.server.ConnectionManager;
import soap.server.Request;
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
    
    private Request mRequest ;
    private boolean mStatus = false;
            
    public ProcessingTaskServer (Request taskAttr) 
    {
        mRequest = taskAttr ;
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
        ConnectionManager con = ConnectionManager.getConnection() ;
        mRequest.sendRequest();
	    if(mRequest.getTypeOfTask().equals(Request.IDENTIFY))
	    {
	        connect() ;
	        return ;
	    }
	    if(mRequest.getTypeOfTask().equals(Request.IMPORT_XML))
	    {
	        importXML() ;
	        //loadProject() ;
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
        mStatus = false ;
    	try
        {
    	    if(mRequest.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED)
            {
                mStatus = true ;
            }
            else
            {
                print(ResourceManager.getInstance().getString("loginMistaken"));
            }
        }
        catch (IOException e)
        {
            print("impossible de se connecter au serveur");
        }
    	return mStatus ;
    } 
    
    /**
     * import the xml from the server.
	 * 
	 * @return the code associated 
	 */
    private boolean importXML()
    {
        mStatus = false ;
        try
        {
    	    if(mRequest.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
    	        try
    	        {
    	            String xml = mRequest.getContent();
    	            print("XML récupéré");
    	            
    	            print (xml);
    	        }
    	        catch (IOException ex)
    	        {
    	            print(ResourceManager.getInstance().getString("loginMistaken"));
    	        }    	        
                mStatus = true ;
            }
            else
            {
                print(ResourceManager.getInstance().getString("loginMistaken"));
            }
        }
        catch (IOException e)
        {
            print("can't connect to the server");
        }
    	return mStatus ;
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
