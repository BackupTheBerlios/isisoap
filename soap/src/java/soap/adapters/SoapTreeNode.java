/*
 * Created on 30 oct. 2004
 */
package soap.adapters;

import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;

import soap.Identity;

import soap.adapters.SoapTreeNode;

/**
 * @author yanagiba
 */
public class SoapTreeNode extends DefaultMutableTreeNode implements Identity, Serializable
{
	
	public SoapTreeNode(Object userObject, boolean isLeaf)
	{
		super(userObject);		
	}


	public SoapTreeNode(Object userObject, SoapTreeNode parent)
	{
		super(userObject);
		setParent(parent);
	}
	

	public String getName()
	{
		return getUserObject().toString();
	}
	
	public String toString()
	{
		return getName();
	}
	
	public int getID()
	{
		if( getUserObject() instanceof Identity )
		{
			return ((Identity)getUserObject()).getID();
		}
		return -1;
	}	
}
