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

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import utils.ResourceManager;

public class PanelButton extends SoapPanel
{
    public static final int OK = 1 ;
    public static final int CANCELOK = 2 ;
    public static final int APPLYOK = 3 ;
    public static final int APPLYCANCELOK = 4 ;
    public static final String CENTER = "center" ;
    public static final String RIGHT = "right" ;
    
    private JButton mApplyButton = new JButton (ResourceManager.getInstance().getString("apply"));
    private JButton mCancelButton = new JButton (ResourceManager.getInstance().getString("cancel"));
    private JButton mOkButton = new JButton (ResourceManager.getInstance().getString("ok")) ; ;
    private Map mMapButton = new HashMap() ;
    private Box mBox = Box.createHorizontalBox() ;
    private String mPosition = "center" ;
    
    public PanelButton ()
    {
        mBox.add(Box.createHorizontalGlue());
    }
    
    public PanelButton (String position)
    {
        this() ;
        mPosition = position ;
    }
    
    public PanelButton (int type)
    {
        this() ;
    	mOkButton.setBounds(mCancelButton.getBounds()) ;
        if (type >= 1)
        {
            mBox.add(mOkButton);
            mMapButton.put(mOkButton.getName(),mOkButton) ;
        }
        if (type == 2 || type > 4)
        {
            mBox.add(mCancelButton);
            mMapButton.put(mCancelButton.getName(),mCancelButton) ;
        }
        if (type == 3 || type > 4)
        {
            mBox.add(mApplyButton);
            mMapButton.put(mApplyButton.getName(),mApplyButton) ;
        }
        this.setLayout(new FlowLayout()) ;
        initUI() ;
    }
    
    public PanelButton (int type, String position)
    {
        this(type);
        mPosition = position ;
    }
        
        
    public JButton getOkButton()
    {
        return mOkButton ;
    }
    
    public JButton getCancelButton()
    {
        return mCancelButton ;
    }
    
    public JButton getApplyButton()
    {
        return mApplyButton ;
    }
    
    public void addButton(String name)
    {
        JButton butt = new JButton(name) ;
        butt.setActionCommand(name.toLowerCase()) ;
        mMapButton.put(name,butt);
        mBox.add(butt);
        initUI() ;
    }
    
    public void addButton(JButton button)
    {
        mMapButton.put(button.getText(),button);
        mBox.add(button);
        initUI() ;
    }
    
    public void removeButton(String name)
    {
        mBox.remove(this.getButton(name));
        mMapButton.remove(name);
        
    }
    
    public boolean existButton(String name)
    {
        return mMapButton.containsKey(name);
    }
    
    public JButton getButton(String name)
    {
        return (JButton)mMapButton.get(name) ;
    }
    
    
    public void setButtonEnable(String name)
    {
        getButton(name).setEnabled(true) ;
    }
    
    public void setButtonDisable(String name)
    {
        getButton(name).setEnabled(false) ;
    }
    
    public void initUI ()
    {
        Border border = new EtchedBorder() ;
        Border margin = new EmptyBorder(10,10,10,10);
        this.setBorder(new CompoundBorder(border, margin));
        if (mPosition.equals(RIGHT))
        {
            this.setLayout(new GridLayout()) ;
        }
        this.add(mBox) ;
    }
    
    public void addButtonListener(ActionListener listener)
    {
        Vector v = new Vector (mMapButton.values()) ;
        for (int i=0; i < v.size();i++)
        {
           ((JButton)v.get(i)).addActionListener(listener);
        }
    }
}
