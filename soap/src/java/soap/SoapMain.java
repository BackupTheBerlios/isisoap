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

import javax.swing.JPanel;

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
import soap.ui.dialog.SeveralStepsDialog;
import soap.ui.panel.SoapTextPanel;
import soap.ui.panel.configuration.ServerInformationPanel;
import soap.ui.panel.configuration.UserInformationPanel;
import utils.ConfigManager;
import utils.ResourceManager;

/**
 * Main class of the project
 *
 *
 */


public class SoapMain 
{
    /**
     * Create the default properties when the application is lauched for the first time
     *
     */
    
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

    /**
     * Allow the user to configurate the application it is lauched for the first time
     *
     *
     */
    
	public static void configurate()
	{
   		SeveralStepsDialog cfgDialog = new SeveralStepsDialog ("Configuration","Configuration du logiciel SOAP","icons/logoConfig.gif") ;
   		SoapTextPanel sp = new SoapTextPanel() ;
        sp.setText("Cet assistant va vous permettre de configurer SOAP.\n" +
        		"Ceci ne va vous prendre que quelques minutes.\n\n" +
        		"Bonne utilisation du logiciel !!!") ;
        cfgDialog.addCenterPanel(sp);
        
        JPanel panel = new UserInformationPanel(cfgDialog) ;
        cfgDialog.addCenterPanel(panel);
        
        panel = new ServerInformationPanel(cfgDialog);
        cfgDialog.addCenterPanel(panel);
   		cfgDialog.setVisible(true);
	}
	
	/**
     * main
     *
     */

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
		// initialize the main frame
	    SoapFrame f = new SoapFrame();
	    context.setTopLevelFrame(f);
	    ListProjects listProjects = ListProjects.getInstance() ;
	    context.setListProjects(listProjects) ;
		f.setVisible(false);
	    if (!configurate)
	    {
	        // lauch the configuration wizard if the software is not configurate
	        configurate() ;
	    }
	    if(ConfigManager.isConfigurate())
	    {
	        f.setVisible(true);
	    }
	    else
	    {
	        // close the application when the user exit the configuration wizard 
	        System.exit(0);
	    }
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
