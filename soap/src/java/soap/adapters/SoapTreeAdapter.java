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
package soap.adapters;


import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import soap.Identity;
import soap.model.core.Element;
import soap.model.extension.SoapListProjects;
import soap.model.extension.SoapProcess;
import soap.model.frontend.SoapMediator;
import soap.model.frontend.event.SoapEvent;
import soap.ui.tabbedPane.SoapCentralTabbedPane;
import utils.Debug;
import utils.IconManager;

public class SoapTreeAdapter implements TreeModel, SoapMediator.Listener
{
    private EventListenerList mListenerList = new EventListenerList();
    private SoapTreeNode mRoot = new SoapTreeNode(new SoapListProjects("Project"), false);
    
    public SoapTreeAdapter()
    {
        super();
    }
    
    public void setRoot(SoapListProjects root)
	{
		if( mRoot == null || mRoot.getUserObject() != root )
		{	
			mRoot = new SoapTreeNode(root, false);
			Object[] path = {mRoot};
			fireTreeNodesInserted(this, path, null, null);
		}
	}
    
    public void setRoot(SoapProcess root)
	{
		if( mRoot == null || mRoot.getUserObject() != root )
		{	
			mRoot = new SoapTreeNode(root, false);
			Object[] path = {mRoot};
			fireTreeStructureChanged(this, path, null, null);
		}
	}
    
    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getRoot()
     */
    public Object getRoot()
    {
        return mRoot;
    }

    /**
	 * Get a child of a tree node by giving its index
	 *
	 * @param parent the node
	 * @param index the index of the child
	 * @return the chil or null if it does not exists
	 */
	public Object getChild(Object parent, int index)
	{	
		if( !(parent instanceof SoapTreeNode) )
		{
			return null;
		}
		TreeNode node = (TreeNode)parent;
		
		if( index < 0 || index >= node.getChildCount() )
		{
			return null;
		}
		
		return ((TreeNode)parent).getChildAt(index);
	}

	/**
	 * Get the number of child of a node
	 *
	 * @param parent the node
	 * @return the number of child
	 */
	public int getChildCount(Object parent)
	{
		if( parent instanceof TreeNode )
		{
			return ((TreeNode)parent).getChildCount();
		}
		return -1;
	}

	/**
	 * Check if a node is a leaf of the tree
	 *
	 * @param node the node
	 * @return true if the node is a leaf, false otherwise
	 */
	public boolean isLeaf(Object node)
	{
		return ((TreeNode)node).isLeaf();
	}


	/**
	 * Find the index of a child in a node
	 *
	 * @param parent the node
	 * @param child the child to evaluate
	 * @return the index of the child or -1 if child is not in parent
	 */
	public int getIndexOfChild(Object parent, Object child)
	{
		if( parent instanceof TreeNode && child instanceof TreeNode )
		{	
			return ((TreeNode)parent).getIndex((TreeNode)child);
		}
		return -1;
	}

	public TreeNode[] getPathToRoot(SoapTreeNode node)
	{
		return node.getPath();
	}
	/**
	 * Add a listener to this tree model
	 *
	 * @param l the listener to add
	 */
	public void addTreeModelListener(TreeModelListener l)
	{
		mListenerList.remove(TreeModelListener.class, l);
		mListenerList.add(TreeModelListener.class, l);
	}

	/**
	 * Remove a listener from this tree model
	 *
	 * @param l the listener to add
	 */
	public void removeTreeModelListener(TreeModelListener l)
	{
		mListenerList.remove(TreeModelListener.class, l);
	}
	
	/**
	 * Recursive search by the id of an element
	 * 
	 * @param id the id of the element to find
	 * @param current the current node
	 * @return the corresponding node or null
	 */
	private SoapTreeNode findWithID(String id, SoapTreeNode current)
	{
		if( current instanceof Identity && ((Identity)current).getID().equals(id) )
		{
			return current;
		}
		
		for(int i=0; i<getChildCount(current); i++)
		{
		    SoapTreeNode node = (SoapTreeNode)getChild(current, i);
			
			node = findWithID(id, node);
			
			if(node!=null)
			{
				return node;
			}
		}
		
		return null;
	}
	
	/**
	 * Find an element by its id
	 * 
	 * @param id the id of the element to find
	 * @return the corresponding node or null
	 */
	public SoapTreeNode findWithID(String id)
	{
		return findWithID(id, mRoot);
	}
		
	public void modelChanged(SoapEvent e)
	{
	    Object source = e.getSource();
        Object[] inserted = e.getInserted();   	
        Object[] parents = e.getParents();
	    Object[] removed = e.getRemoved();
	    System.out.println("modelChanged : parent : "+parents[0]+" inserted : "+inserted[0]);
        Map extras = e.getAttributes();
        handleInsert(inserted,parents,extras ) ;
        
	}
	
	
	protected void handleInsert(Object[] elements,	Object[] parents,Map extras)
	{
	    if (elements.length < 1)
	        return ;
		//System.out.println("tree insert ");
	    SoapTreeNode node = null;
		SoapTreeNode parent = null;
	    if(elements == null || parents == null || elements.length != parents.length) return;
	    
	    for (int i = 0; i < elements.length; i++)
        {
	        node = (SoapTreeNode)findWithID(((Identity)elements[i]).getID());			
			parent = (SoapTreeNode)findWithID(((Identity)parents[i]).getID());
			System.out.println("handleInsert : node : "+(node==null?"null":node.getName())+" parent :"+(parent==null?"null":(parent.getName()+parent.getID())));
			if( node == null )
			{
				node = new SoapTreeNode( elements[i], true );
			}
			if( parent != null )
			{	
				if( node.getParent() == null )
				{
					parent.add(node);
				}
				else
				{
				    System.out.println("parent du node : "+((SoapTreeNode)node.getParent()).getName());
				}
				// insert the element 
				fireTreeNodesInserted( this, parent.getPath(), new int[]{ parent.getIndex(node) }, new Object[]{ node });
			}	    
		}
	}
	
	protected void handleRemove(Object[] elements,	Object[] parents,Map extras)
	{
	    if (elements.length < 1)
	        return ;
	    SoapTreeNode node = null;
		SoapTreeNode parent = null;
	    if(elements == null || parents == null || elements.length != parents.length) return;
	    
	    for (int i = 0; i < elements.length; i++)
        {
	        node = (SoapTreeNode)findWithID(((Identity)elements[i]).getID());			
			parent = (SoapTreeNode)findWithID(((Identity)parents[i]).getID());
			
			if( node == null )
			{
				node = new SoapTreeNode( elements[i], true );
			}
			if( parent != null )
			{	
					parent.remove(node) ; 
				// insert the element 
				fireTreeNodesRemoved( this, parent.getPath(), new int[]{ parent.getIndex(node) }, new Object[]{ node });
			}	    
		}
	}
	
	/**
	 * Notify all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 * @see EventListenerList
	 */
	protected void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children)
	{
		Object[] listeners = mListenerList.getListenerList();
		TreeModelEvent e = null;
		
		/*//for(int i=listeners.length-2; i>=0; i-=2)
		for(int i=0; i<listeners.length; i++)
		{
			//if (listeners[i]==TreeModelListener.class)
		    if (listeners[i] instanceof TreeModelListener)
		    {
				if(e==null)
				{
					try
					{
						e=new TreeModelEvent(source, path, childIndices, children);
					}
					catch(Throwable t){}
				}
				( (TreeModelListener) listeners[i/*i+1*//*]).treeNodesInserted(e);
			}
		}*/
		
		//for(int i=listeners.length-2; i>=0; i-=2)
		for(int i=0; i<listeners.length-1; i+=2)
		{
			if (listeners[i]==TreeModelListener.class && listeners[i+1].getClass() ==  soap.ui.SoapProjectTree.class
			        )
		    {
				if(e==null)
				{
					try
					{
						e=new TreeModelEvent(source, path, childIndices, children);
					}
					catch(Throwable t){}
				}
				System.out.println("************* Listener [i]: "+listeners[i]);
				System.out.println("************* Listener [i+1]: "+listeners[i+1]);
				( (TreeModelListener) listeners[i+1]).treeNodesInserted(e);
			}
		}
	}
	

	/**
	 * Notify all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 * @see EventListenerList
	 */
	protected void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children)
	{
		Object[] listeners = mListenerList.getListenerList();
		TreeModelEvent e = null;
		
		for(int i=0; i<listeners.length; i+=2)
		{
			if (listeners[i]==TreeModelListener.class)
			{
				if(e==null)
				{
					e=new TreeModelEvent(source, path, childIndices, children);
				}
				( (TreeModelListener) listeners[i+1]).treeNodesRemoved(e);
			}
		}
	}
	
	/**
	 * Notify all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 * @see EventListenerList
	 */
	protected void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children)
	{
		Object[] listeners = mListenerList.getListenerList();
		TreeModelEvent e = null;
		
		for(int i=listeners.length-2; i>=0; i-=2)
		{
			if (listeners[i]==TreeModelListener.class)
			{
				if(e==null)
				{
					try{
					e=new TreeModelEvent(source, path, childIndices, children);
					}catch(Throwable t){}
				}
				((TreeModelListener)listeners[i+1]).treeNodesChanged(e);
			}
		}
	}

	/**
	 * Notify all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 * @see EventListenerList
	 */
	protected void fireTreeStructureChanged(Object source, Object[] path, int[] childIndices, Object[] children)
	{
		Object[] listeners = mListenerList.getListenerList();
		TreeModelEvent e = null;
		
		for(int i=listeners.length-2; i>=0; i-=2)
		{
			if (listeners[i]==TreeModelListener.class)
			{
				if(e==null)
				{
					e=new TreeModelEvent(source, path, childIndices, children);
				}
				((TreeModelListener)listeners[i+1]).treeStructureChanged(e);
			}
		}
	}
	
	/**
	 * Associate a central panel to a node
	 *
	 * @param node the node to consider
	 * @return a popup menu for the operations on this node
	 */
	public SoapCentralTabbedPane associatePanel(Object node)
	{
		if(node instanceof SoapTreeNode)
		{
			Element e = (Element)((SoapTreeNode)node).getUserObject();
			
			PanelAssociater pa = new PanelAssociater();
			e.visit(pa);
			return pa.getCentralPanel();
		}
		else
		{
			if(Debug.enabled) Debug.print("associatePanel : Something unknown in the SoapTreeAdapter");
			return null;
		}
	
	}
	
	/**
	 * Associate an icon to a node
	 *
	 * @param node the node to consider
	 * @return an icon representing the node
	 */
	public Icon associateIcon(Object node)
	{
		IconManager im = IconManager.getInstance();
		
		if(node instanceof SoapTreeNode)
		{
			Element e = (Element) ((SoapTreeNode)node).getUserObject();
			IconAssociater ia = new IconAssociater(im);

			e.visit(ia);

			return ia.getIcon();
		}
		else
		{
			if(Debug.enabled) Debug.print("associateIcon : Something unknown in the SpemTreeAdapter");
			return im.getIcon("icons/TreeUnknown.gif");
		}
	
	}

	/**
	 * Associate a menu to a node
	 *
	 * @param node the node to consider
	 * @return a popup menu for the operations on this node
	 */
	public JPopupMenu associateMenu(Object node)
	{ 
		if(node instanceof SoapTreeNode)
		{
			Element e = (Element)((SoapTreeNode)node).getUserObject();
			
			PopupMenuAssociater ma = new PopupMenuAssociater();
			e.visit(ma);
			
			
			return ma.getMenu();
		}
		else
		{
			if(Debug.enabled) Debug.print("associateMenu : Something unknown in the SpemTreeAdapter");
			return null;
		}
	
	}
	
	
	/**
	 * Execute an action associated to a node
	 *
	 * @param node the node to consider
	 */
	public void elementAction(Object node)
	{
		if(node instanceof SoapTreeNode)
		{
			Element e = (Element) ((SoapTreeNode)node).getUserObject();
			//ActionEdit edit = new ActionEdit(e);
			ActionAssociater aa = new ActionAssociater();
			e.visit(aa);
			//postEdit(edit);
		}
		else
		{
			if(Debug.enabled) Debug.print("elementAction : Something unknown in the SpemTreeAdapter");
		}
	}
	
	/**
	 * Adds nodes in the tree
	 * 
	 * @param nodes the nodes to insert
	 * @param parents the parents of each nodes
	 */
	public void insert( Object[] nodes, Object[] parentNodes )
	{
		//if(Debug.enabled) Debug.print("(A) -> SpemTreeAdapter::insert ");
		
		if(nodes == null || parentNodes == null || nodes.length != parentNodes.length)
		    return;
		
        Object[] objects = new Object[nodes.length];
        Object[] parents = new Object[nodes.length];
        Map attr = new HashMap() ;
	    for ( int i = 0; i < objects.length; i++ )
        {
            objects[i] = ((SoapTreeNode)nodes[i]).getUserObject();
            parents[i] = ((SoapTreeNode)parentNodes[i]).getUserObject();
        }
		SoapMediator.getInstance().insertInModel(objects, parents, attr);
	}
	
	public void close(SoapTreeNode node)
	{
	    SoapTreeNode parent = (SoapTreeNode) node.getParent() ;
	    if (parent == null)
	    {
	        return ;
	    }
	    parent.remove(node) ;
	    fireTreeNodesRemoved( this, parent.getPath(), new int[]{ parent.getIndex(node) }, new Object[]{ node });
	}
	
	/**
	 * Removes nodes from the tree
	 * 
	 * @param nodes
	 */
	public void remove( Object[] nodes )
	{
	    //if(Debug.enabled) Debug.print("(A) -> SpemTreeAdapter::remove ");
		
        Object[] objects = new Object[nodes.length];
        Map attr = new HashMap();
	    for ( int i = 0; i < objects.length; i++ )
        {
            objects[i] = ((SoapTreeNode)nodes[i]).getUserObject();
        }        
	    SoapMediator.getInstance().removeFromModel(objects, attr);
	}
	
	private class SoapTreeCellRenderer extends DefaultTreeCellRenderer
	{
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                              boolean leaf, int row, boolean hasFocus)
		{
			Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);			
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

        /* (non-Javadoc)
         * @see javax.swing.event.CellEditorListener#editingCanceled(javax.swing.event.ChangeEvent)
         */
        public void editingCanceled(ChangeEvent arg0)
        {
            // TODO Auto-generated method stub
            
        }

        /* (non-Javadoc)
         * @see javax.swing.event.CellEditorListener#editingStopped(javax.swing.event.ChangeEvent)
         */
        public void editingStopped(ChangeEvent arg0)
        {
            // TODO Auto-generated method stub
            
        }
	}

    public void valueForPathChanged(TreePath arg0, Object arg1)
    {
        
    }
}
