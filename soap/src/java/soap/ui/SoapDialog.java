/*
 * Created on 5 nov. 2004
 */
package soap.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import soap.Context;

/**
 * @author yanagiba
 */
public class SoapDialog extends JDialog
{
    protected static SoapFrame frame = (SoapFrame)Context.getInstance().getTopLevelFrame();
    private JPanel mPanel ;
    
    
    public SoapDialog (JFrame owner, boolean modal) 
    {
        super(owner, "" , modal);
    }
    
    public SoapDialog (JFrame owner, String title, boolean modal) 
    {
        super(owner, title , modal);
    }

    
    public void initUI()
    {
        this.setLocation(frame.getX()+(frame.getWidth()-this.getWidth())/2,frame.getY()+(frame.getHeight()-this.getHeight())/2);
        this.setResizable(false);
        this.setVisible (true) ;
    }

}
