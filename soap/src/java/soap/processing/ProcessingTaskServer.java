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


import soap.server.ConnectionManager;
import soap.server.TaskServerAttributes;
import soap.server.ConnectionManager.ConnectionServer;
import soap.ui.dialog.ProcessingTaskServerDialog;
import utils.MonitoredTaskBase;

public class ProcessingTaskServer extends MonitoredTaskBase 
{  
    private ProcessingTaskServerDialog mTaskDialog = null;
    
    private TaskServerAttributes mTaskAttribute ;
    private int mResult = 0 ;
            
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
    
    private int connect()  
    {	
        mResult = ConnectionManager.NOT_CONNECTED ;
        if (mTaskAttribute.getLogin().equals("go" ) && mTaskAttribute.getPassword().equals("go"))
        {
            mResult = ConnectionManager.CONNECTED ;
            print("connected");
            return ConnectionManager.CONNECTED ;
        }
        else
        {
            return ConnectionManager.NOT_CONNECTED ;
        }
        /*String urlString = "http://yanagiba/iup/index.php";
        InputStream content = null ;        
        try 
        {
            
            mUrl  = new URL (urlString);
            mUrlConnection = mUrl.openConnection();
            String enc =  mLogin + ":" + mPassword ;
            String encoding = new sun.misc.BASE64Encoder().encode(enc.getBytes());
            mUrlConnection.setRequestProperty  ("Authorization", "Basic " + encoding);
        } 
        catch (MalformedURLException e) 
        {
            print("can't connect to the server");
            return ConnectionManager.NOT_CONNECTED ;
        } 
        catch (IOException e) 
        {
            print("can't connect to the server");
            return ConnectionManager.NOT_CONNECTED ;
        }
        
        try
        {
            content = (InputStream)mUrlConnection.getInputStream();
        }
        catch(java.net.SocketException e)
        {
            print("can't connect to the server");
            return ConnectionManager.NOT_CONNECTED ;
        }
        catch (IOException e) 
        {
            print("login or password incorrect");
            return ConnectionManager.NOT_CONNECTED ;
        }
        
        try
        
        {
            if (content == null)
            {
                content = (InputStream)mUrlConnection.getInputStream();
            }
            BufferedReader in = new BufferedReader (new InputStreamReader (content));
            String line;
            while ((line = in.readLine()) != null) 
            {
              System.out.println (line);
              print(line);
            }
         }
         catch (IOException e) 
         {
             print(e.toString());
             System.out.println(e.toString());
             return ConnectionManager.NOT_CONNECTED ;
         }
         mResult = ConnectionManager.CONNECTED ;
        return ConnectionManager.CONNECTED ;*/
    } 
    
    
    private int importXML()
    {
        mResult = 0 ;
        print("ok");
        for (int i = 0; i < 10; i++)
        {
            print("ok");
        }
        return 0 ;
    }
    
    public int createUsers()
    {
        
        return 0 ;
    }
     
    
    public int getResultTask()
    {
        return mResult ;
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
