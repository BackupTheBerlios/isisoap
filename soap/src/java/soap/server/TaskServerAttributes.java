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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TaskServerAttributes
{
    Map mAttributes = new HashMap() ;
    
    public TaskServerAttributes ()
    {
        
    }
    
    public TaskServerAttributes (String typeOfTask)
    {
        mAttributes.put("TYPE_OF_TASK", typeOfTask) ;
    }
    
    public void clearAttributes ()
    {
        mAttributes.clear();
    }
    
    public void setTypeOfTask (String typeOfTask)
    {
        mAttributes.put("TYPE_OF_TASK", typeOfTask) ;
    }
    
    public String getTypeOfTask()
    {
        return (String)mAttributes.get("TYPE_OF_TASK");
    }
    
    public void setUrl(URL pUrl)
    {
        mAttributes.put("URL", pUrl);
    }
    
    public URL getUrl ()
    {
        return (URL) mAttributes.get("URL");
    }
    
    public void setIdentificationAttribute (String login, String password)
    {
        mAttributes.put("LOGIN", login);
        mAttributes.put("PASS", password) ;
    }
    
    public String getLogin()
    {
        return (String)mAttributes.get("LOGIN");
    }
    
    public String getPassword()
    {
        return (String)mAttributes.get("PASS");
    }
}