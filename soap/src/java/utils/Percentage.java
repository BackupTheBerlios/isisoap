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

package utils;

public class Percentage implements Comparable
{
    private Integer mPercent;
    
    public Percentage(Integer percent)
    {
        mPercent = percent;
    }
    
    public int compareTo(Object object)
    {
        return mPercent.compareTo(((Percentage)object).getPercent());
    }
    
    public String toString()
    {
        return mPercent.toString();
    }
    
    public Integer getPercent()
    {
        return mPercent;
    }
}
