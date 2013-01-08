package eu.monnetproject.ontology.owlapi;

import eu.monnetproject.ontology.*;
import eu.monnetproject.ontology.Class;
import org.semanticweb.owlapi.model.*;
import java.util.*;

public class OWLAPIClass extends OWLAPIEntity implements Class {
	final OWLClassExpression clazz;
	
	public OWLAPIClass(OWLClassExpression wrapped,OWLOntology onto) {
		super(onto);
		clazz = wrapped;
	}
	
        protected OWLClass cl() {
            if(clazz instanceof OWLClass) {
                return (OWLClass)clazz;
            } else {
                throw new RuntimeException("Class is a restriction");
            }
        }
        
	protected OWLEntity entity() { return cl(); }
	
	public Collection<Class> getSubClassOf() {
		return convertC(cl().getSubClasses(onto));
	}
        
	public boolean addSubClassOf(Class clazz2) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLSubClassOfAxiom(clazz,convert(clazz2))));
	}
	public boolean removeSubClassOf(Class clazz2) {
		return isChange(manager.removeAxiom(onto,
			factory.getOWLSubClassOfAxiom(clazz,convert(clazz2))));
	}
	
	public Collection<Class> getSuperClassOf() {
		return convertC(cl().getSuperClasses(onto));
	}
	
	public Collection<Class> getEquivalentClass() {
		return convertC(cl().getEquivalentClasses(onto));
	}
	public boolean addEquivalentClass(Class clazz2) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLEquivalentClassesAxiom(clazz,convert(clazz2))));
	}
	public boolean removeEquivalentClass(Class clazz2) {
		return isChange(manager.removeAxiom(onto,
			factory.getOWLEquivalentClassesAxiom(clazz,convert(clazz2))));
	}
	
	public Collection<Class> getDisjointWith() {		
		return convertC(cl().getDisjointClasses(onto));
	}
	public boolean addDisjointWith(Class clazz2) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLDisjointClassesAxiom(clazz,convert(clazz2))));
	}
	public boolean removeDisjointWith(Class clazz2) {
		return isChange(manager.addAxiom(onto,
			factory.getOWLDisjointClassesAxiom(clazz,convert(clazz2))));
	}
	
	/** 
	 * Inverse of Individual.getType
	 */
	 public Collection<Individual> getIsTypeOf() {
	 	 return convertI(cl().getIndividuals(onto));
	 }
	
	/**
	 * Inverse of Property.getDomain
	 */
	 public Collection<Property> getIsDomainOf() {
             Set<Property> rval = new HashSet<Property>();
             for (OWLDataProperty oWLDataProperty : this.onto.getDataPropertiesInSignature()) {
                 for (OWLClassExpression oWLClassExpression : oWLDataProperty.getDomains(onto)) {
                     if(oWLClassExpression.equals(clazz)) {
                         rval.add(new OWLAPIDatatypeProperty(oWLDataProperty, onto));
                     }
                 }
                 
             }
             for (OWLObjectProperty oWLObjectProperty : this.onto.getObjectPropertiesInSignature()) {
                 for (OWLClassExpression oWLClassExpression : oWLObjectProperty.getDomains(onto)) {
                     if(oWLClassExpression.equals(clazz)) {
                         rval.add(new OWLAPIObjectProperty(oWLObjectProperty, onto));
                     }
                 }
                 
             }
             return Collections.unmodifiableSet(rval);
	 }
	
	/** 
	 * Inverse ofObjectProperty.getRange 
	 */
	 public Collection<ObjectProperty> getIsRangeOf() {
             Set<ObjectProperty> rval = new HashSet<ObjectProperty>();
	 	 
             for (OWLObjectProperty oWLObjectProperty : this.onto.getObjectPropertiesInSignature()) {
                 for (OWLClassExpression oWLClassExpression : oWLObjectProperty.getRanges(onto)) {
                     if(oWLClassExpression.equals(clazz)) {
                         rval.add(new OWLAPIObjectProperty(oWLObjectProperty, onto));
                     }
                 }
                 
             }
             return Collections.unmodifiableSet(rval);
	 } 
}

