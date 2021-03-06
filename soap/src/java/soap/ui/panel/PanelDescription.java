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
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import utils.IconManager;

/**
 * a class helping users to create a panel which describe something
 *
 */
public class PanelDescription extends SoapPanel
{
    private Color mColor ;
    private JTextArea mLabelDescription = new JTextArea();
    private JLabel mImage ;
    private String mImageRessource = "";
      
    /**
     * @param description the description
     *
     */
    public PanelDescription(String description)
    {
        mLabelDescription = new JTextArea (description);
        mLabelDescription.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)) ;
        this.setLayout(new BorderLayout());
        this.add(mLabelDescription,BorderLayout.CENTER) ;
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEtchedBorder());
        mLabelDescription.setEditable(false);
    }
    
    /**
     * @param description the description
     * @param image the image uri associated to the description
     */
    public PanelDescription(String description, String image)
    {
        this(description);
        setImage(image);
        
    }

    /**
     * return the description
     * @return the description
     *
     */

    public String getDescription()
    {
        return mLabelDescription.getText() ;
    }
    
    /**
     * set the description
     * @param description the description
     */
    public void setDescription(String description)
    {
        mLabelDescription.setText(description);
    }
    
    /**
     * set the description
     * @param description the description
     * @param image the image uri associated to the description
     */
    public void setDescription(String description, String image)
    {
       setDescription(description);
       setImage(image);
    }
    
    /**
     * return the image uri
     * @return the image uri
     */
    public String getImageRessource()
    {
        return mImageRessource ;
    }
    
    /**
     * set the image associated to the description
     * @param image the image uri 
     */
    public void setImage(String image)
    {
        mImageRessource = image ;
        // remove the previous image
        if (mImage != null)
            this.remove(mImage);
        if(image.equals(""))
        {
            if (mImage != null )
            {
                this.remove(mImage);
            }
        }
        else
        {
            // add the new image
            Icon img = IconManager.getInstance().getIcon(image);
            mImage = new JLabel(img);
            mImage.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
            this.add(mImage,BorderLayout.EAST);
        }
        
    }
    
    /**
     * replace the panel description by a new one
     * @param panelDesc the new panel
     */

    public void replacePanelDescritpion(PanelDescription panelDesc)
    {
        this.setDescription(panelDesc.getDescription(), panelDesc.getImageRessource());
    }
    
    /**
     * add a description to the current description with a new line
     * @param description the new description to append
     */

    public void appendToDescriptionNL(String description)
    {
        String text = mLabelDescription.getText();
        if (!text.equals(""))
        {
            text += "\n" ;
        }
        text += description ;
        setDescription (text);
    }
    
    /**
     * add a description to the current description
     * @param description the new description to append
     */
    public void appendToDescription(String description)
    {
        String text = mLabelDescription.getText();
        if (!text.equals(""))
        {
            text += " - " ;
        }
        text += description ;
        setDescription (text);
    }
    
    /**
     * return the color of the background
     * @return the color
     */
    public Color getBackgroundColor()
    {
        return mColor ;
    }
    
    /**
     * set the color of the background
     * @param color the color
     */
    public void setBackgroundColor(Color color)
    {
        this.setBackground(color);
        mColor = color ;
    }
   
    
}
