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
package soap.model ;

import soap.model.core.Element;
import soap.model.core.ModelElement;
import soap.model.executionProcess.structure.Artifact;
import soap.model.executionProcess.structure.Iteration;
import soap.model.executionProcess.structure.Project;
import soap.model.executionProcess.structure.Task;
import soap.model.executionProcess.structure.Iteration.ListTask;
import soap.model.executionProcess.structure.user.Member;
import soap.model.modelmanagement.IPackage;

/**
 * a default visitor for the executionProcessElement
 *
 */

public class DefaultModelVisitor implements ModelVisitor
{
	/**
	 * Called when visiting an Element
	 *
	 * @param element the visited Element
	 */
	protected void visitElement(Element element) {}	
	
	/**
	 * Called when visiting a ModelElement
	 * Simply treat it as an Element
	 *
	 * @param element the visited ModelElement
	 */
	protected void visitModelElement(ModelElement element)
	{
		visitElement(element);
	}

    public void visitPackage(IPackage pack)
    {
        if( pack instanceof ModelElement )
		{
			visitModelElement((ModelElement)pack);
		}
    }

    public void visitProject(Project project)
    {
        visitPackage(project);
        
    }

    public void visitIteration(Iteration it)
    {
        visitPackage(it);
        
    }

    public void visitListTask(ListTask listTask)
    {
        visitPackage(listTask);
    }
    
    public void visitTask(Task task)
    {
        visitModelElement(task);
    }

    public void visitArtifact(Artifact art)
    {
        visitModelElement(art);
    }

    
    public void visitMember(Member memb)
    {
        visitModelElement(memb);
    }
    
}
