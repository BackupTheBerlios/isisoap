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
package soap.ui.stats;

import org.jfree.data.category.DefaultCategoryDataset;

import soap.Context;
import soap.model.core.EstimationElement;
import soap.model.core.ModelElement;
import soap.model.executionProcess.structure.Iteration;
import soap.model.executionProcess.structure.Project;
import soap.model.modelmanagement.IPackage;
import utils.IndicatorManager;
import utils.ProjectManager;
import utils.ResourceManager;


public class DataSetFactory
{
    public static DefaultCategoryDataset createDatasetEstimatedBudget(IPackage pack) 
	{
	    if(!(pack instanceof EstimationElement))
	        return null;
	    EstimationElement parent = (EstimationElement)pack;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if(parent.getAttribute(EstimationElement.ESTIMATED_HOURS) == null)
            return null;
	    int parentHours = ((Integer)parent.getAttribute(EstimationElement.ESTIMATED_HOURS)).intValue();
	    if(pack instanceof Iteration)
	    {
	        if(pack.getModelElement(0) != null)
	            pack = (IPackage)pack.getModelElement(0);
	        else
	            return null;
	    }
        for(int i = 0 ; i < pack.modelElementCount() ; i++)
        {
            EstimationElement elt = ((EstimationElement)pack.getModelElement(i)); 
            if(elt.getAttribute(EstimationElement.ESTIMATED_HOURS) == null)
                return null;
            int eltHours = ((Integer)elt.getAttribute(EstimationElement.ESTIMATED_HOURS)).intValue();
            dataset.addValue(eltHours*100/parentHours,ResourceManager.getInstance().getString("statsEstimatedBudget"),elt.getName());
        }
        
        return dataset;
    }

    public static DefaultCategoryDataset createDatasetConsumedBudget(IPackage pack) {

        if(!(pack instanceof EstimationElement))
	        return null;
        EstimationElement parent = (EstimationElement)pack;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if(parent.getAttribute(EstimationElement.ESTIMATED_HOURS) == null)
            return null;
	    int parentHours = ((Integer)parent.getAttribute(EstimationElement.ESTIMATED_HOURS)).intValue();
	    if(pack instanceof Iteration)
	    {
	        if(pack.getModelElement(0) != null)
	            pack = (IPackage)pack.getModelElement(0);
	        else
	            return null;
	    }
        for(int i = 0 ; i < pack.modelElementCount() ; i++)
        {
            EstimationElement elt = ((EstimationElement)pack.getModelElement(i)); 
            if(elt.getAttribute(EstimationElement.ELAPSED_HOURS) == null)
                return null;
            int eltHours = ((Integer)elt.getAttribute(EstimationElement.ELAPSED_HOURS)).intValue();
            dataset.addValue(eltHours*100/parentHours,ResourceManager.getInstance().getString("consumedBudget"),elt.getName());
        }
        return dataset;
    }
    
    public static DefaultCategoryDataset createDatasetAdvancement(IPackage pack)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    String projectName = Context.getInstance().getListProjects().getCurrentProject().getName();
	    if(pack instanceof Iteration)
	    {
	        if(pack.getModelElement(0) != null)
	            pack = (IPackage)pack.getModelElement(0);
	        else
	            return null;
	    }
        for(int i = 0 ; i < pack.modelElementCount() ; i++)
        {
            EstimationElement elt = ((EstimationElement)pack.getModelElement(i)); 
            if(IndicatorManager.getInstance().getProperty(projectName,ProjectManager.ADVANCEMENT+"_"+elt.getID()) == null)
                return null;
            int advancement = IndicatorManager.getInstance().getPropertyInteger(projectName,ProjectManager.ADVANCEMENT+"_"+elt.getID());
            dataset.addValue(advancement, ResourceManager.getInstance().getString("advancement"),elt.getName());
        }
        return dataset;
    }
    public static DefaultCategoryDataset createDatasetEstimatedFunctions(IPackage pack)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (((ModelElement)pack).getAttribute(Project.ESTIMATED_FUNCTIONALITIES) == null)
        	return null ;
	    int parentEstimatedFunctions = ((Integer)((ModelElement)pack).getAttribute(Project.ESTIMATED_FUNCTIONALITIES)).intValue(); 
       
	    for(int i = 0 ; i < pack.modelElementCount() ; i++)
        {
            ModelElement elt = ((ModelElement)pack.getModelElement(i)); 
            if(elt.getAttribute(Project.ESTIMATED_FUNCTIONALITIES) == null)
                return null;
            int estimatedFunctions = ((Integer)elt.getAttribute(Project.ESTIMATED_FUNCTIONALITIES)).intValue();
            dataset.addValue(estimatedFunctions*100/parentEstimatedFunctions, ResourceManager.getInstance().getString("statsEstimatedFunctions"),elt.getName());
        }
        return dataset;
    }
    
    public static DefaultCategoryDataset createDatasetRealizedFunctions(IPackage pack)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if(((ModelElement)pack).getAttribute(Project.ESTIMATED_FUNCTIONALITIES) == null)
            return null;
	    int parentEstimatedFunctions = ((Integer)((ModelElement)pack).getAttribute(Project.ESTIMATED_FUNCTIONALITIES)).intValue(); 
       
	    for(int i = 0 ; i < pack.modelElementCount() ; i++)
        {
            ModelElement elt = ((ModelElement)pack.getModelElement(i)); 
            if(elt.getAttribute(Project.REALIZED_FUNCTIONALITIES) == null)
	            return null;
            int realizedFunctions = ((Integer)elt.getAttribute(Project.REALIZED_FUNCTIONALITIES)).intValue();
            dataset.addValue(realizedFunctions*100/parentEstimatedFunctions, ResourceManager.getInstance().getString("realizedFunctions"),elt.getName());
        }
        return dataset;
    }
    
    public static DefaultCategoryDataset createDatasetRemainedFunctions(IPackage pack)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if(((ModelElement)pack).getAttribute(Project.ESTIMATED_FUNCTIONALITIES) == null)
            return null;
        int parentEstimatedFunctions = ((Integer)((ModelElement)pack).getAttribute(Project.ESTIMATED_FUNCTIONALITIES)).intValue();
        int realizedFunctions = 0;
	    for(int i = 0 ; i < pack.modelElementCount() ; i++)
	    {
	        ModelElement elt = ((ModelElement)pack.getModelElement(i));
	        if(elt.getAttribute(Project.REALIZED_FUNCTIONALITIES) == null)
	            return null;
            realizedFunctions += ((Integer)elt.getAttribute(Project.REALIZED_FUNCTIONALITIES)).intValue();
            int remainedFunctions = parentEstimatedFunctions - realizedFunctions;
            dataset.addValue(remainedFunctions*100/parentEstimatedFunctions, ResourceManager.getInstance().getString("remainedFunctions"),elt.getName());
        }
        return dataset;
    }
    
  
    public static DefaultCategoryDataset addDatasets (DefaultCategoryDataset dataset1, DefaultCategoryDataset dataset2)
    {
        if(dataset1 == null)
            return dataset2;
        if(dataset2 == null)
            return dataset1;
        try
        {
            DefaultCategoryDataset result = (DefaultCategoryDataset)dataset1.clone();
            for (int i = 0; i < dataset2.getRowCount(); i++)
            {
                for(int j = 0; j < dataset2.getColumnCount(); j++)
                {
                    result.addValue(dataset2.getValue(i,j),dataset2.getRowKey(i),dataset2.getColumnKey(j));
                }
            }
            return result;
        }
        catch (Exception e)
        {	
            return null;
        }
    }
}
