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

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import utils.ResourceManager;

/**
 * a class helping users to create a panel containing button
 *
 */

public class PanelButton extends SoapPanel
{
    public static final int OK = 1 ;
    public static final int CANCELOK = 2 ;
    public static final int APPLYOK = 3 ;
    public static final int APPLYCANCELOK = 4 ;
    public static final String CENTER = "center" ;
    public static final String RIGHT = "right" ;
    
    private Map mMapButton = new HashMap() ;
    private Box mBox = Box.createHorizontalBox() ;
    private String mPosition = "center" ;
    private int mButtonSize = 10;
    
    public PanelButton ()
    {
        mBox.add(Box.createHorizontalGlue());
    }
    
    /**
     * @param position of all the button in the panel
     * RIGHT OR CENTER
     *
     */

    public PanelButton (String position)
    {
        this() ;
        mPosition = position ;
    }
    
    /**
     *
     * @param type default Panelbutton containing 
     * OK - APPLY - CANCEL 
     *
     */
    public PanelButton (int type)
    {
        this() ;
        if (type >= 1)
        {
            addButton(ResourceManager.getInstance().getString("ok"));
        }
        if (type == 2 || type > 4)
        {
            addButton(ResourceManager.getInstance().getString("cancel"));
        }
        if (type == 3 || type > 4)
        {
            addButton(ResourceManager.getInstance().getString("apply"));
        }
        this.setLayout(new FlowLayout()) ;
        initUI() ;
        
    }
    
    /**
     * @param position of all the button in the panel
     * RIGHT OR CENTER
     * @param type default Panelbutton containing 
     * OK - APPLY - CANCEL 
     * 
     */
    public PanelButton (int type, String position)
    {
        this(type);
        mPosition = position ;
    }
    
    /**
     * return the button ok
     * @return return the ok button
     *
     */
    public JButton getOkButton()
    {
        return getButton(ResourceManager.getInstance().getString("ok")) ;
    }
    
    /**
     * return the button cancel
     * @return return the cancel button
     *
     */
    public JButton getCancelButton()
    {
        return getButton(ResourceManager.getInstance().getString("cancel")) ;
    }
    
    /**
     * return the button apply
     * @return return the apply button
     *
     */
    public JButton getApplyButton()
    {
        return getButton(ResourceManager.getInstance().getString("apply")) ;
    }
    
    /**
     * add a new button to the panel with the name given in parameter
     * @param name the button name 
     */
    public void addButton(String name)
    {
        addButton(new JButton(name)) ;
    }
    
    /**
     * add a new button to the panel
     * @param button the new button 
     * 
     */
    public void addButton(JButton button)
    {
        button.setPreferredSize(new Dimension(85,30));
        button.setMargin(new Insets(2,5,2,5));
        button.setActionCommand(button.getText());
        mMapButton.put(button.getText(),button);
        mBox.add(button);
        initUI() ;
    }
    
    /**
     * add a space after the button int the panel
     * 
     */
    public void addSpace()
    {
        mBox.add (Box.createRigidArea(new Dimension(10,0)));
    }
    
    /**
     * remove a  button from the panel
     * @param button the button name to remove
     */
    public void removeButton(String name)
    {
        mBox.remove(this.getButton(name));
        mMapButton.remove(name);
        
    }
    
    /**
     * check if a button exist  
     * @param name the button name
     */
    public boolean existButton(String name)
    {
        return mMapButton.containsKey(name);
    }
    
    /**
     * get a button from the panel
     * @param name the button name
     */
    public JButton getButton(String name)
    {
        return (JButton)mMapButton.get(name) ;
    }
    
    /**
     * set a button enable  
     * @param name the button name
     */
    public void setButtonEnable(String name)
    {
        getButton(name).setEnabled(true) ;
    }
    
    /**
     * set a button enable  
     * @param name the button name
     */
    public void setButtonDisable(String name)
    {
        getButton(name).setEnabled(false) ;
    }
    
    /**
     * initialize button interface
     *
     */

    private void initUI ()
    {
        this.setBorder(new EmptyBorder(10,10,10,10));
        if (mPosition.equals(RIGHT))
        {
            this.setLayout(new GridLayout()) ;
        }
        this.add(mBox) ;
    }
    
    
    /**
     * add a listener to all the button conataining in the panel
     * @param listener the listener 
     */
    public void addButtonListener(ActionListener listener)
    {
        Vector v = new Vector (mMapButton.values()) ;
        for (int i=0; i < v.size();i++)
        {
           ((JButton)v.get(i)).addActionListener(listener);
        }
    }

}
