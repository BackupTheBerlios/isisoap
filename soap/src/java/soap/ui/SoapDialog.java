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

package soap.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import soap.Context;

public class SoapDialog extends JDialog
{
    protected static SoapFrame parent = (SoapFrame)Context.getInstance().getTopLevelFrame();
    private JPanel mPanel ;
    
    
    public SoapDialog (JFrame owner, boolean modal) 
    {
        super(owner, "" , modal);
    }
    
    public SoapDialog (JFrame owner, String title, boolean modal) 
    {
        super(owner, title , modal);
    }

    
    protected void initUI()
    {
        this.setLocation(parent.getX()+(parent.getWidth()-this.getWidth())/2,parent.getY()+(parent.getHeight()-this.getHeight())/2);
        this.setResizable(false);
        this.setVisible (true) ;
    }

}
