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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;


/**
 * class containing the attribute associated to a server task 
 *
 */

public class Request implements TaskServer
{
    private Map mAttributes = new HashMap() ;
    private OutputStreamWriter mWriter = null;
    private HttpURLConnection mUrlConnection ;
    private String mRequest = "";
    private int nbTypeOfTask = 0 ;
    
    
    public Request (String typeOfTask,URL url)
    {
        setTypeOfTask (typeOfTask) ;
        setUrl(url);
    }
    
    public Request (String typeOfTask,String url)
    {
        setTypeOfTask (typeOfTask) ;
        setUrl(url);
    }
    
    /**
     * clear all the attributes
     *
     */

    public void clearAttributes ()
    {
        mAttributes.clear();
        mRequest = "";
    }
    
    /**
     * set the type of the task
     * @param typeOfTask the type of the task
     */
    public void setTypeOfTask (String typeOfTask)
    {
        mAttributes.put("TYPE_OF_TASK", typeOfTask) ;
    }
    
    /**
     * get the type of the task
     *
     * @return the type of the task
     */
    public String getTypeOfTask()
    {
        return (String)mAttributes.get("TYPE_OF_TASK");
    }
    
    /**
     * specify the attribute URL
     * @param pUrl the URL associated to the task
     */
    public void setUrl(URL pUrl)
    {
        mAttributes.put("URL", pUrl);
    }
    
    /**
     * specify the attribute URL
     * @param pUrl the URL associated to the task
     */
    public void setUrl(String pUrl)
    {
        try
        {
            String urlAux ;
            if (pUrl.lastIndexOf("http://")< 0)
            {
                urlAux = "http://"+ pUrl ;
            }
            else
            {
                urlAux = pUrl ;
            }
            URL url = new URL(urlAux);
            mAttributes.put("URL", url);
        }
        catch (MalformedURLException ex)
        {
            ex.printStackTrace();
        }
        
    }
    
    
    /**
     * get the URL associated to the task
     *
     * @return URL
     */
    public URL getUrl ()
    {
        return (URL) mAttributes.get("URL");
    }
    
    /**
     * set the identity of a user
     * @param login user's login
     * @param password user's password
     */
    public void setIdentificationAttribute (String login, String password)
    {
        mAttributes.put("LOGIN", login);
        mAttributes.put("PASS", password) ;
    }
    
    /**
     * get the user's login
     *
     * @return return user's login
     */
    public String getLogin()
    {
        return (String)mAttributes.get("LOGIN");
    }
    
    /**
     * get user's password
     *
     * @return return user's password
     */
    public String getPassword()
    {
        return (String)mAttributes.get("PASS");
    }
    
    public void addAttributes(String name, String value)
    {
        mAttributes.put(name,value);
    }
    
    public void addAttributes(String name, Collection values) throws Exception
    {
        Iterator it = values.iterator();
        Object o  ;
        for(int i=0; it.hasNext(); i++)
        {
            o = it.next();
            if (! (o instanceof String))
            {
                throw new Exception("collection must be a collection of string");
            }
        }
        for(int i=0; it.hasNext(); i++)
        {	
            addAttributes(name + i, (String) it.next());
        }
        mAttributes.put(name,values);
    }
    /**
     * 
     *
     */
    public void sendRequest()
    {
        String identification = "" ;
        
        String typeOfTask = getTypeOfTask() ;
//        if (typeOfTask.equals(IDENTIFY))
//        {
//            identify();
//            return ;
//        }
//        if (typeOfTask.equals(IMPORT_XML))
//        {
//            importXML();
//            return ;
//        }
//        if (typeOfTask.equals(CREATE_USERS))
//        {
//            createUsers();
//            return ;
//        } 
        send();
    }
    
    public String getContent() throws IOException
    {
        InputStream content  = null ;
        BufferedReader reader = null  ;
        String result = "";
        content = (InputStream)mUrlConnection.getInputStream();
        reader = new BufferedReader (new InputStreamReader (content));
        String line;
        while ((line = reader.readLine()) != null) 
        {
            result += line ;
          //System.out.println (line);
        }
         return result;

    }
    public int getResponseCode() throws IOException
    {
        return mUrlConnection.getResponseCode();
    }
    
    public String getResponseMessage() throws IOException
    {
        Map m = mUrlConnection.getRequestProperties() ;
        Vector n = new Vector (m.keySet()) ;
		Vector v = new Vector (m.values()) ;
		
		for (int i=0; i < v.size();i++)
		{
		    System.out.println(n.get(i).toString() + " --- " + v.get(i).toString());
		}
        return mUrlConnection.getResponseMessage() ;
    }
    private void encodeParameter(String name, String value)
    {
        try
        {
            if(mRequest == "")
            {
                mRequest += URLEncoder.encode(name, "UTF-8")+"="+URLEncoder.encode(value, "UTF-8");
            }
            else
            {
                mRequest += "&"+URLEncoder.encode(name, "UTF-8")+"="+URLEncoder.encode(value, "UTF-8");
            }
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }
    
    private void send()
    {
        try
        {
            URL url =  getUrl() ;
            mUrlConnection = (HttpURLConnection) url.openConnection();
            mUrlConnection.setDoOutput(true);
            mWriter = new OutputStreamWriter(mUrlConnection.getOutputStream());
            Vector names = new Vector (mAttributes.keySet()) ;
            Vector values = new Vector (mAttributes.values()) ;
            for (int i=0; i < values.size();i++)
            {
                encodeParameter(names.get(i).toString(),values.get(i).toString()) ;
            }
            mWriter.write(mRequest);
            mRequest ="" ;
            mWriter.flush();
            mWriter.close();
        }
        catch (Exception e) 
        {
            e.printStackTrace() ;
        }
    }
    
//    /**
//     * 
//     *
//     */
//    public void identify()
//    {      
//        if (mAttributes.get("LOGIN") != null && mAttributes.get("PASS")!=null)
//        send();
//    }
//    
//    /**
//     * 
//     *
//     */
//    public void createUsers()
//    {
//        identify();
//        send();
//    }
//    
//    /**
//     * 
//     *
//     */
//    public void importXML()
//    {
//        identify();
//        send();
//    }
//
//    /* (non-Javadoc)
//     * @see soap.server.TaskServer#createProject()
//     */
//    public void createProject()
//    {
//        // TODO Auto-generated method stub
//        
//    }
//
//    /* (non-Javadoc)
//     * @see soap.server.TaskServer#listProject()
//     */
//    public void listProject()
//    {
//        // TODO Auto-generated method stub
//        
//    }
    
    public String toString ()
    {
        Vector names = new Vector (mAttributes.keySet()) ;
        Vector values = new Vector (mAttributes.values()) ;
        String str = "" ;
        for (int i=0; i < values.size();i++)
        {
            str +=names.get(i) + " - " + values.get(i)+ "\n" ;
        }
        return str ;
    }
}