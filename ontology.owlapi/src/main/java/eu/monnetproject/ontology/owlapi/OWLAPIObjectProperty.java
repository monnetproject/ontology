package eu.monnetproject.ontology.owlapi;

import eu.monnetproject.ontology.*;
import eu.monnetproject.ontology.Class;
import org.semanticweb.owlapi.model.*;

import java.util.*;
import java.net.URI;

public class OWLAPIObjectProperty extends OWLAPIEntity implements ObjectProperty {
	final OWLObjectProperty objProp;
	
	public OWLAPIObjectProperty(OWLObjectProperty wrapped, OWLOntology onto) {
		super(onto);
		objProp = wrapped;
	}
	
	protected OWLEntity entity() { return objProp; }
	protected OWLObjectProperty prop() { return objProp; }
		
	public Collection<ObjectProperty> getInverseOf() {
		return convertOP(objProp.getInverses(onto));
	}
	public boolean addInverseOf(ObjectProperty property) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLInverseObjectPropertiesAxiom(objProp,convert(property))));
	}
	public boolean removeInverseOf(ObjectProperty property) {
		return isChange(manager.removeAxiom(onto,
			factory.getOWLInverseObjectPropertiesAxiom(objProp,convert(property))));
	}
	
	
	public Collection<Class> getDomain() {
		return convertC(objProp.getDomains(onto));
	}
	public boolean addDomain(Class clazz) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLObjectPropertyDomainAxiom(objProp,convert(clazz))));
	}
	public boolean removeDomain(Class clazz) {
		return isChange(manager.removeAxiom(onto,
			factory.getOWLObjectPropertyDomainAxiom(objProp,convert(clazz))));
	}

	public Collection<Class> getRange() {
		return convertC(objProp.getRanges(onto));
	}
	public boolean addRange(ClassOrDatatype clazzOrDatatype) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLObjectPropertyRangeAxiom(objProp,convert((Class)clazzOrDatatype))));
	}
	public boolean removeRange(ClassOrDatatype clazzOrDatatype) {
		return isChange(manager.removeAxiom(onto,
			factory.getOWLObjectPropertyRangeAxiom(objProp,convert((Class)clazzOrDatatype))));
	}

	public Collection<Property> getEquivalentProperty() {
		return convertOP2P(objProp.getEquivalentProperties(onto));
	}
	public boolean addEquivalentProperty(Property property) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLEquivalentObjectPropertiesAxiom(objProp,convert((ObjectProperty)property))));
	}
	public boolean removeEquivalentProperty(Property property) {
		return isChange(manager.removeAxiom(onto,
			factory.getOWLEquivalentObjectPropertiesAxiom(objProp,convert((ObjectProperty)property))));
	}
	
	public Collection<Property> getSubPropertyOf() {
		return convertOP2P(objProp.getSubProperties(onto));
	}
	public boolean addSubPropertyOf(Property property){
		return isChange(manager.addAxiom(onto,
			factory.getOWLSubObjectPropertyOfAxiom(objProp,convert((ObjectProperty)property))));
	}
	public boolean removeSubPropertyOf(Property property){
		return isChange(manager.removeAxiom(onto,
			factory.getOWLSubObjectPropertyOfAxiom(objProp,convert((ObjectProperty)property))));
	}
	
	/** 
	 * Inverse of getSubPropertyOf 
	 */
	 public Collection<Property> getSuperPropertyOf() {
	 	 return convertOP2P(objProp.getSuperProperties(onto));
	 }
}
