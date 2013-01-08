package eu.monnetproject.ontology;

/**
 * A restriction axiom
 *  
 * @author John McCrae
 */
public interface Restriction extends Class {
    Property getOnProperty();
    LiteralOrIndividual getHasValue();
}
