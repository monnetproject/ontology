package eu.monnetproject.ontology;

import java.util.*;

/**
 * An entity
 */
public interface Entity {
	java.net.URI getURI();
	String getID();
	Map<AnnotationProperty,Collection<AnnotationValue>> getAnnotations();
	Collection<AnnotationValue> getAnnotationValues(AnnotationProperty property);
	boolean addAnnotation(AnnotationProperty property, AnnotationValue annotation);
	boolean removeAnnotation(AnnotationProperty property, AnnotationValue annotation);
	Ontology getOntology();
	public Collection<Entity> getPuns();
}
