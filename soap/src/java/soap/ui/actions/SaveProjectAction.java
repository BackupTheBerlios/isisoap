/*
 * Created on 26 oct. 2004
 */
package soap.ui.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;


/**
 * @author yanagiba
 */
public class SaveProjectAction extends ProjectManagementAction
{
	/**
	 * 
	 */	
	public SaveProjectAction()
	{
		super("fileSave", "icons/SaveProject.gif", 'S', Event.CTRL_MASK);
	}
	
	public void actionPerformed(ActionEvent e)
	{
	    saveProject();
	}
	
}
