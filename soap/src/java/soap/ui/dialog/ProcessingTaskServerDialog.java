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

import soap.processing.ProcessingTaskServer;
import soap.server.ConnectionManager;
import soap.ui.SoapDialog;
import soap.ui.panel.PanelDescription;
import utils.MonitoredTaskBase;
import utils.ResourceManager;

public class ProcessingTaskServerDialog extends SoapDialog
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
	public ProcessingTaskServerDialog(JFrame owner, MonitoredTaskBase task) throws HeadlessException
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
			if (((ProcessingTaskServer)mTask).getResultTask() == ConnectionManager.CONNECTED)
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
