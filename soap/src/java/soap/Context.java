/*
 * Created on 31 oct. 2004
 */
package soap;

import java.util.HashMap;

import javax.swing.Action;

import soap.MainFrameInterface;
import soap.adapters.SoapTreeNode;
import soap.adapters.SoapTreeAdapter;
import soap.model.frontend.SoapMediator;

/**
 * @author yanagiba
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
	 * Set the current project the program
	 *
	 * It automatically close every internal frame and set the new project root
	 *
	 * @param project the new project to edit
	 * @param filePath the file path or null if there is no file associated to this project
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
	 * Retrieve the projet currently opened in the program
	 *
	 * @return the project
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
