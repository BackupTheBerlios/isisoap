/*
 * Created on Feb 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package soap.parser;

import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Czerny
 */
public class XMIParser {

	/**
	 * @throws SAXException
	 * @throws IOException
	 *  
	 */
	public XMIParser(String uri) throws IOException, SAXException {
		XMLReader saxReader = XMLReaderFactory
				.createXMLReader("org.apache.xerces.parsers.SAXParser");
		saxReader.setContentHandler(new XMIContentHandler());
		saxReader.parse(uri);
	}

	public static void main(String[] args) {
		if (0 == args.length || 2 < args.length) {
			System.out.println("Usage : SimpleSaxParser uri [parserClassName]");
			System.exit(1);
		}

		String uri = args[0];

		String parserName = null;
		if (2 == args.length) {
			parserName = args[1];
		}

		try {
			XMIParser parser = new XMIParser(uri);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}