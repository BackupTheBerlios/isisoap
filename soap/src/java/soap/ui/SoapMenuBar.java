/*
 * Created on 27 oct. 2004
 */
package soap.ui;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import soap.Context;
import utils.ResourceManager;


/**
 * @author yanagiba
 */
public class SoapMenuBar extends JMenuBar
{
    private int mNextInsert=0;
    
    public SoapMenuBar()
    {
        Context context = Context.getInstance();
        // menu file
		JMenu menu = getMenu(ResourceManager.getInstance().getString("menuFile"));
		menu.add(context.getAction("NewProject"));
		menu.add(context.getAction("OpenProject"));
		menu.addSeparator();
		menu.add(context.getAction("SaveProject"));
		menu.add(context.getAction("SaveAsProject"));
		menu.addSeparator();
		menu.add(context.getAction("Close"));
		menu.addSeparator();
		menu.add(context.getAction("Quit"));
		// menu project
		menu = getMenu(ResourceManager.getInstance().getString("menuProject"));
		menu.add(context.getAction("ProjectProperties"));
		// menu statistics
		menu = getMenu(ResourceManager.getInstance().getString("menuStatistic"));
		menu.add(context.getAction("GenerateStatistics"));
		menu.add(context.getAction("PrintStatistics"));
		menu = getMenu("?");
		menu.add(context.getAction("About"));
		/*menu.add(context.getAction("Print"));
		menu.addSeparator();*/
    }
    
    /**
	 * Retrieve (or create) a first level menu of the menubar
	 * It automatically find a menmonic for this menu
	 * The "?" menu is automatically rigth justified
	 */
	public JMenu getMenu(String menuTitle)
	{
		JMenu menu = null;
		int index = 0;

		for(int n=0; n<getMenuCount(); n++)
		{
			// Append the option to the end of the Options menu
			menu = this.getMenu(n);
			if(menu != null)
			{
				// Find a mnemonic that will fit
				while(index < menuTitle.length() && menu.getMnemonic() == menuTitle.charAt(index))
				{
					index++;
				}

				if(menu.getText().compareTo(menuTitle) == 0)
				{
					return menu;
				}
			}
		}
		//		 Create the new menu and set the mnemonic
		menu = new SoapMenu(menuTitle);
		if(index < menuTitle.length())
		{
			menu.setMnemonic(menuTitle.charAt(index));
		}

		int insert_at = this.getMenuCount() - mNextInsert;
		super.add(menu, insert_at);

		// Insert the glue, update the insert point
		if(menuTitle.toLowerCase().equals("?"))
		{
			super.add(Box.createHorizontalGlue(), insert_at);
			mNextInsert -= 2;
		}

		return menu;
	}
	
	protected class SoapMenu extends JMenu
	{

		public SoapMenu(String title)
		{
			super(title);
		}

		/**
		 * Add an action and set the accelerator value for the new menu entry
		 */
		public JMenuItem add(Action action)
		{
			JMenuItem item = new JMenuItem(action);
			item.setAccelerator((KeyStroke)action.getValue(Action.ACCELERATOR_KEY));

			return super.add(item);
		}

	}
}
