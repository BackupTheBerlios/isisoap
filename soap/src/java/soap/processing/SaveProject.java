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


import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import JSX.ObjOut;

import utils.ErrorManager;


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
	    //Project project = Context.getInstance().getProject();
	    saveComponent();
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
			ErrorManager.getInstance().display("errorTitleSaveProcess", "errorOpenProcess");
		}
	}
	
	private void saveImages() throws IOException
	{
		
	}
	
	
	
	private void saveComponent() throws IOException
	{
	    ZipEntry entryZip = new ZipEntry("Component.xml");
		mZipFile.putNextEntry(entryZip);
		DataOutputStream data = new DataOutputStream( new BufferedOutputStream(mZipFile) );
		ObjOut out = new ObjOut( data );
		
		//vector to save
		//SoapListProcess listProcess = Context.getInstance().getListProjects() ;
		
		//for (int i = 0 ; i < listProcess.modelElementCount(); i++)
		//{
		    Vector  v = new Vector();
		    //SoapProcess process =(SoapProcess) listProcess.getModelElement(0); 
		    
		   // v.add(process) ;
		    //v.add(process.getPackageRole());
		    //out.writeObject(v);
		   // mZipFile.closeEntry();
		    /*process.getComponent().setParent( null );
			//add the component at index 0
			v.add(project.getProcess().getComponent());
			//add the map of the diagrams at index 1 (diagrams and SpemGraphAdapter)
			v.add(project.getDiagramMap());
			//add the SpemTreeAdapter at index 2 (use to save the colors)
			v.add(((SoapTreeAdapter)Context.getInstance().getTopLevelFrame().getTree().getModel()).getRoot());
			//add an extra element to know the current max id
			Activity a = new Activity();
			v.add(new Activity());
			
			out.writeObject(v);
			mZipFile.closeEntry();
			
			project.getProcess().getComponent().setParent( project.getProcess() );*/
		//}		
	}
}
