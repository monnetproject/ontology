package eu.monnetproject.ontology.owlapi;

import eu.monnetproject.ontology.LiteralOrIndividual;
import eu.monnetproject.ontology.Property;
import eu.monnetproject.ontology.Restriction;
import java.util.HashSet;
import java.util.Set;
import org.semanticweb.owlapi.model.OWLHasValueRestriction;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLRestriction;

/**
 *
 * @author John McCrae
 */
public class OWLAPIRestriction extends OWLAPIClass implements Restriction {

    public OWLAPIRestriction(OWLRestriction rest, OWLOntology onto) {
        super(rest,onto);
    }
    
    private OWLRestriction rest() { return (OWLRestriction)clazz; }
    private OWLHasValueRestriction hvRest() { return (OWLHasValueRestriction)clazz; }
    
    public Property getOnProperty() {
        Set<OWLPropertyExpression> props = new HashSet<OWLPropertyExpression>();
        if(rest().getProperty() != null) {
            props.add(rest().getProperty());
            return convertP(props).iterator().next();
        } else {
            return null;
        }
    }

    public LiteralOrIndividual getHasValue() {
        Set<OWLObject> props = new HashSet<OWLObject>();
        if(hvRest().getValue() != null) {
            props.add(hvRest().getValue());
            return convertLOI(props).iterator().next();
        } else {
            return null;
        }
    }
    
}
