/*
 * Created on 31 oct. 2004
 */
package soap.ui.tabbedPane;


import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//import soap.model.extension.SoapProcess.PackageRole;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;

/**
 * @author SCARAVETTI Florent
 */
public class SoapRolesTabbedPane extends SoapCentralTabbedPane 
{
	/*public SoapRolesTabbedPane(PackageRole packRole)
	{
		super();
		
		/*double tab [] = {1.0,1.3};
		double tab2 [] = {2.0,2.3};
		double tab3 [] = {3.0,3.3};
		HistogramDataset hs = new HistogramDataset();
		hs.addSeries("serie 1",tab,2,2,5);
		hs.addSeries("serie 2",tab2,2,0,0.7);
		hs.addSeries("serie 3",tab3,2);
		/*sy.add(0.5,2.4);
		sy.add(1.0,2.5);
		sy.add(1.5,2.1);
		sy.add(2.0,2.7);
		sy.add(2.5,2.9);*/
		
		//DefaultTableXYDataset ds = new DefaultTableXYDataset();
		//ds.addSeries(sy);
		
		//ChartPanel panel = new ChartPanel(ChartFactory.createHistogram("Titre","Axe x","Axe y",hs,PlotOrientation.VERTICAL,true,true,true));
		/*ChartPanel panel = new ChartPanel(ChartFactory.createBoxAndWhiskerChart("Titre","Axe x","Axe y",null,true));
		//ChartPanel panel = new ChartPanel(ChartFactory.createBarChart3D("Titre","Axe x","Axe y",null,PlotOrientation.VERTICAL,false,false,false));
		mStatsPanel.add(panel);
	}*/
}
