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

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.LinkedList;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardLegend;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;


public class OverLaidBarChartFactory
{
 
    public static JFreeChart createDualBarAndLineDiagram(String title, String categoryAxisLabel, String valueAxisLabel, 
            CategoryDataset barDiagramDataset, LinkedList lineDiagramDatasets) 
    {
	        CategoryPlot plot = new CategoryPlot();
	        int inc = 0;
	        BarRenderer barRenderer = null;
	        if(barDiagramDataset != null)
	        {
	            plot.setDataset(barDiagramDataset);
	            barRenderer = new BarRenderer3D();
	            barRenderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
	            plot.setRenderer(barRenderer);
	            inc = 1;
	            plot.setDomainAxis(new CategoryAxis3D(categoryAxisLabel));
	            plot.setRangeAxis(new NumberAxis3D(valueAxisLabel));
	        }
	        else
	        {
	            plot.setDomainAxis(new CategoryAxis(categoryAxisLabel));
	            plot.setRangeAxis(new NumberAxis(valueAxisLabel));
	        }
	        plot.mapDatasetToRangeAxis(0, 0);
	        
	        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
	        plot.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
	        
	        CategoryItemRenderer renderers [] = new LineAndShapeRenderer [lineDiagramDatasets.size()];
	        
	        for(int i = 0 ; i < lineDiagramDatasets.size() ; i ++)
	        {
	            CategoryDataset lineDiagramDataset = (CategoryDataset)lineDiagramDatasets.get(i);
		        plot.setDataset(i+inc, lineDiagramDataset);
		        plot.mapDatasetToRangeAxis(i+inc, 0);
		        if(barDiagramDataset != null && i < barDiagramDataset.getRowCount())
		        {
		            renderers[i] = new LineAndShapeRendererMapToBar(barRenderer, i);
		        }
		        else
		        {
		            renderers[i] = new LineAndShapeRenderer();
		        }
		        renderers[i].setToolTipGenerator(new StandardCategoryToolTipGenerator());
		        renderers[i].setSeriesStroke(0, new BasicStroke(1.5f));
		        renderers[i].setSeriesStroke(1, new BasicStroke(1.5f));
		        plot.setRenderer(i+inc, renderers[i]);
	        }
	        
	        CategoryDataset dataset ;
	        if(lineDiagramDatasets.size() > 0)
	           dataset = (CategoryDataset)lineDiagramDatasets.get(0);
	        else
	           dataset = barDiagramDataset;
	        for(int i = 0; i < dataset.getColumnCount() ; i++)
	           plot.getDomainAxis().addCategoryLabelToolTip(dataset.getColumnKey(i),(String)dataset.getColumnKey(i));
	            
	        plot.setRangeGridlinePaint(Color.black);
	        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
	        
	        JFreeChart chart = new JFreeChart(plot);
	        chart.setTitle(title);
	        chart.setLegend(new StandardLegend());
	        
	        return chart;
	}
}
