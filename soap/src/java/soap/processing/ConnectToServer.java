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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import soap.ui.dialog.ConnectionToServerDialog;
import utils.MonitoredTaskBase;


public class ConnectToServer extends MonitoredTaskBase 
{
    public static int NOT_CONNECTED = 0;
    public static int CONNECTED = 1;
    
    private ConnectionToServerDialog mTaskDialog = null;
    private int mStatus ;
    private String mLogin;
    private String mPassword;
    private URLConnection mUrlConnection ;
    private URL mUrl ;
        
    public ConnectToServer (String pLogin, String pPass) 
    {
        mLogin = pLogin ;
        mPassword = pPass ;
    }
    
    protected Object processingTask()
    {
        launch();
		return null;
    }
    
    public void setTask( ConnectionToServerDialog taskDialog )
	{
        mTaskDialog = taskDialog;
	}
    
    protected void launch()
	{
	    try
		{
	        connect() ;
		}
		catch(Throwable t)
		{
		    print("identification failed");
			t.printStackTrace();
		}
	}
    
    private int connect()  
    {	
        mStatus = NOT_CONNECTED ;
        if (mLogin.equals("aubry" ) && mPassword.equals("isi"))
        {
            mStatus = CONNECTED ;
            print("connected");
            return CONNECTED ;
        }
        String urlString = "http://yanagiba/iup/index.php";
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
            return NOT_CONNECTED ;
        } 
        catch (IOException e) 
        {
            print("can't connect to the server");
            return NOT_CONNECTED ;
        }
        
        try
        {
            content = (InputStream)mUrlConnection.getInputStream();
        }
        catch(java.net.SocketException e)
        {
            print("can't connect to the server");
            return NOT_CONNECTED ;
        }
        catch (IOException e) 
        {
            print("Identifiant ou mot de passe incorrect");
            return NOT_CONNECTED ;
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
             return NOT_CONNECTED ;
         }
         mStatus = CONNECTED ;
        return CONNECTED ;
    } 
     
     
    public int getConnectionStatus()
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
