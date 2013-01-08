package eu.monnetproject.ontology.owlapi;

import eu.monnetproject.ontology.*;
import eu.monnetproject.ontology.Class;

import org.semanticweb.owlapi.model.*;

import java.util.*;
import java.net.URI;

public abstract class OWLAPIEntity extends OWLAPI implements Entity {
    protected final OWLOntologyManager manager;

    protected OWLAPIEntity(OWLOntology onto) {
        super(onto);
        this.manager = onto.getOWLOntologyManager();
    }

    protected abstract OWLEntity entity();

    public java.net.URI getURI() {
        return entity().getIRI().toURI();
    }

    public String getID() {
        // OWLAPI does not allow blank entities
        return null;
    }

    public Map<AnnotationProperty, Collection<AnnotationValue>> getAnnotations() {
        Map<AnnotationProperty, Collection<AnnotationValue>> rval =
                new HashMap<AnnotationProperty, Collection<AnnotationValue>>();
        for (OWLAnnotation anno : entity().getAnnotations(onto)) {
            if (anno.getValue() instanceof OWLLiteral) {
                AnnotationProperty newAP = new OWLAPIAnnotationProperty(anno.getProperty(), onto);
                if (!rval.containsKey(newAP)) {
                    rval.put(newAP, new HashSet<AnnotationValue>());
                }
                rval.get(newAP).add(new OWLAPILiteralValue((OWLLiteral) anno.getValue(), onto));
            } else if (anno.getValue() instanceof OWLNamedIndividual) {
                AnnotationProperty newAP = new OWLAPIAnnotationProperty(anno.getProperty(), onto);
                if (!rval.containsKey(newAP)) {
                    rval.put(newAP, new HashSet<AnnotationValue>());
                }
                rval.get(newAP).add(new OWLAPIIndividual((OWLNamedIndividual) anno.getValue(), onto));
            } else if (anno.getValue() instanceof IRI) {
                AnnotationProperty newAP = new OWLAPIAnnotationProperty(anno.getProperty(), onto);
                if (!rval.containsKey(newAP)) {
                    rval.put(newAP, new HashSet<AnnotationValue>());
                }
                rval.get(newAP).add(new OWLAPIIndividual(factory.getOWLNamedIndividual((IRI)anno.getValue()), onto));
                
            }
        }
        return Collections.unmodifiableMap(rval);
    }

    public Collection<AnnotationValue> getAnnotationValues(AnnotationProperty property) {
        return convertA(entity().getAnnotations(onto, convert(property)));
    }

    public boolean addAnnotation(AnnotationProperty property, AnnotationValue annotation) {
        return isChange(manager.addAxiom(onto,
                factory.getOWLAnnotationAssertionAxiom(convert(property), entity().getIRI(), convert(annotation))));
    }

    public boolean removeAnnotation(AnnotationProperty property, AnnotationValue annotation) {
        return isChange(manager.removeAxiom(onto,
                factory.getOWLAnnotationAssertionAxiom(convert(property), entity().getIRI(), convert(annotation))));
    }

    protected OWLAnnotationProperty convert(AnnotationProperty prop) {
        if (prop instanceof OWLAPIAnnotationProperty) {
            return ((OWLAPIAnnotationProperty) prop).annoProp;
        } else {
            return factory.getOWLAnnotationProperty(IRI.create(prop.getURI()));
        }
    }

    protected OWLClassExpression convert(Class prop) {
        if (prop instanceof OWLAPIClass) {
            return ((OWLAPIClass) prop).clazz;
        } else {
            return factory.getOWLClass(IRI.create(prop.getURI()));
        }
    }

    protected OWLDataProperty convert(DatatypeProperty prop) {
        if (prop instanceof OWLAPIDatatypeProperty) {
            return ((OWLAPIDatatypeProperty) prop).dataProp;
        } else {
            return factory.getOWLDataProperty(IRI.create(prop.getURI()));
        }
    }

    protected OWLNamedIndividual convert(Individual prop) {
        if (prop instanceof OWLAPIIndividual) {
            return ((OWLAPIIndividual) prop).indiv;
        } else {
            return factory.getOWLNamedIndividual(IRI.create(prop.getURI()));
        }
    }

    protected OWLLiteral convert(LiteralValue prop) {
        if (prop instanceof OWLAPILiteralValue) {
            return ((OWLAPILiteralValue) prop).lit;
        } else {
            if (prop.getLanguage() != null) {
                return factory.getOWLStringLiteral(prop.getValue(), prop.getLanguage().toString());
            } else if (prop.getDatatype() != null) {
                return factory.getOWLTypedLiteral(prop.getValue(), convert(prop.getDatatype()));
            } else {
                return factory.getOWLStringLiteral(prop.getValue());
            }
        }
    }

    protected OWLObjectProperty convert(ObjectProperty prop) {
        if (prop instanceof OWLAPIObjectProperty) {
            return ((OWLAPIObjectProperty) prop).objProp;
        } else {
            return factory.getOWLObjectProperty(IRI.create(prop.getURI()));
        }
    }

    protected OWLDatatype convert(Datatype prop) {
        if (prop instanceof OWLAPIDatatype) {
            return ((OWLAPIDatatype) prop).datatype;
        } else {
            return factory.getOWLDatatype(IRI.create(prop.getURI()));
        }
    }
    
    protected OWLAnnotationValue convert(AnnotationValue av) {
        if(av instanceof OWLAPILiteralValue) {
            return ((OWLAPILiteralValue)av).lit;
        } else if(av instanceof Individual) {
            return factory.getOWLAnonymousIndividual(((Individual)av).getURI().toString());
        } else {
            return factory.getOWLLiteral(((LiteralValue)av).getValue());
        }
    }

    protected boolean isChange(List<OWLOntologyChange> changes) {
        if (changes.isEmpty()) {
            return false;
        }
        for (OWLOntologyChange change : changes) {
            if (change.isAxiomChange()) {
                return true;
            }
        }
        return false;
    }
    
    public Ontology getOntology() {
        return new OWLAPIOntology(onto, factory);
    }

    @Override
    public String toString() {
        return entity().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof OWLAPIEntity) {
            return entity().equals(((OWLAPIEntity) o).entity());
        } else if (o != null && o instanceof Entity) {
            if (o instanceof Class) {
                return entity().equals(convert((Class) o));
            } else if (o instanceof AnnotationProperty) {
                return entity().equals(convert((AnnotationProperty) o));
            } else if (o instanceof DatatypeProperty) {
                return entity().equals(convert((DatatypeProperty) o));
            } else if (o instanceof DatatypeProperty) {
                return entity().equals(convert((Individual) o));
            } else if (o instanceof DatatypeProperty) {
                return entity().equals(convert((ObjectProperty) o));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return entity().hashCode();
    }

    /**
     * Return all puns of this entity. This is all entities that have the same URI but a different ontology
     * type (e.g., Class and Individual)
     */
    @Override
    public Collection<Entity> getPuns() {
    	
    	Collection<Entity> puns = new HashSet<Entity>();
    	
    	for (Entity ent : getOntology().getEntities(getURI())) {
    		if (!ent.equals(this))
    			puns.add(ent);
    	}
    	
    	return puns;
    	
    }
}
