/*
 * Created on 6 nov. 2004
 */
package soap.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
/**
 * @author yanagiba
 */
public class PanelDescription extends SoapPanel
{
    private Color mColor ;
    private JLabel mLabelDescription = new JLabel();
    
    
    public PanelDescription(String description)
    {
        mLabelDescription = new JLabel(description);
        mLabelDescription.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)) ;
        this.setLayout(new BorderLayout());
        this.add(mLabelDescription) ;
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
