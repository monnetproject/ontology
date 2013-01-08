package eu.monnetproject.ontology;

import java.util.*;

/**
 * A class
 */
public interface Class extends Entity, ClassOrDatatype {
	Collection<Class> getSubClassOf();
	boolean addSubClassOf(Class clazz);
	boolean removeSubClassOf(Class clazz);
	
	/** 
	 * Inverse of getSubClassOf 
	 */
	Collection<Class> getSuperClassOf(); 
	
	Collection<Class> getEquivalentClass();
	boolean addEquivalentClass(Class clazz);
	boolean removeEquivalentClass(Class clazz);
	
	Collection<Class> getDisjointWith();
	boolean addDisjointWith(Class clazz);
	boolean removeDisjointWith(Class clazz);
	
	/** 
	 * Inverse of Individual.getType
	 */
	Collection<Individual> getIsTypeOf();
	
	/**
	 * Inverse of Property.getDomain
	 */
	Collection<Property> getIsDomainOf();
	
	/** 
	 * Inverse ofObjectProperty.getRange 
	 */
	Collection<ObjectProperty> getIsRangeOf();
}
