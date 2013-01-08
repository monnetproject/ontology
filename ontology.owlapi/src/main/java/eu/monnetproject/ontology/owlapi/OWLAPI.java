package eu.monnetproject.ontology.owlapi;

import org.semanticweb.owlapi.model.*;
import eu.monnetproject.ontology.*;
import eu.monnetproject.ontology.Class;
import java.util.*;

abstract class OWLAPI {

    protected final OWLDataFactory factory;
    protected OWLOntology onto;

    public OWLAPI(OWLOntology onto) {
        this.onto = onto;
        this.factory = onto.getOWLOntologyManager().getOWLDataFactory();
    }

    protected Collection<Entity> convertE(Set<OWLEntity> ents) {
        HashSet<Entity> entities = new HashSet<Entity>();
        for (OWLEntity ent : ents) {
            if (ent instanceof OWLNamedIndividual) {
                entities.add(new OWLAPIIndividual((OWLNamedIndividual) ent, onto));
            } else if (ent instanceof OWLObjectProperty) {
                entities.add(new OWLAPIObjectProperty((OWLObjectProperty) ent, onto));
            } else if (ent instanceof OWLDataProperty) {
                entities.add(new OWLAPIDatatypeProperty((OWLDataProperty) ent, onto));
            } else if (ent instanceof OWLAnnotationProperty) {
                entities.add(new OWLAPIAnnotationProperty((OWLAnnotationProperty) ent, onto));
            } else if (ent instanceof OWLClass) {
                entities.add(new OWLAPIClass((OWLClass) ent, onto));
            }
        }
        return Collections.unmodifiableSet(entities);
    }

    public Collection<AnnotationProperty> convertAP(Set<OWLAnnotationProperty> vals) {
        HashSet<AnnotationProperty> rv = new HashSet<AnnotationProperty>();
        for (OWLAnnotationProperty val : vals) {
            rv.add(new OWLAPIAnnotationProperty(val, onto));
        }
        return Collections.unmodifiableSet(rv);
    }

    public Collection<Property> convertAP2P(Set<OWLAnnotationProperty> vals) {
        HashSet<Property> rv = new HashSet<Property>();
        for (OWLAnnotationProperty val : vals) {
            if (val instanceof OWLAnnotationProperty) {
                rv.add(new OWLAPIAnnotationProperty((OWLAnnotationProperty) val, onto));
            }
        }
        return Collections.unmodifiableSet(rv);
    }

    public Map<Property, Collection<LiteralOrIndividual>> convertAP2M(Map<OWLAnnotationProperty, Set<OWLIndividual>> vals) {
        HashMap<Property, Collection<LiteralOrIndividual>> rv =
                new HashMap<Property, Collection<LiteralOrIndividual>>();
        for (Map.Entry<OWLAnnotationProperty, Set<OWLIndividual>> val : vals.entrySet()) {
            if (val.getKey() instanceof OWLAnnotationProperty) {
                rv.put(new OWLAPIAnnotationProperty((OWLAnnotationProperty) val.getKey(), onto),
                        convertLOI(val.getValue()));
            }
        }
        return Collections.unmodifiableMap(rv);
    }

    public Collection<Class> convertC(Set<OWLClassExpression> vals) {
        HashSet<Class> rv = new HashSet<Class>();
        for (OWLClassExpression val : vals) {
            if (val instanceof OWLClass) {
                rv.add(new OWLAPIClass((OWLClass) val, onto));
            }
        }
        return Collections.unmodifiableSet(rv);
    }

    public Collection<Datatype> convertD(Set<OWLDataRange> vals) {
        HashSet<Datatype> rv = new HashSet<Datatype>();
        for (OWLDataRange val : vals) {
            if (val instanceof OWLDatatype) {
                rv.add(new OWLAPIDatatype((OWLDatatype) val, onto));
            }
        }
        return Collections.unmodifiableSet(rv);
    }

    public Collection<Property> convertDP2P(Set<OWLDataPropertyExpression> vals) {
        HashSet<Property> rv = new HashSet<Property>();
        for (OWLDataPropertyExpression val : vals) {
            rv.add(new OWLAPIDatatypeProperty((OWLDataProperty) val, onto));
        }
        return Collections.unmodifiableSet(rv);
    }

    public Map<Property, Collection<LiteralOrIndividual>> convertDP2M(Map<OWLDataPropertyExpression, Set<OWLLiteral>> vals) {
        HashMap<Property, Collection<LiteralOrIndividual>> rv =
                new HashMap<Property, Collection<LiteralOrIndividual>>();
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> val : vals.entrySet()) {
            rv.put(new OWLAPIDatatypeProperty((OWLDataProperty) val.getKey(), onto),
                    convertLOI(val.getValue()));
        }
        return Collections.unmodifiableMap(rv);
    }

    public Collection<DatatypeProperty> convertDP(Set<OWLDataPropertyExpression> vals) {
        HashSet<DatatypeProperty> rv = new HashSet<DatatypeProperty>();
        for (OWLDataPropertyExpression val : vals) {
            rv.add(new OWLAPIDatatypeProperty((OWLDataProperty) val, onto));
        }
        return Collections.unmodifiableSet(rv);
    }

    public Collection<Individual> convertI(Set<OWLIndividual> vals) {
        HashSet<Individual> rv = new HashSet<Individual>();
        for (OWLIndividual val : vals) {
            rv.add(new OWLAPIIndividual((OWLNamedIndividual) val, onto));
        }
        return Collections.unmodifiableSet(rv);
    }

    public Collection<LiteralValue> convertL(Set<OWLLiteral> vals) {
        HashSet<LiteralValue> rv = new HashSet<LiteralValue>();
        for (OWLLiteral val : vals) {
            rv.add(new OWLAPILiteralValue(val, onto));
        }
        return Collections.unmodifiableSet(rv);
    }

    public Collection<AnnotationValue> convertA(Set<OWLAnnotation> vals) {
        HashSet<AnnotationValue> rv = new HashSet<AnnotationValue>();
        for (OWLAnnotation val : vals) {
            if (val.getValue() instanceof OWLLiteral) {
                rv.add(new OWLAPILiteralValue((OWLLiteral) val.getValue(), onto));
            } else if (val.getValue() instanceof OWLIndividual) {
                rv.add(new OWLAPIIndividual(factory.getOWLNamedIndividual(
                        IRI.create(val.getValue().toString())), onto));
            } else if (val.getValue() instanceof IRI) {
                rv.add(new OWLAPIIndividual(factory.getOWLNamedIndividual(
                        (IRI) val.getValue()), onto));
            }
        }
        return Collections.unmodifiableSet(rv);
    }

    public Collection<LiteralOrIndividual> convertA2LOI(Set<OWLAnnotation> vals) {
        HashSet<LiteralOrIndividual> rv = new HashSet<LiteralOrIndividual>();
        for (OWLAnnotation val : vals) {
            rv.add(new OWLAPILiteralValue((OWLLiteral) val.getValue(), onto));
        }
        return Collections.unmodifiableSet(rv);
    }

    public Collection<ObjectProperty> convertOP(Set<OWLObjectPropertyExpression> vals) {
        HashSet<ObjectProperty> rv = new HashSet<ObjectProperty>();
        for (OWLObjectPropertyExpression val : vals) {
            if (val instanceof OWLObjectProperty) {
                rv.add(new OWLAPIObjectProperty((OWLObjectProperty) val, onto));
            }
        }
        return Collections.unmodifiableSet(rv);
    }

    public Collection<Property> convertOP2P(Set<OWLObjectPropertyExpression> vals) {
        HashSet<Property> rv = new HashSet<Property>();
        for (OWLObjectPropertyExpression val : vals) {
            if (val instanceof OWLObjectProperty) {
                rv.add(new OWLAPIObjectProperty((OWLObjectProperty) val, onto));
            }
        }
        return Collections.unmodifiableSet(rv);
    }

    public Map<Property, Collection<LiteralOrIndividual>> convertOP2M(Map<OWLObjectPropertyExpression, Set<OWLIndividual>> vals) {
        HashMap<Property, Collection<LiteralOrIndividual>> rv =
                new HashMap<Property, Collection<LiteralOrIndividual>>();
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> val : vals.entrySet()) {
            if (val.getKey() instanceof OWLObjectProperty) {
                rv.put(new OWLAPIObjectProperty((OWLObjectProperty) val.getKey(), onto),
                        convertLOI(val.getValue()));
            }
        }
        return Collections.unmodifiableMap(rv);
    }

    public Collection<Property> convertP(Set<OWLPropertyExpression> vals) {
        HashSet<Property> rv = new HashSet<Property>();
        for (OWLPropertyExpression val : vals) {
            if (val instanceof OWLObjectProperty) {
                rv.add(new OWLAPIObjectProperty((OWLObjectProperty) val, onto));
            } else if (val instanceof OWLDataProperty) {
                rv.add(new OWLAPIDatatypeProperty((OWLDataProperty) val, onto));
            }
        }
        return Collections.unmodifiableSet(rv);
    }

    public Collection<LiteralOrIndividual> convertLOI(Set vals) {
        HashSet<LiteralOrIndividual> rv = new HashSet<LiteralOrIndividual>();
        for (Object val : vals) {
            if (val instanceof OWLNamedIndividual) {
                rv.add(new OWLAPIIndividual((OWLNamedIndividual) val, onto));
            } else if (val instanceof OWLLiteral) {
                rv.add(new OWLAPILiteralValue((OWLLiteral) val, onto));
            }
        }
        return Collections.unmodifiableSet(rv);
    }
}
