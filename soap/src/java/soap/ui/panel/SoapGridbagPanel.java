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
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * a class helping users to create a panel with a gridbaglayout
 * more detail : see the the gridbaglayout 
 *
 */
public class SoapGridbagPanel extends SoapPanel
{
    private GridBagLayout mGridbag = new GridBagLayout();
    private GridBagConstraints mConstraint = new GridBagConstraints();
    
    public final static int RELATIVE = GridBagConstraints.RELATIVE ;
    public final static int END = GridBagConstraints.REMAINDER ;
    
    public SoapGridbagPanel()
    {
        super();
        initUI() ;
    }
    
    private void initUI() 
    {
        this.setLayout(mGridbag);
        //mConstraint.insets = new Insets(10,10,10,10);
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }
    

    public void addComponent(Component comp, int position, double  weightx , double weighty, int fill)
    {
        mConstraint.weightx = weightx;
        mConstraint.weighty = weighty;
		mConstraint.gridwidth = position;
		mConstraint.fill = fill ;
		mGridbag.setConstraints(comp, mConstraint);
		this.add(comp) ;
    }
    
    public void defaultAddComponent(Component comp, int position, double  weightx , double weighty)
    {
        int fill = GridBagConstraints.HORIZONTAL ;
        addComponent (comp, position, weightx , weighty, fill);
    }
    
    public void defaultAddComponent(Component comp, int position, double  weightx , double weighty, Insets insets)
    {
        int fill = GridBagConstraints.HORIZONTAL ;
        mConstraint.insets = insets;
        addComponent (comp, position, weightx , weighty, fill);
    }
    
    public void defaultAddComponent(Component comp, int position )
    {
        this.defaultAddComponent(comp,position,1,0);
    }
    
    public void addSpace()
    {
        this.addLine(0);
    }
    
    public void addLine(double weighty)
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
    
    public void setConstraint(GridBagConstraints constraint)
    {
        mConstraint = constraint;
    }
    
    public GridBagConstraints getConstraint()
    {
        return mConstraint;
    }
    
}
