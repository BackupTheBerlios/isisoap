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
package utils;

import java.util.HashMap;
import java.util.Properties;


public class IndicatorManager
{
    private static IndicatorManager mIndicatorManager = new IndicatorManager();
	
	private HashMap mProperties = new HashMap();
	
	
	
	private IndicatorManager()
	{
	}
	
	/**
	 * Initialize properties of a project
	 */
	public void setIndicatorsValues(String projectName, Properties properties)
	{
	    mProperties.put(projectName, properties);
	}


	/**  
	 * Retrieve the ProjectManager instance
	 * 
	 * @return ProjectManager the projectManager 
	 */
	public static IndicatorManager getInstance()
	{
		return mIndicatorManager;
	}

	/**
	 * Retrieve the properties containing indicators values attached to a project
	 * 
	 * @return Properties the properties
	 */
	public Properties getProperties(String projectName)
	{
		return (Properties)mProperties.get(projectName);
	}

	/**
	 * Set a value associated to a key in a project
	 *
	 * @param key the key of the string needed
	 * @param value the value of the string needed
	 */
	public void setProperty(String projectName, String key, String value)
	{
		if(getProperties(projectName) != null)
		{
		    getProperties(projectName).setProperty(key, value);
		}
		else
		{
		    Properties properties = new Properties();
		    properties.setProperty(key, value);
		    setIndicatorsValues(projectName,properties);
		}
	}


	/**
	 * Get a string associated to a key in a project
	 *
	 * @param key the key of the string needed
	 */
	public String getProperty(String projectName, String key)
	{
	    if(getProperties(projectName) != null)
	    {
	        return getProperties(projectName).getProperty(key);
	    }
	    else
	    {
	        return null;
	    }
	}

	/**
	 * Get a boolean associated to a key
	 *
	 * @param key the key of the string needed
	 */
	public boolean getPropertyBoolean(String projectName, String key)
	{
		return Boolean.valueOf(getProperty(projectName,key)).booleanValue();
	}

	/**
	 * Get an integer associated to a key
	 *
	 * @param key the key of the string needed
	 */
	public int getPropertyInteger(String projectName, String key)
	{
		if(getProperty(projectName, key) != null)
		    return Integer.parseInt(getProperty(projectName, key));
	    return 0;
	}

	/**
	 * Get a double associated to a key
	 *
	 * @param key the key of the string needed
	 */
	public double getPropertyDouble(String projectName, String key)
	{
		return Double.parseDouble(getProperty(projectName,key));
	}
}
