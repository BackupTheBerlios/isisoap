/*
 * Created on 1 nov. 2004
 */
package soap.ui.tabbedPane;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;

import soap.Context;
import soap.adapters.SoapTasksTableSorter;
import soap.model.executionProcess.structure.Iteration;
import soap.ui.SoapTasksTable;
import soap.ui.panel.SoapPanel;


/**
 * @author SCARAVETTI Florent
 */
public class SoapIterationTabbedPane extends DefaultCentralTabbedPane 
{
	
	public SoapIterationTabbedPane(Iteration it)
	{
		super();
		// Iteration information
		JPanel iterationPanel = new JPanel() ;
		iterationPanel.setBorder(BorderFactory.createEmptyBorder(8,10,10,10)) ;
		iterationPanel.setLayout(new GridLayout(3,2));
		iterationPanel.add(new JLabel("Nom de l'itération : "));
		iterationPanel.add(new JLabel(it.getName()));
		iterationPanel.add(new JLabel("Date de début : "));
		iterationPanel.add(new JLabel(it.getStartDate().toString()));
		iterationPanel.add(new JLabel("Date de fin : "));
		iterationPanel.add(new JLabel(it.getEndDate().toString()));
		
		SoapPanel iterationInfoPanel = new SoapPanel();
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyleIteration = BorderFactory.createTitledBorder( loweredetched,"Informations sur l'itération");
		iterationInfoPanel.setBorder(titleStyleIteration);		
		iterationInfoPanel.setLayout(new BorderLayout());
		iterationInfoPanel.add(iterationPanel,BorderLayout.CENTER) ;
		
		// Iteration Realization information
		float advancement = ((float)it.getElapsedHours()/(float)(it.getElapsedHours()+it.getRemainedHoursToFinish()))*100;
		float consumedBudget = ((float)it.getElapsedHours()/(float)it.getEstimatedHours())*100;
		System.out.println(it.getElapsedHours());
		System.out.println(it.getEstimatedHours());
		System.out.println(consumedBudget);
		JPanel realizationPanel = new JPanel() ;
		realizationPanel.setBorder(BorderFactory.createEmptyBorder(8,10,10,10)) ;
		realizationPanel.setLayout(new GridLayout(5,2));
		realizationPanel.add(new JLabel("Charges prévues : "));
		realizationPanel.add(new JLabel(it.getEstimatedHours()+" heures"));
		realizationPanel.add(new JLabel("Charges passées : "));
		realizationPanel.add(new JLabel(it.getElapsedHours()+" heures"));
		realizationPanel.add(new JLabel("Charges restantes pour finir : "));
		realizationPanel.add(new JLabel(it.getRemainedHoursToFinish()+" heures"));
		realizationPanel.add(new JLabel("Avancement : "));
		realizationPanel.add(new JLabel(String.valueOf((int)advancement)+" %"));
		realizationPanel.add(new JLabel("Budget consommé : "));
		realizationPanel.add(new JLabel(String.valueOf((int)consumedBudget)+" %"));
		
		
		SoapPanel realizationInfoPanel = new SoapPanel();
		Border loweredetchedRealization = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyleRealization = BorderFactory.createTitledBorder( loweredetchedRealization,"Informations sur la réalisation de l'itération");
		realizationInfoPanel.setBorder(titleStyleRealization);		
		realizationInfoPanel.setLayout(new BorderLayout());
		realizationInfoPanel.add(realizationPanel,BorderLayout.CENTER) ;
		
		// Add to data panel
		
		GridBagLayout gridbag = new GridBagLayout();
	   	GridBagConstraints c = new GridBagConstraints();
        mDataPanel.setLayout(gridbag);
      
		c.weightx = 1.0;
		c.weighty = 0 ;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
				
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(iterationInfoPanel, c);
		mDataPanel.add(iterationInfoPanel) ;
		
		gridbag.setConstraints(realizationInfoPanel, c);
		mDataPanel.add(realizationInfoPanel) ;
		
		c.weighty = 2.0;
		SoapPanel.makeLabel(mDataPanel, " ", gridbag, c);
		
		SoapTasksTable table = new SoapTasksTable(Context.getInstance().getListProjects().getListSoapProjects());
		mStatsPanel.setLayout(new BorderLayout());
		mStatsPanel.add(table.getTableHeader(),BorderLayout.NORTH);
		mStatsPanel.add(table,BorderLayout.CENTER);
		
	}
}
