package soap.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.LocatorImpl;

import soap.Context;
import soap.model.core.ModelElement;
import soap.model.executionProcess.structure.Artifact;
import soap.model.executionProcess.structure.Iteration;
import soap.model.executionProcess.structure.Project;
import soap.model.executionProcess.structure.Task;
import soap.model.executionProcess.structure.user.Member;

/**
 * @author Czerny
 */
public class XMIContentHandler implements ContentHandler {

	// Maps that will contain the OBJECTS generated during the parse before linking them.
	private HashMap elements;
	private ArrayList tmpElems;
	private ArrayList tmpIds;
	// Variable containing the parent tag or "" if there is none. 
	private ArrayList zone;
	private ArrayList lastTag;
	// Temporary current ModelElement
	private ModelElement workingElement;
	// Constants used for the zones identifications. 
	public static final String ROOT = "ROOT";
	public static final String CATEGORY = "CTG";
	public static final String OBJECT = "OBJ";
	public static final String LIST = "LST";
	public static final String LINK = "LNK";
	public static final String ATTRIBUTE = "ATT"; 
	public static final String UNUSED = "NSD";
	// Constants used for the actions identifications. TODO Delete ?
/*	public static final String NEW_OBJECT = "N_OBJ";
	public static final String NEW_LIST = "N_CTG";
	public static final String NEW_LINK = "N_LNK";
*/
	private Locator locator;
	
	/**
	 * Default constructor.
	 */
	public XMIContentHandler() {
		super();
		locator = new LocatorImpl();
		elements = new HashMap();
		tmpElems = new ArrayList();
		tmpIds = new ArrayList();
		zone = new ArrayList();
		lastTag = new ArrayList();
	}

	/**
	 * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
	 */
	public void setDocumentLocator(Locator value) {
		locator = value;
	}

	/**
	 * Event thrown at the beginning of the XMI stream parse.
	 * 
	 * @throws SAXException
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	public void startDocument() throws XMIException {
		System.out.println("And parsing begun... ");
	}

	/**
	 * Event thrown at the end of the XMI stream parse.
	 * 
	 * @throws SAXException
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	public void endDocument() throws XMIException {
		// Creation of the final HashMap of elements by grouping the two temporary lists
/*		for (int i = 0; i < tmpElems.size(); i++) {
			elements.put(((ModelElement) tmpElems.get(i)).getID(), new Object[] {tmpElems.get(i), tmpIds.get(i)});
			System.out.println("Element " + ((ModelElement) tmpElems.get(i)).getName() + " (" + ((ModelElement) tmpElems.get(i)).getID() + ") " + " added with ID " + tmpIds.get(i));
		}
*/		Project mProject = null;
		for (int i = 0; i < tmpElems.size(); i++){
			if (tmpElems.get(i) instanceof Project)
				mProject = (Project) tmpElems.get(i);
			break;
		}
		Context.getInstance().getListProjects().addProject(mProject);
		System.out.println("Parsing done. ");
	}

	/**
	 * Both of the <code>nameSpaceURI</code> and <code>attributs</code> attributes are supposed to remain empty for
	 * all the XMI files' tags.
	 * 
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) throws XMIException {
		System.out.println("Opening tag : " + localName + " " + rawName);

		if (nameSpaceURI != "") {
			throw new XMIException("A namespace was detected in an XMI file : " + nameSpaceURI + ", in tag " + localName);
		}
		if (attributs.getLength() != 0)
			throw new XMIException("Attributes were detected in an XMI file for the tag : " + localName);
		
		treatTag(localName);
		
	}

	/**
	 * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void endElement(String nameSpaceURI, String localName, String rawName)
			throws XMIException {
		System.out.println("Closing tag : " + localName);

		if (nameSpaceURI != "") {
			throw new XMIException("A namespace was detected in an XMI file : " + nameSpaceURI + ", in tag " + localName);
		}
		if (zone.size() == 1)
			System.out.print(""); // TODO Delete. 
		if (XMIInterpreter.LISTS.contains(localName) || XMIInterpreter.LINKS.contains(localName) || XMIInterpreter.CATEGORIES.contains(localName))
			workingElement = null;
		zone.remove(zone.size() - 1);
		lastTag.remove(lastTag.size() - 1);
	}

	/**
	 * @param tag The XMI tag used to determine the action to perform. 
	 * @throws XMIException
	 */
	private void treatTag(String tag) throws XMIException{
		if (tag.equals(XMIInterpreter.CTG_PROJECT_ELEMENT))
			System.out.println((String) zone.get(zone.size() - 1) + zone.size());
		if (zone.isEmpty()){
			zone.add(ROOT);
			lastTag.add(tag);
			return;
		} else if (zone.get(zone.size() - 1).equals(ROOT)){
			if (XMIInterpreter.CATEGORIES.contains(tag)){
				zone.add(CATEGORY);
				lastTag.add(tag);
				return;
			}
		} else if (zone.get(zone.size() - 1).equals(CATEGORY)){
			if (XMIInterpreter.OBJECTS.contains(tag)){
				workingElement = XMIInterpreter.createObject(tag);
				if (workingElement != null)
					tmpElems.add(workingElement);

				zone.add(OBJECT);
				lastTag.add(tag);
				return;
			} else if (XMIInterpreter.LISTS.contains(tag)){
				zone.add(LIST);
				lastTag.add(tag);
				return;
			} else if (XMIInterpreter.LINKS.contains(tag)){
				zone.add(LINK);
				lastTag.add(tag);
				return;
			} 
		} else if (zone.get(zone.size() - 1).equals(OBJECT)){
			if (XMIInterpreter.ATTRIBUTES.contains(tag)){
				zone.add(ATTRIBUTE);
				lastTag.add(tag);
				return;
			} else if (XMIInterpreter.LISTS.contains(tag)){
				zone.add(LIST);
				lastTag.add(tag);
				return;
			}
		} else if (zone.get(zone.size() - 1).equals(LIST)){
			if (XMIInterpreter.OBJECTS.contains(tag)){
				workingElement = XMIInterpreter.createObject(tag);
				if (workingElement != null)
					tmpElems.add(workingElement);

				zone.add(OBJECT);
				lastTag.add(tag);
				return;
			} else if (XMIInterpreter.LISTS.contains(tag)){
				zone.add(LIST);
				lastTag.add(tag);
				return;
			} else if (XMIInterpreter.LINKS.contains(tag)){
				zone.add(LINK);
				lastTag.add(tag);
				return;
			} else if (XMIInterpreter.IDS.contains(tag)){
				zone.add(ATTRIBUTE);
				lastTag.add(tag);
				return;
			}
		} else if (zone.get(zone.size() - 1).equals(LINK)){
			if (XMIInterpreter.OBJECTS.contains(tag)){
				zone.add(OBJECT);
				lastTag.add(tag);
				return;
			} else if (XMIInterpreter.LISTS.contains(tag)){
				zone.add(LIST);
				lastTag.add(tag);
				return;
			}
		} else if (zone.get(zone.size() - 1).equals(ATTRIBUTE)){
			if (lastTag.get(lastTag.size() - 1).equals(XMIInterpreter.ATT_ID))
				tmpIds.add(tag);
			else XMIInterpreter.completeObject((ModelElement) tmpElems.get(tmpElems.size() - 1), (String) lastTag.get(lastTag.size() - 1), tag);
			return;
		}
		zone.add(UNUSED);
		lastTag.add(tag);
	}
	
	/**
	 * This event is not supposed to be thrown for XMI documents.
	 * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String,
	 *      java.lang.String)
	 */
	public void startPrefixMapping(String prefix, String URI)
			throws XMIException {
		System.out.println("Unhandled namespace start encountered : " + URI
				+ ", prefix : " + prefix);
	}

	/**
	 * This event is not supposed to be thrown for XMI documents.
	 * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
	 */
	public void endPrefixMapping(String prefix) throws XMIException {
		System.out.println("End of unhandled namespace : " + prefix);
		throw new XMIException("Malformed XMI expression : namespace encountered. Prefix : " + prefix);
	}

	/**
	 * Event thrown when a character is found outside tags.  
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	public void characters(char[] ch, int start, int end) throws XMIException {
		if (String.valueOf(ch, start, end).equals("\n"))
			return;
		if (lastTag.get(lastTag.size() - 1).equals(XMIInterpreter.CTG_PROJECT_LINKS) || zone.get(zone.size() - 1).equals(CATEGORY))
			System.out.print("");
		// We only treat characters when we are in an ATTRIBUTE or LINK zone. 
		if (zone.get(zone.size() - 1).equals(ATTRIBUTE)) {
			System.out.println("-" + String.valueOf(ch, start, end) + "-");
			// If we are reading the value of an expected ID, we add it to the IDs list. 
			if (lastTag.get(lastTag.size() - 1).equals(XMIInterpreter.ATT_ID) && !zone.get(zone.size() - 4).equals(LINK)){//XMIInterpreter.IDS.contains(lastTag.get(lastTag.size() - 1))
				if (tmpElems.size() == tmpIds.size() + 1)
					tmpIds.add(((ModelElement) tmpElems.get(tmpElems.size() - 1)).getID() + String.valueOf(ch, start, end));
			} // If the ID indicates a link to edit.  
			else if (XMIInterpreter.IDS.contains(lastTag.get(lastTag.size() - 1))){
				if (zone.get(zone.size() - 3).equals(LINK))
					focusOnElement(String.valueOf(ch, start, end), tagToIDPrefix((String) lastTag.get(lastTag.size() - 1)));
				else {
					int i = 0;
					while (lastTag.get(lastTag.size() - 1) == lastTag.get(lastTag.size() - 1 - i))
						i++;
					linkElements(workingElement, findElement(String.valueOf(ch, start, end), tagToIDPrefix((String) lastTag.get(lastTag.size() - 1 - i))));
				}
			}
			// Other attribute values are updated for the current object. 
			else XMIInterpreter.completeObject((ModelElement) tmpElems.get(tmpElems.size() - 1), (String) lastTag.get(lastTag.size() - 1), String.valueOf(ch, start, end));
		}
	}

	private boolean focusOnElement(String id, String tag){
		if ((workingElement = findElement(id, tag)) != null)
			return true;
		return false;
	}

	private ModelElement findElement(String id, String tag) {
		ModelElement tmp = null;
		
		for (int i = 0; i < tmpElems.size(); i++){
			if (tmpIds.get(i).equals(tag + id)) {
				tmp = (ModelElement) tmpElems.get(i);
				break;
			}
		}
		return tmp;
	}
	
	private void linkElements(ModelElement parent, ModelElement child){
		if (parent instanceof Project){
			if (child instanceof Iteration){
				((Project) parent).addIteration((Iteration) child);
				((Iteration) child).setProject((Project) parent);
			} else if (child instanceof Member){
				((Project) parent).addMember((Member) child);
				((Member) child).setParent((Project) parent);
			}
		} else if (parent instanceof Iteration){
			if (child instanceof Task){
				((Iteration) parent).addTask((Task) child);
				((Task) child).setParent((Iteration) parent);
			} 
		} else if (parent instanceof Member){
			if (child instanceof Artifact){
				((Member) parent).addArtifact((Artifact) child);
				((Artifact) child).addMember((Member) parent);
			} else if (child instanceof Task){
				((Member) parent).addTask((Task) child);
				((Task) child).setMember((Member) parent);
			}
		} else if (parent instanceof Task){
			if (child instanceof Artifact){
				if (lastTag.get(lastTag.size() - 3).equals(XMIInterpreter.OBJ_TASK_INPUT_ARTIFACT) || lastTag.get(lastTag.size() - 3).equals(XMIInterpreter.OBJ_COLLABORATIVE_TASK_INPUT_ARTIFACT))
					((Task) parent).addInputArtifact((Artifact) child);
				else if (lastTag.get(lastTag.size() - 3).equals(XMIInterpreter.OBJ_TASK_OUTPUT_ARTIFACT) || lastTag.get(lastTag.size() - 3).equals(XMIInterpreter.OBJ_COLLABORATIVE_TASK_OUTPUT_ARTIFACT))
					((Task) parent).addOutputArtifact((Artifact) child);
				((Artifact) child).addTask((Task) parent);
			}
		}
	//	Projet -> Iteration			OK
	//	Projet -> Membre			OK	
	//	Iteration -> Tache			OK
	//	Membre -> Artefact			OK
	//	Membre -> Tache				OK
	//	TacheCol -> Artefact in		OK
	//	TacheCol -> Artefact out	OK
	//	Tache -> Artefact in		OK
	//	Tache -> Artefact out		OK
	}
	private String tagToIDPrefix(String tag){
		if (tag.equals(XMIInterpreter.ATT_ID_PROJECT))
			return XMIInterpreter.OBJ_PROJECT;
		else if (tag.equals(XMIInterpreter.ATT_ID_COLLABORATIVE_TASK))
			return XMIInterpreter.OBJ_COLLABORATIVE_TASK;
		else if (tag.equals(XMIInterpreter.ATT_ID_ITERATION) || tag.equals(XMIInterpreter.LST_ITERATION_ID_LIST))
			return XMIInterpreter.OBJ_ITERATION;
		else if (tag.equals(XMIInterpreter.ATT_ID_TASK) || tag.equals(XMIInterpreter.LST_TASK_ID_LIST))
			return XMIInterpreter.OBJ_TASK;
		else if (tag.equals(XMIInterpreter.ATT_ID_MEMBER) || tag.equals(XMIInterpreter.LST_MEMBER_ID_LIST) || tag.equals(XMIInterpreter.LST_MEMBER_LIST) || tag.equals(XMIInterpreter.LST_MEMBERS_LIST))
			return XMIInterpreter.OBJ_MEMBER;
		else if (tag.equals(XMIInterpreter.LST_ARTIFACT_LIST) || tag.equals(XMIInterpreter.LST_ARTIFACTS_LIST))
			return XMIInterpreter.OBJ_ARTIFACT;
		return "elt" + Character.toUpperCase(tag.charAt(2)) + tag.substring(3);
	}

	/**
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	public void ignorableWhitespace(char[] ch, int start, int end)
			throws SAXException {
	}

	/**
	 * This event is not supposed to be thrown for XMI documents. 
	 * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String,
	 *      java.lang.String)
	 */
	public void processingInstruction(String target, String data)
			throws XMIException {
		System.out.println("Processing instruction : " + target);
		System.out.println("  with parameters : " + data);
		throw new XMIException("Malformed XMI expression : processing instruction encountered. Instruction : " + target + ", parameters : " + data);
	}

	/**
	 * This event is not supposed to be thrown for XMI documents as they are generated automatically. 
	 * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
	 */
	public void skippedEntity(String arg0) throws XMIException {
		throw new XMIException("Malformed XMI entity. " + arg0 + " was skipped. ");
	}
}