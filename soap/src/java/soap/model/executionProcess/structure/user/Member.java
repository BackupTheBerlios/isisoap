/*
 * Created on 7 nov. 2004
 */
package soap.model.executionProcess.structure.user;

import soap.model.ModelVisitor;
import soap.model.core.ModelElement;

/**
 * @author yanagiba
 */
public class Member extends ModelElement
{
    private String mFirstName="" ;
    private String mLastName="" ;
    
    public Member()
    {
        super() ;
    }
    
    public Member(String firstName, String lastName)
    {
        mFirstName = firstName ;
        mLastName = lastName ;
    }
    
    public String getFirstName()
    {
        return mFirstName ;
    }
    
    public void setFirstName(String name)
    {
        mFirstName = name ;
    }
    
    public String getLastName()
    {
        return mLastName ;
    }
    
    public void setLastName(String name)
    {
        mLastName = name ;
    }

    /* (non-Javadoc)
     * @see soap.model.core.Element#visit(soap.model.ModelVisitor)
     */
    public void visit(ModelVisitor visitor)
    {
        // TODO Auto-generated method stub
        
    }
}
