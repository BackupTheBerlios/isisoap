/*
 * SOAP Supervising, Observing, Analysing Projects
 * Copyright (C) 2003-2004 SOAPteam
 * 
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

package soap.model.frontend.event;

import java.util.Map;

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
