

package utils;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Helper class for application localization.
 * It is implemented as a singleton.
 *
 * @version $Revision: 1.1 $
 */
public class ResourceManager
{
	private static ResourceManager msResourceManager = new ResourceManager();
	private static ResourceBundle msResources = null;

	private ResourceManager() { }
  
	/**
	 * Retrieve the unique instance of ResourceManager
	 *
	 * @return the instance
	 */
	public static ResourceManager getInstance()
	{
		return msResourceManager;
	}

	/**
	 * Set the resource file used in the application
	 *
	 * @param resourceFile the file name minus ".properties"
	 */
	public static void setResourceFile(String resourceFile, Locale locale)
	{
		msResources = ResourceBundle.getBundle(resourceFile, locale);
	}

	/**
	 * Get a string associated to a key
	 *
	 * @param key the key of the string needed
	 */
	public String getString(String key)
	{
		if(msResources!=null)
		{
			return  msResources.getString(key);
		}
		else
		{
			System.err.println("ResourceManager not intialized...");
			return "";
		}
	}
}
