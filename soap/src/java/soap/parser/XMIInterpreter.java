package soap.parser;

import java.util.ArrayList;

import soap.model.core.EstimationElement;
import soap.model.core.ModelElement;
import soap.model.executionProcess.structure.Artifact;
import soap.model.executionProcess.structure.Iteration;
import soap.model.executionProcess.structure.Project;
import soap.model.executionProcess.structure.Task;
import soap.model.executionProcess.structure.user.Member;

/**
 * @author Czerny
 */
public class XMIInterpreter {
	// Tags values handled
	public static final String ROOT_PROJECT_EXPORT = "exportProjet";
	
	public static final String CTG_EXECUTION_EXPORT = "exportExecution";
	public static final String CTG_PROJECT_ELEMENT = "elementProjet";
	public static final String CTG_PROJECT_LINKS = "lienProjet";
	public static final String CTG_PROJECT_TO_PROCESS_LINKS = "lienProjetProcessus";

	public static final String OBJ_PROJECT = "projet";
//	public static final String OBJ_METRIC = "eltMetrique";
//	public static final String OBJ_RISK = "eltRisque";
	public static final String OBJ_MEMBER = "eltMembre";
	public static final String OBJ_ITERATION = "eltIteration";
	public static final String OBJ_TASK = "eltTache";
//	public static final String OBJ_CONDITION = "eltCondition";
	public static final String OBJ_COLLABORATIVE_TASK = "eltTacheCollaborative";
	public static final String OBJ_ARTIFACT = "eltArtefact";
//	public static final String OBJ_PROBLEM = "eltProbleme";
	public static final String OBJ_PROJECT_ITERATION = "projetIteration";
	public static final String OBJ_PROJECT_METRIC = "projetMetrique";
	public static final String OBJ_PROJECT_RISK = "projetRisque";
	public static final String OBJ_PROJECT_MEMBER = "projetMembre";
	public static final String OBJ_ITERATION_TASK = "iterationTache";
	public static final String OBJ_MEMBER_ARTIFACT = "MembreArtefact";
	public static final String OBJ_MEMBER_TASK = "membreTache";
	public static final String OBJ_COLLABORATIVE_TASK_INPUT_ARTIFACT = "TacheColArtefact_Entree";
	public static final String OBJ_COLLABORATIVE_TASK_OUTPUT_ARTIFACT = "TacheColArtefact_Sortie";
	public static final String OBJ_TASK_INPUT_ARTIFACT = "TacheArtefact_Entree";
	public static final String OBJ_TASK_OUTPUT_ARTIFACT = "TacheArtefact_Sortie";
	
//	public static final String LST_WORK_DEF_CONDITION = "listeConditionDefTravail";
//	public static final String LST_METRICS_LIST = "listeMetriques";
//	public static final String LST_RISKS_LIST = "listeRisques";
	public static final String LST_MEMBERS_LIST = "listeMembres";
	public static final String LST_ITERATIONS_LIST = "listeIterations";
	public static final String LST_TASKS_LIST = "listeTaches";
//	public static final String LST_CONDITIONS_LIST = "listeConditionTache";
	public static final String LST_COLLABORATIVE_TASKS_LIST = "listeTacheCollaboratives";
	public static final String LST_ARTIFACTS_LIST = "listeArtefacts";
//	public static final String LST_PROBLEMS_LIST = "listeProblemes";
//	public static final String LST_PROBLEM_LIST = "listeProbleme";
	public static final String LST_ITERATION_ID_LIST = "listeIdIteration";
//	public static final String LST_METRIC_ID_LIST = "listeIdMetrique";
	public static final String LST_MEMBER_ID_LIST = "listeIdMembre";
//	public static final String LST_RISK_ID_LIST = "listeIdRisque";
	public static final String LST_TASK_ID_LIST = "listeIdTache";
	public static final String LST_MEMBER_LIST = "listeMembre";
	public static final String LST_ARTIFACT_LIST = "listeArtefact";
	public static final String LST_TASK_LIST = "listeTache";
	
	public static final String LNK_PROJECT_ITERATION = "listeProjetIteration";
//	public static final String LNK_PROJECT_METRIC = "listeProjetMetrique";
//	public static final String LNK_PROJECT_RISK = "listeProjetRisque";
	public static final String LNK_ITERATION_TASK = "listeIterationTache";
	public static final String LNK_PROJECT_MEMBER = "listeProjetMembre";
	public static final String LNK_MEMBER_ARTIFACT = "listeMembreArtefact";
	public static final String LNK_MEMBER_TASK = "listeMembreTache";
//	public static final String LNK_TASK_BEGIN_IF_CONDITION = "listeTache_DebuteSi_Condition";
//	public static final String LNK_CONDITION_FOR_TASK = "listeCondition_PorteSur_Tache";
	public static final String LNK_INPUT_ARTIFACT_TASK = "listeTacheArtefact_Entree";
	public static final String LNK_OUTPUT_ARTIFACT_TASK = "listeTacheArtefact_Sortie";
	public static final String LNK_INPUT_ARTIFACT_COLLABORATIVE_TASK = "listeTacheColArtefact_Entree";
	public static final String LNK_OUTPUT_ARTIFACT_COLLABORATIVE_TASK = "listeTacheColArtefact_Sortie";
//	public static final String LNK_COLLABORATIVE_TASK_RESPONSABILITY = "listeMembreTacheCollaborative_Responsable";
	public static final String LNK_COLLABORATIVE_TASK_REALISATION = "listeMembreTacheCollaborative_Realise";
//	public static final String LNK_TASK_PROBLEM = "listeTache_Provoque_Probleme";
//	public static final String LNK_PROBLEM_SOLVES_TASK = "listeProbleme_Resoud_Tache";
//	public static final String LNK_PROCESSUS_PROJECT = "ProcessusProjet";
//	public static final String LNK_PRODUCT_ARTIFACT = "listeProduitArtefact";
//	public static final String LNK_ACTIVITY_TASK = "listeActiviteTache";
//	public static final String LNK_MEMBER_ROLE = "MembreRole";

	public static final String ATT_ID = "id";
	public static final String ATT_ID_PROJECT = "idProjet";
	public static final String ATT_ID_ITERATION = "idIteration";
	public static final String ATT_ID_MEMBER = "idMembre";
	public static final String ATT_ID_TASK = "idTache";
	public static final String ATT_ID_COLLABORATIVE_TASK = "idTacheCol";
	public static final String ATT_NAME = "nom";
	public static final String ATT_DESCRIPTION = "description";
	public static final String ATT_BEGIN_DATE = "dateDebut";
	public static final String ATT_END_DATE = "dateFin";
	public static final String ATT_BUDGET = "budget";
	public static final String ATT_VALUE = "valeur";
	public static final String ATT_TYPE = "type";
	public static final String ATT_PRIORITY = "priorite";
	public static final String ATT_IMPACT = "impact";
	public static final String ATT_STATE = "etat";
	public static final String ATT_FIRSTNAME = "prenom";
	public static final String ATT_LOGIN = "login";
	public static final String ATT_ADDRESS = "adresse";
	public static final String ATT_TELEPHONE = "telephone";
	public static final String ATT_EMAIL = "email";
	public static final String ATT_NUMBER = "numero";
	public static final String ATT_ESTIMATED_BEGIN_DATE = "dateDebutPrevue";
	public static final String ATT_REAL_BEGIN_DATE = "dateDebutReelle";
	public static final String ATT_ESTIMATED_END_DATE = "dateFinPrevue";
	public static final String ATT_REAL_END_DATE = "dateFinReelle";
	public static final String ATT_ESTIMATED_WORK = "chargePrevue";
	public static final String ATT_ELAPSED_TIME = "tempsPasse";
	public static final String ATT_REMAINING_TIME = "resteAPasser";
	public static final String ATT_DELIVERABLE = "livrable";
	public static final String ATT_CAUSE = "cause";
	
	public static final String NULL = "null";
	// Lists containing expected tags. These lists are initialized statically.
	public static final ArrayList CATEGORIES;
	public static final ArrayList OBJECTS;
	public static final ArrayList LISTS;
	public static final ArrayList LINKS;
	public static final ArrayList ATTRIBUTES;
	public static final ArrayList IDS;
	// Constant static tag LISTS initialisation. 
	static {
		CATEGORIES = new ArrayList(4);
		CATEGORIES.add(CTG_EXECUTION_EXPORT);
		CATEGORIES.add(CTG_PROJECT_ELEMENT);
		CATEGORIES.add(CTG_PROJECT_LINKS);
		CATEGORIES.add(CTG_PROJECT_TO_PROCESS_LINKS);
		
		OBJECTS = new ArrayList(21);
		OBJECTS.add(OBJ_PROJECT);
//		OBJECTS.add(OBJ_METRIC);
//		OBJECTS.add(OBJ_RISK);
		OBJECTS.add(OBJ_MEMBER);
		OBJECTS.add(OBJ_ITERATION);
		OBJECTS.add(OBJ_TASK);
//		OBJECTS.add(OBJ_CONDITION);
		OBJECTS.add(OBJ_COLLABORATIVE_TASK);
		OBJECTS.add(OBJ_ARTIFACT);
//		OBJECTS.add(OBJ_PROBLEM);
		OBJECTS.add(OBJ_PROJECT_ITERATION);
		OBJECTS.add(OBJ_PROJECT_METRIC);
		OBJECTS.add(OBJ_PROJECT_RISK);
		OBJECTS.add(OBJ_PROJECT_MEMBER);
		OBJECTS.add(OBJ_ITERATION_TASK);
		OBJECTS.add(OBJ_MEMBER_ARTIFACT);
		OBJECTS.add(OBJ_MEMBER_TASK);
		OBJECTS.add(OBJ_COLLABORATIVE_TASK_INPUT_ARTIFACT);
		OBJECTS.add(OBJ_COLLABORATIVE_TASK_OUTPUT_ARTIFACT);
		OBJECTS.add(OBJ_TASK_INPUT_ARTIFACT);
		OBJECTS.add(OBJ_TASK_OUTPUT_ARTIFACT);

		LISTS = new ArrayList(18);
//		LISTS.add(LST_WORK_DEF_CONDITION);
//		LISTS.add(LST_METRICS_LIST);
//		LISTS.add(LST_RISKS_LIST);
		LISTS.add(LST_MEMBERS_LIST);
		LISTS.add(LST_ITERATIONS_LIST);
		LISTS.add(LST_TASKS_LIST);
//		LISTS.add(LST_CONDITIONS_LIST);
		LISTS.add(LST_COLLABORATIVE_TASKS_LIST);
		LISTS.add(LST_ARTIFACTS_LIST);
//		LISTS.add(LST_PROBLEM_LIST);
//		LISTS.add(LST_PROBLEMS_LIST);
		LISTS.add(LST_ARTIFACT_LIST);
		LISTS.add(LST_ITERATION_ID_LIST);
		LISTS.add(LST_MEMBER_LIST);
//		LISTS.add(LST_METRIC_ID_LIST);
//		LISTS.add(LST_RISK_ID_LIST);
		LISTS.add(LST_TASK_ID_LIST);
		LISTS.add(LST_TASK_LIST);
		LISTS.add(LST_MEMBER_ID_LIST);
		
		LINKS = new ArrayList(21);
		LINKS.add(LNK_PROJECT_ITERATION);
//		LINKS.add(LNK_PROJECT_METRIC);
//		LINKS.add(LNK_PROJECT_RISK);
		LINKS.add(LNK_ITERATION_TASK);
		LINKS.add(LNK_PROJECT_MEMBER);
		LINKS.add(LNK_MEMBER_ARTIFACT);
		LINKS.add(LNK_MEMBER_TASK);
//		LINKS.add(LNK_TASK_BEGIN_IF_CONDITION);
//		LINKS.add(LNK_CONDITION_FOR_TASK);
		LINKS.add(LNK_INPUT_ARTIFACT_TASK);
		LINKS.add(LNK_OUTPUT_ARTIFACT_TASK);
		LINKS.add(LNK_INPUT_ARTIFACT_COLLABORATIVE_TASK);
		LINKS.add(LNK_OUTPUT_ARTIFACT_COLLABORATIVE_TASK);
//		LINKS.add(LNK_COLLABORATIVE_TASK_RESPONSABILITY);
		LINKS.add(LNK_COLLABORATIVE_TASK_REALISATION);
//		LINKS.add(LNK_TASK_PROBLEM);
//		LINKS.add(LNK_PROBLEM_SOLVES_TASK);
//		LINKS.add(LNK_PROCESSUS_PROJECT);
//		LINKS.add(LNK_PRODUCT_ARTIFACT);
//		LINKS.add(LNK_ACTIVITY_TASK);
//		LINKS.add(LNK_MEMBER_ROLE);
		
		ATTRIBUTES = new ArrayList(31);
		ATTRIBUTES.add(ATT_ADDRESS);
		ATTRIBUTES.add(ATT_BEGIN_DATE);
		ATTRIBUTES.add(ATT_BUDGET);
		ATTRIBUTES.add(ATT_CAUSE);
		ATTRIBUTES.add(ATT_DELIVERABLE);
		ATTRIBUTES.add(ATT_DESCRIPTION);
		ATTRIBUTES.add(ATT_ELAPSED_TIME);
		ATTRIBUTES.add(ATT_EMAIL);
		ATTRIBUTES.add(ATT_END_DATE);
		ATTRIBUTES.add(ATT_ESTIMATED_BEGIN_DATE);
		ATTRIBUTES.add(ATT_ESTIMATED_END_DATE);
		ATTRIBUTES.add(ATT_ESTIMATED_WORK);
		ATTRIBUTES.add(ATT_FIRSTNAME);
		ATTRIBUTES.add(ATT_ID);
		ATTRIBUTES.add(ATT_ID_PROJECT);
		ATTRIBUTES.add(ATT_ID_ITERATION);
		ATTRIBUTES.add(ATT_ID_MEMBER);
		ATTRIBUTES.add(ATT_ID_TASK);
		ATTRIBUTES.add(ATT_ID_COLLABORATIVE_TASK);
		ATTRIBUTES.add(ATT_IMPACT);
		ATTRIBUTES.add(ATT_LOGIN);
		ATTRIBUTES.add(ATT_NAME);
		ATTRIBUTES.add(ATT_NUMBER);
		ATTRIBUTES.add(ATT_PRIORITY);
		ATTRIBUTES.add(ATT_REAL_BEGIN_DATE);
		ATTRIBUTES.add(ATT_REAL_END_DATE);
		ATTRIBUTES.add(ATT_REMAINING_TIME);
		ATTRIBUTES.add(ATT_STATE);
		ATTRIBUTES.add(ATT_TELEPHONE);
		ATTRIBUTES.add(ATT_TYPE);
		ATTRIBUTES.add(ATT_VALUE);
		
		IDS = new ArrayList(6);
		IDS.add(ATT_ID);
		IDS.add(ATT_ID_PROJECT);
		IDS.add(ATT_ID_ITERATION);
		IDS.add(ATT_ID_MEMBER);
		IDS.add(ATT_ID_TASK);
		IDS.add(ATT_ID_COLLABORATIVE_TASK);
	}

	/**
	 * This method creates a non initialised object with a type depending of the <code>tag</code>. 
	 * @param tag the XMI tag specifying the type of <code>Object</code> to create. 
	 * @return the <code>Object</code> created. 
	 * @throws XMIException if the <code>tag</code> does not match with any of the expected XMI tags. 
	 */
	public static ModelElement createObject(String tag) throws XMIException{
		ModelElement newObj;
		
		if (tag.equals(OBJ_PROJECT))
			newObj = new Project(new ID(OBJ_PROJECT));
		else if (tag.equals(OBJ_MEMBER))
			newObj = new Member(new ID(OBJ_MEMBER));
		else if (tag.equals(OBJ_ITERATION))
			newObj = new Iteration(new ID(OBJ_ITERATION));
		else if (tag.equals(OBJ_TASK))
			newObj = new Task(new ID(OBJ_TASK));
		else if (tag.equals(OBJ_COLLABORATIVE_TASK))
			newObj = new Task(new ID(OBJ_COLLABORATIVE_TASK));
		else if (tag.equals(OBJ_ARTIFACT))
			newObj = new Artifact(new ID(OBJ_ARTIFACT));
		else newObj = null;

		if (newObj != null)
			System.out.println("/!\\ " + newObj.getClass().toString() + " /!\\"); 

		return newObj;
	}
	
	/**
	 * This method alters the target <code>Object</code>'s attribute specified by the <code>tag</code> with the value <code>value</code>. 
	 * @param target The <code>Object</code> to alter. 
	 * @param tag The XMI tag indicating the attribute concerned by the modification. 
	 * @param value The value to set in the target <code>Object</code>. 
	 */
	public static void completeObject(ModelElement target, String tag, String value){
		if (value.equals(NULL))
			return;
		// Case of a Project
		if (target instanceof Project){
			if (tag.equals(ATT_NAME))
				target.setName(value);
			else if (tag.equals(ATT_BEGIN_DATE))
				target.setAttribute(EstimationElement.START_DATE, value);
			else if (tag.equals(ATT_END_DATE))
				target.setAttribute(EstimationElement.END_DATE, value);
			else if (tag.equals(ATT_ELAPSED_TIME))
				target.setAttribute(EstimationElement.ELAPSED_HOURS, value);
			else if (tag.equals(ATT_REMAINING_TIME))
				target.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, value);
			else if (tag.equals(ATT_BUDGET))
				target.setAttribute(EstimationElement.BUDGET, value);
			else if (tag.equals(ATT_DESCRIPTION))
				target.setAttribute(EstimationElement.DESCRIPTION, value);
			return;
		} // Case of a Member
		if (target instanceof Member){
			if (tag.equals(ATT_NAME))
				((Member) target).setLastName(value);
			else if (tag.equals(ATT_FIRSTNAME))
				((Member) target).setFirstName(value);
			else if (tag.equals(ATT_LOGIN))
				((Member) target).setLogin(value);
			else if (tag.equals(ATT_EMAIL))
				((Member) target).setEMail(value);
			else if (tag.equals(ATT_ADDRESS))
				((Member) target).setAddress(value);
			else if (tag.equals(ATT_TELEPHONE))
				((Member) target).setTelephone(value);
			return;
		} // Case of an Iteration
		if (target instanceof Iteration){
			if (tag.equals(ATT_NUMBER))
				target.setName(value);
			else if (tag.equals(ATT_ESTIMATED_BEGIN_DATE))
				target.setAttribute(EstimationElement.ESTIMATED_START_DATE, value);
			else if (tag.equals(ATT_ESTIMATED_END_DATE))
				target.setAttribute(EstimationElement.ESTIMATED_END_DATE, value);
			else if (tag.equals(ATT_REAL_BEGIN_DATE))
				target.setAttribute(EstimationElement.START_DATE, value);
			else if (tag.equals(ATT_REAL_END_DATE))
				target.setAttribute(EstimationElement.END_DATE, value);
			return;
		} // Case of a Task
		if (target instanceof Task){
			if (tag.equals(ATT_NAME))
				target.setName(value);
			else if (tag.equals(ATT_DESCRIPTION))
				target.setAttribute(EstimationElement.DESCRIPTION, value);
			else if (tag.equals(ATT_ELAPSED_TIME))
				target.setAttribute(EstimationElement.ELAPSED_HOURS, value);
			else if (tag.equals(ATT_REMAINING_TIME))
				target.setAttribute(EstimationElement.REMAINED_HOURS_TO_FINISH, value);
			else if (tag.equals(ATT_ESTIMATED_WORK))
				target.setAttribute(EstimationElement.ESTIMATED_HOURS, value);
			else if (tag.equals(ATT_ESTIMATED_BEGIN_DATE))
				target.setAttribute(EstimationElement.ESTIMATED_START_DATE, value);
			else if (tag.equals(ATT_ESTIMATED_END_DATE))
				target.setAttribute(EstimationElement.ESTIMATED_END_DATE, value);
			else if (tag.equals(ATT_REAL_BEGIN_DATE))
				target.setAttribute(EstimationElement.START_DATE, value);
			else if (tag.equals(ATT_REAL_END_DATE))
				target.setAttribute(EstimationElement.END_DATE, value);
			else if (tag.equals(ATT_STATE))
				target.setAttribute(EstimationElement.STATE, value);
			return;
		} // Case of an Artifact
		if (target instanceof Artifact){
			if (tag.equals(ATT_NUMBER))
				target.setName(value);
			else if (tag.equals(ATT_DESCRIPTION))
				target.setAttribute(EstimationElement.DESCRIPTION, value);
			else if (tag.equals(ATT_DELIVERABLE))
				target.setAttribute(EstimationElement.DELIVERABLE, value);
			else if (tag.equals(ATT_STATE))
				target.setAttribute(EstimationElement.STATE, value);
			return;
		}
	}
}