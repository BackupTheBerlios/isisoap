/*
 * Created on 2 nov. 2004
 */
package soap.model.frontend.event;

import java.util.Map;

/**
 * @author yanagiba
 */
public class SoapEvent
{
    private Object[] mInserted = null;
    private Object[] mRemoved = null;
    private Object[] mParents = null;
	private Object mSource = null;
	private Object mTarget = null;
	private Map mAttributes;
	
	
	public String toString()
	{
		return "Inserted "+mInserted+" Parent "+mParents+" source "+mSource+" target "+mTarget;
	}
		
	public SoapEvent( Object[] inserted, Object[] removed, Object[] parent, Map attr )
	{
		mAttributes = attr ;
		mInserted = inserted;
		mParents = parent;
		mRemoved = removed ;
	}
	
	/**
	 * 
	 * @return the inserted element
	 */
	public Object[] getInserted() {return mInserted;}
	
	/**
	 * 
	 * @return the removed element
	 */
	public Object[] getRemoved() {return mRemoved;}
	
	
	public Object getSource() { return mSource; }
	public Object getTarget() { return mTarget; }
	/**
	 * 
	 * @return the parent of the inserted element
	 */
	public Object[] getParents() { return mParents; }
	/**
	 * 
	 * @return a map containing informations passing by the object which calls the ApesMediator.
	 *  
	 */
	public Map getAttributes() { return mAttributes; }

}
