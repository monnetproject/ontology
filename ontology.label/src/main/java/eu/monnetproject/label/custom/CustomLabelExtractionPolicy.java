package eu.monnetproject.label.custom;

import java.net.URI;
import java.util.*;
import eu.monnetproject.util.Logger;

import eu.monnetproject.lang.Language;

import eu.monnetproject.label.LabelExtractor;

import eu.monnetproject.ontology.*;
import eu.monnetproject.util.Logging;

import aQute.bnd.annotation.component.*;

//@Component(factory="eu.monnetproject.lep.custom")
public class CustomLabelExtractionPolicy implements LabelExtractor {

    private Logger log = Logging.getLogger(this);
    public URI label;

    public CustomLabelExtractionPolicy() {
    }

    public CustomLabelExtractionPolicy(URI label) {
        this.label = label;
    }

    //@Activate
    public void init(Map properties) {
        if (!properties.containsKey("label")) {
            throw new IllegalArgumentException("Must create custom label extractor with a URI");
        } else {
            Object labelAsObject = properties.get("label");
            if (labelAsObject instanceof URI) {
                label = (URI) labelAsObject;
            } else {
                label = URI.create(labelAsObject.toString());
            }
        }
    }

    @Override
    public Map<Language, Collection<String>> getLabels(Entity entity) {

        AnnotationProperty property = null;
        for (AnnotationProperty prop : entity.getAnnotations().keySet()) {
            if (prop.getURI().equals(label)) {
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
