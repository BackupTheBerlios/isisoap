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

package soap.model.frontend;

import java.io.Serializable;
import java.util.Map;
import java.util.Vector;

import soap.Context;
import soap.model.core.ModelElement;
import soap.model.executionProcess.structure.Artifact;
import soap.model.executionProcess.structure.Iteration;
import soap.model.executionProcess.structure.Project;
import soap.model.executionProcess.structure.Task;
import soap.model.executionProcess.structure.Iteration.ListTask;
import soap.model.extension.SoapListProjects;
import soap.model.frontend.event.SoapEvent;
import soap.model.modelmanagement.IPackage;
import utils.ConfigManager;
import utils.ResourceManager;

public class SoapMediator implements Serializable
{
	private static final SoapMediator mInstance = new SoapMediator();
	
	private ResourceManager mResource = ResourceManager.getInstance();
	private ConfigManager mConfig = ConfigManager.getInstance();
	
	private Vector mListeners = new Vector();
	
	private SoapMediator() { }
	
	/**
	 * Return the unique instance of the SoapMediator
	 * @return the unique instance of the SoapMediator
	 */
	public static SoapMediator getInstance()
	{
		return mInstance;
	}
	
	
	/**
	 * Set a new process
	 * @param ap the process
	 */
	public void addProject(Project p)
	{
	    initNewProject(p);
	}
	
	public void closeProject(Project p)
	{
	    removeFromModel(new Object [] {p} , null);
	}
	
	/**
	 * Create a new process
	 * @param ap
	 */
	private void initNewProject(Project p )
	{
	    SoapListProjects listProjects = Context.getInstance().getListProjects().getListSoapProjects() ;
	   /* String name = mConfig.getProperty("PackageRole");
	    p.setAttribute(EstimationElement.START_DATE,new SoapDate("22/10/2004"));
	   // p.setStartDate(new SoapDate(22,10,2004));
	    p.setAttribute(EstimationElement.END_DATE,new SoapDate("14/03/2004"));
		p.setSupervisor(new Supervisor("Bernard","Cherbonneau"));
		p.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(400));
		p.setAttribute(Project.ESTIMATED_FUNCTIONALITIES, new Integer(25));
		Iteration it1 = new Iteration(p.getName(),"Itération 1") ;
		it1.setAttribute(EstimationElement.START_DATE,new SoapDate(22,10,2004));
		it1.setAttribute(EstimationElement.END_DATE,new SoapDate(05,11,2004));
		it1.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(40));
		it1.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(47));
		it1.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		it1.setAttribute(Project.ESTIMATED_FUNCTIONALITIES, new Integer(2));
		it1.setAttribute(Project.REALIZED_FUNCTIONALITIES, new Integer(2));
		Iteration it2 = new Iteration(p.getName(),"Itération 2") ;
		it2.setAttribute(EstimationElement.START_DATE,new SoapDate(05,11,2004));
		it2.setAttribute(EstimationElement.END_DATE, new SoapDate(19,11,2004));
		it2.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(60));
		it2.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(55));
		it2.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		it2.setAttribute(Project.ESTIMATED_FUNCTIONALITIES, new Integer(3));
		it2.setAttribute(Project.REALIZED_FUNCTIONALITIES, new Integer(3));
		Iteration it3 = new Iteration(p.getName(),"Itération 3") ;
		it3.setAttribute(EstimationElement.START_DATE,new SoapDate(19,11,2004));
		it3.setAttribute(EstimationElement.END_DATE, new SoapDate(10,12,2004));
		it3.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(55));
		it3.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(50));
		it3.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		it3.setAttribute(Project.ESTIMATED_FUNCTIONALITIES, new Integer(4));
		it3.setAttribute(Project.REALIZED_FUNCTIONALITIES, new Integer(3));
		
		Iteration it4 = new Iteration(p.getName(),"Itération 4") ;
		it4.setAttribute(EstimationElement.START_DATE,new SoapDate(11,12,2004));
		it4.setAttribute(EstimationElement.END_DATE, new SoapDate(4,01,2005));
		it4.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(53));
		it4.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(55));
		it4.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		it4.setAttribute(Project.ESTIMATED_FUNCTIONALITIES, new Integer(5));
		it4.setAttribute(Project.REALIZED_FUNCTIONALITIES, new Integer(5));
		
		Iteration it5 = new Iteration(p.getName(),"Itération 5") ;
		it5.setAttribute(EstimationElement.START_DATE,new SoapDate(5,01,2005));
		it5.setAttribute(EstimationElement.END_DATE, new SoapDate(22,01,2005));
		it5.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(20));
		it5.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(45));
		it5.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(26));
		it5.setAttribute(Project.ESTIMATED_FUNCTIONALITIES, new Integer(4));
		it5.setAttribute(Project.REALIZED_FUNCTIONALITIES, new Integer(1));
	
		ListTask listTask1 = it1.new ListTask(p.getName(),"Liste des tâches");
		Member m1 = new Member("SCARAVETTI","Florent");
		Member m2 = new Member("NGO THANH","Trung");
		Member m3 = new Member("CZERNY","Jean");
		Member m4 = new Member("FAUROUX","Claire");
		Task task1 = new Task(p.getName(),"Analyser les besoins ");
		task1.setAttribute(EstimationElement.START_DATE,new SoapDate(22,10,2004));
		task1.setAttribute(EstimationElement.END_DATE, new SoapDate(24,10,2004));
		task1.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(6));
		task1.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(5));
		task1.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task1.setPriority(1);
		task1.setMember(m3);
		listTask1.addTask(task1);
		Task task2 = new Task(p.getName(),"Réaliser le document de vision ");
		task2.setAttribute(EstimationElement.START_DATE,new SoapDate(24,10,2004));
		task2.setAttribute(EstimationElement.END_DATE, new SoapDate(25,10,2004));
		task2.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(2));
		task2.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(4));
		task2.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task2.setPriority(1);
		task2.setMember(m3);
		listTask1.addTask(task2);
		Task task3 = new Task(p.getName(),"Identifier les cas d'utilisation ");
		task3.setAttribute(EstimationElement.START_DATE,new SoapDate(26,10,2004));
		task3.setAttribute(EstimationElement.END_DATE, new SoapDate(29,10,2004));
		task3.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(7));
		task3.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(5));
		task3.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task3.setPriority(1);
		task3.setMember(m1);
		listTask1.addTask(task3);
		Task task4 = new Task(p.getName(),"Réaliser le modèle des cas d'utilisation ");
		task4.setAttribute(EstimationElement.START_DATE,new SoapDate(30,10,2004));
		task4.setAttribute(EstimationElement.END_DATE, new SoapDate(2,11,2004));
		task4.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(5));
		task4.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(4));
		task4.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task4.setPriority(2);
		task4.setMember(m2);
		listTask1.addTask(task4);
		Task task5 = new Task(p.getName(),"Evaluer l'itération ");
		task5.setAttribute(EstimationElement.START_DATE,new SoapDate(3,11,2004));
		task5.setAttribute(EstimationElement.END_DATE, new SoapDate(4,11,2004));
		task5.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(2));
		task5.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(3));
		task5.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task5.setPriority(2);
		task5.setMember(m4);
		listTask1.addTask(task5);
		Task task6 = new Task(p.getName(),"Définir l'itération suivante ");
		task6.setAttribute(EstimationElement.START_DATE,new SoapDate(4,11,2004));
		task6.setAttribute(EstimationElement.END_DATE, new SoapDate(5,11,2004));
		task6.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(2));
		task6.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(3));
		task6.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task6.setPriority(2);
		task6.setMember(m3);
		listTask1.addTask(task6);
		ListTask listTask2 = it2.new ListTask(p.getName(),"Liste des tâches");
		Task task7 = new Task(p.getName(),"Mettre à jour le document de vision ");
		task7.setAttribute(EstimationElement.START_DATE,new SoapDate(5,11,2004));
		task7.setAttribute(EstimationElement.END_DATE, new SoapDate(6,11,2004));
		task7.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(1));
		task7.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(3));
		task7.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task7.setPriority(2);
		task7.setMember(m3);
		listTask2.addTask(task7);
		Task task8 = new Task(p.getName(),"Mettre à jour le modèle des cas d'utilisation ");
		task8.setAttribute(EstimationElement.START_DATE,new SoapDate(5,11,2004));
		task8.setAttribute(EstimationElement.END_DATE, new SoapDate(7,11,2004));
		task8.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(7));
		task8.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(5));
		task8.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task8.setPriority(2);
		task8.setMember(m1);
		listTask2.addTask(task8);
		Task task9 = new Task(p.getName(),"Analyser l'architecture du logiciel");
		task9.setAttribute(EstimationElement.START_DATE,new SoapDate(5,11,2004));
		task9.setAttribute(EstimationElement.END_DATE, new SoapDate(9,11,2004));
		task9.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(11));
		task9.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(13));
		task9.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task9.setPriority(1);
		task9.setMember(m2);
		listTask2.addTask(task9);
		Task task10 = new Task(p.getName(),"Réaliser le document d'architecture");
		task10.setAttribute(EstimationElement.START_DATE,new SoapDate(10,11,2004));
		task10.setAttribute(EstimationElement.END_DATE, new SoapDate(11,11,2004));
		task10.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(4));
		task10.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(5));
		task10.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task10.setMember(m2);
		task10.setPriority(1);
		listTask2.addTask(task10);
		Task task11 = new Task(p.getName(),"Produire une maquette de l’application");
		task11.setAttribute(EstimationElement.START_DATE,new SoapDate(11,11,2004));
		task11.setAttribute(EstimationElement.END_DATE, new SoapDate(16,11,2004));
		task11.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(20));
		task11.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(15));
		task11.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task11.setMember(m1);
		task11.setPriority(1);
		listTask2.addTask(task11);
		Task task12 = new Task(p.getName(),"Evaluer l'itération ");
		task12.setAttribute(EstimationElement.START_DATE,new SoapDate(16,11,2004));
		task12.setAttribute(EstimationElement.END_DATE, new SoapDate(17,11,2004));
		task12.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(3));
		task12.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(2));
		task12.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task12.setMember(m3);
		task12.setPriority(2);
		listTask2.addTask(task12);
		Task task13 = new Task(p.getName(),"Définir l'itération suivante ");
		task13.setAttribute(EstimationElement.START_DATE,new SoapDate(17,11,2004));
		task13.setAttribute(EstimationElement.END_DATE, new SoapDate(19,11,2004));
		task13.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(2));
		task13.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(3));
		task13.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task13.setMember(m4);
		task13.setPriority(2);
		listTask2.addTask(task13);
		
		ListTask listTask3 = it3.new ListTask(p.getName(),"Liste des tâches");
		Task task14 = new Task(p.getName(),"Mettre à jour le modèle des cas d'utilisation");
		task14.setAttribute(EstimationElement.START_DATE,new SoapDate(20,11,2004));
		task14.setAttribute(EstimationElement.END_DATE, new SoapDate(21,11,2004));
		task14.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(3));
		task14.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(2));
		task14.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task14.setPriority(2);
		task14.setMember(m4);
		listTask3.addTask(task14);
		Task task15 = new Task(p.getName(),"Réaliser le référentiel des exigences");
		task15.setAttribute(EstimationElement.START_DATE,new SoapDate(4,12,2004));
		task15.setAttribute(EstimationElement.END_DATE, new SoapDate(5,12,2004));
		task15.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(1));
		task15.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(1));
		task15.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task15.setPriority(2);
		task15.setMember(m1);
		listTask3.addTask(task15);
		Task task16 = new Task(p.getName(),"Mettre à jour le document l'architecture du logiciel");
		task16.setAttribute(EstimationElement.START_DATE,new SoapDate(2,12,2004));
		task16.setAttribute(EstimationElement.END_DATE, new SoapDate(4,12,2004));
		task16.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(4));
		task16.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(3));
		task16.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task16.setPriority(2);
		task16.setMember(m2);
		listTask3.addTask(task16);
		Task task17 = new Task(p.getName(),"Coder le prototype");
		task17.setAttribute(EstimationElement.START_DATE,new SoapDate(20,11,2004));
		task17.setAttribute(EstimationElement.END_DATE, new SoapDate(04,12,2004));
		task17.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(16));
		task17.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(15));
		task17.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task17.setMember(m1);
		task17.setPriority(1);
		listTask3.addTask(task17);
		Task task18 = new Task(p.getName(),"Coder le prototype");
		task18.setAttribute(EstimationElement.START_DATE,new SoapDate(20,11,2004));
		task18.setAttribute(EstimationElement.END_DATE, new SoapDate(04,12,2004));
		task18.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(17));
		task18.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(16));
		task18.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task18.setMember(m2);
		task18.setPriority(1);
		listTask3.addTask(task18);
		Task task19 = new Task(p.getName(),"Analyser les indicateurs");
		task19.setAttribute(EstimationElement.START_DATE,new SoapDate(21,11,2004));
		task19.setAttribute(EstimationElement.END_DATE, new SoapDate(24,11,2004));
		task19.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(5));
		task19.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(6));
		task19.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task19.setMember(m3);
		task19.setPriority(1);
		listTask3.addTask(task19);
		Task task20 = new Task(p.getName(),"Evaluer l'itération ");
		task20.setAttribute(EstimationElement.START_DATE,new SoapDate(05,12,2004));
		task20.setAttribute(EstimationElement.END_DATE, new SoapDate(06,12,2004));
		task20.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(3));
		task20.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(2));
		task20.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task20.setMember(m4);
		task20.setPriority(2);
		listTask3.addTask(task20);
		Task task21 = new Task(p.getName(),"Définir l'itération suivante ");
		task21.setAttribute(EstimationElement.START_DATE,new SoapDate(05,12,2004));
		task21.setAttribute(EstimationElement.END_DATE, new SoapDate(06,12,2004));
		task21.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(2));
		task21.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(3));
		task21.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task21.setMember(m4);
		task21.setPriority(2);
		listTask3.addTask(task21);
		
		ListTask listTask4 = it4.new ListTask(p.getName(),"Liste des tâches");
		Task task22 = new Task(p.getName(),"Affiner l'architecture du logiciel");
		task22.setAttribute(EstimationElement.START_DATE,new SoapDate(13,12,2004));
		task22.setAttribute(EstimationElement.END_DATE, new SoapDate(14,12,2004));
		task22.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(3));
		task22.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(2));
		task22.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task22.setMember(m2);
		task22.setPriority(1);
		listTask4.addTask(task22);
		Task task23 = new Task(p.getName(),"Codage du cas d'utilisation ouverture et sauvegarde");
		task23.setAttribute(EstimationElement.START_DATE,new SoapDate(16,12,2004));
		task23.setAttribute(EstimationElement.END_DATE, new SoapDate(29,12,2004));
		task23.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(10));
		task23.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(12));
		task23.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task23.setMember(m1);
		task23.setPriority(1);
		listTask4.addTask(task23);
		Task task24 = new Task(p.getName(),"Codage du cas d'utilisation configuration du logiciel");
		task24.setAttribute(EstimationElement.START_DATE,new SoapDate(17,12,2004));
		task24.setAttribute(EstimationElement.END_DATE, new SoapDate(02,01,2005));
		task24.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(12));
		task24.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(10));
		task24.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task24.setMember(m2);
		task24.setPriority(1);
		listTask4.addTask(task24);
		Task task25 = new Task(p.getName(),"Evaluer l'itération ");
		task25.setAttribute(EstimationElement.START_DATE,new SoapDate(01,01,2005));
		task25.setAttribute(EstimationElement.END_DATE, new SoapDate(03,01,2005));
		task25.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(4));
		task25.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(3));
		task25.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task25.setMember(m4);
		task25.setPriority(2);
		listTask4.addTask(task25);
		Task task26 = new Task(p.getName(),"Définir l'itération suivante ");
		task26.setAttribute(EstimationElement.START_DATE,new SoapDate(01,01,2005));
		task26.setAttribute(EstimationElement.END_DATE, new SoapDate(03,01,2005));
		task26.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(3));
		task26.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(2));
		task26.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(0));
		task26.setMember(m3);
		task26.setPriority(2);
		listTask4.addTask(task26);
		
		ListTask listTask5 = it1.new ListTask(p.getName(),"Liste des tâches");
		Task task27 = new Task(p.getName(),"Codage du cas d'utilisation génération des statistiques");
		task27.setAttribute(EstimationElement.START_DATE,new SoapDate(05,01,2005));
		task27.setAttribute(EstimationElement.END_DATE, new SoapDate(18,01,2005));
		task27.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(9));
		task27.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(10));
		task27.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(3));
		task27.setMember(m1);
		task27.setPriority(1);
		listTask5.addTask(task27);
		Task task28 = new Task(p.getName(),"Codage partie serveur");
		task28.setAttribute(EstimationElement.START_DATE,new SoapDate(06,01,2005));
		task28.setAttribute(EstimationElement.END_DATE, new SoapDate(17,01,2005));
		task28.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(6));
		task28.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(13));
		task28.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(6));
		task28.setMember(m2);
		task28.setPriority(1);
		listTask5.addTask(task28);
		Task task29 = new Task(p.getName(),"Evaluer l'itération ");
		task29.setAttribute(EstimationElement.START_DATE,new SoapDate(19,01,2005));
		task29.setAttribute(EstimationElement.END_DATE, new SoapDate(21,01,2005));
		task29.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(0));
		task29.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(2));
		task29.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(2));
		task29.setMember(m4);
		task29.setPriority(2);
		listTask5.addTask(task29);
		Task task30 = new Task(p.getName(),"Définir l'itération suivante ");
		task30.setAttribute(EstimationElement.START_DATE,new SoapDate(20,01,2005));
		task30.setAttribute(EstimationElement.END_DATE, new SoapDate(21,01,2005));
		task30.setAttribute(EstimationElement.ELAPSED_HOURS, new Integer(0));
		task30.setAttribute(EstimationElement.ESTIMATED_HOURS, new Integer(3));
		task30.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, new Integer(3));
		task30.setMember(m3);
		task30.setPriority(2);
		listTask5.addTask(task30);
		
		IndicatorManager indMan = IndicatorManager.getInstance();
		indMan.setProperty(p.getName(),"indicator1_"+p.getID(),"30");
		indMan.setProperty(p.getName(),"indicator2_"+p.getID(),"40");
		indMan.setProperty(p.getName(),"indicator3_"+p.getID(),"30");
		indMan.setProperty(p.getName(),"indicator4_"+p.getID(),"70");
		indMan.setProperty(p.getName(),"indicator1_"+it1.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+it1.getID(),"90");
		indMan.setProperty(p.getName(),"indicator3_"+it1.getID(),"100");
		indMan.setProperty(p.getName(),"indicator4_"+it1.getID(),"0");
		indMan.setProperty(p.getName(),"indicator1_"+it2.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+it2.getID(),"100");
		indMan.setProperty(p.getName(),"indicator3_"+it2.getID(),"100");
		indMan.setProperty(p.getName(),"indicator4_"+it2.getID(),"0");
		indMan.setProperty(p.getName(),"indicator1_"+it3.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+it3.getID(),"105");
		indMan.setProperty(p.getName(),"indicator3_"+it3.getID(),"100");
		indMan.setProperty(p.getName(),"indicator4_"+it3.getID(),"0");
		indMan.setProperty(p.getName(),"indicator1_"+it4.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+it4.getID(),"95");
		indMan.setProperty(p.getName(),"indicator3_"+it4.getID(),"100");
		indMan.setProperty(p.getName(),"indicator4_"+it4.getID(),"0");
		indMan.setProperty(p.getName(),"indicator1_"+it5.getID(),"70");
		indMan.setProperty(p.getName(),"indicator2_"+it5.getID(),"80");
		indMan.setProperty(p.getName(),"indicator3_"+it5.getID(),"60");
		indMan.setProperty(p.getName(),"indicator4_"+it5.getID(),"40");
		indMan.setProperty(p.getName(),"indicator1_"+task1.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task1.getID(),"105");
		indMan.setProperty(p.getName(),"indicator1_"+task2.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task2.getID(),"90");
		indMan.setProperty(p.getName(),"indicator1_"+task3.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task3.getID(),"100");
		indMan.setProperty(p.getName(),"indicator1_"+task4.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task4.getID(),"95");
		indMan.setProperty(p.getName(),"indicator1_"+task5.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task5.getID(),"110");
		indMan.setProperty(p.getName(),"indicator1_"+task6.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task6.getID(),"105");
		indMan.setProperty(p.getName(),"indicator1_"+task7.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task7.getID(),"90");
		indMan.setProperty(p.getName(),"indicator1_"+task8.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task8.getID(),"100");
		indMan.setProperty(p.getName(),"indicator1_"+task9.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task9.getID(),"95");
		indMan.setProperty(p.getName(),"indicator1_"+task10.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task10.getID(),"110");
		indMan.setProperty(p.getName(),"indicator1_"+task11.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task11.getID(),"90");
		indMan.setProperty(p.getName(),"indicator1_"+task12.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task12.getID(),"95");
		indMan.setProperty(p.getName(),"indicator1_"+task13.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task13.getID(),"105");
		indMan.setProperty(p.getName(),"indicator1_"+task14.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task14.getID(),"95");
		indMan.setProperty(p.getName(),"indicator1_"+task15.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task15.getID(),"90");
		indMan.setProperty(p.getName(),"indicator1_"+task16.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task16.getID(),"105");
		indMan.setProperty(p.getName(),"indicator1_"+task17.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task17.getID(),"110");
		indMan.setProperty(p.getName(),"indicator1_"+task18.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task18.getID(),"90");
		indMan.setProperty(p.getName(),"indicator1_"+task19.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task19.getID(),"105");
		indMan.setProperty(p.getName(),"indicator1_"+task20.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task20.getID(),"110");
		indMan.setProperty(p.getName(),"indicator1_"+task21.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task21.getID(),"95");
		indMan.setProperty(p.getName(),"indicator1_"+task22.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task22.getID(),"85");
		indMan.setProperty(p.getName(),"indicator1_"+task23.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task23.getID(),"95");
		indMan.setProperty(p.getName(),"indicator1_"+task24.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task24.getID(),"100");
		indMan.setProperty(p.getName(),"indicator1_"+task25.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task25.getID(),"105");
		indMan.setProperty(p.getName(),"indicator1_"+task26.getID(),"100");
		indMan.setProperty(p.getName(),"indicator2_"+task26.getID(),"105");
		indMan.setProperty(p.getName(),"indicator1_"+task27.getID(),"75");
		indMan.setProperty(p.getName(),"indicator2_"+task27.getID(),"90");
		indMan.setProperty(p.getName(),"indicator1_"+task28.getID(),"35");
		indMan.setProperty(p.getName(),"indicator2_"+task28.getID(),"30");
		indMan.setProperty(p.getName(),"indicator1_"+task29.getID(),"0");
		indMan.setProperty(p.getName(),"indicator2_"+task29.getID(),"0");
		indMan.setProperty(p.getName(),"indicator1_"+task30.getID(),"0");
		indMan.setProperty(p.getName(),"indicator2_"+task30.getID(),"0");
		
	    insertInModel(new Object[]{p, it1,it2,it3,it4,it5, listTask1,listTask2,listTask3,listTask4,listTask5}, 
	            	  new Object[]{listProjects,p,p,p,p,p,it1,it2,it3,it4,it5}, null);*/
	    
	    insertInModel(new Object[]{p}, 
	          	  new Object[]{listProjects}, null);
	    
	    loadProject(p);
	    
	}
	
	/**
	 * Load an existing project
	 * @param parent
	 */
	private void loadProject( IPackage parent )
	{
		for( int i = 0; i < parent.modelElementCount(); i++ )
		{	
		    insertInModel( new Object[]{parent.getModelElement(i)} ,new Object[]{parent},null );
		    if( parent.getModelElement(i) instanceof IPackage && !(parent.getModelElement(i) instanceof ListTask))
			{
				loadProject((IPackage)parent.getModelElement(i));
			}
		}
	}
	
	/**
	 * Adds a listener for the Mediator event posted after the model changes
	 * 
	 * @param l the listener to add
	 * @return true if the listener can be added, false otherwise
	 */
	public boolean addSoapMediatorListener( Listener l )
	{
		if( !mListeners.contains( l ) )
		{
			mListeners.add( l );
			return true;
		}
		return false;
	}
	
	/**
	 * Removes a listener previously added with addSoapMediatorListener
	 * 
	 * @param l the listener to add
	 * @return true if the listener can be added, false otherwise
	 */
	public boolean removeSoapMediatorListener( Listener l )
	{
		return mListeners.remove( l );
	}

	/**
	 * Clear all current SoapMediator listeners
	 */
	public void clearListeners()
	{
		mListeners.clear();
	}
	
	/**
	 * Clear all listeners and all registered diagrams
	 */
	public void clearAll()
	{
		mListeners.clear();
	}
	

	/**
	 * Notify all listeners that have registered interest for
	 * notification on this event type.  
	 * 
	 * @param e the event
	 */
	protected void fireModelChanged( SoapEvent e )
	{
		for( int i = 0; i < mListeners.size(); i++ )
		{
			((Listener) mListeners.get( i )).modelChanged( e );
		}
	}
	
	public void insertInModel (Object[] elements, Object[] parents,Map extras)
	{
	    if( parents == null || elements == null || elements.length != parents.length)
			throw new IllegalArgumentException("SoapMediator.insertInModel : elements and parents length was different!");
	    SoapEvent event = createInsertEvent(elements, parents, extras) ;
	    if (event != null)
	    {
	        fireModelChanged( event );
	    }
	}
	
	public SoapEvent createInsertEvent(Object[] elements,	Object[] parents,Map extras)
	{
	    SoapEvent e = null ;	  
	    for (int i = 0; i < parents.length; i++)
	    {
	        if(elements[i] instanceof ModelElement && parents[i] instanceof IPackage)
	        {
	            if (! verifyInsertElementIntoPackage((ModelElement)elements[i], (IPackage)parents[i]))
	            {
	            	return null;
	            }
	        }
	        else
	        {
	            return null;
	        }
	   }
	   e = new SoapEvent(elements, null,parents ,extras) ;
	   return e ;
	}

	public boolean verifyInsertElementIntoPackage (ModelElement element, IPackage parent)
	{    
	    if (parent instanceof SoapListProjects && ! (element instanceof Project) ||
	            parent instanceof Project && ! (element instanceof Iteration) ||
	            	parent instanceof Iteration && ! (element instanceof ListTask) ||
	            		parent instanceof Task && ! (element instanceof Artifact))
	    {
	        return false ;
	    }
	    parent.addModelElement(element) ;
	    return true ;
	}

	
	/**
	 * Removes elements from the model
	 *  
	 * @param elements the elements to removed
     * @param extras a stored map wich will be returned by the SoapEvent
	 */
	public void removeFromModel(Object[] elements, Map extras)
	{
	    if(elements == null)
			throw new IllegalArgumentException("SoapMediator.removeFromModel : no elements specified");
	    SoapEvent event = createRemoveEvent(elements, extras) ;
	    if (event != null)
	    {
	        fireModelChanged( event );
	    }
	}
	
	public SoapEvent createRemoveEvent (Object[] elements, Map extras)
	{
	    SoapEvent event = null ;
	    IPackage parents [] = new IPackage[elements.length];
	    
	    for (int i = 0; i < elements.length ; i++)
	    {
	        if(elements[i] instanceof ModelElement && ((ModelElement)elements[i]).getParent() != null)
	        {
	            parents[i] = ((ModelElement)elements[i]).getParent() ;
	            parents[i].removeModelElement((ModelElement)elements[i]) ;
	        }
	        else
	        {
	            return null;
	        }
	    }
	    event = new SoapEvent(null, elements, parents, extras);
	    return event ;
	}     
	
	/**
	 * Defines the interface for an object that listens to changes in a SoapMediator
	 */
	public static interface Listener
	{
		public void modelChanged(SoapEvent e );
	}
}
