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

package soap.model.process.structure;

import junit.framework.TestCase;

import soap.model.process.structure.ProcessPerformer;
import soap.model.process.structure.WorkDefinition;
import soap.model.process.structure.WorkProduct;

public class TestWorkProduct extends TestCase
{
	WorkProduct workprod = new WorkProduct("produit_de_travail");
	WorkDefinition workdef = new WorkDefinition("définition_de_travail");
	WorkDefinition workdef2 = new WorkDefinition("définition_de_travail2");
	ProcessPerformer properf = new ProcessPerformer("proc_perf");
	
	
	public void testWorkProduct() {
		assertTrue(workprod.getName()=="produit_de_travail");	
	}
	
	public void testgetReferences() {
		workprod.setReferences(1);
		assertTrue(workprod.getReferences()==1);	
	}
	
	public void testgetResponsible() {
		workprod.setResponsible(properf);
		assertTrue(workprod.getResponsible()==properf);	
	}
	
	public void testaddInputWorkDefinition() {
		assertTrue(workprod.addInputWorkDefinition(workdef));	
		workprod.addInputWorkDefinition(workdef);
		assertFalse(workprod.addInputWorkDefinition(workdef));		
	}
	
	public void testremoveInputWorkDefinition() {
		assertFalse(workprod.removeInputWorkDefinition(workdef));	
		workprod.addInputWorkDefinition(workdef);
		assertTrue(workprod.removeInputWorkDefinition(workdef));		
	}
	
	public void testcontainsInputWorkDefinition() {
		assertFalse(workprod.containsInputWorkDefinition(workdef));	
		workprod.addInputWorkDefinition(workdef);
		assertTrue(workprod.containsInputWorkDefinition(workdef));		
	}
	
	public void testaddOutputWorkDefinition() {
		assertTrue(workprod.addOutputWorkDefinition(workdef));	
		workprod.addOutputWorkDefinition(workdef);
		assertFalse(workprod.addOutputWorkDefinition(workdef));		
	}
	
	public void testremoveOutputWorkDefinition() {
		assertFalse(workprod.removeOutputWorkDefinition(workdef));	
		workprod.addOutputWorkDefinition(workdef);
		assertTrue(workprod.removeOutputWorkDefinition(workdef));		
	}
	
	public void testcontainsOutputWorkDefinition() {
		assertFalse(workprod.containsOutputWorkDefinition(workdef));	
		workprod.addOutputWorkDefinition(workdef);
		assertTrue(workprod.containsOutputWorkDefinition(workdef));		
	}
	
	public void testgetInputCount() {
		assertTrue(workprod.getInputCount()==0);	
		workprod.addInputWorkDefinition(workdef);
		workprod.addInputWorkDefinition(workdef2);
		assertTrue(workprod.getInputCount()==2);	
		assertFalse(workprod.getInputCount()==1);	
	}
	
	public void testgetOutputCount() {
		assertTrue(workprod.getOutputCount()==0);	
		workprod.addOutputWorkDefinition(workdef);
		workprod.addOutputWorkDefinition(workdef2);
		assertTrue(workprod.getOutputCount()==2);	
		assertFalse(workprod.getOutputCount()==1);	
	}
	
	public void testgetInput() {
		workprod.addInputWorkDefinition(workdef);
		assertTrue(workprod.getInput(0)==workdef);
		workprod.addInputWorkDefinition(workdef2);
		assertFalse(workprod.getInput(1)==workdef);
		assertTrue(workprod.getInput(1)==workdef2);
	}
	
	public void testgetOutput() {
		workprod.addOutputWorkDefinition(workdef);
		assertTrue(workprod.getOutput(0)==workdef);
		workprod.addOutputWorkDefinition(workdef2);
		assertFalse(workprod.getOutput(1)==workdef);
		assertTrue(workprod.getOutput(1)==workdef2);
	}
	
}
