/*
 * Created on 27 oct. 2004
 */
package soap.ui.actions;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import soap.Context;
import utils.IconManager;
import utils.ResourceManager;

/**
 * @author yanagiba
 */
public abstract class SoapAction extends AbstractAction
{
//  / The context of the application is accessible here
	protected Context context = Context.getInstance();
	private String mName;
	
	public void refresh()
	{
		putValue(Action.SHORT_DESCRIPTION, ResourceManager.getInstance().getString(mName));
	}
	/**
	 * @param label the resource key for the action label
	 * @param icon the filename for the action icon
	 */
	public SoapAction(String label, String icon)
	{
		super(ResourceManager.getInstance().getString(label),
		     IconManager.getInstance().getIcon(icon)
			);
		mName = label;
		putValue(Action.SHORT_DESCRIPTION, ResourceManager.getInstance().getString(label));
	}

	/**
	 * @param label the resource key for the action label
	 * @param icon the filename for the action icon
	 * @param key a key associated to the action
	 * @param eventMask a mask for the shortcut of this action
	 */
	public SoapAction(String label, String icon, char key, int eventMask)
	{
		this(label, icon);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(key, eventMask));
	}
	
	/**
	 * @param label the resource key for the action label
	 * @param icon the filename for the action icon
	 * @param key a key associated to the action
	 * @param eventMask a mask for the shortcut of this action
	 */
	public SoapAction(String label, String icon, int key, int eventMask)
	{
		this(label, icon);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(key, eventMask));
	}

	/**
	 * @param label the resource key for the action label
	 * @param icon the filename for the action icon
	 * @param key a key associated to the action
	 * @param eventMask a mask for the shortcut of this action
	 */
	public SoapAction(String label, Icon icon, int key, int eventMask)
	{
		super(label, icon);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(key, eventMask));
		putValue(Action.SHORT_DESCRIPTION, ResourceManager.getInstance().getString(label));
	}
}
