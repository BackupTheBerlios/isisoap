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

package soap.model.executionProcess.structure.user;

import soap.model.ModelVisitor;
import soap.model.core.ModelElement;

public class Member extends ModelElement
{
    private String mFirstName="" ;
    private String mLastName="" ;
    private String mEMail="";
    private String mLogin="";
    private String mPassword="";
    
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
    
    public String getEMail()
    {
        return mEMail;
    }
    
    public void setEMail(String email)
    {
        mEMail = email;
    }
    
    public String getLogin()
    {
        return mLogin;
    }
    
    public void setLogin(String login)
    {
        mLogin = login;
    }
    
    public String getPassword()
    {
        return mPassword;
    }
    
    public void setPassword(String password)
    {
        mPassword = password;
    }
    
    
    public String toString()
    {
        return mFirstName + " " + mLastName;
    }

    /* (non-Javadoc)
     * @see soap.model.core.Element#visit(soap.model.ModelVisitor)
     */
    public void visit(ModelVisitor visitor)
    {
        // TODO Auto-generated method stub
        
    }
}
