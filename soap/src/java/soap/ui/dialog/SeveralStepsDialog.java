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

package soap.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import soap.ui.SoapDialog;
import soap.ui.panel.PanelButton;
import soap.ui.panel.PanelDescription;
import utils.IconManager;
import utils.ResourceManager;

/**
 * panel displaying sucessive panel
 *
 */

public class SeveralStepsDialog extends SoapDialog
{
    private PanelDescription mPanelDescription ;
    private PanelButton mPanelButton = new PanelButton();
    private static ResourceManager resMan = ResourceManager.getInstance();
    
    private Vector mListPanel = new Vector() ;
    private int mCurrentPanel = 0 ;
    private String mImageRessource ;
    private String mDescription ;
    private String mImagePbRessource = "";
    
    private Container mContent = this.getContentPane() ;
   
    public SeveralStepsDialog(String title, String description, String resourceName)
    {
        super(parent,title,true) ;
        mImageRessource = resourceName ;
        mDescription = description ;
        mPanelDescription = new PanelDescription(description);
        //initUI();
    }
    
    public SeveralStepsDialog(String title, String description, String resourceName, String resourcePanelDescription)
    {
        super(parent,title,true) ;
        mImageRessource = resourceName ;
        mDescription = description ;
        mImagePbRessource = resourcePanelDescription;
        mPanelDescription = new PanelDescription(description, resourcePanelDescription);
        //initUI();
    }
    
    protected void initUI()
    {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        mPanelButton.addButton(resMan.getString("previous")) ;
        mPanelButton.addSpace();
        mPanelButton.addButton(resMan.getString("next")) ;
        ButtonListener buttonListener = new ButtonListener() ;
        mPanelButton.addButtonListener(buttonListener) ;
        mPanelButton.setButtonDisable(resMan.getString("previous")) ;
        
        if (mListPanel.size() == 1)
        {
            JButton finish = new JButton(resMan.getString("finish")) ;
            mPanelButton.removeButton(resMan.getString("next"));
            finish.addActionListener(buttonListener) ;
            mPanelButton.addButton(finish);    
            
        }
        
        mContent.setLayout(new BorderLayout());
        mContent.add(mPanelDescription, BorderLayout.NORTH) ;
        this.setImage(mImageRessource);
        if (mListPanel.size()>0)
        {
            mContent.add((JPanel)mListPanel.get(mCurrentPanel), BorderLayout.CENTER) ;
        }
        mContent.add(mPanelButton,BorderLayout.SOUTH) ;
        
        setSize(550,470);
        setLocation(parent.getX()+(parent.getWidth()-this.getWidth())/2,parent.getY()+(parent.getHeight()-this.getHeight())/2);
        setResizable(false);
    }
    
    public void setVisible(boolean visible)
    {
        initUI();
        super.setVisible(true);
    }
    
    public void addCenterPanel(JPanel centerPanel)
    {
        mListPanel.add(centerPanel) ;
    }   
    
    public PanelDescription getPanelDescription()
    {
        return mPanelDescription ;
    }
    
    public void setPanelDescription(PanelDescription descriptionPanel)
    {
        mContent.add(descriptionPanel, BorderLayout.NORTH) ;
        mImagePbRessource = descriptionPanel.getImageRessource() ;
    }
    
    protected void setCenterPanel(JPanel centerPanel)
    {
        mContent.removeAll();
        mContent.add(mPanelDescription, BorderLayout.NORTH) ;
        this.setImage(mImageRessource);
        mContent.add(mPanelButton,BorderLayout.SOUTH) ;
        mContent.add(centerPanel, BorderLayout.CENTER) ;
        mContent.repaint() ;
        mContent.validate(); 
    }   
    
    protected void setImage(String resourceName)
    {
        Icon img = IconManager.getInstance().getIcon(resourceName);
        JLabel label = new JLabel(img);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(label);
		mContent.add(panel,BorderLayout.WEST) ;
    }
    
    public JPanel getCurrentPanel()
    {
        return (JPanel)mListPanel.get(mCurrentPanel) ;
    }
    
    public JPanel getPanel(int i)
    {
        return (JPanel)mListPanel.get(i) ;
    }
      
    public interface StepsValidator
    {
        public boolean validateSteps();
        public void save(); 
    }
    
    private class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            JButton previous = mPanelButton.getButton(resMan.getString("previous")) ;
            JButton next = mPanelButton.getButton(resMan.getString("next")) ;
            StepsValidator validator = null;
            
            if (getCurrentPanel() instanceof StepsValidator)
            {
                validator = (StepsValidator)getCurrentPanel() ;
            }
            
            if (event.getActionCommand().equals(resMan.getString("previous")) )
            {  
                mPanelDescription.setDescription(mDescription,mImagePbRessource);
                if (mPanelButton.existButton(resMan.getString("finish")))
                {
                    mPanelButton.removeButton(resMan.getString("finish"));
                    next = new JButton(resMan.getString("next")) ;
                    next.addActionListener(this) ;
                    mPanelButton.addButton(next);
                    
                    
                }
                if (mCurrentPanel == 1)
                {
                    mPanelButton.setButtonDisable(resMan.getString("previous")) ;
                }
                
                if (mCurrentPanel > 0)
                {
                    mCurrentPanel-- ;
                    mPanelButton.setButtonEnable(resMan.getString("next"));
                    SeveralStepsDialog.this.setCenterPanel((JPanel)mListPanel.get(mCurrentPanel)) ;
                }
                return ;
                              
            }
            if (event.getActionCommand().equals(resMan.getString("next")))
            {   
                if (validator != null )
                {
                    if (!validator.validateSteps())
                    {
                        return ;
                    }
                }
                mPanelDescription.setDescription(mDescription,mImagePbRessource);
                if (mCurrentPanel == mListPanel.size()-2)
                {
                    JButton finish = new JButton(resMan.getString("finish")) ;
                    mPanelButton.removeButton(resMan.getString("next"));
                    finish.addActionListener(this) ;
                    mPanelButton.addButton(finish);                    
                }                     
                if (mCurrentPanel <= mListPanel.size() - 1)
                {
                    mCurrentPanel++ ;
                    mPanelButton.setButtonEnable(resMan.getString("previous"));
                    SeveralStepsDialog.this.setCenterPanel((JPanel)mListPanel.get(mCurrentPanel)) ;
                }
                return ;
            }
            if (event.getActionCommand().equals(resMan.getString("finish")))
            {
                if (validator != null )
                {
                    if (!validator.validateSteps())
                    {
                        return ;
                    }
                }
                for(int i = 0 ; i < mListPanel.size() ; i++)
                {
                    if(mListPanel.get(i) instanceof StepsValidator)
                    {
                        validator = (StepsValidator)mListPanel.get(i) ;
                        validator.save();
                    }
                }
                mPanelDescription.setDescription(mDescription);
                SeveralStepsDialog.this.dispose();
            }
        }
    }
}
