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

package soap.server;


import soap.processing.ProcessingTaskServer;
import soap.ui.SoapFrame;
import soap.ui.dialog.ProcessingTaskServerDialog;
import utils.ResourceManager;


/**
 * class using to retrieve the connection to the server
 *
 */

public class ConnectionManager
{   
    private static ConnectionManager mConnectionManager = new ConnectionManager();
    private SoapFrame parent = (SoapFrame)soap.Context.getInstance().getTopLevelFrame();
    
    private ProcessingTaskServerDialog mTaskDialog ;
    private ProcessingTaskServer mMonitor = null;   
    private String mUrl = "http://localhost:8080/owep/Soap/" ;
    private Request mRequest = new Request("","");
    private String mLogin = null;
    private String mPass = null;
    
   

    public ConnectionManager (){ };
    
    public static ConnectionManager getConnection () 
	{
		return mConnectionManager;
	}
    
    
    /**
     * check if the user is connected to the server
     * 
     * @return the connection to the server
     */
    public boolean isConnect()
    {
        if (mLogin== null || mPass==null)
            return false;
        else
            return true ;
    }
    
    
	public boolean identify()
	{
        IdentificationDialog identDialog = null ;
        
        mRequest.setUrl(mUrl);
        mRequest.setTypeOfTask(Request.IDENTIFY);
        if(!isConnect())
        {
            identDialog = new IdentificationDialog(ResourceManager.getInstance().getString("identificationDialogTitle")) ;
            if (identDialog.showIdentificationDialog() != IdentificationDialog.CONNECT)
            {   
                 return false ;
            }  
            else
            {
                mRequest.setIdentificationAttribute(identDialog.getLogin(),identDialog.getPassword()) ;
                process(true);
                if (mMonitor.getResultTask() && !isConnect())
                {
                    mLogin = identDialog.getLogin() ;
                    mPass = identDialog.getPassword() ;
                }
                return mMonitor.getResultTask() ;
            }
        }
        else
        {
            return true ;
        }
        
	}
    
    
    /**
     * import the xml
     * @return  true if it is succeeded
     */
    public boolean updateProject()
    {
        IdentificationDialog identDialog = null ;
        
        mRequest.setTypeOfTask(Request.IMPORT_XML);
        mRequest.setUrl(mUrl);
        if(!isConnect())
        {
            identDialog = new IdentificationDialog(ResourceManager.getInstance().getString("identificationDialogTitle")) ;
            if (identDialog.showIdentificationDialog() != IdentificationDialog.CONNECT)
            {   
                 return false ;
            }  
            else
            {
                mRequest.setIdentificationAttribute(identDialog.getLogin(),identDialog.getPassword()) ;
            }
        }
        process(false);
        if (mMonitor.getResultTask() && !isConnect())
        {
            mLogin = identDialog.getLogin() ;
            mPass = identDialog.getPassword() ;  
        }
        return mMonitor.getResultTask() ;
    }
    
    /**
     * create users
     * 
     * @return  true if it is succeeded
     */
    public boolean createUsers()
    {
        mRequest =  new Request(Request.CREATE_USERS, mUrl);
        process(true) ;
        return mMonitor.getResultTask() ;
    }
    
    /**
     * lauch the monitor
     * 
     */
    private void process(boolean close)
    {
		mMonitor = new ProcessingTaskServer(mRequest) ;
        mTaskDialog = new ProcessingTaskServerDialog(parent,mMonitor,close );
		mTaskDialog.setName(ResourceManager.getInstance().getString("loading"));	
		mMonitor.setTask(mTaskDialog);
		mTaskDialog.setVisible();
    }
    
    
}