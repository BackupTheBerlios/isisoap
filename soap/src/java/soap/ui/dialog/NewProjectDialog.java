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

import soap.ui.SoapDialog;
import soap.ui.panel.NewProjectPanel;
import soap.ui.panel.PanelButton;
import soap.ui.panel.PanelDescription;
import utils.ResourceManager;

public class NewProjectDialog extends SoapDialog
{
    private  PanelDescription mPanelDescription = new PanelDescription(ResourceManager.getInstance().getString("createNewProject")) ;
    private  PanelButton mPanelButton = new PanelButton(2);
    
    public NewProjectDialog(String title)
    {
        super(parent, title, true) ;
        initDialog() ;
    }
    
    public PanelDescription getPanelDescription()
    {
        return mPanelDescription ;
    }
    
    public PanelButton getPanelButton()
    {
        return mPanelButton ;
    }
    
    public void initDialog()
    {
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        content.add(mPanelDescription, BorderLayout.NORTH) ;
        content.add(new NewProjectPanel(this)) ;
        content.add(mPanelButton,BorderLayout.SOUTH) ;
        this.setSize(400,400);
        super.initUI() ;
    }
}
