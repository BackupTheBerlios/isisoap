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

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

public class SoapTextPanel extends SoapPanel
{
    private JTextArea mText = new JTextArea();
    
    public SoapTextPanel ()
    {
        this.setLayout(new BorderLayout()) ;
        mText.setBorder(BorderFactory.createEmptyBorder(5,5,10,10));
        this.add(mText,BorderLayout.CENTER);
        
    }

    public void setText (String text)
    {
        mText.setText(text) ;
    }
}
