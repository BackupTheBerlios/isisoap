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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
import soap.model.executionProcess.structure.Project;
import soap.model.executionProcess.structure.user.Supervisor;
import soap.ui.dialog.NewProjectDialog;
import utils.ConfigManager;
import utils.ProjectManager;
import utils.ResourceManager;
import utils.SmartChooser;


public class NewProjectPanel extends SoapPanel
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
    protected NewProjectDialog mSoapDialog ;  
    
    private String mDescription ;
    
    public NewProjectPanel (NewProjectDialog s)
    {
        super() ;
        mSoapDialog = s ;
        initUI() ;
    }
    
    protected void initUI()
    {           
        ResourceManager resMan =  ResourceManager.getInstance() ;
        // button listener
        NewProjectButtonListener buttonListener = new NewProjectButtonListener() ;
        
		// get the button associate to the Jdialog which contain the panel 
        PanelButton panelButton =  mSoapDialog.getPanelButton() ;
        // ---button ok
        JButton okButton = panelButton.getOkButton() ;
        okButton.setActionCommand("ok") ;
        okButton.addActionListener(buttonListener);
        okButton.setEnabled(false) ;
        // ---button cancel
        JButton cancelButton = panelButton.getCancelButton() ;
        cancelButton.setActionCommand("cancel") ;
        cancelButton.addActionListener(buttonListener);
        
        // project
        NewProjectPanelListener projectListener =  new NewProjectPanelListener();   
        GridBagLayout gridbag = new GridBagLayout();
    	GridBagConstraints c = new GridBagConstraints();
        this.setLayout(gridbag);
        JLabel nameLabel = new JLabel(resMan.getString("projectName")) ;
        mNameProjectTF = new JTextField (30) ;  
        mNameProjectTF.addCaretListener(projectListener);
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
		mDefaultLocation.addActionListener(buttonListener) ;
		mDefaultLocation.setSelected(true) ;
		mExternalLocation = new JRadioButton(resMan.getString("inExternal")) ;
		mExternalLocation.setActionCommand("external");
		mExternalLocation.addActionListener(buttonListener) ;
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
		mBrowse.addActionListener(buttonListener) ;
		directoryPanel.add(mBrowse) ;
		locationPanel.add(mDefaultLocation) ;
		locationPanel.add(mExternalLocation) ;
		locationPanel.add(directoryPanel) ;   		
		
		
		
		/******indicators*****/
		/*// border
		JPanel indicatorsPanel = new JPanel() ;
        indicatorsPanel.setLayout(new GridLayout(3,1));
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyleIndicators = BorderFactory.createTitledBorder( border, "Indicateurs");
		indicatorsPanel.setBorder(titleStyleIndicators);
		
		// Indicators to display*/
		
		
		
		
		
		/******add******/
		//c.gridy = GridBagConstraints.
	    c.weightx = 1.0;
		c.weighty = 0 ;
		c.fill = GridBagConstraints.HORIZONTAL ;
				
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(nameLabel, c);
		this.add(nameLabel) ;
		
		c.weightx = 5.0;
		c.gridwidth = GridBagConstraints.REMAINDER ;
		gridbag.setConstraints(mNameProjectTF, c);
		this.add(mNameProjectTF) ;
		
		makeLabel(this, " ", gridbag, c);
		
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(supervisorName, c);
		this.add(supervisorName) ;
		
		c.weightx = 5.0;
		c.gridwidth = GridBagConstraints.REMAINDER ;
		gridbag.setConstraints(supervisorNameTF, c);
		this.add(supervisorNameTF) ;
		
		makeLabel(this, " ", gridbag, c);
		
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(supervisorFirstName, c);
		this.add(supervisorFirstName) ;
		
		c.weightx = 5.0;
		c.gridwidth = GridBagConstraints.REMAINDER ;
		gridbag.setConstraints(supervisorFirstNameTF, c);
		this.add(supervisorFirstNameTF) ;
		
		makeLabel(this, " ", gridbag, c);
		
		c.weightx = 1.0;
		gridbag.setConstraints(locationPanel, c);
		this.add(locationPanel) ;    
		
		this.setBorder(BorderFactory.createEmptyBorder(0,10,10,10)) ;
    } 
    
    private class NewProjectButtonListener implements ActionListener
    {        
        public void actionPerformed(ActionEvent event)
        {
            if (event.getActionCommand() == "external" )
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
        				int result = chooser.showDialog(mSoapDialog,"ok");
        				if (result == JFileChooser.APPROVE_OPTION )
						{
        				    String path = chooser.getSelectedFile().getAbsolutePath() +separator;
							mDirectoryPath.setText(path+mNameProjectTF.getText());
						}
                    }
                    else
                    {
                        if (event.getActionCommand().equals("ok"))
                        {
                            Supervisor supervisor = new Supervisor(supervisorFirstNameTF.getText(),supervisorNameTF.getText()) ;
                            Project project = new Project(mNameProjectTF.getText());
                            project.setSupervisor(supervisor) ;
                            //project.addAttribute("DirectoryPath", mDirectoryPath.getText()) ;
                            Context.getInstance().getListProjects().addProject(project);
                            ProjectManager.getInstance().initProject(project.getName(),ProjectManager.getInstance().getDefaultProjectProperties());
                            File project_dir = new File(mDirectoryPath.getText()) ;
                            //project_dir.mkdir() ;
                    	    NewProjectPanel.this.mSoapDialog.dispose();
                        }
                        else
                        {
                            NewProjectPanel.this.mSoapDialog.dispose();
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
                PanelDescription panelDesc =  NewProjectPanel.this.mSoapDialog.getPanelDescription() ;
                PanelButton panelButt = NewProjectPanel.this.mSoapDialog.getPanelButton() ;
                String text = ((JTextField)obj).getText() ;
                mDirectoryPath.setText(mDirectory+separator+text) ;
			    // check if the projects exists
                if (Context.getInstance().getListProjects().existProject(text)
			            && ! ( text.equals(panelDesc.getDescription() ))) 
			    {
			        panelDesc.setDescription(resMan.getString("pbExistingProject")) ;
			        panelButt.getOkButton().setEnabled(false) ;
			    }
			    else
			    {
			        // check if there is a space in the name
			        if (text.lastIndexOf(" ") != -1)
			        {
			            panelDesc.setDescription(resMan.getString("pbEspaceInName")) ;
			            panelButt.getOkButton().setEnabled(false) ;
			        }
			        else
			        {
			            panelDesc.setDescription(resMan.getString("createNewProject")) ;
			            // check if the name of the project is null
			            if (text.length() == 0)
			                panelButt.getOkButton().setEnabled(false) ;
			            else
			                panelButt.getOkButton().setEnabled(true) ;
			        }     
			     }    
            }
        }
    }    
}
