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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import soap.Context;
import utils.IconManager;
import utils.ResourceManager;

public abstract class SoapAction extends AbstractAction
{
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
