package eu.monnetproject.ontology;

import java.util.*;

/**
 * A property in the ontology
 */
public interface Property extends Entity {
	Collection<Class> getDomain();
	boolean addDomain(Class clazz);
	boolean removeDomain(Class clazz);

	Collection<? extends ClassOrDatatype> getRange();
	boolean addRange(ClassOrDatatype clazzOrDatatype);
	boolean removeRange(ClassOrDatatype clazzOrDatatype);

	Collection<Property> getEquivalentProperty();
	boolean addEquivalentProperty(Property property);
	boolean removeEquivalentProperty(Property property);
	
	Collection<Property> getSubPropertyOf();
	boolean addSubPropertyOf(Property property);
	boolean removeSubPropertyOf(Property property);
	
	/** 
	 * Inverse of getSubPropertyOf 
	 */
	Collection<Property> getSuperPropertyOf();
	/*
	Collection<Property> getPropertyDisjointWith();
	boolean addPropertyDisjointWith(Property property);
	boolean removePropertyDisjointWith(Property property);*/
}
