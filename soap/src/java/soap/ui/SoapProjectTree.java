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

package soap.ui;

import java.awt.Component;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import soap.Context;
import soap.adapters.SoapTreeAdapter;
import soap.adapters.SoapTreeNode;
import soap.model.executionProcess.structure.Project;
import soap.ui.CentralPanel.SoapCentralPanel;

public class SoapProjectTree extends JTree implements  TreeModelListener
{
	
	public SoapProjectTree()
	{
		super(new SoapTreeAdapter());
		setEditable(false);
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		SoapTreeCellRenderer renderer = new SoapTreeCellRenderer();
		setCellRenderer(renderer);
		setCellEditor(new SoapTreeCellEditor(this, renderer));
		setToggleClickCount(0);
		
		
		setExpandsSelectedPaths(true);
		setInvokesStopCellEditing(true);
		
		final SoapProjectTree local_tree = this;
		MouseListener ml = new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
			    int selRow = getRowForLocation(e.getX(), e.getY());
				TreePath selPath = getPathForLocation(e.getX(), e.getY());
				if(selRow != -1)
				{
				    Object tabTreeNode [] = selPath.getPath(); 
				    if(tabTreeNode.length >= 2)
				    {
				        Context.getInstance().getListProjects().setCurrentProject((Project)((SoapTreeNode)tabTreeNode[1]).getUserObject());
				    }
				}
				// the modifiers test is needed in order to make it work on OSes that don't correctly set the isPopupTrigger flag (swing sux0r)
				if(selRow != -1 && (e.isPopupTrigger() || (e.getModifiers() & InputEvent.BUTTON3_MASK)!=0) )
				{
					JPopupMenu popup = associateMenu(selPath.getLastPathComponent());
					if(popup!=null)
					{
						e.consume();
						setSelectionPath(selPath);
						popup.show(local_tree, e.getX(), e.getY());
					}
				}
				else if(selRow != -1 && e.getClickCount()==1)
				{
					e.consume();
					SoapCentralPanel centralPanel = associatePanel(selPath.getLastPathComponent());
					((SoapFrame)Context.getInstance().getTopLevelFrame()).openCentralPanel(centralPanel);
				}
				else if(selRow != -1 && e.getClickCount()==2)
				{
					e.consume();
					((SoapTreeAdapter)getModel()).elementAction(selPath.getLastPathComponent());
				}
				else if(selRow != -1 && e.getClickCount()==3)
				{
					e.consume();
					startEditingAtPath(selPath);
				}
			}
		};
		
		addMouseListener(ml);
		
		if(getModel()!=null)
		{
			getModel().addTreeModelListener(this);
		}
	}
	
	public void setModel(TreeModel newModel)
	{
		if(getModel()!=null)
		{
			getModel().removeTreeModelListener(this);
		}
		
		super.setModel(newModel);
		
		if(getModel()!=null)
		{
			getModel().addTreeModelListener(this);
		}
	}

	public boolean isPathEditable(TreePath path)
	{
		if(path.getLastPathComponent() == getModel().getRoot())
		{
			return false;
		}
		
		return super.isPathEditable(path);
	}
	
	private Icon associateIcon(Object value)
	{
		return ((SoapTreeAdapter)getModel()).associateIcon(value);
	}
	
	private JPopupMenu associateMenu(Object value)
	{
		return ((SoapTreeAdapter)getModel()).associateMenu(value);
	}

	private SoapCentralPanel associatePanel(Object value)
	{
		return ((SoapTreeAdapter)getModel()).associatePanel(value);
	}
	
	private class SoapTreeCellRenderer extends DefaultTreeCellRenderer
	{
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                              boolean leaf, int row, boolean hasFocus)
		{
			Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			
			if( value instanceof SoapTreeNode )
			{
				SoapTreeNode node = (SoapTreeNode)value;
				setIcon(associateIcon(node));
			}
			
			return c;
		}
		
	}
	
	private class SoapTreeCellEditor extends DefaultTreeCellEditor implements CellEditorListener
	{
		private SoapTreeNode mCurrentNode = null;
		
		
		
		public SoapTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer)
		{
			super(tree, renderer);

			
			addCellEditorListener(this);
		}
		
		public Component getTreeCellEditorComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                              boolean leaf, int row)
		{
			Component c = super.getTreeCellEditorComponent(tree, value, sel, expanded, leaf, row);
			editingIcon = associateIcon(value);
			
			if( editingComponent instanceof JTextField && value instanceof SoapTreeNode )
			{
				mCurrentNode = (SoapTreeNode)value;
				((JTextField)editingComponent).selectAll();
			}
			
			return c;
		}

		
		public void editingStopped(ChangeEvent e)
		{
		}


        public void editingCanceled(ChangeEvent arg0)
        {            
        }
	}

	
	protected void processMouseEvent(MouseEvent e) 
	{
		//catch an exception throws by BasicTreeUI$MouseHandler.handleSelection(BasicTreeUI.java:2815)
		//the exception is throwed when you editing a TreeNode and stop editing by clicking in the tree
		try{ super.processMouseEvent(e); }catch(Throwable t){}
	}

    public void treeNodesInserted(TreeModelEvent event)
    {
          // display the new project inserted  
         Object tabTreeNode [] = event.getTreePath().getPath(); 
         if(tabTreeNode.length == 1)
   		 {
             if(event.getChildIndices() != null)
             {
                 SoapTreeNode inserted = (SoapTreeNode)getModel().getChild(tabTreeNode[0],event.getChildIndices()[event.getChildIndices().length-1]);
                 Context.getInstance().getListProjects().setCurrentProject((Project)inserted.getUserObject());
                 SoapCentralPanel centralTabbedPane = associatePanel(inserted);
         		((SoapFrame)Context.getInstance().getTopLevelFrame()).openCentralPanel(centralTabbedPane);
         		setSelectionPath(new TreePath(inserted.getPath()));
             }
        }
        updateUI();
    }


    public void treeNodesRemoved(TreeModelEvent event)
    {
        SoapCentralPanel centralTabbedPane ;
        
        // retrieve the parent node of the node removed
        TreePath parentPath = event.getTreePath() ;
        if (getRowForPath(parentPath) == 0)
        {
            try 
            {
                // get the last project in the treenode and display the centralTabbePane
                SoapTreeNode lastChild = (SoapTreeNode)((SoapTreeNode)parentPath.getLastPathComponent()).getLastChild();
                centralTabbedPane = associatePanel(lastChild);
                ((SoapFrame)Context.getInstance().getTopLevelFrame()).openCentralPanel(centralTabbedPane);
            }
            catch (Exception e)
            {
                //case when it is the root
                Context.getInstance().getListProjects().setCurrentProject(null);
                centralTabbedPane = associatePanel((SoapTreeNode)parentPath.getLastPathComponent());
                ((SoapFrame)Context.getInstance().getTopLevelFrame()).openCentralPanel(centralTabbedPane);
            }
        }
        updateUI(); 
    }
    
    public void treeNodesChanged(TreeModelEvent e) 
	{
		updateUI(); 
	}

    public void treeStructureChanged(TreeModelEvent arg0)
    {
        
    }
}
