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

package soap.model.core;

import utils.SoapDate;

/** Base class for the elements of the model which can be estimated
 * 
 */
public abstract class EstimationElement extends SoapElement 
{
    public static final int START_DATE = 0;
    public static final int END_DATE = 1;
    public static final int ESTIMATED_HOURS = 2;
    public static final int ELAPSED_HOURS = 3;
    public static final int REMAINED_HOURS_TO_FINISH = 4;
    
    public static final int BUDGET = 7;
    public static final int DESCRIPTION = 8;

    public static final int ESTIMATED_START_DATE = 9;
    public static final int ESTIMATED_END_DATE = 10;
    // Task
    public static final int STATE = 11;
    // Artifact
    public static final int DELIVERABLE = 12;

    public EstimationElement(String projectName)
	{
		super(projectName);
	}
    
	public EstimationElement(String projectName, String name)
	{
		super(projectName, name);
	}
	
	public EstimationElement(String projectName, String name, SoapDate startDate, SoapDate endDate)
	{
	    super(projectName, name);
	    setAttribute(START_DATE,startDate);
	    setAttribute(END_DATE,endDate);
	}
}
