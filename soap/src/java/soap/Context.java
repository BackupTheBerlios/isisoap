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

package soap;

import java.util.HashMap;

import javax.swing.Action;

import soap.adapters.SoapTreeAdapter;
import soap.adapters.SoapTreeNode;
import soap.model.frontend.SoapMediator;

/**
 * Application Context
 *
 * This class centralize the context of the running application.
 * It is implemented as a singleton.
 *
 */ 

public class Context
{
    private static final Context mInstance = new Context();
	private MainFrameInterface mTopLevel;
	private HashMap mActionMap = new HashMap();
	private ListProjects mListProjects ;
	private Context() {};
	
	/**
	 * Allows to retrieve the Context instance
	 * @return the unique instance of Context
	 */
	public static Context getInstance()
	{
		return mInstance;
	}
	
	/**
	 * Set the list of the current open projects 
	 *
	 * @param listProjects the list which contains all project 
	 */
	public void setListProjects(ListProjects listProjects)
	{
	    SoapMediator.getInstance().clearAll();
	    SoapTreeAdapter adapter = (SoapTreeAdapter)mTopLevel.getProjectTree().getModel();
	    listProjects.setListeners() ;
	    if( ((SoapTreeNode)adapter.getRoot()).getUserObject() != listProjects.getListSoapProjects() )
		{
			adapter.setRoot(listProjects.getListSoapProjects());
		}
	   this.mListProjects = listProjects;
	}

	
	
	/**
	 * Retrieve the list of the current open projects 
	 *
	 * @return the list of the prjects
	 */
	public ListProjects getListProjects()
	{
		return ListProjects.getInstance();
		
	}


	/**
	 * Retrieve the top level frame of the GUI
	 * @return the main frame
	 */
	public MainFrameInterface getTopLevelFrame()
	{
		return mTopLevel;
	}
	
	/**
	 * Set the top level frame of the GUI
	 * @param topLevel
	 */
	public void setTopLevelFrame(MainFrameInterface topLevel)
	{
		mTopLevel = topLevel;
	}
	
	/**
	 * Register an action in the context
	 * When an action is registered it can be accessed everywhere thanks to getAction
	 *
	 * @param key the identifier of the action in the context
	 * @param action the action itself
	 */
	public void registerAction(String key, Action action)
	{
		mActionMap.put(key, action);
	}
	
	/**
	 * Retrieve a previously registered action from the context
	 * Useful to enable/disable an action in the whole application
	 *
	 * @param key the identifier of the action you need
	 * @return the action or null if there's nothing registered with the given key
	 */
	public Action getAction(String key)
	{
		return (Action)mActionMap.get(key);
	}
	
	/**
	 * Remove a previously registered action
	 * @param key the identifier of the action to remove
	 * @return the removed action or null
	 */
	public Action removeAction(String key)
	{
		if(mActionMap.containsKey(key))
		{
			return (Action)mActionMap.remove(key);
		}
		return null;
	}
}
