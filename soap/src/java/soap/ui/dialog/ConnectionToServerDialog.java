/*
 * Created on 27 nov. 2004
 */
package soap.ui.dialog;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import soap.processing.ConnectToServer;
import soap.ui.SoapDialog;
import soap.ui.panel.PanelDescription;
import utils.MonitoredTaskBase;
import utils.ResourceManager;

/**
 * @author masahiko
 */
public class ConnectionToServerDialog extends SoapDialog
{
    private ResourceManager mResources = ResourceManager.getInstance();
	
	public final static int MONITOR_DELAY = 100;
	public final static String NL = "\n";

	private JProgressBar mProgressBar = new JProgressBar();
	private JTextArea mTaskOutput = new JTextArea(5, 20);
	private JButton mClose = new JButton("Close");
	private Timer mTimer;
	private MonitoredTaskBase mTask;
	private String mLastMessage = null;
	private PanelDescription mPanelDescription = new PanelDescription("connexion");
	


	/**
	 * @param owner
	 * @param task
	 * @throws java.awt.HeadlessException
	 */
	public ConnectionToServerDialog(JFrame owner, MonitoredTaskBase task) throws HeadlessException
	{
		super(owner, true);
		init(task);
	}

	private void init(MonitoredTaskBase task)
	{
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		mTask = task;
		mTaskOutput.setEditable(false);
		mClose.setEnabled(false);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mPanelDescription, BorderLayout.NORTH);
		JPanel centerPanel = new JPanel() ;
		centerPanel.setLayout(new BorderLayout()) ;
		centerPanel.add(mProgressBar,BorderLayout.NORTH);
		mTaskOutput.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)) ;
		centerPanel.add(mTaskOutput,BorderLayout.CENTER) ;
		JScrollPane scroll = new JScrollPane(centerPanel);
		getContentPane().add(scroll, BorderLayout.CENTER);
		Box b = Box.createHorizontalBox();
		b.add(Box.createHorizontalGlue());
		b.add(mClose);
		b.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		getContentPane().add(b, BorderLayout.SOUTH);
		
		mClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		
		mTimer = new Timer(MONITOR_DELAY, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				checkStates();
			}
		  });
		
		checkStates();
		this.setSize(300,200);
	}
	
	public void forceRefresh()
	{
		mTimer.stop();
		checkStates();
		mTimer.start();
	}
	
	private void checkStates()
	{
		if(mTask.done())
		{
			mTimer.stop();

			String message = mTask.getMessage();
			
			if(message!=null && !message.equals(mLastMessage))
			{
			    mPanelDescription.setDescription(message) ;
				mTaskOutput.append(message+NL);
				mTaskOutput.setCaretPosition(mTaskOutput.getDocument().getLength());
			}

			mProgressBar.setStringPainted(true);
			mProgressBar.setIndeterminate(false);
			mProgressBar.setMinimum(0);
			mProgressBar.setMaximum(100);
			mProgressBar.setValue(100);
			
			mClose.setEnabled(true);
			setDefaultCloseOperation( DISPOSE_ON_CLOSE);
			Toolkit.getDefaultToolkit().beep();
			if (((ConnectToServer)mTask).getConnectionStatus() == ConnectToServer.CONNECTED)
			{
			    this.dispose() ;
			}
			return;
		}
		
		int current = mTask.getCurrent();
		int length = mTask.getLengthOfTask();
		String message = mTask.getMessage();
		
		if(message!=null && !message.equals(mLastMessage))
		{
		    mPanelDescription.setDescription(message) ;
			mTaskOutput.append(message+NL);
			mTaskOutput.setCaretPosition(mTaskOutput.getDocument().getLength());
			mLastMessage = message;
		}
		
		if(length==MonitoredTaskBase.INDETERMINED)
		{
			mProgressBar.setStringPainted(false);
			mProgressBar.setIndeterminate(true);
		}
		else
		{
			mProgressBar.setStringPainted(true);
			mProgressBar.setIndeterminate(false);
			mProgressBar.setMinimum(0);
			mProgressBar.setMaximum(length);
			mProgressBar.setValue(current);
		}
	}
	
	
	public void show()
	{
		mTimer.start();
		mTask.go();
		super.show();
	}
	
	
}
