/*
 * APES is a Process Engineering Software
 * Copyright (C) 2003-2004 IPSquad
 * team@ipsquad.tuxfamily.org
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

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;

import soap.ListProjects;


import utils.MonitoredTaskBase;
import utils.ResourceManager;
import utils.TaskMonitorDialog;

import soap.Context;
import soap.adapters.SoapTreeAdapter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import JSX.ObjIn;



/**
 *
 * @version $Revision: 1.1 $
 */
public class LoadProject extends MonitoredTaskBase 
{
	private File mFile = null;
	private TaskMonitorDialog mTask = null;
	private ResourceManager mResource = ResourceManager.getInstance();
	//private Project mProject = new Project("","",null);
	private ListProjects mListProjects = Context.getInstance().getListProjects();
	
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
			loadComponent( zipFile ) ;
			/*if( !loadComponent( zipFile ) )
			{
				loadInterfaces(zipFile, mProject.getProcess());
				print(mResource.getString("loadRebuild"));
			}
			else
			{	
				print(mResource.getString("loadRebuild"));
				mProject.getProcess().buildInterfaces();
			}*/
			
			//Context.getInstance().setProject(mProject, mFile.getAbsolutePath());
			print(mResource.getString("loadSuccess"));
		}
		catch(Throwable t)
		{
			print(t.getMessage());
			print(mResource.getString("loadFailed"));
			t.printStackTrace();
		}
		//mProject = null;
	}

	/**
	 * Load the component in the process giving in parameter.
	 * 
	 * @param projectZip the zip containing the Component.xml file
	 * @param p the project where to store the diagrams adapters
	 * @param ap the process where to store the component
	 * @return true if successfull, false otherwise
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	protected boolean loadComponent(ZipInputStream projectZip) throws IOException, ClassNotFoundException
	{
	    print(mResource.getString("loadSearchComponent"));
		
		DataInputStream data = findData("Component.xml");
			
		SoapTreeAdapter adapter = (SoapTreeAdapter)Context.getInstance().getTopLevelFrame().getProjectTree().getModel(); 	

		if( data != null )
		{
			print(mResource.getString("loadComponent"));
			ObjIn in = new ObjIn(data);
			Vector v = (Vector)in.readObject();
			
			if( v.size() == 1 )
			{	
			    //System.out.println(v.elementAt(0).getClass()) ; 
			    //Project p = new Project( (SoapProcess) v.elementAt(0) ) ;
			    //Context.getInstance().getListProjects().addProject(p) ;
				/*adapter.setRoot((SoapTreeNode)v.get(2));
				mProject.setProcess((SoapProcess)((SoapTreeNode)adapter.getRoot()).getUserObject());
				mProject.getProcess().addModelElement((ProcessComponent)v.get(0));
				mProject.setDiagramMap((HashMap)v.get(1));
			
				Activity a = new Activity();
				int count = new Integer(a.getName().substring(6)).intValue();
				int nb = new Integer(((Activity)v.get(3)).getName().substring(6)).intValue();
				while( count++ < nb )
				{
					new Activity().resetName();
				}	*/
			
				projectZip.close();
				
				print(mResource.getString("loadComponentSuccess"));
				return true;
			}
		}

		print(mResource.getString("loadComponentFailed"));
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
			DataInputStream data = new DataInputStream( new BufferedInputStream(zipFile) );
			
			
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
	
	protected class InterfacesHandler extends DefaultHandler
	{
		private boolean mIsProvidedInterface = true;
		private Vector mProvidedProductNames = new Vector();
		private Vector mRequiredProductNames = new Vector();
		
		public InterfacesHandler()
		{
			super();
		}
		
		public Vector getProvidedProductNames()
		{
			return mProvidedProductNames;
		}
		
		public Vector getRequiredProductNames()
		{
			return mRequiredProductNames;
		}
		
		public void startElement (String uri, String localName,
								  String qName, Attributes attributes) throws SAXException
		{
			if(qName=="RequiredInterface")
			{	
				mIsProvidedInterface = false;
			}
			else if(qName=="WorkProduct")
			{
				if( mIsProvidedInterface )
				{	
					mProvidedProductNames.add(attributes.getValue(0));
				}
				else
				{
					mRequiredProductNames.add(attributes.getValue(0));
				}
			}
		}	
	}
}
