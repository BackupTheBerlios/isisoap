/*
 * Created on 6 nov. 2004
 */
package soap.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Container;

import soap.ui.SoapDialog;
import soap.ui.panel.NewProjectPanel;
import soap.ui.panel.PanelButton;
import soap.ui.panel.PanelDescription;
import utils.ResourceManager;

/**
 * @author yanagiba
 */
public class NewProjectDialog extends SoapDialog
{
    private  PanelDescription mPanelDescription = new PanelDescription(ResourceManager.getInstance().getString("createNewProject")) ;
    private  PanelButton mPanelButton = new PanelButton(2);
    
    public NewProjectDialog(String title)
    {
        super(frame, title, true) ;
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
