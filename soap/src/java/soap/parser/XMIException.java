/*
 * Created on 5 f?vr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package soap.parser;

import org.xml.sax.SAXException;

/**
 * @author altrazar
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XMIException extends SAXException {

	/**
	 * @param e
	 */
	public XMIException(Exception e) {
		super(e);
	}

	/**
	 * @param message
	 */
	public XMIException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param e
	 */
	public XMIException(String message, Exception e) {
		super(message, e);
	}
}
