/*
 * SOAP Supervising, Observing, Analysing Projects
 * Copyright (C) 2003-2004 SoapTeam
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
package soap.adapters;

import java.util.Enumeration;
import java.util.Properties;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utils.ProjectManager;


public class SoapListDefaultsIndicatorsAdapter extends AbstractListModel
{
    private Properties mDefaultProperties ;
    private Properties mChosenProperties;
    private int mNbChosenIndicators;
    
    public SoapListDefaultsIndicatorsAdapter()
    {
        mDefaultProperties = ProjectManager.getInstance().getDefaultProjectProperties();
        mChosenProperties = new Properties();
        mNbChosenIndicators = 0 ;
    }
    
    public int getSize()
    {
        return Integer.parseInt(mDefaultProperties.getProperty("indicators.nbIndicators"));
    }

    public Object getElementAt(int ligne)
    {
        return mDefaultProperties.getProperty("indicators.indicator"+(ligne+1)+".name");
    }
    
    public void saveDefaultIndicators(String projectName)
    {
        mChosenProperties.setProperty("indicators.nbIndicators", String.valueOf(mNbChosenIndicators));
        ProjectManager.getInstance().initProject(projectName,mChosenProperties);
    }
    
    public class IndicatorsListSelectionListener implements ListSelectionListener
    {
        
        public void valueChanged(ListSelectionEvent event)
        {
            int[] selectedIndices = ((JList)event.getSource()).getSelectedIndices();
            mNbChosenIndicators = selectedIndices.length;
            mChosenProperties.clear();
            for(int i = 0 ; i < mNbChosenIndicators ; i++)
            {
                Enumeration enumeration = mDefaultProperties.keys();
                while(enumeration.hasMoreElements())
                {
                    String element = (String)enumeration.nextElement();
                    if(element.startsWith("indicators.indicator"+(selectedIndices[i]+1)))
                    {
                            mChosenProperties.setProperty(element,mDefaultProperties.getProperty(element));  
                    }
                }
            }
        }
    }
}
