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

package soap.ui.panel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class SoapGridbagPanel extends SoapPanel
{
    GridBagLayout mGridbag = new GridBagLayout();
    GridBagConstraints mConstraint = new GridBagConstraints();
    
    public static int RELATIVE = GridBagConstraints.RELATIVE ;
    public static int END = GridBagConstraints.REMAINDER ;
    
    public SoapGridbagPanel()
    {
        super();
        initUI() ;
    }
    
    public void initUI() 
    {
        this.setLayout(mGridbag);
        this.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
    }
    
    public void addComponent(Component comp, int position, int  weightx , int weighty)
    {
        mConstraint.weightx = weightx;
        mConstraint.weighty = weighty;
		mConstraint.gridwidth = position;
		mConstraint.fill = GridBagConstraints.HORIZONTAL ;
		mGridbag.setConstraints(comp, mConstraint);
		this.add(comp) ;
    }
    
    public void addComponent(Component comp, int position )
    {
        this.addComponent(comp,position,1,0);
    }
    
    public void addSpace()
    {
        this.addSpace(0);
    }
    
    public void addSpace(int weighty)
    {
        mConstraint.weightx = 1;
        mConstraint.weighty = weighty;
		mConstraint.gridwidth = END;
		mConstraint.fill = GridBagConstraints.HORIZONTAL ;
        JLabel label = new JLabel(" ");
        mGridbag.setConstraints(label, mConstraint);
		this.add(label) ;
    }
    
    public void reset()
    {
        this.removeAll();
    }
    
}
