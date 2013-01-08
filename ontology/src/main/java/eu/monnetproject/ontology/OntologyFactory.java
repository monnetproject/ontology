package eu.monnetproject.ontology;

import java.net.URI;

/**
 * A factory for generating entities in the ontology
 */
public interface OntologyFactory {
	
	/**
	 * Make an annotation property, or return instance if already in ontology
	 */
	AnnotationProperty makeAnnotationProperty(URI uri);
	
	/**
	 * Make an anonymous class
	 */
	Class makeClass();
	/**
	 * Make a named class, or return instance if already in ontology
	 */
	Class makeClass(URI uri);
	
	/**
	 * Make a datatype, or return instance if already in ontology
	 */
	Datatype makeDatatype(URI uri);
	
	/**
	 * Make a datatype property, or return instance if already in ontology
	 */
	DatatypeProperty makeDatatypeProperty(URI uri);
	
	/**
	 * Make an anonymous individual
	 */
	Individual makeIndividual();
	/**
	 * Make an individual, or return instance if already in ontology
	 */
	Individual makeIndividual(URI uri);
	
	/**
	 * Make an untyped literal
	 */
	LiteralValue makeLiteral(String value);
	/**
	 * Make a literal with a language tag
	 */
	LiteralValue makeLiteralWithLanguage(String value, String language);
	/**
	 * Make a typed literal
	 */
	LiteralValue makeLiteralWithDatatype(String value, Datatype datatype);
	
	/**
	 * Make an object property, or return instance if already in ontology
	 */
	ObjectProperty makeObjectProperty(URI uri);	
        
        /**
         * Create an anonymous class indicating an object hasValue restriction
         * @param prop The property
         * @param value The value
         * @return  The class
         */
        Restriction makeObjectHasValueRestriction(ObjectProperty prop, Individual value);
        
        /**
         * Create an anonymous class indicating a datatype hasValue restriction
         * @param prop The property
         * @param value The value
         * @return  The class
         */
        Restriction makeDatatypeHasValueRestriction(DatatypeProperty prop, LiteralValue value);
}
