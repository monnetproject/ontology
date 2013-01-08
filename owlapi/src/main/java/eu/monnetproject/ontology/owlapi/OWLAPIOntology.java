package eu.monnetproject.ontology.owlapi;

import eu.monnetproject.ontology.*;
import eu.monnetproject.ontology.Class;

import org.semanticweb.owlapi.model.*;

import java.util.*;
import java.net.URI; 

public class OWLAPIOntology extends OWLAPI implements Ontology {

    //final OWLOntology onto;
    final OWLDataFactory dataFactory;
    final String baseURI;
    final OWLOntologyManager manager;
    private static final String OWL = "http://www.w3.org/2002/07/owl#";
	//private DataSource dataSource;

    public OWLAPIOntology(OWLOntology onto, OWLDataFactory factory) {
        super(onto);
        if(onto == null) {
            throw new IllegalArgumentException("Null ontology object");
        }
        //this.onto = onto;
        this.dataFactory = factory;
        this.manager = onto.getOWLOntologyManager();
        if (onto.getOntologyID().getOntologyIRI() == null) {
            baseURI = "file:ontology#";
        } else {
            String ontoURI = onto.getOntologyID().getOntologyIRI().toString();
            if (ontoURI.lastIndexOf('#') > 0) {
                baseURI = ontoURI.substring(0, ontoURI.lastIndexOf("#") + 1);
            } else if (ontoURI.lastIndexOf('/') > 0) {
                baseURI = ontoURI.substring(0, ontoURI.lastIndexOf("/") + 1);
            } else {
                baseURI = ontoURI;
            }
        }
    }

    @Override
    public URI getURI() {
        if(onto != null && onto.getOntologyID() != null && onto.getOntologyID().getOntologyIRI() != null) {
            return onto.getOntologyID().getOntologyIRI().toURI();
        } else {
            return null;
        }
    }
    
    @Override
    public URI getBaseURI() {
        return URI.create(this.baseURI);
    }

    @Override
    public Collection<Entity> getEntities() {
        return convertE(onto.getSignature());
    }

    @Override
    public Collection<Class> getClasses() {
        HashSet<Class> rv = new HashSet<Class>();
        for (OWLClass val : onto.getClassesInSignature()) {
            if (val instanceof OWLClass) {
                rv.add(new OWLAPIClass((OWLClass) val, onto));
            }
        }
        return rv;
    }

    @Override
    public Collection<Individual> getIndividuals() {
        HashSet<Individual> rv = new HashSet<Individual>();
        for (OWLIndividual val : onto.getIndividualsInSignature()) {
            if (val instanceof OWLIndividual) {
                if (val instanceof OWLNamedIndividual) {
                    rv.add(new OWLAPIIndividual((OWLNamedIndividual) val, onto));
                }
            }
        }
        return rv;
    }

    @Override
    public Collection<Property> getProperties() {
        Set<Property> rval = new HashSet<Property>();
        rval.addAll(getObjectProperties());
        rval.addAll(getDatatypeProperties());
        rval.addAll(getAnnotationProperties());
        return Collections.unmodifiableSet(rval);
    }

    @Override
    public Collection<ObjectProperty> getObjectProperties() {
        HashSet<ObjectProperty> rv = new HashSet<ObjectProperty>();
        for (OWLObjectProperty val : onto.getObjectPropertiesInSignature()) {
            if (val instanceof OWLObjectProperty) {
                rv.add(new OWLAPIObjectProperty((OWLObjectProperty) val, onto));
            }
        }
        return rv;
    }

    @Override
    public Collection<DatatypeProperty> getDatatypeProperties() {
        HashSet<DatatypeProperty> rv = new HashSet<DatatypeProperty>();
        for (OWLDataProperty val : onto.getDataPropertiesInSignature()) {
            if (val instanceof OWLDataProperty) {
                rv.add(new OWLAPIDatatypeProperty((OWLDataProperty) val, onto));
            }
        }
        return rv;
    }

    @Override
    public Collection<AnnotationProperty> getAnnotationProperties() {
        return convertAP(onto.getAnnotationPropertiesInSignature());
    }

    @Override
    public Collection<Entity> getEntities(URI uri) {
        return convertE(onto.getEntitiesInSignature(IRI.create(uri)));
    }
    private OWLAPIOntologyFactory factory = new OWLAPIOntologyFactory();

    @Override
    public OntologyFactory getFactory() {
        return factory;
    }

    @Override
    public boolean addClass(Class type) {
        OWLClassExpression oce;
        if(type instanceof OWLAPIClass) {
            oce = ((OWLAPIClass)type).clazz;
        } else {
            oce = dataFactory.getOWLClass(IRI.create(type.getURI()));
        }
        final List<OWLOntologyChange> changes = manager.addAxiom(onto, dataFactory.getOWLSubClassOfAxiom(oce, dataFactory.getOWLClass(IRI.create(OWL+"Thing"))));
        return !changes.isEmpty();
    }

    @Override
    public boolean removeClass(Class type) {
        OWLClassExpression oce;
        if(type instanceof OWLAPIClass) {
            oce = ((OWLAPIClass)type).clazz;
        } else {
            oce = dataFactory.getOWLClass(IRI.create(type.getURI()));
        }
        final List<OWLOntologyChange> changes = manager.removeAxiom(onto, dataFactory.getOWLSubClassOfAxiom(oce, dataFactory.getOWLClass(IRI.create(OWL+"Thing"))));
        return !changes.isEmpty();
    }

    @Override
    public boolean addIndividual(Individual indvdl) {
        OWLIndividual oi;
        if(indvdl instanceof OWLAPIIndividual) {
            oi = ((OWLAPIIndividual)indvdl).indiv;
        } else {
            oi = dataFactory.getOWLNamedIndividual(IRI.create(indvdl.getURI()));
        }
        final List<OWLOntologyChange> changes = manager.addAxiom(onto, dataFactory.getOWLClassAssertionAxiom(dataFactory.getOWLClass(IRI.create(OWL+"Thing")),oi));
        return !changes.isEmpty();
    }

    @Override
    public boolean removeIndividual(Individual indvdl) {
        OWLIndividual oi;
        if(indvdl instanceof OWLAPIIndividual) {
            oi = ((OWLAPIIndividual)indvdl).indiv;
        } else {
            oi = dataFactory.getOWLNamedIndividual(IRI.create(indvdl.getURI()));
        }
        final List<OWLOntologyChange> changes = manager.removeAxiom(onto, dataFactory.getOWLClassAssertionAxiom(dataFactory.getOWLClass(IRI.create(OWL+"Thing")),oi));
        return !changes.isEmpty();
    }

    @Override
    public boolean addObjectProperty(ObjectProperty op) {
        OWLObjectProperty oop;
        if(op instanceof OWLAPIObjectProperty) {
            oop = ((OWLAPIObjectProperty)op).objProp;
        } else {
            oop = dataFactory.getOWLObjectProperty(IRI.create(op.getURI()));
        }
        final List<OWLOntologyChange> changes = manager.addAxiom(onto, dataFactory.getOWLSubObjectPropertyOfAxiom(oop, dataFactory.getOWLObjectProperty(IRI.create(OWL+"topObjectProperty"))));
        return !changes.isEmpty();
    }

    @Override
    public boolean removeObjectProperty(ObjectProperty op) {
        OWLObjectProperty oop;
        if(op instanceof OWLAPIObjectProperty) {
            oop = ((OWLAPIObjectProperty)op).objProp;
        } else {
            oop = dataFactory.getOWLObjectProperty(IRI.create(op.getURI()));
        }
        final List<OWLOntologyChange> changes = manager.removeAxiom(onto, dataFactory.getOWLSubObjectPropertyOfAxiom(oop, dataFactory.getOWLObjectProperty(IRI.create(OWL+"topObjectProperty"))));
        return !changes.isEmpty();
    }

    @Override
    public boolean addDatatypeProperty(DatatypeProperty dp) {
        OWLDataProperty oop;
        if(dp instanceof OWLAPIDatatypeProperty) {
            oop = ((OWLAPIDatatypeProperty)dp).dataProp;
        } else {
            oop = dataFactory.getOWLDataProperty(IRI.create(dp.getURI()));
        }
        final List<OWLOntologyChange> changes = manager.addAxiom(onto, dataFactory.getOWLSubDataPropertyOfAxiom(oop, dataFactory.getOWLDataProperty(IRI.create(OWL+"topDataProperty"))));
        return !changes.isEmpty();
    }

    @Override
    public boolean removeDatatypeProperty(DatatypeProperty dp) {
        OWLDataProperty oop;
        if(dp instanceof OWLAPIDatatypeProperty) {
            oop = ((OWLAPIDatatypeProperty)dp).dataProp;
        } else {
            oop = dataFactory.getOWLDataProperty(IRI.create(dp.getURI()));
        }
        final List<OWLOntologyChange> changes = manager.removeAxiom(onto, dataFactory.getOWLSubDataPropertyOfAxiom(oop, dataFactory.getOWLDataProperty(IRI.create(OWL+"topDataProperty"))));
        return !changes.isEmpty();
    }

//    public void setDataSource(DataSource dataSource) {
//    	this.dataSource = dataSource;
//    }
//
//    public DataSource getDataSource() {
//    	return this.dataSource;
//    }

    /**
     * Clear the ontology
     */
    @Override
    public boolean removeAllAxioms() {
    	
    	for (OWLAxiom axiom : onto.getAxioms()) {
        	manager.removeAxiom(onto,axiom);
    	}

        return true;
    }

    @Override
    public boolean addAnnotationProperty(AnnotationProperty ap) {
        return true;
    }

    @Override
    public boolean removeAnnotationProperty(AnnotationProperty ap) {
        return true;
    }

    public class OWLAPIOntologyFactory implements OntologyFactory {

        private Random random = new Random();

        @Override
        public AnnotationProperty makeAnnotationProperty(URI uri) {
            return new OWLAPIAnnotationProperty(dataFactory.getOWLAnnotationProperty(IRI.create(uri)), onto);
        }

        @Override
        public Class makeClass() {
            return new OWLAPIClass(dataFactory.getOWLClass(IRI.create(baseURI + "genid" + Math.abs(random.nextInt()))), onto);
        }

        @Override
        public Class makeClass(URI uri) {
            return new OWLAPIClass(dataFactory.getOWLClass(IRI.create(uri)), onto);
        }

        @Override
        public Datatype makeDatatype(URI uri) {
            return new OWLAPIDatatype(dataFactory.getOWLDatatype(IRI.create(uri)), onto);
        }

        @Override
        public DatatypeProperty makeDatatypeProperty(URI uri) {
            return new OWLAPIDatatypeProperty(dataFactory.getOWLDataProperty(IRI.create(uri)), onto);
        }

        @Override
        public Individual makeIndividual() {
            return new OWLAPIIndividual(dataFactory.getOWLNamedIndividual(IRI.create(baseURI + "genid" + Math.abs(random.nextInt()))), onto);
        }

        @Override
        public Individual makeIndividual(URI uri) {
            return new OWLAPIIndividual(dataFactory.getOWLNamedIndividual(IRI.create(uri)), onto);
        }

        @Override
        public LiteralValue makeLiteral(String value) {
            return new OWLAPILiteralValue(dataFactory.getOWLLiteral(value), onto);
        }

        @Override
        public LiteralValue makeLiteralWithLanguage(String value, String language) {
            return new OWLAPILiteralValue(dataFactory.getOWLStringLiteral(value, language.toString()), onto);
        }

        @Override
        public LiteralValue makeLiteralWithDatatype(String value, Datatype datatype) {
            if (datatype instanceof OWLAPIDatatype) {
                return new OWLAPILiteralValue(dataFactory.getOWLLiteral(value,
                        ((OWLAPIDatatype) datatype).datatype), onto);
            } else {
                return new OWLAPILiteralValue(dataFactory.getOWLLiteral(value,
                        dataFactory.getOWLDatatype(IRI.create(datatype.getURI()))), onto);
            }
        }
        
        @Override
        public ObjectProperty makeObjectProperty(URI uri) {
            return new OWLAPIObjectProperty(dataFactory.getOWLObjectProperty(IRI.create(uri)), onto);
        }

        @Override
        public Restriction makeObjectHasValueRestriction(ObjectProperty op, Individual indvdl) {
            OWLObjectPropertyExpression odpe;
            if(op instanceof OWLAPIObjectProperty) {
                odpe = ((OWLAPIObjectProperty)op).objProp;
            } else {
                odpe = dataFactory.getOWLObjectProperty(IRI.create(op.getURI()));
            }
            OWLNamedIndividual ol;
            if(indvdl instanceof OWLAPIIndividual) {
                ol = ((OWLAPIIndividual)indvdl).indiv;
            } else {
                ol = dataFactory.getOWLNamedIndividual(IRI.create(indvdl.getURI()));
            }
            OWLObjectHasValue odhv = dataFactory.getOWLObjectHasValue(odpe, ol);
            return new OWLAPIRestriction(odhv, onto);
        }

        @Override
        public Restriction makeDatatypeHasValueRestriction(DatatypeProperty dp, LiteralValue lv) {
            OWLDataPropertyExpression odpe;
            if(dp instanceof OWLAPIDatatypeProperty) {
                odpe = ((OWLAPIDatatypeProperty)dp).dataProp;
            } else {
                odpe = dataFactory.getOWLDataProperty(IRI.create(dp.getURI()));
            }
            OWLLiteral ol;
            if(lv instanceof OWLAPILiteralValue) {
                ol = ((OWLAPILiteralValue)lv).lit;
            } else {
                ol = dataFactory.getOWLLiteral(lv.getValue());
            }
            OWLDataHasValue odhv = dataFactory.getOWLDataHasValue(odpe, ol);
            return new OWLAPIRestriction(odhv, onto);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof OWLAPIOntology)) {
            return false;
        }
        return onto.equals(((OWLAPIOntology)obj).onto);
    }

    @Override
    public int hashCode() {
        return onto.hashCode();
    }
}
