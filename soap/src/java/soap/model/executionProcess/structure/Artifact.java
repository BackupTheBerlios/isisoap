/*
 * Created on 8 nov. 2004
 */
package soap.model.executionProcess.structure;

import soap.model.ModelVisitor;
import soap.model.core.ModelElement;
import soap.model.process.structure.WorkProduct;

/**
 * @author yanagiba
 */
public class Artifact extends ModelElement
{
    private WorkProduct mWorkProduct ;
    //String mVersion  = "";
    
    public Artifact()
    {
        super() ;
    }
    
    public Artifact(String name)
    {
        super(name) ;
    }
    
    public WorkProduct getWorkProduct()
    {
        return mWorkProduct ;
    }
    
    public void setWorkProduct (WorkProduct w)
    {
        mWorkProduct = w ;
    }
    
    
    /* (non-Javadoc)
     * @see soap.model.spem.core.Element#visit(soap.model.spem.SpemVisitor)
     */
    public void visit(ModelVisitor visitor)
    {
        // TODO Auto-generated method stub

    }

}
