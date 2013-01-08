package eu.monnetproject.ontology;

import java.util.*;

/**
 * An individual
 */
public interface Individual extends Entity, LiteralOrIndividual, AnnotationValue {
	Collection<Class> getType();
	boolean addType(Class clazz);
	boolean removeType(Class clazz);
	
	Collection<Individual> getSameAs();
	boolean addSameAs(Individual individual);
	boolean removeSameAs(Individual individual);
	
	Collection<Individual> getDifferentFrom();
	boolean addDifferentFrom(Individual individual);
	boolean removeDifferentFrom(Individual individual);
	
	/**
	 * Return all properties on this individual. This excludes properties in the namespace of the ontology
	 * languages e.g., OWL + RDFS
	 */
	Map<Property,Collection<LiteralOrIndividual>> getProperties();
	Collection<LiteralOrIndividual> getPropertyValues(Property property);
	boolean addProperty(Property property, LiteralOrIndividual value);
	boolean removeProperty(Property property, LiteralOrIndividual value);
	
	/**
	 * Inverse of getProperty
	 */
	Map<Property,Collection<Individual>> getReferringProperty();
	/**
	 * Inverse of getPropertyValue
	 */
	Collection<Individual> getReferringPropertyValues(Property property);
}
