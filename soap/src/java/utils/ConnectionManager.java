/*
 * Created on 28 nov. 2004
 */
package utils;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import soap.Context;
import soap.processing.ConnectToServer;
import soap.ui.SoapDialog;
import soap.ui.SoapFrame;
import soap.ui.dialog.ConnectionToServerDialog;
import soap.ui.panel.PanelButton;
import soap.ui.panel.PanelDescription;

/**
 * @author masahiko
 */
public class ConnectionManager
{
    public static int NOT_CONNECTED = 0;
    public static int CONNECTED = 1;
    
    private ConnectionToServerDialog mTaskDialog ;
    private ConnectToServer mMonitor = null;
    private static ConnectionManager mConnectionManager = new ConnectionManager();
    private String mLogin = null;
    private String mPassword = null;
    private URLConnection mUrlConnection ;
    private URL mUrl ;
   

    public ConnectionManager ()
    {
        
    }
    
    public static ConnectionManager getInstance()
	{
		return mConnectionManager;
	}
    
    
    public int getConnection () 
    {
       if (mLogin == null || mPassword==null)
       {
			IdentificationDialog identDialog = new IdentificationDialog("fdfd") ;
			if (identDialog.showIdentificationDialog() == IdentificationDialog.CONNECT)
			{
				mMonitor = new ConnectToServer(identDialog.getLogin(),identDialog.getPassword());
	           	//mMonitor = new ConnectToServer() ;
				SoapFrame parent = (SoapFrame)Context.getInstance().getTopLevelFrame();				
				mTaskDialog = new ConnectionToServerDialog(parent,mMonitor);
				mTaskDialog.setName("Loading");
				mTaskDialog.setLocation(parent.getWidth()/2-mTaskDialog.getWidth()/2,parent.getHeight()/2-mTaskDialog.getHeight()/2);	
				mMonitor.setTask(mTaskDialog);
				mTaskDialog.show();
				if (mMonitor.getConnectionStatus() == ConnectToServer.CONNECTED)
				{
				    mLogin = identDialog.getLogin() ;
				    mPassword = identDialog.getPassword() ;
				    mTaskDialog.dispose() ;
				    return CONNECTED ;
				}
				else
				{
				    return NOT_CONNECTED ;
				}
				
			}
			else
			{
			    return NOT_CONNECTED ;
			}
			
       }
       return CONNECTED ;
     }   
    
    private class IdentificationDialog extends SoapDialog
    {
        public static final int CONNECT = 1 ;
        public static final int CANCEL = 0 ;
     
        private PanelDescription mPanelDescription = new PanelDescription(ResourceManager.getInstance().getString("identification")) ;
        private PanelButton mPanelButton = new PanelButton();
        private JTextField mLoginTF = new JTextField() ;
        private JTextField mPassTF = new JTextField() ;
        private ActionListener buttonListener = new ButtonListener() ;
        private int mButtonClick ;
        
        public IdentificationDialog(String title)
        {
            super(frame, title, true) ;
            
        }  
      
        public void initDialog()
        {
            Container content = this.getContentPane();
            content.setLayout(new BorderLayout());
            content.add(mPanelDescription, BorderLayout.NORTH) ;
            mPanelButton.addButton("connect") ;
            mPanelButton.addButton("cancel");
            mPanelButton.addButtonListener(buttonListener) ;
            JPanel identifyPanel = new JPanel() ;
            JLabel logLabel = new JLabel("Login ");
            JLabel passLabel = new JLabel("pass ");
            identifyPanel.setLayout(new GridLayout(2,2)) ;
            identifyPanel.add(logLabel) ;
            identifyPanel.add(mLoginTF) ;
            identifyPanel.add(passLabel) ;
            identifyPanel.add(mPassTF) ;
            identifyPanel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10)) ;
            content.add(identifyPanel,BorderLayout.CENTER) ;
            content.add(mPanelButton,BorderLayout.SOUTH) ;
            this.setSize(200,200);
            super.initUI() ;   
        }
           
        
        public int showIdentificationDialog()
        {
            initDialog() ;
            return mButtonClick ;
        }
        
        public String getLogin()
        {
            return mLoginTF.getText() ;
        }
        
        public String getPassword()
        {
            return mPassTF.getText() ;
        }
            
        private class ButtonListener implements ActionListener
        {
            
 	       public void actionPerformed(ActionEvent event)
 	       {
 	           Object o = event.getSource() ;
 	           if (o instanceof JButton)
 	           {
 	               if (((JButton)o).getLabel().equals("connect"))
 	               {
 	                   IdentificationDialog.this.mButtonClick = CONNECT ;
 	               }
 	               else
 	               {
 	                   if (((JButton)o).getLabel().equals("cancel"))
 	                   {
 	                       IdentificationDialog.this.mButtonClick = CANCEL;
 	                      
 	                   }
 	               }
 	               IdentificationDialog.this.dispose();
 	           }
 	       }
 	   }
    }
    
}