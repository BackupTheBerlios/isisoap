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

package soap.ui.panel.configuration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import soap.ui.dialog.SeveralStepsDialog;
import soap.ui.dialog.SeveralStepsDialog.StepsValidator;
import soap.ui.panel.SoapGridbagPanel;
import utils.ResourceManager;

/**
 * panel to display the user information
 */

public class UserInformationPanel extends SoapGridbagPanel implements StepsValidator
{
   
    private JTextField supervisorNameTF = new JTextField(30) ;
    private JTextField supervisorFirstNameTF = new JTextField(30) ;
    private JTextField mailTF = new JTextField(30) ;
    private JTextField loginTF = new JTextField(30) ;
    private JPasswordField passTF = new JPasswordField();
    private static ResourceManager resMan = ResourceManager.getInstance(); 
    private JRadioButton director = new JRadioButton(resMan.getString("director")) ;
    private JRadioButton supervisor = new JRadioButton(resMan.getString("supervisor")) ;
    
    
    private SeveralStepsDialog mDialog ;
    
    
    public UserInformationPanel(SeveralStepsDialog dialog)
    {
        super();
        mDialog = dialog ;
        initUI() ;
    }
    
    private void initUI()
    {
        ResourceManager resMan =  ResourceManager.getInstance() ;
        
        JLabel statusLabel = new JLabel(resMan.getString("userStatus")) ;  
        JLabel supervisorName = new JLabel(resMan.getString("userName")) ;
        JLabel supervisorFirstName = new JLabel(resMan.getString("userFirstName")) ;
        JLabel mail = new JLabel(resMan.getString("userMail")) ;
        JLabel login = new JLabel(resMan.getString("userLogin")) ;
        JLabel pass = new JLabel(resMan.getString("userPassword")) ;
        loginTF.setEditable(false);
		passTF.setEditable(false);
        
        // radio button 
        ButtonListener listener = new ButtonListener();
        JPanel typeOfUser = new JPanel ();
		ButtonGroup locationButtonGroup = new ButtonGroup() ;
		director.setActionCommand("director");
		director.addActionListener(listener);
		supervisor.addActionListener(listener);
		supervisor.setActionCommand("supervisor");
		supervisor.setSelected(true) ;
		locationButtonGroup.add(supervisor);
		locationButtonGroup.add(director);
		typeOfUser.add(supervisor);
		typeOfUser.add(director);
		supervisorNameTF.setEditable(false);
		supervisorFirstNameTF.setEditable(false);
        mailTF.setEditable(false);
		

        this.defaultAddComponent(statusLabel, SoapGridbagPanel.RELATIVE,0.5,0) ;
        
		this.defaultAddComponent(typeOfUser,SoapGridbagPanel.END);
		
        this.addSpace();
        
        this.defaultAddComponent(supervisorName,SoapGridbagPanel.END);
        this.defaultAddComponent(supervisorNameTF,SoapGridbagPanel.END);
                
        this.defaultAddComponent(supervisorFirstName,SoapGridbagPanel.END);
        this.defaultAddComponent(supervisorFirstNameTF,SoapGridbagPanel.END);
        
        
        this.defaultAddComponent(mail,SoapGridbagPanel.END);
        this.defaultAddComponent(mailTF,SoapGridbagPanel.END);
                
        this.defaultAddComponent(login,SoapGridbagPanel.END);
        this.defaultAddComponent(loginTF,SoapGridbagPanel.END);
                
        this.defaultAddComponent(pass,SoapGridbagPanel.END);
        this.defaultAddComponent(passTF,SoapGridbagPanel.END);
        

        
        
        this.addLine(2);
    }
    
    public boolean validateSteps()
    {
        boolean valide = true;
        String mail ;
        
        mDialog.getPanelDescription().setDescription("","icons/alert.gif");
        
        if(director.isSelected())
        {
            if (supervisorNameTF.getText().equals(""))
            {
                mDialog.getPanelDescription().appendToDescription(resMan.getString("errorUserNameEmpty")) ;
                valide = false ;
	        }
	        if (supervisorFirstNameTF.getText().equals("")) 
	        {
	            mDialog.getPanelDescription().appendToDescription(resMan.getString("errorUserFirstNameEmpty")) ;
	            valide = false ;
	        }
	        
	        mail = mailTF.getText() ;
	        if (mail.equals("")) 
	        {
	            mDialog.getPanelDescription().appendToDescription(resMan.getString("errorUserMailEmpty")) ;
	            valide = false ;
	        }
	        else
	        {
	            if (mail.lastIndexOf("@") == -1 || mail.lastIndexOf(".") == -1)
	            {
	                mDialog.getPanelDescription().appendToDescription(resMan.getString("errorUserMailMistaken")) ;
	                valide = false ;
	            }
	        }

            if(loginTF.getText().equals(""))
            {
                mDialog.getPanelDescription().appendToDescription(resMan.getString("errorUserLoginEmpty")) ;
                valide = false;
            }
        }
        
        return valide ;
    }

    public void save()
    {
        
    }
    
    private class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        { 
            if (event.getActionCommand() == "director" )
            {
                loginTF.setEditable(true) ;
                passTF.setEditable(true);
                supervisorFirstNameTF.setEditable(true);
                supervisorNameTF.setEditable(true);
                mailTF.setEditable(true);
            }
            else
            {
                loginTF.setText("");
                passTF.setText("");
                supervisorFirstNameTF.setText("");
                supervisorNameTF.setText("");
                mailTF.setText("");
                loginTF.setEditable(false) ;
                passTF.setEditable(false);   
                supervisorFirstNameTF.setEditable(false);
                supervisorNameTF.setEditable(false);
                mailTF.setEditable(false);
            }
            
        }
    }
    
}
