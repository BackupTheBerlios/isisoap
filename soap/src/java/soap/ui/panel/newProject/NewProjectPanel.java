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

package soap.ui.panel.newProject;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import soap.Context;
import soap.ui.dialog.SeveralStepsDialog;
import soap.ui.dialog.SeveralStepsDialog.StepsValidator;
import soap.ui.panel.SoapGridbagPanel;
import utils.ConfigManager;
import utils.ResourceManager;
import utils.SmartChooser;

/**
 * panel to create new project
 *
 */


public class NewProjectPanel extends SoapGridbagPanel implements StepsValidator
{   
   
    protected String separator = System.getProperty("file.separator") ;
    protected String mDirectory = ConfigManager.getInstance().getProperty("WorkspaceDefaultPath") ;
    protected JTextField mNameProjectTF ;
    protected JTextField supervisorNameTF ;
    protected JTextField supervisorFirstNameTF ;
    protected JTextField mDirectoryPath ;
    protected JRadioButton mDefaultLocation ;
    protected JRadioButton mExternalLocation ;
    protected JButton mBrowse ;
    protected SeveralStepsDialog mDialog; 
    
    private String mDescription ;
    
    public NewProjectPanel (SeveralStepsDialog dialog)
    {
        super() ;
        mDialog = dialog ;
        initUI() ;
    }
    
    protected void initUI()
    {           
        ResourceManager resMan =  ResourceManager.getInstance() ;
        NewProjectButtonListener buttonListener = new NewProjectButtonListener(); 
       
        JLabel nameLabel = new JLabel(resMan.getString("projectName")) ;
        mNameProjectTF = new JTextField (30) ;  
        mNameProjectTF.addCaretListener(new NewProjectPanelListener());
        
        // supervisor
        JLabel supervisorName = new JLabel(resMan.getString("supervisorLastName")) ;
        supervisorNameTF = new JTextField(30) ;
        supervisorNameTF.setText("Aubry") ;
        supervisorNameTF.setEditable(false) ;
        JLabel supervisorFirstName = new JLabel(resMan.getString("supervisorFirstName")) ;
        supervisorFirstNameTF = new JTextField(30) ;
        supervisorFirstNameTF.setText("Claude");
        supervisorFirstNameTF.setEditable(false);
        // server
        JTextField servorTextField = new JTextField() ;
        
        /*****location*******/
        // border
        JPanel locationPanel = new JPanel() ;
        locationPanel.setLayout(new GridLayout(3,1));
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyle = BorderFactory.createTitledBorder( loweredetched,resMan.getString("location"));
		locationPanel.setBorder(titleStyle);
		
		// radio button 

		ButtonGroup locationButtonGroup = new ButtonGroup() ;
		mDefaultLocation = new JRadioButton(resMan.getString("inWorkspace")) ;
		mDefaultLocation.setActionCommand("default");
		mDefaultLocation.addActionListener(buttonListener);
		mDefaultLocation.setSelected(true) ;
		mExternalLocation = new JRadioButton(resMan.getString("inExternal")) ;
		mExternalLocation.setActionCommand("external");
		mExternalLocation.addActionListener(buttonListener);
		locationButtonGroup.add(mDefaultLocation);
		locationButtonGroup.add(mExternalLocation);
		
		// directory to choose
		JPanel directoryPanel = new JPanel();
		directoryPanel.setLayout(new FlowLayout()) ;
		directoryPanel.add( new JLabel(resMan.getString("directory"))) ;
		mDirectoryPath = new JTextField(15) ;
		mDirectoryPath.setEditable(false) ;
		mDirectoryPath.setText(mDirectory);
		directoryPanel.add(mDirectoryPath) ;
		mBrowse = new JButton(resMan.getString("browse")) ;
		mBrowse.setActionCommand("browse");
		mBrowse.setEnabled(false) ;
		mBrowse.addActionListener(buttonListener);
		directoryPanel.add(mBrowse) ;
		locationPanel.add(mDefaultLocation) ;
		locationPanel.add(mExternalLocation) ;
		locationPanel.add(directoryPanel) ;   		
		
		
		/******add******/
		
		this.defaultAddComponent(nameLabel, SoapGridbagPanel.RELATIVE, 1, 0, new Insets(0,10,0,10)) ;
		this.defaultAddComponent(mNameProjectTF,SoapGridbagPanel.END,5.0,0);
		
		this.addSpace();
		
		this.defaultAddComponent(supervisorName, SoapGridbagPanel.RELATIVE) ;
		this.defaultAddComponent(supervisorNameTF,SoapGridbagPanel.END,5.0,0);
		
		this.addSpace();
		
		this.defaultAddComponent(supervisorFirstName, SoapGridbagPanel.RELATIVE) ;
		this.defaultAddComponent(supervisorFirstNameTF,SoapGridbagPanel.END,5.0,0);
		
		this.addSpace();
		
		this.defaultAddComponent(locationPanel, SoapGridbagPanel.END) ;
		
    } 
    
    public String getProjectName()
    {
        return mNameProjectTF.getText();
    }
    
    public String getDirectory()
    {
        return mDirectoryPath.getText();
    }
    
    public String getSupervisorName()
    {
        return supervisorNameTF.getText();
    }
    
    public String getSupervisorFirstName()
    {
        return supervisorFirstNameTF.getText();
    }
    
    public void save()
    {
    }
   
    public boolean validateSteps()
    {
        String text = mNameProjectTF.getText() ;
        ResourceManager resMan = ResourceManager.getInstance();
        
        if(text.equals(""))
        {
            mDialog.getPanelDescription().setDescription(resMan.getString("pbProjectNameEmpty"),"icons/alert.gif") ;
            return false;
        }
        
        // check if the projects exists
        if (Context.getInstance().getListProjects().existProject(text)) 
	    {
            mDialog.getPanelDescription().setDescription(resMan.getString("pbExistingProject"),"icons/alert.gif") ;
	        return false;
	    }
	    else
	    {
	        // check if there is a space in the name
	        if (text.lastIndexOf(" ") != -1)
	        {
	            mDialog.getPanelDescription().setDescription(resMan.getString("pbEspaceInName"),"icons/alert.gif") ;
	            return false;
	        } 
	     } 
        return true;
    }
    
    private class NewProjectButtonListener implements ActionListener
    {        
        public void actionPerformed(ActionEvent event)
        {
            if (event.getActionCommand().equals("external") )
            {
                mDirectoryPath.setText("") ;
                mDirectoryPath.setEditable(true) ;
                mBrowse.setEnabled(true) ;
            }
            else
            {
                if (event.getActionCommand().equals("default"))
                {
                    mDirectoryPath.setText(mDirectory+separator+mNameProjectTF.getText()) ;
                    mDirectoryPath.setEditable(false) ;
                    mBrowse.setEnabled(false) ;
                }
                else
                {
                    if (event.getActionCommand().equals("browse"))
                    {
                        SmartChooser chooser = SmartChooser.getChooser();
                		chooser.setAcceptAllFileFilterUsed(false);
        				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        				int result = chooser.showDialog(mDialog,ResourceManager.getInstance().getString("ok"));
        				if (result == JFileChooser.APPROVE_OPTION )
						{
        				    String path = chooser.getSelectedFile().getAbsolutePath() +separator;
							mDirectoryPath.setText(path+mNameProjectTF.getText());
						}
                    }
                }
            }  
        } 
    }

    
    private class NewProjectPanelListener implements CaretListener
    {       
        public void caretUpdate(CaretEvent event)
        {
            ResourceManager resMan =  ResourceManager.getInstance() ;
            
            Object obj = event.getSource() ; 
            if ( obj instanceof JTextField)
            {
                String text = ((JTextField)obj).getText() ;
                mDirectoryPath.setText(mDirectory+separator+text) ;
            }
        }
    }    
}