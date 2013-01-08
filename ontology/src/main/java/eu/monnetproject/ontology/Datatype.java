package eu.monnetproject.ontology;

import java.util.*;

/**
 * A type of data
 */
public interface Datatype extends ClassOrDatatype {
	java.net.URI getURI();

	/** 
	 * Inverse of DatatypeProperty.getRange
	 */
	Collection<DatatypeProperty> getIsRangeOf();	
}
