/*
 * Created on 5 nov. 2004
 */
package soap.ui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author yanagiba
 */
public class SoapPanel extends JPanel
{
    private String mName ;
    
    public SoapPanel ()
    {
        super() ;
    }
    
    public SoapPanel (String name)
    {
        super() ;
        mName = name ;
    }
    
    public String getName()
    {
       return mName ; 
    }
    
    public void setName (String name)				
	{
        this.mName = name ;
	}
    
    protected void makeButton(String name, GridBagLayout gridbag,
			  GridBagConstraints c) 
	{
        JButton button = new JButton(name);
		gridbag.setConstraints(button, c);
		//mPanel.add(button);
	}
	public static void  makeLabel(JPanel panel, String name,
				  				GridBagLayout gridbag,
				  					GridBagConstraints c) 
	{
		JLabel label = new JLabel(name);
		gridbag.setConstraints(label, c);
		panel.add(label);
	}
}
