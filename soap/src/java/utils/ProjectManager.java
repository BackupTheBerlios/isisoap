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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class ProjectManager
{
	public static final int PROJECT = 1;
	public static final int ITERATION = 2;
	public static final int TASK = 4;
	
	// indicators
	public static final String ADVANCEMENT = "indicator1";
	public static final String CONSUMED_BUDGET = "indicator2";
	public static final String REALIZED_FUNCTIONS = "indicator3";
	public static final String REMAINED_FUNCTIONS = "indicator4";
	
	// indicators properties
	public static final int NAME = 0;
	public static final int MINLIMIT = 1;
	public static final int MAXLIMIT = 2;
	public static final int CONCERNS = 3;
	public static final int TYPE = 4;
	public static final int UNIT = 5;
    
	// indicators type
	public static final String INTEGER_TYPE = "Integer";
	public static final String FLOAT_TYPE = "Float";
	public static final String PERCENT_TYPE = "Percentage";
	
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
		properties.setProperty("indicators.indicator1.concerns",String.valueOf(PROJECT|ITERATION|TASK));
		properties.setProperty("indicators.indicator1.type","Percentage");
		properties.setProperty("indicators.indicator1.unit","%");
		properties.setProperty("indicators.indicator2.name",
		        ResourceManager.getInstance().getString("consumedBudget"));
		properties.setProperty("indicators.indicator2.minLimit","0");
		properties.setProperty("indicators.indicator2.maxLimit","70");
		properties.setProperty("indicators.indicator2.concerns",String.valueOf(PROJECT|ITERATION|TASK));
		properties.setProperty("indicators.indicator2.type","Percentage");
		properties.setProperty("indicators.indicator2.unit","%");
		properties.setProperty("indicators.indicator3.name",
		        ResourceManager.getInstance().getString("realizedFunctions"));
		properties.setProperty("indicators.indicator3.minLimit","60");
		properties.setProperty("indicators.indicator3.maxLimit","100");
		properties.setProperty("indicators.indicator3.concerns",String.valueOf(PROJECT|ITERATION));
		properties.setProperty("indicators.indicator3.type","Percentage");
		properties.setProperty("indicators.indicator3.unit","%");
		properties.setProperty("indicators.indicator4.name",
		        ResourceManager.getInstance().getString("remainedFunctions"));
		properties.setProperty("indicators.indicator4.minLimit","0");
		properties.setProperty("indicators.indicator4.maxLimit","40");
		properties.setProperty("indicators.indicator4.concerns",String.valueOf(PROJECT|ITERATION));
		properties.setProperty("indicators.indicator4.type","Percentage");
		properties.setProperty("indicators.indicator4.unit","%");
	   
		return properties;
	}
	
	public HashMap getIndicatorsName(String projectName, int concerns)
	{
	    Enumeration enumeration = getProperties(projectName).keys();
	    HashMap result = new HashMap();
        while (enumeration.hasMoreElements())
        {
            String key = (String)enumeration.nextElement();
            if(key.endsWith(".concerns") && ((getPropertyInteger(projectName,key) & concerns) != 0))
            {
                result.put(key.substring("indicators.".length(),key.length()-".concerns".length()),
                	getProperty(projectName,key.substring(0,key.length()-".concerns".length())+".name"));
            }
        }
        return result;
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
	    if(getProperties(projectName) != null)
		{
		    getProperties(projectName).setProperty(key, value);
		}
		else
		{
		    Properties properties = new Properties();
		    properties.setProperty(key, value);
		    initProject(projectName,properties);
		}
	}

	public String getIndicatorProperty(String projectName, String indicatorNum, int key)
	{
	    switch(key)
	    {
	        case NAME : 
	            return getProperty(projectName,"indicators."+indicatorNum+".name");
	        case MINLIMIT :
	            return getProperty(projectName,"indicators."+indicatorNum+".minLimit");
	        case MAXLIMIT :
	            return getProperty(projectName,"indicators."+indicatorNum+".maxLimit");
	        case CONCERNS :
	            return getProperty(projectName,"indicators."+indicatorNum+".concerns");
	        case TYPE :
	            return getProperty(projectName,"indicators."+indicatorNum+".type");
	        case UNIT : 
	            return getProperty(projectName,"indicators."+indicatorNum+".unit");
	    }
	    return null;
	}
	
	public int getIndicatorPropertyInteger(String projectName, String indicatorNum, int key)
	{
	    switch(key)
	    {
	        case MINLIMIT :
	            return getPropertyInteger(projectName,"indicators."+indicatorNum+".minLimit");
	        case MAXLIMIT :
	            return getPropertyInteger(projectName,"indicators."+indicatorNum+".maxLimit");
	        case CONCERNS :
	            return getPropertyInteger(projectName,"indicators."+indicatorNum+".concerns");
	    }
	    return -1;
	}
	
	public void setIndicatorProperty(String projectName, String indicatorNum, int key, String value)
	{
	    switch(key)
	    {
	        case NAME : 
	            setProperty(projectName,"indicators."+indicatorNum+".name",value);
	            break;
	        case MINLIMIT :
	            setProperty(projectName,"indicators."+indicatorNum+".minLimit",value);
	            break;
	        case MAXLIMIT :
	            setProperty(projectName,"indicators."+indicatorNum+".maxLimit",value);
	            break;
	        case CONCERNS :
	            setProperty(projectName,"indicators."+indicatorNum+".concerns",value);
	            break;
	        case TYPE :
	            setProperty(projectName,"indicators."+indicatorNum+".type",value);
	            break;
	        case UNIT : 
	            setProperty(projectName,"indicators."+indicatorNum+".unit",value);
	    }
	}
	
	public void setIndicatorPropertyInteger(String projectName, String indicatorNum, int key, int value)
	{
	    switch(key)
	    {
	        case MINLIMIT :
	            setPropertyInteger(projectName,"indicators."+indicatorNum+".minLimit",value);
	            break;
	        case MAXLIMIT :
	            setPropertyInteger(projectName,"indicators."+indicatorNum+".maxLimit",value);
	            break;
	        case CONCERNS :
	            setPropertyInteger(projectName,"indicators."+indicatorNum+".concerns",value);
	            break;
	    }
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
	 * Set an integer associated to a key
	 *
	 * @param key the key of the string needed
	 */
	public void setPropertyInteger(String projectName, String key, int value)
	{
		setProperty(projectName, key, String.valueOf(value));
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

