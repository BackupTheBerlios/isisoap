/*
 * Created on 11 nov. 2004
 */
package soap.model;

import soap.model.extension.SoapListProjects;

/**
 * @author yanagiba
 */
public interface SoapElementVisitor extends ModelVisitor
{
    public void visitSoapListProjects (SoapListProjects p);
}
