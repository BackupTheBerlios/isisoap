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
package soap.ui.panel.innerCentralPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import soap.model.executionProcess.structure.user.Member;
import soap.ui.SoapMembersTable;
import soap.ui.CentralPanel.SoapProjectCentralPanel;
import soap.ui.dialog.UserAttributesDialog;
import soap.ui.panel.SoapGridbagPanel;


public class ProjectUsersPanel extends ProjectInnerCentralPanel
{
    private SoapMembersTable mTable;
    private JButton mRemoveButton;
    private JButton mModifyButton;
    
    public ProjectUsersPanel(SoapProjectCentralPanel parent)
    {
        super(parent);
        initUI();
    }
    
    protected void initUI()
    {
        // Create table panel
	    SoapGridbagPanel tablePanel = new SoapGridbagPanel();
		mTable = new SoapMembersTable();
		mTable.getSelectionModel().addListSelectionListener(new TableSelectionListener());
		mTable.setPreferredScrollableViewportSize(new Dimension(350,400));
		tablePanel.defaultAddComponent(mTable.getTableHeader(),SoapGridbagPanel.END);
		tablePanel.defaultAddComponent(new JScrollPane(mTable),SoapGridbagPanel.END, 1.0, 0,new Insets(0,20,0,0));
		
	    // Create buttons panel
	    SoapGridbagPanel buttonsPanel = new SoapGridbagPanel ();
	    UsersButtonsListener buttonListener = new UsersButtonsListener();
	    JButton newButton = new JButton(resMan.getString("new"));
	    mModifyButton = new JButton(resMan.getString("modify"));
	    mRemoveButton = new JButton(resMan.getString("remove"));
	    newButton.addActionListener(buttonListener);
	    mModifyButton.addActionListener(buttonListener);
	    mRemoveButton.addActionListener(buttonListener);
	    mModifyButton.setEnabled(false);
        mRemoveButton.setEnabled(false);
	    buttonsPanel.defaultAddComponent(newButton,SoapGridbagPanel.END, 1.0, 0, new Insets(5,30,5,30));
	    buttonsPanel.defaultAddComponent(mModifyButton,SoapGridbagPanel.END, 1.0, 0, new Insets(5,30,5,30));
	    buttonsPanel.defaultAddComponent(mRemoveButton,SoapGridbagPanel.END, 1.0, 0, new Insets(5,30,300,30));
	    
	    // Add to user Panel
	    this.add(new JScrollPane());
		defaultAddComponent(tablePanel, SoapGridbagPanel.RELATIVE);
		defaultAddComponent(buttonsPanel, SoapGridbagPanel.END);
		
    }
    private class UsersButtonsListener implements ActionListener
	{
	    
	    public void actionPerformed(ActionEvent event)
        {
           JButton button = (JButton)event.getSource();
           if(button.getText().equals(resMan.getString("new")))
           {
               UserAttributesDialog dialog = new UserAttributesDialog (resMan.getString("memberCreateDialogTitle"),resMan.getString("memberCreateDialogDescription"));
           }
           else
           {
               Member member = mProject.getMember(mTable.getSelectedRow());
               if(button.getText().equals(resMan.getString("modify")))
               {
                   UserAttributesDialog dialog = new UserAttributesDialog (resMan.getString("memberModifyDialogTitle"),resMan.getString("memberModifyDialogDescription"), member);
               }
               else
               {
                   if(button.getText().equals(resMan.getString("remove")))
                   {
                       mProject.removeMember(member); 
                       mTable.getSelectionModel().clearSelection();
                       mModifyButton.setEnabled(false);
                       mRemoveButton.setEnabled(false);
                   }  
               } 
           }
           mTable.updateUI();
        }
}
	
	private class TableSelectionListener implements ListSelectionListener
	{
	    public void valueChanged(ListSelectionEvent event)
        {
	        mModifyButton.setEnabled(true);
            mRemoveButton.setEnabled(true);
        }
	}
}
