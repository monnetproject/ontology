package eu.monnetproject.label;

import eu.monnetproject.lang.Language;
import eu.monnetproject.ontology.*;
import java.util.Collection;
import java.util.Map;

/**
 * Specifies how to get the label for a particular ontology element
 * @author John McCrae
 */
public interface LabelExtractor {


    /**
     * Get the labels as a map from languages to list of labels
     * @param entity The ontology entity
     * @return The map, or an empty map if no labels exist
     */
    public Map<Language,Collection<String>> getLabels(Entity entity);
     
    /**
     * Used to indicate that no language was specified by the source
     */
    public final static Language NO_LANGUAGE = Language.getInstance("No Language", "No Language", null, null, "___");
}
