/*
 * Created on 30 oct. 2004
 *
 */
package soap.ui;

import javax.swing.JToolBar;

import soap.Context;


/**
 * @author SCARAVETTI Florent
 */

public class SoapToolBar extends JToolBar
{
	public SoapToolBar()
	{
		super();

		Context context = Context.getInstance();

		setFloatable(false);

		add(context.getAction("NewProject"));
		add(context.getAction("OpenProject"));
		add(context.getAction("SaveProject"));
	}

}
