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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import utils.ConfigManager;
import utils.ResourceManager;

public class InformationPanel extends SoapPanel
{
    protected String separator = System.getProperty("file.separator") ;
    protected String mDirectory = ConfigManager.getInstance().getProperty("WorkspaceDefaultPath") ;
    protected JTextField supervisorNameTF ;
    protected JTextField supervisorFirstNameTF ;
    protected JTextField mailTF ;

    
    public InformationPanel()
    {
        super();
        initUI() ;
    }
    
    public void initUI()
    {
        ResourceManager resMan =  ResourceManager.getInstance() ;
        
        GridBagLayout gridbag = new GridBagLayout();
    	GridBagConstraints c = new GridBagConstraints();
        this.setLayout(gridbag);
        
        JLabel introductionLabel = new JLabel("Veuillez remplir les informations vous concernant") ;  
        JLabel supervisorName = new JLabel(resMan.getString("supervisorLastName")) ;
        supervisorNameTF = new JTextField(30) ;
        JLabel supervisorFirstName = new JLabel(resMan.getString("supervisorFirstName")) ;
        supervisorFirstNameTF = new JTextField(30) ;
        JLabel mail = new JLabel("votre e-mail") ;
        mailTF = new JTextField(30) ;
        
        

		/******add******/
		//c.gridy = GridBagConstraints.
	    c.weightx = 1.0;
		c.weighty = 0 ;
		c.fill = GridBagConstraints.HORIZONTAL ;
				
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(introductionLabel, c);
		this.add(introductionLabel) ;
		
		
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
		
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER ;
		gridbag.setConstraints(supervisorFirstNameTF, c);
		this.add(supervisorFirstNameTF) ;
		
		makeLabel(this, " ", gridbag, c);
		
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(mail, c);
		this.add(mail) ;
		
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER ;
		gridbag.setConstraints(mailTF, c);
		this.add(mailTF) ;
		
		
		c.weighty = 2.0;
		c.gridwidth = GridBagConstraints.REMAINDER ;
		makeLabel(this, " ", gridbag, c);
		  
		
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)) ;
    }
}
