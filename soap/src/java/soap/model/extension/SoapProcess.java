/*
 * APES is a Process Engineering Software
 * Copyright (C) 2003-2004 IPSquad
 * team@ipsquad.tuxfamily.org
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
package soap.model.extension;

import java.util.Vector;

import soap.model.ModelVisitor;
import soap.model.core.ModelElement;
import soap.model.modelmanagement.IPackage;
/**
 * 
 * @version $Revision: 1.1 $
 */
public class SoapProcess extends ModelElement implements IPackage 
{

    private PackageRole mPackageRole = null;
    private PackageWorkDefinition mPackageWorkDefinition = null;
    private PackageWorkProduct mPackageWorkProduct = null ;
    private PackageProcessRole mPackageProcessRole = null;
    
	public SoapProcess()
	{
		super("");
	}
	
	public SoapProcess(String name)
	{
		super(name);
	}
	
	public void visit(ModelVisitor visitor) 
	{
			visitor.visitProcess(this);
	}
	
	public boolean addModelElement(ModelElement e)
	{
		
	    if(e == null)
		{
			return false;
		}
		
		if( e instanceof PackageRole && mPackageRole == null )
		{
		    mPackageRole = (PackageRole) e;
		    mPackageRole.setParent(this);
			return true;
		}

		if( e instanceof PackageWorkDefinition && mPackageWorkDefinition == null )
		{
		    mPackageWorkDefinition = (PackageWorkDefinition) e;
		    mPackageWorkDefinition.setParent(this);
			return true;
		}

		if( e instanceof PackageWorkProduct && mPackageWorkProduct == null )
		{
		    mPackageWorkProduct = (PackageWorkProduct) e;
		    mPackageWorkProduct.setParent(this);
			return true;
		}
		
		if( e instanceof PackageProcessRole && mPackageProcessRole == null )
		{
		    mPackageProcessRole = (PackageProcessRole) e;
		    mPackageProcessRole.setParent(this);
			return true;
		}
		return false;
	}

	public boolean removeModelElement( ModelElement me )
	{
	    if( mPackageRole == me )
		{
		    mPackageRole.setParent( null );
		    mPackageRole = null;
			return true;
		}

		if( me == mPackageWorkDefinition )
		{
		    mPackageWorkDefinition.setParent( null );
		    mPackageWorkDefinition = null;
			return true;
		}
		if( me == mPackageWorkProduct )
		{
		    mPackageWorkProduct.setParent( null );
		    mPackageWorkProduct = null;
			return true;
		}
		if( me == mPackageProcessRole )
		{
		    mPackageProcessRole.setParent(null);
		    mPackageProcessRole = null;
			return true;
		}
		
		return false;
	}

	public boolean containsModelElement( ModelElement me )
	{
	    if( me instanceof PackageRole )
		{
			return mPackageRole ==  me;
		}

		if( me instanceof PackageWorkDefinition )
		{
			return mPackageWorkDefinition == me;
		}
		
		if( me instanceof PackageWorkProduct )
		{
			return mPackageWorkProduct == me;
		}
		
		if( me instanceof PackageProcessRole )
		{
			return mPackageProcessRole == me;
		}
		return false;
	}
	
	public ModelElement getModelElement(int i) 
	{
		
		return null;
	}
	
	
	public int modelElementCount() 
	{
		int count = 0;
		
		return count;
	}	
	
	public PackageRole getPackageRole()
	{
	    return this.mPackageRole ;
	}
	
	public PackageWorkDefinition getPackageWorkDefinition()
	{
	    return mPackageWorkDefinition ;
	}
	
	public PackageWorkProduct getPackageWorkProduct()
	{
	    return this.mPackageWorkProduct ;
	}
	
	public PackageProcessRole getPackageProcessRole()
	{
	    return mPackageProcessRole ;
	}
	
	public static class PackageRole extends ModelElement implements IPackage 
	{
	    private Vector mRoles = new Vector();
	    
	    public PackageRole (String name)
	    {
	        super("");
	        this.setName(name);
	    }
	    public void visit(ModelVisitor visitor)
		{
			visitor.visitPackage(this);
		}

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#addModelElement(soap.model.spem.core.ModelElement)
         */
        public boolean addModelElement(ModelElement e)
        {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#removeModelElement(soap.model.spem.core.ModelElement)
         */
        public boolean removeModelElement(ModelElement e)
        {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#containsModelElement(soap.model.spem.core.ModelElement)
         */
        public boolean containsModelElement(ModelElement e)
        {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#getModelElement(int)
         */
        public ModelElement getModelElement(int i)
        {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#modelElementCount()
         */
        public int modelElementCount()
        {
            // TODO Auto-generated method stub
            return 0;
        }
	    
	}
	 
    public static class PackageWorkDefinition extends ModelElement implements IPackage
    {
        private Vector mWorkDefinitions = new Vector();
        
        public PackageWorkDefinition (String name)
	    {
	        super("");
	        this.setName(name);
	    }
        
        public void visit(ModelVisitor visitor)
        {
            visitor.visitPackage(this);
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#addModelElement(soap.model.spem.core.ModelElement)
         */
        public boolean addModelElement(ModelElement e)
        {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#removeModelElement(soap.model.spem.core.ModelElement)
         */
        public boolean removeModelElement(ModelElement e)
        {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#containsModelElement(soap.model.spem.core.ModelElement)
         */
        public boolean containsModelElement(ModelElement e)
        {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#getModelElement(int)
         */
        public ModelElement getModelElement(int i)
        {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#modelElementCount()
         */
        public int modelElementCount()
        {
            // TODO Auto-generated method stub
            return 0;
        }
        
    }
    	    
    public static class PackageWorkProduct extends ModelElement implements IPackage
    {
        private Vector mWorkProducts = new Vector();
        
        public PackageWorkProduct (String name)
	    {
	        super("");
	        this.setName(name);
	    }
        
        public void visit(ModelVisitor visitor)
		{
			visitor.visitPackage(this);
		}
        
        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#addModelElement(soap.model.spem.core.ModelElement)
         */
        public boolean addModelElement(ModelElement e)
        {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#removeModelElement(soap.model.spem.core.ModelElement)
         */
        public boolean removeModelElement(ModelElement e)
        {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#containsModelElement(soap.model.spem.core.ModelElement)
         */
        public boolean containsModelElement(ModelElement e)
        {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#getModelElement(int)
         */
        public ModelElement getModelElement(int i)
        {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#modelElementCount()
         */
        public int modelElementCount()
        {
            // TODO Auto-generated method stub
            return 0;
        }
        
    }
	
    public static class PackageProcessRole extends ModelElement implements IPackage
    {
        public PackageProcessRole (String name)
	    {
	        super("");
	        this.setName(name);
	    }
        
        private Vector mProcessRoles = new Vector();
        
        public void visit(ModelVisitor visitor)
		{
			visitor.visitPackage(this);
		}
   
        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#addModelElement(soap.model.spem.core.ModelElement)
         */
        public boolean addModelElement(ModelElement e)
        {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#removeModelElement(soap.model.spem.core.ModelElement)
         */
        public boolean removeModelElement(ModelElement e)
        {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#containsModelElement(soap.model.spem.core.ModelElement)
         */
        public boolean containsModelElement(ModelElement e)
        {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#getModelElement(int)
         */
        public ModelElement getModelElement(int i)
        {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see soap.model.spem.modelmanagement.IPackage#modelElementCount()
         */
        public int modelElementCount()
        {
            // TODO Auto-generated method stub
            return 0;
        }
        
    }
	
	
}
