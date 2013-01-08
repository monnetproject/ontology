package eu.monnetproject.label;

import eu.monnetproject.lang.*;

/**
 * Infer a language for a label
 * @author John McCrae
 */
public interface LanguageInferrer {
	/**
	 * Get the inferred language for the label
	 * @param label The label
	 * @return The language or null for no inferred language
	 */
	Language getLang(String label);
}
