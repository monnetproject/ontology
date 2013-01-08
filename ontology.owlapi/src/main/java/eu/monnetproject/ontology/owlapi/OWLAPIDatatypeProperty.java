package eu.monnetproject.ontology.owlapi;

import eu.monnetproject.ontology.*;
import eu.monnetproject.ontology.Class;
import org.semanticweb.owlapi.model.*;

import java.util.*;
import java.net.URI;

public class OWLAPIDatatypeProperty extends OWLAPIEntity implements DatatypeProperty {
	final OWLDataProperty dataProp;
	
	public OWLAPIDatatypeProperty(OWLDataProperty wrapped,OWLOntology onto) {
		super(onto);
		dataProp = wrapped;
	}
	
	public Collection<Datatype> getRange() {
		return convertD(dataProp.getRanges(onto));
	}
	
	protected OWLEntity entity() { return dataProp; }
	
	protected OWLDataProperty prop() { return dataProp; }
	
	
	public Collection<Class> getDomain() {
		return convertC(dataProp.getDomains(onto));
	}
	public boolean addDomain(Class clazz) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLDataPropertyDomainAxiom(dataProp,convert(clazz))));
	}
	public boolean removeDomain(Class clazz) {
		return isChange(manager.removeAxiom(onto,
			factory.getOWLDataPropertyDomainAxiom(dataProp,convert(clazz))));
	}

	public boolean addRange(ClassOrDatatype clazzOrDatatype) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLDataPropertyRangeAxiom(dataProp,convert((Datatype)clazzOrDatatype))));
	}
	public boolean removeRange(ClassOrDatatype clazzOrDatatype) {
		return isChange(manager.removeAxiom(onto,
			factory.getOWLDataPropertyRangeAxiom(dataProp,convert((Datatype)clazzOrDatatype))));
	}

	public Collection<Property> getEquivalentProperty() {
		return convertDP2P(dataProp.getEquivalentProperties(onto));
	}
	public boolean addEquivalentProperty(Property property) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLEquivalentDataPropertiesAxiom(dataProp,convert((DatatypeProperty)property))));
	}
	public boolean removeEquivalentProperty(Property property) {
		return isChange(manager.removeAxiom(onto,
			factory.getOWLEquivalentDataPropertiesAxiom(dataProp,convert((DatatypeProperty)property))));
	}
	
	public Collection<Property> getSubPropertyOf() {
		return convertDP2P(dataProp.getSubProperties(onto));
	}
	public boolean addSubPropertyOf(Property property){
		return isChange(manager.removeAxiom(onto,
			factory.getOWLSubDataPropertyOfAxiom(dataProp,convert((DatatypeProperty)property))));
	}
	public boolean removeSubPropertyOf(Property property){
		return isChange(manager.removeAxiom(onto,
			factory.getOWLSubDataPropertyOfAxiom(dataProp,convert((DatatypeProperty)property))));
	}
	
	/** 
	 * Inverse of getSubPropertyOf 
	 */
	 public Collection<Property> getSuperPropertyOf() {
	 	 return convertDP2P(dataProp.getSuperProperties(onto));
	 }
}
	
