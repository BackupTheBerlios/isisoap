/*
 * Created on 6 nov. 2004
 */
package soap.ui.panel;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import soap.Context;
import soap.model.executionProcess.structure.Project;
import soap.model.executionProcess.structure.user.Supervisor;
import utils.ResourceManager;
import utils.SmartChooser;

/**
 * @author yanagiba
 */
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
        mMapButton.put(name,butt);
        mBox.add(butt);
        initUI() ;
    }
    
    public JButton getButton(String name)
    {
        return (JButton)mMapButton.get(name) ;
    }
    
    public void initUI ()
    {
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)) ;
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
