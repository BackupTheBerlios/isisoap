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

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JTextField;

import soap.ui.dialog.SeveralStepsDialog;
import soap.ui.dialog.SeveralStepsDialog.StepsValidator;
import soap.ui.panel.SoapGridbagPanel;
import utils.ResourceManager;

/**
 * panel to display the server information
 */
public class ServerInformationPanel extends SoapGridbagPanel implements StepsValidator
{
    private SeveralStepsDialog mDialog ; 
    private JTextField mAddress ;
    private static ResourceManager resMan = ResourceManager.getInstance();
    
    public ServerInformationPanel(SeveralStepsDialog dialog)
    {
        super();
        mDialog = dialog ;
        initUI();
    }
    
    private void initUI()
    {
        JLabel label = new JLabel() ;
        label.setText(resMan.getString("enterServerInformation"));
        this.defaultAddComponent(label, SoapGridbagPanel.END) ;
        
        this.addSpace();
        
        label = new JLabel() ;
        label.setText(resMan.getString("serverAddress"));
        this.defaultAddComponent(label,SoapGridbagPanel.END);
        
        mAddress = new JTextField(30);
        this.defaultAddComponent(mAddress,SoapGridbagPanel.END,2,0);
        
        this.addLine(2);
    }
 
    
    public boolean validateSteps()
    {
        String address = ""  ;
        if(mAddress.getText().equals(""))
        {
            mDialog.getPanelDescription().setDescription(resMan.getString("errorServerAddressEmpty"),"icons/alert.gif");
            return false;
        }
        if (!mAddress.getText().startsWith("http://"))
        {
            address +="http://";
        }
        address+=mAddress.getText();
        try 
        {
            URL url = new URL(address);
        }
        catch (MalformedURLException ex)
        {
            mDialog.getPanelDescription().setDescription(resMan.getString("errorURLMalformed"),"icons/alert.gif");
            return false;
        }
        return true ;
    }
    
    public void save()
    {
        
    }
}
