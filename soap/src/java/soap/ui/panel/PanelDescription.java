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

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class PanelDescription extends SoapPanel
{
    private Color mColor ;
    private JLabel mLabelDescription = new JLabel();
      
    public PanelDescription(String description)
    {
        mLabelDescription = new JLabel(description);
        mLabelDescription.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)) ;
        this.setLayout(new BorderLayout());
        this.add(mLabelDescription,BorderLayout.CENTER) ;
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEtchedBorder());
    }

    
    public String getDescription()
    {
        return mLabelDescription.getText() ;
    }
    
    public void setDescription(String description)
    {
        mLabelDescription.setText(description);
    }
    
    public Color getBackgroundColor()
    {
        return mColor ;
    }
    
    public void setBackgroundColor(Color color)
    {
        this.setBackground(color);
        mColor = color ;
    }
   
    
}
