/*
 * Created on 12 nov. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package soap.model.core;

import utils.SoapDate;


/**
 * @author SCARAVETTI Florent
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class EstimationElement extends ModelElement 
{

	private SoapDate mStartDate;
	private SoapDate mEndDate;
	private int mEstimatedHours ;
	private int mElapsedHours ;
	private int mRemainedHoursToFinish;
	
	public EstimationElement(String name)
	{
		super(name);
	}

	public EstimationElement()
	{
		
	}
	
	public SoapDate getStartDate()
	{
		return mStartDate;
	}
	
	public SoapDate getEndDate()
	{
		return mEndDate;
	}
	
	public void setStartDate(SoapDate startDate)
	{
		mStartDate = startDate;
	}
	
	public void setEndDate(SoapDate endDate)
	{
		mEndDate = endDate;
	}
	
	public int getEstimatedHours()
	{
		return mEstimatedHours ;
	}
		
	public void setEstimatedHours(int hours) 
	{
		mEstimatedHours = hours ;
	}
	    
	public int getElapsedHours()
	{
		return mElapsedHours ;
	}
	    
	public void setElapsedHours(int hours) 
	{
		mElapsedHours = hours ;
	}
	
	public int getRemainedHoursToFinish()
	{
		return mRemainedHoursToFinish;
	}
	
	public void setRemainedHoursToFinish(int hours)
	{
		mRemainedHoursToFinish = hours;
	}
	
}
