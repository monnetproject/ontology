package eu.monnetproject.ontology.owlapi;

import eu.monnetproject.ontology.*;
import eu.monnetproject.ontology.Class;

import org.semanticweb.owlapi.model.*;

import java.util.*;
import java.net.URI;

/**
 * 
 * @author Dennis Spohr
 *
 */
public class OWLAPIAnnotationProperty extends OWLAPIEntity implements AnnotationProperty {
	final OWLAnnotationProperty annoProp;
	
	public OWLAPIAnnotationProperty(OWLAnnotationProperty wrapped,OWLOntology onto) {
		super(onto);
		annoProp = wrapped;
	}
	
	protected OWLEntity entity() { return annoProp; }
	
	public Collection<Class> getDomain() {
		throw new UnsupportedOperationException();
	}
	public boolean addDomain(Class clazz) {
		throw new UnsupportedOperationException();
	}
	public boolean removeDomain(Class clazz) {
		throw new UnsupportedOperationException();
	}

	public Collection<? extends ClassOrDatatype> getRange(){
		throw new UnsupportedOperationException();
	}
	public boolean addRange(ClassOrDatatype clazzOrDatatype) {
		throw new UnsupportedOperationException();
	}
	public boolean removeRange(ClassOrDatatype clazzOrDatatype){
		throw new UnsupportedOperationException();
	}

	public Collection<Property> getEquivalentProperty(){
		throw new UnsupportedOperationException();
	}
	public boolean addEquivalentProperty(Property property){
		throw new UnsupportedOperationException();
	}
	public boolean removeEquivalentProperty(Property property){
		throw new UnsupportedOperationException();
	}
	
	public Collection<Property> getSubPropertyOf(){
		return convertAP2P(annoProp.getSubProperties(onto));
	}
	public boolean addSubPropertyOf(Property property){
		return isChange(manager.addAxiom(onto,
				factory.getOWLSubAnnotationPropertyOfAxiom(annoProp,convert((AnnotationProperty)property))));
	}
	public boolean removeSubPropertyOf(Property property){
		return isChange(manager.removeAxiom(onto,
				factory.getOWLSubAnnotationPropertyOfAxiom(annoProp,convert((AnnotationProperty)property))));
	}
	
	public Collection<Property> getSuperPropertyOf(){
	 	 return convertAP2P(annoProp.getSuperProperties(onto));
	}
}
