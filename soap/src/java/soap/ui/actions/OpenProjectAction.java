/*
 * Created on 26 oct. 2004
 */
package soap.ui.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import utils.SimpleFileFilter;

import soap.Context;
import soap.processing.LoadProject;
import soap.ui.SoapFrame;
import utils.ConfigManager;
import utils.ErrorManager;
import utils.ResourceManager;
import utils.SmartChooser;
import utils.TaskMonitorDialog;

//import soap.apes.processing.LoadProject;
//import soap.apes.ui.ApesFrame;



/**
 * @author yanagiba
 */
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
