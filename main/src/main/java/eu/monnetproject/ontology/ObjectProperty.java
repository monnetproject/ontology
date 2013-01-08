package eu.monnetproject.ontology;

import java.util.*;

/**
 * A property whose range is a class
 */
public interface ObjectProperty extends Property {
	Collection<Class> getRange();
	
	Collection<ObjectProperty> getInverseOf();
	boolean addInverseOf(ObjectProperty property);
	boolean removeInverseOf(ObjectProperty property);
}
