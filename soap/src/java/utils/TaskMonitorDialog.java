/*
 * APES is a Process Engineering Software
 * This file was part of : cameleon
 * Copyright (C) 2003 Nathalie Aussenac-Gilles (CNRS)
 * Copyright (C) 2003-2004 IPSquad
 * team@ipsquad.tuxfamily.org
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

package utils;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;


/**
 * Open a new project in the application
 *
 * @version $Revision: 1.1 $
 */
public class TaskMonitorDialog extends JDialog
{
	private ResourceManager mResources = ResourceManager.getInstance();
	
	public final static int MONITOR_DELAY = 100;
	public final static String NL = "\n";

	private JProgressBar mProgressBar = new JProgressBar();
	private JTextArea mTaskOutput = new JTextArea(13, 20);
	private JButton mClose = new JButton("Close");
	private Timer mTimer;
	private MonitoredTaskBase mTask;
	private String mLastMessage = null;
	
	
	/**
	 * @param owner
	 * @param task
	 * @throws java.awt.HeadlessException
	 */
	public TaskMonitorDialog(Frame owner, MonitoredTaskBase task) throws HeadlessException
	{
		super(owner, true);
		init(task);
	}

	/**
	 * @param owner
	 * @throws java.awt.HeadlessException
	 */
	public TaskMonitorDialog(Dialog owner, MonitoredTaskBase task) throws HeadlessException
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
		
		getContentPane().add(mProgressBar, BorderLayout.NORTH);
		JScrollPane scroll = new JScrollPane(mTaskOutput);
		getContentPane().add(scroll, BorderLayout.CENTER);
		
		Box b = Box.createHorizontalBox();
		b.add(Box.createHorizontalGlue());
		b.add(mClose);
		
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
		pack();
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
			return;
		}
		
		int current = mTask.getCurrent();
		int length = mTask.getLengthOfTask();
		String message = mTask.getMessage();
		
		if(message!=null && !message.equals(mLastMessage))
		{
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
