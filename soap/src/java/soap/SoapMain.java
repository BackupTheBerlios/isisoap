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
import java.util.Locale;
import java.util.Properties;

import soap.ui.SoapFrame;
import soap.ui.actions.AboutAction;
import soap.ui.actions.CloseProjectAction;
import soap.ui.actions.GenerateStatsAction;
import soap.ui.actions.NewProjectAction;
import soap.ui.actions.OpenProjectAction;
import soap.ui.actions.PreferencesAction;
import soap.ui.actions.PrintStatsAction;
import soap.ui.actions.ProjectPropertiesAction;
import soap.ui.actions.QuitAction;
import soap.ui.actions.SaveAsProjectAction;
import soap.ui.actions.SaveProjectAction;
import soap.ui.dialog.ConfigurationDialog;
import utils.ConfigManager;
import utils.ResourceManager;


public class SoapMain 
{
    public static Properties createDefaultProperties()
	{
		ResourceManager resManager = ResourceManager.getInstance();
		
		Properties properties = new Properties();
		properties.setProperty("Project","Projet");
		properties.setProperty("WorkspaceDefaultPath",System.getProperty("user.home"));
		properties.setProperty("PackageRole","Roles");
		properties.setProperty("PackageWorkDefinition","Work definitions");
		properties.setProperty("PackageWorkProduct","Work products");
		properties.setProperty("PackageProcessRole","Process role");
		
		String language = Locale.getDefault().getLanguage() ;
		properties.setProperty("Language",language);
		properties.setProperty("LocalFile","resources/Soap_"+language) ;

		return properties;
	}

	public static void configurate()
	{
   		ConfigurationDialog cfgDialog = new ConfigurationDialog ("Configurationnnnn") ;
    	cfgDialog.setVisible(true);
	}
	public static void main(String[] args) 
	{
	    boolean configurate = ConfigManager.isConfigurate() ;
	  
		ConfigManager.init(createDefaultProperties());
	    Locale locale = new Locale(ConfigManager.getInstance().getProperty("Language"));
	    ResourceManager.setResourceFile(ConfigManager.getInstance().getProperty("LocalFile"),locale);
	    // initialize the context 
	    Context context = Context.getInstance();
	    // initialize the actions
		initActions(context);
		//JFrame f1 = new URLPassword();
		// initialize the main frame
	    SoapFrame f = new SoapFrame();
	    context.setTopLevelFrame(f);
	    ListProjects listProjects = ListProjects.getInstance() ;
	    context.setListProjects(listProjects) ;
	    //Context.getInstance().getListProjects().addProject(new Project ("Soap"));
		f.setVisible(false);
	    if (!configurate)
	    {
	        configurate() ;
	    }
	    f.setVisible(true);
	}
	public static void initActions(Context context)
	{
	    // file action
		context.registerAction("NewProject", new NewProjectAction());
		context.registerAction("OpenProject", new OpenProjectAction());
		context.registerAction("Close", new CloseProjectAction());
		context.registerAction("CloseNoHotKey", new CloseProjectAction(CloseProjectAction.NO_HOT_KEY));
		context.registerAction("SaveProject", new SaveProjectAction());
		context.registerAction("SaveAsProject", new SaveAsProjectAction());
		context.registerAction("Quit", new QuitAction());
		// project action
		context.registerAction("ProjectProperties",new ProjectPropertiesAction());	
		context.registerAction("Preferences",new PreferencesAction());	
		context.registerAction("About", new AboutAction());

		//Statistics
		context.registerAction("GenerateStatistics", new GenerateStatsAction());
		context.registerAction("PrintStatistics", new PrintStatsAction());
		//tree action
		//context.registerAction("TreeProjectProperties", new AddToModelAction("treeAddPackage", new SoapTreeNode(new SPackage(""), true)));
		
	}
}
