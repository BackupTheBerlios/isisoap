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

package soap.ui.actions;


import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

import soap.Context;
import soap.processing.SaveProject;
import soap.ui.SoapFrame;
import utils.ErrorManager;
import utils.ProjectManager;
import utils.ResourceManager;
import utils.SimpleFileFilter;
import utils.SmartChooser;


public abstract class ProjectManagementAction extends SoapAction
{
	protected SimpleFileFilter filter = new SimpleFileFilter(ResourceManager.getInstance().getString("soapFileExtension"),
	                                                         ResourceManager.getInstance().getString("soapFileType"));


	/**
	 * @param label the resource key for the action label
	 * @param icon the filename for the action icon
	 */
	public ProjectManagementAction(String label, String icon)
	{
		super(label, icon);
	}

	/**
	 * @param label the resource key for the action label
	 * @param icon the filename for the action icon
	 * @param key a key associated to the action
	 * @param eventMask a mask for the shortcut of this action
	 */
	public ProjectManagementAction(String label, String icon, char key, int eventMask)
	{
		super(label, icon, key, eventMask);
	}
	
	/**
	 * @param label the resource key for the action label
	 * @param icon the filename for the action icon
	 * @param key a key associated to the action
	 * @param eventMask a mask for the shortcut of this action
	 */
	public ProjectManagementAction(String label, String icon, int key, int eventMask)
	{
		super(label, icon, key, eventMask);
	}


	/**
	 * Save the current
	 *
	 * @return true if the file has been saved
	 */
	protected boolean saveProject()
	{
		String projectName = Context.getInstance().getListProjects().getCurrentProject().getName();
	    if(ProjectManager.getInstance().getProperty(projectName,"projectPath") == null)
		{
			return saveProjectAs();
		}
		else
		{
			return saveProject(ProjectManager.getInstance().getProperty(projectName,"projectPath"));
		}
	}	

	/**
	 * Save the current project in the specified file
	 *
	 * @param filePath the path to save the project
	 * @return true if the file has been saved
	 */
	protected boolean saveProject(String filePath)
	{
		try
		{
			FileOutputStream outstream = new FileOutputStream( new File( filePath ) );
			ZipOutputStream zipFile = new ZipOutputStream( outstream );

			SaveProject sp = new SaveProject(zipFile);
			sp.save();
			
			zipFile.close();

			//context.setFilePath(filePath);
		}
		catch(Throwable t)
		{
			t.printStackTrace();
			ErrorManager.getInstance().display("errorTitleSaveProject", "errorSaveProject");
			return false;
		}
		return true;
	}

	/**
	 * Save the current project under a new name
	 *
	 * @return true if the file has been saved
	 */
	protected boolean saveProjectAs()
	{
		SmartChooser chooser = SmartChooser.getChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileFilter(filter);
		String projectName = Context.getInstance().getListProjects().getCurrentProject().getName();
		chooser.setDirectory(ProjectManager.getInstance().getProperty(projectName,"defaultProjectDirectoryPath"));
		
		if(chooser.showSaveDialog(((SoapFrame)context.getTopLevelFrame()).getContentPane())==SmartChooser.APPROVE_OPTION)
		{
			if(chooser.getSelectedFile().isDirectory())
			{
				ErrorManager.getInstance().display("errorTitleSaveProject", "errorWrongFileName");
				return false;
			}

			String selected_file=chooser.getSelectedFile().getAbsolutePath();
			int last_separator= selected_file.lastIndexOf( File.separatorChar );
			int last_point=0;
			if(last_separator!=-1)
				last_point=selected_file.indexOf( '.', last_separator);
			else last_point=selected_file.lastIndexOf( '.');
			File f=null;
			if(last_point==-1)
				f=new File(selected_file +"."+ ResourceManager.getInstance().getString( "soapFileExtension" ));
			else
			{
				String suffixe=selected_file.substring(last_point,selected_file.length());
				if( suffixe.compareTo( "."+ResourceManager.getInstance().getString( "soapFileExtension" ) ) !=0 )
					f=new File( selected_file +"."+ ResourceManager.getInstance().getString( "soapFileExtension" ) );
				else f=new File(selected_file);
			}

			if(!filter.accept(f))
			{
				ErrorManager.getInstance().display("errorTitleSaveProject", "errorWrongFileName");
				return false;
			}
			
			if(f.exists())
			{
				int choice=JOptionPane.showInternalConfirmDialog(((SoapFrame)Context.getInstance().getTopLevelFrame()).getContentPane(),
				                                                 ResourceManager.getInstance().getString("msgSaveFileAlreadyExists"),
				                                                 ResourceManager.getInstance().getString("msgTitleSaveConfirm"),
				                                                 JOptionPane.YES_NO_OPTION,
										 JOptionPane.QUESTION_MESSAGE);
				
				if(choice!=JOptionPane.YES_OPTION) return false;
			}
			
			ProjectManager.getInstance().setProperty(projectName,"projectPath",f.getAbsolutePath());
			
			return saveProject(f.getAbsolutePath());
		}
		return false;
	}
}
