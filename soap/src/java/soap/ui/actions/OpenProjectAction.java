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

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import soap.Context;
import soap.processing.LoadProject;
import soap.ui.SoapFrame;
import utils.ConfigManager;
import utils.ErrorManager;
import utils.ResourceManager;
import utils.SimpleFileFilter;
import utils.SmartChooser;
import utils.TaskMonitorDialog;


public class OpenProjectAction extends SoapAction
{
    private LoadProject mMonitor = null;
	private TaskMonitorDialog mTask = null;
	private File mFile = null;
	private ResourceManager mResource = ResourceManager.getInstance();
	private SimpleFileFilter filter = new SimpleFileFilter(ResourceManager.getInstance().getString("soapFileExtension"),
            ResourceManager.getInstance().getString("soapFileType"));
	
	public OpenProjectAction()
	{
	    super("fileOpen", "icons/OpenProject.gif", 'O', Event.CTRL_MASK);
	}
	
	public void actionPerformed(ActionEvent e)
	{
	    openProject();
	}
	
	private void openProject()
	{
	    try
		{
		    SmartChooser chooser = SmartChooser.getChooser();
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setFileFilter(filter);
			chooser.setDirectory(ConfigManager.getInstance().getProperty("WorkspaceDefaultPath"));
			
			
			if(chooser.showOpenDialog(((SoapFrame)context.getTopLevelFrame()).getContentPane())==JFileChooser.APPROVE_OPTION)
			{
				if(!filter.accept(chooser.getSelectedFile()) || chooser.getSelectedFile().isDirectory())
				{
					ErrorManager.getInstance().display("errorTitleOpenProcess", "errorWrongFileName");
					return;
				}
				mFile = chooser.getSelectedFile();
				mMonitor = new LoadProject(mFile);
				
				SoapFrame parent = (SoapFrame)Context.getInstance().getTopLevelFrame();				
				mTask = new TaskMonitorDialog(parent,mMonitor);
				mTask.setName("Loading");
				mTask.setLocation(parent.getWidth()/2-mTask.getWidth()/2,parent.getHeight()/2-mTask.getHeight()/2);	
				mMonitor.setTask(mTask);
				mTask.show();
			}
		}
		catch(Throwable t)
		{
			t.printStackTrace();
			ErrorManager.getInstance().display("errorTitleOpenProcess", "errorOpenProcess");
		}
	}
}
