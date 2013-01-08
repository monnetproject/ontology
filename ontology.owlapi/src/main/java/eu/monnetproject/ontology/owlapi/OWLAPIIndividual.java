package eu.monnetproject.ontology.owlapi;

import eu.monnetproject.ontology.*;
import eu.monnetproject.ontology.Class;
import org.semanticweb.owlapi.model.*;

import java.util.*;
import java.net.URI;

public class OWLAPIIndividual extends OWLAPIEntity implements Individual {
	final OWLNamedIndividual indiv;
	
	public OWLAPIIndividual(OWLNamedIndividual wrapped,OWLOntology onto) {
		super(onto);
		indiv = wrapped;
	}
	protected OWLEntity entity() { return indiv; }
	
	public Collection<Class> getType() {
		return convertC(indiv.getTypes(onto));
	}
	public boolean addType(Class clazz) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLClassAssertionAxiom(convert(clazz),indiv)));
	}
	public boolean removeType(Class clazz) {
		return isChange(manager.removeAxiom(onto,
			factory.getOWLClassAssertionAxiom(convert(clazz),indiv)));
	}
	
	public Collection<Individual> getSameAs() {
		return convertI(indiv.getSameIndividuals(onto));
	}
	public boolean addSameAs(Individual individual) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLSameIndividualAxiom(convert(individual),indiv)));
	}		
	public boolean removeSameAs(Individual individual){
		return isChange(manager.removeAxiom(onto,
			factory.getOWLSameIndividualAxiom(convert(individual),indiv)));
	}
	
	public Collection<Individual> getDifferentFrom() {
		return convertI(indiv.getDifferentIndividuals(onto));
	}
	public boolean addDifferentFrom(Individual individual) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLDifferentIndividualsAxiom(indiv,convert(individual))));
	}		
		
	public boolean removeDifferentFrom(Individual individual) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLDifferentIndividualsAxiom(indiv,convert(individual))));
	}		
	
	public Map<Property,Collection<LiteralOrIndividual>> getProperties() {
		HashMap<Property,Collection<LiteralOrIndividual>> rval = new HashMap<Property,Collection<LiteralOrIndividual>>();
		rval.putAll(convertDP2M(indiv.getDataPropertyValues(onto)));
		rval.putAll(convertOP2M(indiv.getObjectPropertyValues(onto)));
		return Collections.unmodifiableMap(rval);
	}
	public Collection<LiteralOrIndividual> getPropertyValues(Property property) {
		if(property instanceof DatatypeProperty) {
			return convertLOI(indiv.getDataPropertyValues(convert((DatatypeProperty)property),onto));
		} else if(property instanceof ObjectProperty) {
			return convertLOI(indiv.getObjectPropertyValues(convert((ObjectProperty)property),onto));
		} else {
			return convertA2LOI(entity().getAnnotations(onto,convert((AnnotationProperty)property)));
		}
	}
	public boolean addProperty(Property property, LiteralOrIndividual value) {
		if(property instanceof DatatypeProperty && value instanceof LiteralValue) {
			return isChange(manager.addAxiom(onto,
				factory.getOWLDataPropertyAssertionAxiom(convert((DatatypeProperty)property),
					indiv,
					convert((LiteralValue)value))));
		} else if(property instanceof ObjectProperty && value instanceof Individual) {
			
			return isChange(manager.addAxiom(onto,
				factory.getOWLObjectPropertyAssertionAxiom(convert((ObjectProperty)property),
					indiv,
					convert((Individual)value))));
		} else if(property instanceof AnnotationProperty && value instanceof LiteralValue) {
			return addAnnotation((AnnotationProperty)property,(LiteralValue)value);
		} else {
			throw new IllegalArgumentException("Invalid Parameters to addProperty");
		}
	}
			
	public boolean removeProperty(Property property, LiteralOrIndividual value) {
		if(property instanceof DatatypeProperty && value instanceof LiteralValue) {
			return isChange(manager.removeAxiom(onto,
				factory.getOWLDataPropertyAssertionAxiom(convert((DatatypeProperty)property),
					indiv,
					convert((LiteralValue)value))));
		} else if(property instanceof ObjectProperty && value instanceof Individual) {
			
			return isChange(manager.removeAxiom(onto,
				factory.getOWLObjectPropertyAssertionAxiom(convert((ObjectProperty)property),
					indiv,
					convert((Individual)value))));
		} else if(property instanceof AnnotationProperty && value instanceof LiteralValue) {
			return removeAnnotation((AnnotationProperty)property,(LiteralValue)value);
		} else {
			throw new IllegalArgumentException("Invalid Parameters to removeProperty");
		}
	}
		
	public Map<Property,Collection<Individual>> getReferringProperty() {
		throw new UnsupportedOperationException("TODO");
	}
		
	/**
	 * Inverse of getPropertyValue
	 */
	public Collection<Individual> getReferringPropertyValues(Property property) {
		throw new UnsupportedOperationException("TODO");
	}
}
