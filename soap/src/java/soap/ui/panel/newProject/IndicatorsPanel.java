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
package soap.ui.panel.newProject;


import java.awt.Insets;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import soap.Context;
import soap.adapters.SoapListDefaultsIndicatorsAdapter;
import soap.adapters.SoapListDefaultsIndicatorsAdapter.IndicatorsListSelectionListener;
import soap.model.executionProcess.structure.Project;
import soap.model.executionProcess.structure.user.Supervisor;
import soap.ui.dialog.SeveralStepsDialog;
import soap.ui.dialog.SeveralStepsDialog.StepsValidator;
import soap.ui.panel.SoapGridbagPanel;
import utils.ProjectManager;
import utils.ResourceManager;

/**
 * panel to display the indicator to choose
 *
 */


public class IndicatorsPanel extends SoapGridbagPanel implements StepsValidator
{
    protected SeveralStepsDialog mDialog; 
    private SoapListDefaultsIndicatorsAdapter mListIndicatorsModel ;
    
    public IndicatorsPanel (SeveralStepsDialog dialog)
    {
        super() ;
        mDialog = dialog ;
        initUI() ;
    }
    
    protected void initUI()
    { 
        SoapGridbagPanel indicatorsPanel = new SoapGridbagPanel();
        JLabel chooseIndicators = new JLabel(ResourceManager.getInstance().getString("chooseIndicators"));
        mListIndicatorsModel = new SoapListDefaultsIndicatorsAdapter();
        JList listIndicators = new JList(mListIndicatorsModel);
        listIndicators.addListSelectionListener(mListIndicatorsModel.new IndicatorsListSelectionListener());
        
        indicatorsPanel.defaultAddComponent(chooseIndicators,SoapGridbagPanel.END,1,0,new Insets (10,10,10,10));
        indicatorsPanel.defaultAddComponent(listIndicators,SoapGridbagPanel.END,1,0, new Insets (10,10,10,10));
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyle = BorderFactory.createTitledBorder( loweredetched,ResourceManager.getInstance().getString("defaultIndicators"));
		indicatorsPanel.setBorder(titleStyle);
		
		defaultAddComponent(indicatorsPanel,SoapGridbagPanel.END, 1, 0, new Insets(20,10,0,10));
		addLine(2);
    }
    
    public void save()
    {
        NewProjectPanel newProjectPanel = (NewProjectPanel)mDialog.getPanel(0);
        
        Supervisor supervisor = new Supervisor(newProjectPanel.getSupervisorFirstName(),newProjectPanel.getSupervisorName()) ;
        supervisor.setEMail("ca@aubryconseil.com");
        supervisor.setLogin("aubry");
        supervisor.setPassword("aubry");
        
        Project project = new Project(newProjectPanel.getProjectName());
        project.setSupervisor(supervisor) ;
        //project.addAttribute("DirectoryPath", mDirectoryPath.getText()) ;
        mListIndicatorsModel.saveDefaultIndicators(project.getName());
        Context.getInstance().getListProjects().addProject(project);
        File project_dir = new File(newProjectPanel.getDirectory()) ;
        // save the directory
        String projectName = Context.getInstance().getListProjects().getCurrentProject().getName();
		ProjectManager.getInstance().setProperty(projectName,"defaultProjectDirectoryPath",newProjectPanel.getDirectory());
        project_dir.mkdir() ;
    }
    
    public boolean validateSteps()
    {
        return true;
    }
}
