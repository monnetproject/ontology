package eu.monnetproject.label;

import java.net.*;
import java.util.*;

/**
 * A factory for creating customized label factories
 *
 * @author John McCrae
 */
public interface LabelExtractorFactory {
	/**
	 * Create a new label extractor interface
	 * @param extraURIs Any non-default URIs to check
	 * @param fallback Fallback to using URI to extract
	 * @param inferLang Use language inference if language not stated
	 */
	LabelExtractor getExtractor(Collection<URI> extraURIs, boolean fallback, boolean inferLang);
	
	/**
	 * Create a new label extractor that aggregates other label extractors
	 * @param extractors The extractors to use in order
	 * @param inferrer The inferrer to guess the language if not present or null for no inference
	 */
	LabelExtractor getExtractor(List<LabelExtractor> extractors, LanguageInferrer inferrer);
}
