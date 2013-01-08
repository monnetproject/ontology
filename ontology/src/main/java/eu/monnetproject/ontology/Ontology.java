package eu.monnetproject.ontology;

import java.net.URI;
import java.util.*;

/**
 * An ontology
 */
public interface Ontology {
	/**
	 * Get all entities in the ontology
	 */
	Collection<Entity> getEntities();
	
	/**
	 * Get all classes in the ontology
	 */
	Collection<Class> getClasses();
        
        /**
         * Add a class to the ontology
         * @param c The class
         * @return true if the ontology changed
         */
        boolean addClass(Class c);
                
        /**
         * Remove a class from the ontology
         * @param c The class
         * @return true if the ontology changed
         */
        boolean removeClass(Class c);
        

        /**
         * Remove all axioms from the ontology
         * 
         * @return true if axioms were successfully removed
         */
        public boolean removeAllAxioms();
        
	/**
	 * Get all individuals in the ontology
	 */
	Collection<Individual> getIndividuals();
        
        /**
         * Add an individual to the ontology
         * @param i The individual
         * @return true if the ontology changed
         */
        boolean addIndividual(Individual i);
        
        /**
         * Remove an individual from the ontology
         * @param i The individual
         * @return true if the ontology changed
         */
        boolean removeIndividual(Individual i);
	/**
	 * Get all properties (not annotation) in the ontology
	 */
	Collection<Property> getProperties();
	/**
	 * Get all object properties in the ontology
	 */
	Collection<ObjectProperty> getObjectProperties();
        /**
         * Add an object property to the ontology
         * @param op The property
         * @return true if the ontology changed
         */
        boolean addObjectProperty(ObjectProperty op);
        /**
         * Remove an object property from an ontology
         * @param op The property
         * @return true if the ontology changed
         */
        boolean removeObjectProperty(ObjectProperty op);
	/**
	 * Get all datatype properties in the ontology
	 */
	Collection<DatatypeProperty> getDatatypeProperties();	
        /**
         * Add a datatype property to the ontology
         * @param dp The property
         * @return true if the ontology changed
         */
        boolean addDatatypeProperty(DatatypeProperty dp);
        /**
         * Remove a datatype property from the ontology
         * @param dp The property
         * @return true if the ontology changed
         */
        boolean removeDatatypeProperty(DatatypeProperty dp);
	/**
	 * Get all annotation properties in the ontology
	 */
	Collection<AnnotationProperty> getAnnotationProperties();
        /**
         * Add an annotation property to the ontology
         * @param ap The property
         * @return true if the ontology changed
         */
        boolean addAnnotationProperty(AnnotationProperty ap);
        /**
         * Remove an annotation property from the ontology
         * @param ap The property
         * @return true if the ontology changed
         */
        boolean removeAnnotationProperty(AnnotationProperty ap);
	/**
	 * Get an entity with a given URI
	 * @param uri The uri
	 * @return The set of entities with the given URI
	 */
	Collection<Entity> getEntities(URI uri);
	
	//Collection<Datatype> getDatatypes();
	
	/**
	 * Get a factory for this ontology
	 */
	OntologyFactory getFactory();

	/**
	 * Get the base URI of this ontology. This method returns the same as getURI() cut at the last index of # or /
	 */
	URI getBaseURI();
        
	/**
	 * Get the URI of this ontology
	 */
	URI getURI();
        
	/**
	 * Get the data source of this ontology.
	 */
	//Reader getDataSource();
            
	/**
	 * Set the data source of this ontology.
	 */
	//void setDataSource(Reader dataSource);

}
