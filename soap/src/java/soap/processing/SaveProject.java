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


import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import soap.Context;
import soap.model.executionProcess.structure.Project;
import utils.ErrorManager;
import utils.IndicatorManager;
import utils.ProjectManager;
import JSX.ObjOut;

/**
 * Monitor used to save a project
 *
 */
public class SaveProject
{
	private final static String IMAGE_PATH="images";
	
	private ZipOutputStream mZipFile;
	
	public SaveProject(ZipOutputStream zipFile)
	{
		mZipFile = zipFile;
	}
	
	public void save() throws IOException
	{
	    saveProject();
	    saveProjectProperties();
	    saveIndicatorsValues();
	    mZipFile.close();
	}
	
	private static String normalizeName(String name)
	{
		name=name.replace(' ','_');
		name=name.replace('\'','_');
		name=name.replace(':','_');
		name=name.replace('\\','_');
		name=name.replace('/','_');
		name=name.replace('*','_');
		name=name.replace('?','_');
		name=name.replace('"','_');
		name=name.replace('<','_');
		name=name.replace('>','_');
		name=name.replace('|','_');
		name=name.replace('é','e');
		name=name.replace('è','e');
		name=name.replace('ù','u');
		name=name.replace('ç','c');
		name=name.replace('à','a');
		name=name.replace('ô','o');

		return name;
	}
	
	public void createImagesDirectory()
	{
		String entryDir = IMAGE_PATH + System.getProperty("file.separator");
		ZipEntry entryZip = new ZipEntry(entryDir);

		try
		{
			mZipFile.putNextEntry(entryZip);
			mZipFile.closeEntry();
		}
		catch(Throwable t)
		{
			t.printStackTrace();
			ErrorManager.getInstance().display("errorTitleSaveProject", "errorSaveProject");
		}
	}
	
	private void saveImages() throws IOException
	{
		
	}
	
	private void saveProject() throws IOException
	{
	    ZipEntry entryZip = new ZipEntry("Project.xml");
		mZipFile.putNextEntry(entryZip);
		DataOutputStream data = new DataOutputStream( new BufferedOutputStream(mZipFile) );
		ObjOut out = new ObjOut( data );
		
		//project to save
		Project currentProject = Context.getInstance().getListProjects().getCurrentProject();
		    
		out.writeObject(currentProject);
		mZipFile.closeEntry();	
	}
	
	private void saveProjectProperties() throws IOException
	{
		Project currentProject = Context.getInstance().getListProjects().getCurrentProject();
		
	    ZipEntry entryZip = new ZipEntry(currentProject.getName()+".project");
		mZipFile.putNextEntry(entryZip);
		BufferedOutputStream data =  new BufferedOutputStream(mZipFile );
		
		ProjectManager.getInstance().getProperties(currentProject.getName()).store(data,currentProject.getName());

		mZipFile.closeEntry();	
	}
	
	private void saveIndicatorsValues() throws IOException
	{
	    Project currentProject = Context.getInstance().getListProjects().getCurrentProject();
		
	    ZipEntry entryZip = new ZipEntry(currentProject.getName()+".indicators");
		mZipFile.putNextEntry(entryZip);
		BufferedOutputStream data =  new BufferedOutputStream(mZipFile );
		if(IndicatorManager.getInstance().getProperties(currentProject.getName()) != null)
		    IndicatorManager.getInstance().getProperties(currentProject.getName()).store(data,currentProject.getName());

		mZipFile.closeEntry();	
	}
}
