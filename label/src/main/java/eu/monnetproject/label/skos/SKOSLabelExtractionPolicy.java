package eu.monnetproject.label.skos;

import eu.monnetproject.lang.Language;
import eu.monnetproject.label.LabelExtractor;
import eu.monnetproject.ontology.*;
import java.net.URI;
import java.util.LinkedList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import aQute.bnd.annotation.component.*;

/**
 *
 * @author John McCrae
 */
@aQute.bnd.annotation.component.Component(provide = LabelExtractor.class)
public class SKOSLabelExtractionPolicy implements LabelExtractor {

    private static final String SKOS = "http://www.w3.org/2004/02/skos/core#";
    private static final String SKOSXL = "http://www.w3.org/2008/05/skos-xl#";

    public SKOSLabelExtractionPolicy() {
    }

    /*
     * public SKOSLabelExtractor(OntologyHandler handler) { this.handler =
     * handler; }
     *
     * public Collection<String> getLabels(Entity entity) { return
     * getLabels(entity, defaultLanguage); }
     *
     * public Collection<String> getLabels(Entity entity, Language language) {
     * Set<String> rval = new HashSet<String>(); rval.addAll(getLabel(entity,
     * language, "prefLabel")); rval.addAll(getLabel(entity, language,
     * "altLabel")); rval.addAll(getLabel(entity, language, "hiddenLabel"));
     * return rval;
    }
     */
    public Map<Language, Collection<String>> getLabels(Entity entity) {
        Map<Language, Collection<String>> rval = new HashMap<Language, Collection<String>>();
        for (AnnotationProperty prop : entity.getAnnotations().keySet()) {
            if (prop.getURI().equals(URI.create(SKOS + "prefLabel"))
                    || prop.getURI().equals(URI.create(SKOS + "altLabel"))
                    || prop.getURI().equals(URI.create(SKOS + "hiddenLabel"))) {
                for (AnnotationValue value2 : entity.getAnnotationValues(prop)) {
                    if (value2 instanceof LiteralValue) {
                        LiteralValue value = (LiteralValue) value2;
                        Language lang = value.getLanguage() == null ? LabelExtractor.NO_LANGUAGE : value.getLanguage();
                        if (!rval.containsKey(lang)) {
                            rval.put(lang, new HashSet<String>());
                        }
                        rval.get(lang).add(value.getValue());
                    }
                }
            } else if (prop.getURI().equals(URI.create(SKOSXL + "prefLabel"))
                    || prop.getURI().equals(URI.create(SKOSXL + "altLabel"))
                    || prop.getURI().equals(URI.create(SKOSXL + "hiddenLabel"))) {
                for (AnnotationValue val1 : entity.getAnnotationValues(prop)) {
                    if (val1 instanceof Individual) {
                        for (Map.Entry<Property, Collection<LiteralOrIndividual>> props2 : ((Individual) val1).getProperties().entrySet()) {
                            Property prop2 = props2.getKey();
                            if (prop2.getURI().equals(URI.create(SKOSXL + "literalForm"))) {
                                for (LiteralOrIndividual val2 : props2.getValue()) {
                                    if (val2 instanceof LiteralValue) {
                                        LiteralValue lit = (LiteralValue) val2;
                                        Language lang = lit.getLanguage() == null ? LabelExtractor.NO_LANGUAGE : lit.getLanguage();
                                        if (!rval.containsKey(lang)) {
                                            rval.put(lang, new HashSet<String>());
                                        }
                                        rval.get(lang).add(lit.getValue());
                                    }
                                }
                            }
                        }
                    }
                    for (Map.Entry<AnnotationProperty, Collection<AnnotationValue>> props2 : ((Individual) val1).getAnnotations().entrySet()) {
                        Property prop2 = props2.getKey();
                        if (prop2.getURI().equals(URI.create(SKOSXL + "literalForm"))) {
                            for (AnnotationValue val2 : props2.getValue()) {
                                if (val2 instanceof LiteralValue) {
                                    LiteralValue lit = (LiteralValue) val2;
                                    Language lang = lit.getLanguage() == null ? LabelExtractor.NO_LANGUAGE : lit.getLanguage();
                                    if (!rval.containsKey(lang)) {
                                        rval.put(lang, new HashSet<String>());
                                    }
                                    rval.get(lang).add(lit.getValue());
                                }
                            }
                        }
                    }
                }
            }
        }
        if (entity instanceof Individual) {
            Individual indiv = (Individual) entity;
            for (Map.Entry<Property, Collection<LiteralOrIndividual>> props : indiv.getProperties().entrySet()) {
                Property prop1 = props.getKey();
                if (prop1.getURI().equals(URI.create(SKOSXL + "prefLabel"))
                        || prop1.getURI().equals(URI.create(SKOSXL + "altLabel"))
                        || prop1.getURI().equals(URI.create(SKOSXL + "hiddenLabel"))) {
                    for (LiteralOrIndividual val1 : props.getValue()) {
                        if (val1 instanceof Individual) {
                            for (Map.Entry<Property, Collection<LiteralOrIndividual>> props2 : ((Individual) val1).getProperties().entrySet()) {
                                Property prop2 = props2.getKey();
                                if (prop2.getURI().equals(URI.create(SKOSXL + "literalForm"))) {
                                    for (LiteralOrIndividual val2 : props2.getValue()) {
                                        if (val2 instanceof LiteralValue) {
                                            LiteralValue lit = (LiteralValue) val2;
                                            Language lang = lit.getLanguage() == null ? LabelExtractor.NO_LANGUAGE : lit.getLanguage();
                                            if (!rval.containsKey(lang)) {
                                                rval.put(lang, new HashSet<String>());
                                            }
                                            rval.get(lang).add(lit.getValue());
                                        }
                                    }
                                }
                            }
                        }
                        for (Map.Entry<AnnotationProperty, Collection<AnnotationValue>> props2 : ((Individual) val1).getAnnotations().entrySet()) {
                            Property prop2 = props2.getKey();
                            if (prop2.getURI().equals(URI.create(SKOSXL + "literalForm"))) {
                                for (AnnotationValue val2 : props2.getValue()) {
                                    if (val2 instanceof LiteralValue) {
                                        LiteralValue lit = (LiteralValue) val2;
                                        Language lang = lit.getLanguage() == null ? LabelExtractor.NO_LANGUAGE : lit.getLanguage();
                                        if (!rval.containsKey(lang)) {
                                            rval.put(lang, new HashSet<String>());
                                        }
                                        rval.get(lang).add(lit.getValue());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return rval;
    }
    /*
     * HashMap<Language,Collection<String>> rval = new HashMap<Language,
     * Collection<String>>(); getLabel(entity, "prefLabel", rval);
     * getLabel(entity, "altLabel",rval); getLabel(entity, "hiddenLabel",rval);
     * return rval; }
     *
     * public Map<Language, Collection<String>> getLabels(OntologyHandler
     * handler, Entity entity) { HashMap<Language,Collection<String>> rval = new
     * HashMap<Language, Collection<String>>(); getLabel(entity, "prefLabel",
     * rval); getLabel(entity, "altLabel",rval); getLabel(entity,
     * "hiddenLabel",rval); return rval; }
     *
     * private void getLabel(Entity entity, String skos, HashMap<Language,
     * Collection<String>> rval) {
     * addAll(rval,handler.getLiteralAnnotation(entity, URI.create(SKOS + "#" +
     * skos), null));
     *
     * if (entity instanceof Individual) {
     * addAll(rval,handler.getDataPropertyValue((Individual) entity,
     * handler.getDataProperty(URI.create(SKOSXL + "#" + skos))));
     * Collection<Individual> indivs =
     * handler.getObjectPropertyObject((Individual) entity,
     * handler.getObjectProperty(URI.create(SKOSXL + "#" + skos))); for
     * (Individual indiv : indivs) {
     * addAll(rval,handler.getDataPropertyValue(indiv,
     * handler.getDataProperty(URI.create(SKOSXL + "#literalForm")))); } } }
     *
     * private void addAll(Map<Language,Collection<String>> rval,
     * Collection<Literal> literals) { for(Literal lit : literals) {
     * if(lit.getLanguage() == null) {
     * if(!rval.containsKey(LabelExtractor.NO_LANGUAGE))
     * rval.put(LabelExtractor.NO_LANGUAGE, new ArrayList<String>());
     * rval.get(LabelExtractor.NO_LANGUAGE).add(lit.getValue()); } else {
     * if(!rval.containsKey(lit.getLanguage())) rval.put(lit.getLanguage(), new
     * ArrayList<String>()); rval.get(lit.getLanguage()).add(lit.getValue()); }
     * } }
     *
     * public void setDefaultLanguage(Language language) { this.defaultLanguage
     * = language;
    }
     */
}
