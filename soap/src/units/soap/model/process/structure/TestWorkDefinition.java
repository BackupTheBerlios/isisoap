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

import soap.model.process.structure.Activity;
import soap.model.process.structure.ProcessPerformer;
import soap.model.process.structure.WorkDefinition;

public class TestWorkDefinition extends TestCase
{
	WorkDefinition workdef = new WorkDefinition("d�finition_de_travail");
	WorkDefinition workdef2 = new WorkDefinition("d�finition_de_travail2");
	ProcessPerformer proPerf = new ProcessPerformer("process_performer");
	Activity a = new Activity("act");
	Activity a2 = new Activity("act2");
		
	public void testWorkDefinition() { 
		assertTrue(workdef.getName()=="d�finition_de_travail");	
	}
	
	public void testgetOwner() { 
		workdef.setOwner(proPerf);
		assertTrue(workdef.getOwner()==proPerf);	
	}
	
	public void testsetOwner() { 
		assertTrue(workdef.setOwner(proPerf));
		workdef.setOwner(proPerf);
		assertFalse(workdef.setOwner(proPerf));	
	}
	
	public void testaddSubWork() { 
		assertTrue(workdef.addSubWork(a));
		workdef.addSubWork(a);
		assertFalse(workdef.addSubWork(a));	
	}
	
	public void testremoveSubWork() { 
		assertFalse(workdef.removeSubWork(a));
		workdef.addSubWork(a);
		assertTrue(workdef.removeSubWork(a));	
	}
	
	public void testcontainsSubWork() { 
		assertFalse(workdef.containsSubWork(a));
		workdef.addSubWork(a);
		assertTrue(workdef.containsSubWork(a));	
	}
	
	public void testgetSubWork() { 
		workdef.addSubWork(a);
		workdef.addSubWork(a2);
		assertFalse(workdef.getSubWork(0)==a2);
		assertTrue(workdef.getSubWork(0)==a);	
		assertTrue(workdef.getSubWork(1)==a2);	
	}
	
	public void testsubWorkCount() { 
		workdef.addSubWork(a);
		workdef.addSubWork(a2);
		assertFalse(workdef.subWorkCount()==1);
		assertTrue(workdef.subWorkCount()==2);		
	}
	
	public void testaddParentWork() { 
		assertTrue(workdef.addParentWork(workdef));
		workdef.addParentWork(workdef);
		assertFalse(workdef.addParentWork(workdef));	
	}
	
	public void testremoveParentWork() { 
		assertFalse(workdef.removeParentWork(workdef));
		workdef.addParentWork(workdef);
		assertTrue(workdef.removeParentWork(workdef));	
	}
	
	public void testcontainsParentWork() { 
		assertFalse(workdef.containsParentWork(workdef));
		workdef.addParentWork(workdef);
		assertTrue(workdef.containsParentWork(workdef));	
	}
	
	public void testgetParentWork() { 
		workdef.addParentWork(workdef);
		assertFalse(workdef.getParentWork(1)==workdef);
		assertTrue(workdef.getParentWork(0)==workdef);	
	}
	
	public void testparentWorkCount() { 
		workdef.addParentWork(workdef);
		assertFalse(workdef.parentWorkCount()==0);
		workdef.addParentWork(workdef2);
		assertTrue(workdef.parentWorkCount()==2);	
	}
	
}
