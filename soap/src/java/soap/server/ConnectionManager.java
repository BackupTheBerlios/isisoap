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

public class ConnectionManager
{
    public static String CONNECTION = "CONNECTION" ; 
    public static int NOT_CONNECTED = 0;
    public static int CONNECTED = 1;
    
    private static ConnectionManager mConnectionManager = new ConnectionManager();
    private SoapFrame parent = (SoapFrame)soap.Context.getInstance().getTopLevelFrame();
    
    private ProcessingTaskServerDialog mTaskDialog ;
    private ProcessingTaskServer mMonitor = null;   
    private ConnectionServer mConnection= null ;
    private TaskServerAttributes mTaskAttr  = new TaskServerAttributes();
    
   

    public ConnectionManager ()
    {
        
    }
    
    public static ConnectionManager getInstance()
	{
		return mConnectionManager;
	}
    
    
    public ConnectionServer getConnection () 
    {
       if (mTaskAttr.getLogin() == null || mTaskAttr.getPassword()==null)
       {
			IdentificationDialog identDialog = new IdentificationDialog("identification") ;
			if (identDialog.showIdentificationDialog() == IdentificationDialog.CONNECT)
			{
			    mTaskAttr.setTypeOfTask(CONNECTION);
			    mTaskAttr.setIdentificationAttribute(identDialog.getLogin(),identDialog.getPassword()) ;
				mMonitor = new ProcessingTaskServer(mTaskAttr);  				
				mTaskDialog = new ProcessingTaskServerDialog(parent,mMonitor);
				mTaskDialog.setName("Loading");
				mTaskDialog.setLocation(parent.getWidth()/2-mTaskDialog.getWidth()/2,parent.getHeight()/2-mTaskDialog.getHeight()/2);	
				mMonitor.setTask(mTaskDialog);
				mTaskDialog.show();
				if (mMonitor.getResultTask() == CONNECTED)
				{
				    mTaskAttr.setIdentificationAttribute(identDialog.getLogin(),identDialog.getPassword()) ;
				    mTaskDialog.dispose() ;
				    mConnection = new ConnectionServer() ;
				    return mConnection ;
				}
				else
				{
				    mTaskAttr.clearAttributes() ;
				}
				
			}
			else
			{
			    mTaskAttr.clearAttributes() ;
			}
			
       }
       return mConnection ;
     }   
    
    public boolean isConnect()
    {
        if (mTaskAttr.getLogin() == null || mTaskAttr.getPassword()==null)
            return false;
        else
            return true ;
    }
    
    public class ConnectionServer
    {
        public final static String IMPORT = "IMPORT" ;
        public final static String CREATE_USERS = "CREATE_USERS" ;
      
        private ProcessingTaskServerDialog mTaskDialog ;
        private ProcessingTaskServer mMonitor  ;
        private TaskServerAttributes mTaskAttr ;
        
        private ConnectionServer()
        {   
        }
        
        public int importXML()
        {
            mTaskAttr = new TaskServerAttributes(IMPORT);
            mMonitor = new ProcessingTaskServer(mTaskAttr) ;
            process() ;
            return mMonitor.getResultTask() ;
        }
        
        public int createUsers()
        {
            mTaskAttr = new TaskServerAttributes(CREATE_USERS);
            mMonitor = new ProcessingTaskServer(mTaskAttr) ;
            process() ;
            return mMonitor.getResultTask() ;
        }
        
        private void process()
        {
    		mTaskDialog = new ProcessingTaskServerDialog(parent,mMonitor);
    		mTaskDialog.setName("Loading");
    		mTaskDialog.setLocation(parent.getWidth()/2-mTaskDialog.getWidth()/2,parent.getHeight()/2-mTaskDialog.getHeight()/2);	
    		mMonitor.setTask(mTaskDialog);
    		mTaskDialog.show();
        }
    }
    
    
}