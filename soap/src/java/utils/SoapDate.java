/*
 * Created on 19 nov. 2004
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author SCARAVETTI Florent
 */
public class SoapDate extends Date
{
	private GregorianCalendar mCalendar = new GregorianCalendar();
	
	public SoapDate(int day, int month, int year)
	{
		setDay(day);
		setMonth(month);
		setYear(year);
	}
	
	public SoapDate(String date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try 
		{
			setTime(dateFormat.parse(date).getTime());
		}
		catch (ParseException ex)
		{
			Debug.print("Erreur de conversion");
		}
	}
	
	public void setDay(int day)
	{
		mCalendar.set(Calendar.DATE,day);
	}
	
	public int getDay()
	{
		return mCalendar.get(Calendar.DATE);
	}
	
	public void setMonth(int month)
	{
		mCalendar.set(Calendar.MONTH,month-1);
	}
	
	public int getMonth()
	{
		return mCalendar.get(Calendar.MONTH)+1;
	}
	
	public void setYear(int year)
	{
		mCalendar.set(Calendar.YEAR,year);
	}
	
	public int getYear()
	{
		return mCalendar.get(Calendar.YEAR);
	}
	
	public long getTime()
	{
		return mCalendar.getTimeInMillis();
	}
	
	public void setTime(long time)
	{
		mCalendar.setTimeInMillis(time);
	}
	
	public String toString()
	{
		return getDay()+"/"+getMonth()+"/"+getYear();
	}
}
