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


package soap.model;

import soap.model.executionProcess.structure.Artifact;
import soap.model.executionProcess.structure.Iteration;
import soap.model.executionProcess.structure.Project;
import soap.model.executionProcess.structure.Task;
import soap.model.executionProcess.structure.Iteration.ListTask;
import soap.model.executionProcess.structure.user.Member;
import soap.model.extension.SoapProcess;
import soap.model.modelmanagement.IPackage;
import soap.model.process.structure.Activity;
import soap.model.process.structure.ProcessPerformer;
import soap.model.process.structure.ProcessRole;
import soap.model.process.structure.WorkDefinition;
import soap.model.process.structure.WorkProduct;



public interface ModelVisitor
{
    /**
	 * Called when the visited element is a package
	 *
	 * @param pack the visited package
	 */
	public void visitPackage(IPackage pack);
	
	
	//	visit executionProcessElement
	/**
	 * Called when the visited element is a project
	 *
	 * @param project the visited project
	 */
	public void visitProject(Project project);
	
	/**
	 * Called when the visited element is a iteration
	 *
	 * @param it the visited iteration
	 */
	public void visitIteration(Iteration it);
	
	/**
	 * Called when the visited element is a task
	 *
	 * @param task the visited task
	 */
	public void visitListTask(ListTask listTask );
	
	/**
	 * Called when the visited element is a task
	 *
	 * @param task the visited task
	 */
	public void visitTask(Task task );
	
	/**
	 * Called when the visited element is an Artifact
	 *
	 * @param art the visited Artifact
	 */
	public void visitArtifact(Artifact art );
	
	/**
	 * Called when the visited element is an Artifact
	 *
	 * @param art the visited Artifact
	 */
	public void visitMember(Member memb );
	
    //visit processElement
	/**
	 * Called when the visited element is a process
	 *
	 * @param sp the visited SoapProcess
	 */
	public void visitProcess(SoapProcess sp);
	
	/**
	 * Called when the visited element is a work definition
	 *
	 * @param work the visited work definition
	 */
	public void visitWorkDefinition(WorkDefinition work);

	/**
	 * Called when the visited element is a process performer
	 *
	 * @param performer the visited process performer
	 */
	public void visitProcessPerformer(ProcessPerformer performer);


	/**
	 * Called when the visited element is a work product
	 *
	 * @param product the visited work product
	 */
	public void visitProduct(WorkProduct product);

	/**
	 * Called when the visited element is a process role
	 *
	 * @param role the visited process role
	 */
	public void visitRole(ProcessRole role);


	/**
	 * Called when the visited element is an activity
	 *
	 * @param activity the visited activity
	 */
	public void visitActivity(Activity activity);


    



	
}
