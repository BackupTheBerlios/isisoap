/*
 * Created on 26 oct. 2004
 */
package soap;
import java.util.Locale;
import java.util.Properties;

import soap.Context;
import soap.model.executionProcess.structure.Project;
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

import utils.ConfigManager;
import utils.ResourceManager;

import soap.ui.SoapFrame;

/**
 * @author yanagiba
 */
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
	public static void main(String[] args) 
	{
	    
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
	    Context.getInstance().getListProjects().addProject(new Project ("Soap"));
		f.show();
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
