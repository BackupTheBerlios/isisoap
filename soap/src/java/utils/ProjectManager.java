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

package utils;

import java.util.HashMap;
import java.util.Properties;

public class ProjectManager
{
	private static ProjectManager mProjectManager = new ProjectManager();
	
	private HashMap mProperties = new HashMap();
	
	
	private ProjectManager()
	{
	}
	
	/**
	 * Return default properties of a project
	 */
	public Properties getDefaultProjectProperties()
	{
	    Properties properties = new Properties();

	    properties.setProperty("indicators.nbIndicators","4");
	    properties.setProperty("indicators.indicator1.name",
		        ResourceManager.getInstance().getString("advancement"));
		properties.setProperty("indicators.indicator1.minLimit","50");
		properties.setProperty("indicators.indicator1.maxLimit","100");
		properties.setProperty("indicators.indicator2.name",
		        ResourceManager.getInstance().getString("consumedBudget"));
		properties.setProperty("indicators.indicator2.minLimit","0");
		properties.setProperty("indicators.indicator2.maxLimit","70");
		properties.setProperty("indicators.indicator3.name",
		        ResourceManager.getInstance().getString("realizedFunctions"));
		properties.setProperty("indicators.indicator3.minLimit","60");
		properties.setProperty("indicators.indicator3.maxLimit","100");
		properties.setProperty("indicators.indicator4.name",
		        ResourceManager.getInstance().getString("remainedFunctions"));
		properties.setProperty("indicators.indicator4.minLimit","0");
		properties.setProperty("indicators.indicator4.maxLimit","40");
	   
		return properties;
	}
	/**
	 * Initialize properties of a project
	 */
	public void initProject(String projectName, Properties properties)
	{
			mProperties.put(projectName, properties);
	}


	/**  
	 * Retrieve the ProjectManager instance
	 * 
	 * @return ProjectManager the projectManager 
	 */
	public static ProjectManager getInstance()
	{
		return mProjectManager;
	}

	/**
	 * Retrieve the properties attached to a project
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
		((Properties)mProperties.get(projectName)).setProperty(key, value);
	}


	/**
	 * Get a string associated to a key in a project
	 *
	 * @param key the key of the string needed
	 */
	public String getProperty(String projectName, String key)
	{
	    if(mProperties.get(projectName) != null)
	    {
	        return ((Properties)mProperties.get(projectName)).getProperty(key);
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
		return Integer.parseInt(getProperty(projectName, key));
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

