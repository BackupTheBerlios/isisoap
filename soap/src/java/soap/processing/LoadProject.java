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

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import soap.Context;
import soap.ListProjects;
import soap.model.executionProcess.structure.Project;
import utils.IndicatorManager;
import utils.MonitoredTaskBase;
import utils.ProjectManager;
import utils.ResourceManager;
import utils.TaskMonitorDialog;

/**
 * Monitor used to load a project
 *
 */
public class LoadProject extends MonitoredTaskBase 
{
	private File mFile = null;
	private TaskMonitorDialog mTask = null;
	private ResourceManager mResource = ResourceManager.getInstance();
	private ListProjects mListProjects = Context.getInstance().getListProjects();
	private Project mProject ;
	
	public LoadProject(File file)
	{
		mFile = file;
	}
	
	public void setTask( TaskMonitorDialog task )
	{
		mTask = task;
	}
	
	protected Object processingTask() 
	{
		launch();
		return null;
	}
	
	/**
	 * Start loading the project
	 *
	 */
	protected void launch()
	{
	    try
		{
			boolean hasComponent = true;
			
			ZipInputStream zipFile = new ZipInputStream( new FileInputStream(new File(mFile.getAbsolutePath())));
			
			if(loadProject( zipFile ) && loadProjectProperties(zipFile) && loadIndicatorsValues(zipFile))
			{
			    Context.getInstance().getListProjects().addProject(mProject);
			    print(mResource.getString("loadSuccess"));
			}
			else
			{
			    print(mResource.getString("loadFailed"));
			}
		}
		catch(Throwable t)
		{
			print(t.getMessage());
			print(mResource.getString("loadFailed"));
			t.printStackTrace();
		}
	}

	/**
	 * Load the project in the zip given in parameter.
	 * 
	 * @param projectZip the zip containing the Project.xml file
	 * @return true if successfull, false otherwise
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	protected boolean loadProject(ZipInputStream projectZip) throws IOException, ClassNotFoundException
	{
//	  print(mResource.getString("loadSearchProject"));
		
		DataInputStream data = findData("Project.save"); 	

		if( data != null )
		{
			print(mResource.getString("loadProject"));
			ObjectInputStream in = new ObjectInputStream(data);
			mProject = (Project)in.readObject();
			if(!Context.getInstance().getListProjects().isProjectOpened(mProject))
			{
				print(mResource.getString("loadProjectSuccess"));
				return true;
			}
			else
			{
			    print(mResource.getString("projectExisting"));
				projectZip.close();
				return false;
			}	
		}
		print(mResource.getString("loadProjectFailed"));
		projectZip.close();
		return false;
	}
	
	/**
	 * Load the project properties in the zip given in parameter.
	 * 
	 * @param projectZip the zip containing the .project file
	 * @return true if successfull, false otherwise
	 * @throws IOException
	 */
	protected boolean loadProjectProperties(ZipInputStream projectZip) throws IOException
	{
	    //print(mResource.getString("loadSearchProjectProperties"));
		
		DataInputStream data = findData(mProject.getName()+".project"); 	
		
		if( data != null )
		{
		    Properties properties = new Properties ();
		    print(mResource.getString("loadProjectProperties"));
		    properties.load(data);
		    ProjectManager.getInstance().initProject(mProject.getName(),properties);
		    print(mResource.getString("loadProjectPropertiesSuccess"));
		    return true;
		}
		print(mResource.getString("loadProjectPropertiesFailed"));
		projectZip.close();
		return false;
	}
	
	/**
	 * Load incators values in the zip given in parameter.
	 * 
	 * @param projectZip the zip containing the .project file
	 * @return true if successfull, false otherwise
	 * @throws IOException
	 */
	protected boolean loadIndicatorsValues(ZipInputStream projectZip) throws IOException
	{
	    //print(mResource.getString("loadSearchIndicatorsValues"));
		
		DataInputStream data = findData(mProject.getName()+".indicators"); 	
		
		if( data != null )
		{
		    Properties properties = new Properties ();
		    print(mResource.getString("loadIndicatorsValues"));
		    properties.load(data);
		    IndicatorManager.getInstance().setIndicatorsValues(mProject.getName(),properties);
		    print(mResource.getString("loadIndicatorsValuesSuccess"));
			projectZip.close();
		    return true;
		}
		print(mResource.getString("loadIndicatorsValuesFailed"));
		projectZip.close();
		return false;
	}
	/**
	 * Search and open the file given by fileName in projectZip.
	 * 
	 * @param projectZip the zip where you want to open a file
	 * @param fileName the file to open
	 * @return the DataInputStream containing the file
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private DataInputStream findData(String fileName) throws IOException
	{	
		ZipInputStream zipFile = new ZipInputStream( new FileInputStream(new File(mFile.getAbsolutePath())));
		
		ZipEntry zipEntry = zipFile.getNextEntry();
		
		while( zipEntry != null )
		{
		    DataInputStream data = new DataInputStream(zipFile);
			
			
			if( zipEntry.getName().equals(fileName) )
			{
				return data;
			}
			else
			{
				zipEntry = zipFile.getNextEntry();
			}
		}
		zipFile.close();
		return null;
	}
	
	/**
	 * Print a new message to the TaskMonitorDialog
	 * 
	 * @param msg
	 */
	protected void print( String msg )
	{
		setMessage(msg);
		if( mTask != null )
		{
			mTask.forceRefresh();
		}
	}
}
