/*
 * SOAP Supervising, Observing, Analysing Projects
 * Copyright (C) 2003-2004 SoapTeam
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
package soap.ui.stats;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;


public class LineAndShapeRendererMapToBar extends LineAndShapeRenderer
{
    private BarRenderer mBarRenderer;
    private int mNumSerie;
    
    public LineAndShapeRendererMapToBar(BarRenderer barRenderer, int numSerie)
    {
        super();
        mBarRenderer = barRenderer;
        mNumSerie = numSerie;
    }
    
    public void drawItem(Graphics2D g2, CategoryItemRendererState state,
            Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis,
            ValueAxis rangeAxis, CategoryDataset dataset, int row, int column) 
    {

		// nothing is drawn for null...
		Number v = dataset.getValue(row, column);
		if (v == null) 
		   return;
		
		PlotOrientation orientation = plot.getOrientation();
		
		// current data point which is associated to a serie...
		double x1 = domainAxis.getCategoryStart(column, getColumnCount(), dataArea,
                plot.getDomainAxisEdge());
		int seriesCount = mBarRenderer.getPlot().getDataset().getRowCount();
	    int categoryCount = mBarRenderer.getPlot().getDataset().getColumnCount();
	    if (seriesCount > 1 && mNumSerie < seriesCount)
	    {
	        double seriesGap =  dataArea.getWidth() * 0.2 / (categoryCount * (seriesCount - 1));
	        double seriesW = calculateSeriesWidth(dataArea.getWidth(), domainAxis, categoryCount, seriesCount);
	        x1 = x1 + mNumSerie * (seriesW + seriesGap) 
	                          + (seriesW / 2.0) - (state.getBarWidth() / 2.0);
	        
	    }
	    else
	    {
	        x1 = domainAxis.getCategoryMiddle(
	     			   column, getColumnCount(), dataArea, plot.getDomainAxisEdge());
	    }
	        
		double value = v.doubleValue();
		double y1 = rangeAxis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());
		
		
		Shape shape = getItemShape(row, column);
		if (orientation == PlotOrientation.HORIZONTAL) 
		{
		   shape = createTransformedShape(shape, y1, x1);
		}
		else if (orientation == PlotOrientation.VERTICAL) 
		{
		   shape = createTransformedShape(shape, x1, y1);
		}
		if (isDrawShapes()) 
		{
		   if (getItemShapeFilled(row, column)) 
		   {
		       g2.setPaint(getItemPaint(row, column));
		       g2.fill(shape);
		   }
		   else 
		   {
		       g2.setPaint(getItemOutlinePaint(row, column));
		       g2.setStroke(getItemOutlineStroke(row, column));
		       g2.draw(shape);
		   }
		}
		
		if (isDrawLines()) 
		{
		   if (column != 0) 
		   {
		       Number previousValue = dataset.getValue(row, column - 1);
		       if (previousValue != null) 
		       {
		           // previous data point...
		           double previous = previousValue.doubleValue();
		           double x0 = domainAxis.getCategoryStart(column - 1, getColumnCount(), dataArea,
		                    plot.getDomainAxisEdge());
		         //  seriesCount = getRowCount();
			     //  categoryCount = getColumnCount();
			       if (seriesCount > 1 && mNumSerie < seriesCount) 
			       {
			           double seriesGap =  dataArea.getWidth() * 0.2 / (categoryCount * (seriesCount - 1));
			           double seriesW = calculateSeriesWidth(dataArea.getWidth(), domainAxis, categoryCount, seriesCount);
			           x0 = x0 + mNumSerie * (seriesW + seriesGap) 
			                         + (seriesW / 2.0) - (state.getBarWidth() / 2.0);
			       }
			       else
			       {
			           x0 = domainAxis.getCategoryMiddle(column - 1, getColumnCount(), 
			                   dataArea, plot.getDomainAxisEdge());
			       }
	            
		           double y0 = rangeAxis.valueToJava2D(previous, dataArea, plot.getRangeAxisEdge());
		
		           Line2D line = null;
		           if (orientation == PlotOrientation.HORIZONTAL) 
		           {
		               line = new Line2D.Double(y0, x0, y1, x1);
		           }
		           else if (orientation == PlotOrientation.VERTICAL) 
		           {
		               line = new Line2D.Double(x0, y0, x1, y1);
		           }
		           g2.setPaint(getItemPaint(row, column));
		           g2.setStroke(getItemStroke(row, column));
		           g2.draw(line);
		       }
		   }
		}
		
		// draw the item label if there is one...
		if (isItemLabelVisible(row, column)) 
		{
		   if (orientation == PlotOrientation.HORIZONTAL) 
		   {
		       drawItemLabel(g2, orientation, dataset, row, column, y1, x1, (value < 0.0));
		   }
		   else if (orientation == PlotOrientation.VERTICAL) 
		   {
		       drawItemLabel(g2, orientation, dataset, row, column, x1, y1, (value < 0.0));                
		   }
		}
		
		// collect entity and tool tip information...
		if (state.getInfo() != null) 
		{
		   EntityCollection entities = state.getInfo().getOwner().getEntityCollection();
		   if (entities != null && shape != null) 
		   {
		       String tip = null;
		       CategoryToolTipGenerator tipster = getToolTipGenerator(row, column);
		       if (tipster != null) 
		       {
		           tip = tipster.generateToolTip(dataset, row, column);
		       }
		       String url = null;
		       if (getItemURLGenerator(row, column) != null) 
		       {
		           url = getItemURLGenerator(row, column).generateURL(dataset, row, column);
		       }
		       CategoryItemEntity entity = new CategoryItemEntity(shape, tip, url, dataset,
		               row, dataset.getColumnKey(column), column);
		       entities.addEntity(entity);
		   }
		}
    }
    
    protected double calculateSeriesWidth(double space, CategoryAxis axis, int categories, int series)
    {
        double factor = 1.0 - 0.20 - axis.getLowerMargin() - axis.getUpperMargin();
        if (categories > 1) 
        {
            	factor = factor - axis.getCategoryMargin();
        }
        return (space * factor) / (categories * series);
    }
}
