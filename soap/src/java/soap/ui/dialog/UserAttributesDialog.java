/*
 * SOAP Supervising, Observing, Analysing Projects
 * Copyright (C) 2003-2004 SoapTeam
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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import soap.Context;
import soap.model.executionProcess.structure.user.Member;
import soap.ui.SoapDialog;
import soap.ui.panel.PanelButton;
import soap.ui.panel.PanelDescription;
import soap.ui.panel.SoapGridbagPanel;
import utils.ResourceManager;


public class UserAttributesDialog extends SoapDialog
{   
    private PanelDescription mPanelDescription;
    private JTextField mSupervisorNameTF = new JTextField(30) ;
    private JTextField mSupervisorFirstNameTF = new JTextField(30) ;
    private JTextField mMailTF = new JTextField(30) ;
    private JTextField mLoginTF = new JTextField(30) ;
    private JPasswordField mPassTF = new JPasswordField();
    private PanelButton mPanelButton = new PanelButton(PanelButton.CANCELOK);
    private Member mMember = null;
    
    private ResourceManager resMan =  ResourceManager.getInstance() ;
    
    public UserAttributesDialog (String title, String description)
    {
        super((JFrame)Context.getInstance().getTopLevelFrame(),title,true);
        mPanelDescription = new PanelDescription(description);
        initUI();
    }
    
    public UserAttributesDialog (String title, String description, Member m)
    {
        super((JFrame)Context.getInstance().getTopLevelFrame(),title,true);
        mPanelDescription = new PanelDescription(description);
        mMember = m;
        initUI();
    }
    
    protected void initUI()
    {
        JLabel supervisorName = new JLabel(resMan.getString("memberName")) ;
        JLabel supervisorFirstName = new JLabel(resMan.getString("memberFirstName")) ;
        JLabel mail = new JLabel(resMan.getString("memberMail")) ;
        JLabel login = new JLabel(resMan.getString("memberLogin")) ;
        JLabel pass = new JLabel(resMan.getString("memberPassword")) ;
        
        SoapGridbagPanel panel = new SoapGridbagPanel();
        panel.defaultAddComponent(supervisorName,SoapGridbagPanel.END,1,0,new Insets(0,20,0,20));
        panel.defaultAddComponent(mSupervisorNameTF,SoapGridbagPanel.END);
                
        panel.defaultAddComponent(supervisorFirstName,SoapGridbagPanel.END);
        panel.defaultAddComponent(mSupervisorFirstNameTF,SoapGridbagPanel.END);
        
        
        panel.defaultAddComponent(mail,SoapGridbagPanel.END);
        panel.defaultAddComponent(mMailTF,SoapGridbagPanel.END);
                
        panel.defaultAddComponent(login,SoapGridbagPanel.END);
        panel.defaultAddComponent(mLoginTF,SoapGridbagPanel.END);
                
        panel.defaultAddComponent(pass,SoapGridbagPanel.END);
        panel.defaultAddComponent(mPassTF,SoapGridbagPanel.END);
        
        if(mMember != null)
        {
            mSupervisorNameTF.setText(mMember.getLastName());
            mSupervisorFirstNameTF.setText(mMember.getFirstName());
            mMailTF.setText(mMember.getEMail());
            mLoginTF.setText(mMember.getLogin());
            mPassTF.setText(mMember.getPassword());
        }
        
        mPanelButton.addButtonListener(new ButtonListener());
        
        setLayout(new BorderLayout());
        add(mPanelDescription, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(mPanelButton, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(mPanelButton.getOkButton());
        
        setSize(350,340);
        super.initUI();
    }

    private class ButtonListener implements ActionListener
    {
        
        public void actionPerformed(ActionEvent event)
        {
            if(event.getSource().equals(mPanelButton.getOkButton()))
        	{
                if(isUserInformationCorrect())
        	    {    
                    if(mMember != null)
            	    {
            	        mMember.setLastName(mSupervisorNameTF.getText());
            	        mMember.setFirstName(mSupervisorFirstNameTF.getText());
            	        mMember.setEMail(mMailTF.getText());
            	        mMember.setLogin(mLoginTF.getText());
            	        mMember.setPassword(String.valueOf(mPassTF.getPassword()));
            	    }
            	    else
            	    {    
            	        Member m = new Member(mSupervisorFirstNameTF.getText(), mSupervisorNameTF.getText());
            	        m.setEMail(mMailTF.getText());
            	        m.setLogin(mLoginTF.getText());
            	        m.setPassword(String.valueOf(mPassTF.getPassword()));
            	        Context.getInstance().getListProjects().getCurrentProject().addMember(m);
            	    }
            	    dispose();
        	    }
        	}
            else
            {
                dispose();
            }
        }
    	private boolean isUserInformationCorrect()
    	{
    	    boolean valide = true;
        	
    	    mPanelDescription.setDescription("","icons/alert.gif");
    	    
    	    if (mSupervisorNameTF.getText().equals(""))
            {
                mPanelDescription.appendToDescription(resMan.getString("errorUserNameEmpty")) ;
                valide = false ;
	        }
	        if (mSupervisorFirstNameTF.getText().equals("")) 
	        {
	            mPanelDescription.appendToDescription(resMan.getString("errorUserFirstNameEmpty")) ;
	            valide = false ;
	        }
	        
	        if (mMailTF.getText().equals("")) 
	        {
	            mPanelDescription.appendToDescription(resMan.getString("errorUserMailEmpty")) ;
	            valide = false ;
	        }
	        else
	        {
	            if (mMailTF.getText().lastIndexOf("@") == -1 || mMailTF.getText().lastIndexOf(".") == -1)
	            {
	                mPanelDescription.appendToDescription(resMan.getString("errorUserMailMistaken")) ;
	                valide = false ;
	            }
	        }

            if(mLoginTF.getText().equals(""))
            {
                mPanelDescription.appendToDescription(resMan.getString("errorUserLoginEmpty")) ;
                valide = false;
            }
        	return valide;
    	}
	}
}

