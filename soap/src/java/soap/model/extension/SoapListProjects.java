/*
 * Created on 1 nov. 2004
 */
package soap.model.extension;


import soap.model.ModelVisitor;
import soap.model.SoapElementVisitor;

import soap.model.modelmanagement.SPackage;

/**
 * @author yanagiba
 */
public class SoapListProjects extends SPackage 
{
    
    public SoapListProjects()
	{
		super("");
	}
	
	public SoapListProjects(String name)
	{
		super(name);
	}
	
	public void visit(ModelVisitor visitor) 
	{
		if( visitor instanceof SoapElementVisitor )
		{	
			((SoapElementVisitor)visitor).visitSoapListProjects(this);
		}
	}

}
