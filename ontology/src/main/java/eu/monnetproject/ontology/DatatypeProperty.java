package eu.monnetproject.ontology;

import java.util.*;

/**
 * A property whose range is a datatype
 */
public interface DatatypeProperty extends Property { 
	Collection<Datatype> getRange();
}
