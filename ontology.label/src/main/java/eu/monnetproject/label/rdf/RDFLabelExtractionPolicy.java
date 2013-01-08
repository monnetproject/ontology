package eu.monnetproject.label.rdf;

import java.net.URI;
import java.util.*;
import eu.monnetproject.lang.Language;

import eu.monnetproject.label.LabelExtractor;

import eu.monnetproject.ontology.*;

import aQute.bnd.annotation.component.*;

@Component(provide=LabelExtractor.class)
public class RDFLabelExtractionPolicy implements LabelExtractor {

    public static final URI RDFS_LABEL = URI.create("http://www.w3.org/2000/01/rdf-schema#label");

    public RDFLabelExtractionPolicy() {
    }

    public Map<Language, Collection<String>> getLabels(Entity entity) {
        AnnotationProperty property = null;
        for (AnnotationProperty prop : entity.getAnnotations().keySet()) {
            if (prop.getURI().equals(RDFS_LABEL)) {
                property = prop;
            }
        }
        if (property == null) {
            return Collections.EMPTY_MAP;
        }
        Collection<AnnotationValue> lits = entity.getAnnotationValues(property);
        HashMap<Language, Collection<String>> rv = new HashMap<Language, Collection<String>>();

        for (AnnotationValue av : lits) {
            if (av instanceof LiteralValue) {
                LiteralValue lit = (LiteralValue) av;
                Language language = lit.getLanguage();
                if (language == null) {
                    language = LabelExtractor.NO_LANGUAGE;
                }
                if (!rv.containsKey(language)) {
                    rv.put(language, new HashSet<String>());
                }
                rv.get(language).add(lit.getValue());
            }
        }
        return rv;
    }
}
