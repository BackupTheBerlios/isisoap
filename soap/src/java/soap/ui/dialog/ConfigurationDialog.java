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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import soap.ui.SoapDialog;
import soap.ui.panel.InformationPanel;
import soap.ui.panel.PanelButton;
import soap.ui.panel.PanelDescription;
import soap.ui.panel.SoapGridbagPanel;
import soap.ui.panel.SoapTextPanel;
import utils.IconManager;


public class ConfigurationDialog extends SoapDialog
{
    private PanelDescription mPanelDescription = new PanelDescription("configurationnnnnnnn") ;
    private PanelButton mPanelButton = new PanelButton();
    
    private Vector mListPanel = new Vector() ;
    private int mCurrentPanel = 0 ;
   // private JLayeredPane mContent = this.getLayeredPane() ;
    private Container mContent = this.getContentPane() ;
   
    public ConfigurationDialog(String title)
    {
        super(parent,title,true) ;
    }
    
    
    {
        
        SoapTextPanel sp = new SoapTextPanel() ;
        sp.setText("cette assistant va vous permettre de configurer SOAP\n" +
        		"ceci ne vas vs prendre que quelques minutes\n\n" +
        		"bonne utilisation du logiciel !!!") ;
        addCenterPanel(sp);
        JPanel panel = new InformationPanel() ;
        addCenterPanel(panel);
        SoapGridbagPanel gridPanel = new SoapGridbagPanel();
        
        JLabel label = new JLabel() ;
        label.setText("veuillez entrez les informations concernant le serveur");
        gridPanel.addComponent(label, SoapGridbagPanel.END) ;
        
        gridPanel.addSpace();
        
        label = new JLabel() ;
        label.setText("adresse du serveur");
        gridPanel.addComponent(label,SoapGridbagPanel.END);
        JTextField adress = new JTextField(30);
        gridPanel.addComponent(adress,SoapGridbagPanel.END,2,0);
        
        gridPanel.addSpace(2);
        addCenterPanel(gridPanel);
        
        
        
        
    }
    public void setVisible(boolean visible) 
    {
        addWindowListener(new SoapFrameListener());
        
        mPanelButton.addButton("previous") ;
        mPanelButton.addButton("next") ;
        ButtonListener buttonListener = new ButtonListener() ;
        JButton previous = mPanelButton.getButton("previous") ;
        mPanelButton.addButtonListener(buttonListener) ;
        mPanelButton.setButtonDisable("previous") ;
        if (mListPanel.size() == 1)
        {
            JButton finish = new JButton("finish") ;
            mPanelButton.removeButton("next");
            finish.addActionListener(buttonListener) ;
            mPanelButton.addButton(finish);    
            
        }
        
        mContent.setLayout(new BorderLayout());
        mContent.add(mPanelDescription, BorderLayout.NORTH) ;
        this.setPanelLeft();
        if (mListPanel.size()>0)
        {
            mContent.add((JPanel)mListPanel.get(mCurrentPanel), BorderLayout.CENTER) ;
        }
        mContent.add(mPanelButton,BorderLayout.SOUTH) ;

        Rectangle r = getGraphicsConfiguration().getBounds();
        Double x = new Double (r.getX()+(r.getWidth()-r.width*2/3)/2) ;
        Double y = new Double (r.getY()+(r.getHeight()- r.height*2/3)/2) ;
		setBounds(x.intValue(), y.intValue(), r.width*2/3, r.height*2/3);
		super.setVisible(true);

    }
    
    public void addCenterPanel(JPanel centerPanel)
    {
        mListPanel.add(centerPanel) ;
    }   
    
    
    protected void setPanelDescription(JPanel descriptionPanel)
    {
        mContent.add(descriptionPanel, BorderLayout.NORTH) ;
    }
    
    protected void setCenterPanel(JPanel centerPanel)
    {
        mContent.removeAll();
        mContent.add(mPanelDescription, BorderLayout.NORTH) ;
        this.setPanelLeft();
        mContent.add(mPanelButton,BorderLayout.SOUTH) ;
        mContent.add(centerPanel, BorderLayout.CENTER) ;
        mContent.repaint() ;
        mContent.validate(); 
    }   
    
    protected void setPanelLeft()
    {
        Icon img = IconManager.getInstance().getIcon("icons/logoConfig.gif");
        JLabel label = new JLabel(img);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(label);
		mContent.add(panel,BorderLayout.WEST) ;
    }
    
    private class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            JButton previous = mPanelButton.getButton("previous") ;
            JButton next = mPanelButton.getButton("next") ;
            
            
            if (event.getActionCommand() == "previous" )
            {  
                
                if (mPanelButton.existButton("finish"))
                {
                    mPanelButton.removeButton("finish");
                    next = new JButton("next") ;
                    next.addActionListener(this) ;
                    mPanelButton.addButton(next);
                    
                    
                }
                if (mCurrentPanel == 1)
                {
                    mPanelButton.setButtonDisable("previous") ;
                }
                
                if (mCurrentPanel > 0)
                {
                    mCurrentPanel-- ;
                    mPanelButton.setButtonEnable("next");
                    ConfigurationDialog.this.setCenterPanel((JPanel)mListPanel.get(mCurrentPanel)) ;
                }
                return ;
                              
            }
            if (event.getActionCommand().equals("next"))
            {   
                
                if (mCurrentPanel == mListPanel.size()-2)
                {
                    JButton finish = new JButton("finish") ;
                    mPanelButton.removeButton("next");
                    finish.addActionListener(this) ;
                    mPanelButton.addButton(finish);                    
                }                     
                if (mCurrentPanel <= mListPanel.size() - 1)
                {
                    mCurrentPanel++ ;
                    mPanelButton.setButtonEnable("previous");
                    ConfigurationDialog.this.setCenterPanel((JPanel)mListPanel.get(mCurrentPanel)) ;
                }
                return ;
            }
            if (event.getActionCommand().equals("finish"))
            {  
                ConfigurationDialog.this.dispose();
            }
        }
    }
    
    private class SoapFrameListener extends WindowAdapter
	{
		/**
		 * Allows to intercept the close event to call the QuitAction
		 */
		public void windowClosing(WindowEvent e)
		{
		    System.exit(0) ;
		}
	}

    
}
