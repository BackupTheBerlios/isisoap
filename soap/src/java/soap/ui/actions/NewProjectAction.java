/*
 * Created on 26 oct. 2004
 */
package soap.ui.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;

import soap.ui.dialog.NewProjectDialog;
import utils.ConnectionManager;

/**
 * @author yanagiba
 */
public class NewProjectAction extends SoapAction
{    
    public NewProjectAction()
	{
		super("fileNew", "icons/NewProject.gif", 'N', Event.CTRL_MASK);
	}

	public void actionPerformed(ActionEvent e)
	{   
	   newProject() ;
	}
	
	public void newProject()
	{
	    ConnectionManager connectManager = ConnectionManager.getInstance() ;
	    if (connectManager.getConnection() == ConnectionManager.CONNECTED) 
	    //if (((IdentificationAction)Context.getInstance().getAction("Identification")).identify() == 1)
	    {
	        NewProjectDialog newProjDiag = new NewProjectDialog("Nouveau projet") ;
	    }
	}
}
