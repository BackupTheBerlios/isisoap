/*
 * Created on 11 nov. 2004
 */
package soap.model;

import soap.model.extension.SoapListProjects;


/**
 * @author yanagiba
 */
public class DefaultSoapElementVisitor extends DefaultModelVisitor implements SoapElementVisitor
{

    public void visitSoapListProjects(SoapListProjects p)
    {
        visitModelElement(p);
    }

}
