package eu.monnetproject.ontology;

import eu.monnetproject.data.*;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;

/**
 * A read/write mechanism for implementations of ontologies
 */
public interface OntologySerializer {
	/**
	 * Create a new (empty) ontology
	 */
	Ontology create(URI uri);
	
	/**
	 * Read an ontology from a data source
	 */
        @Deprecated
	Ontology read(DataSource source);
        
        /**
	 * Read an ontology from a data source
	 */
        Ontology read(Reader source);
        
        /**
	 * Read an ontology from a data source
         * @param source The location to read the ontology from
         * @param graph The graph to insert the result into (if triple store)
	 */
        Ontology read(Reader source, URI graph);
        
	/**
	 * Write an ontology to a data target
	 */
        @Deprecated
	void write(Ontology ontology, DataTarget target);
        
	/**
	 * Write an ontology to a data target using the specified format ("RDFXML", "OWLXML", "N3" or "TURTLE")
	 */
        @Deprecated
	void write(Ontology ontology, DataTarget target, String format);
        
        /**
	 * Write an ontology to a data target
	 */
        void write(Ontology ontology, Writer target);
        
	/**
	 * Write an ontology to a data target using the specified format ("RDFXML", "OWLXML", "N3" or "TURTLE")
	 */
	void write(Ontology ontology, Writer target, OntologyFormat format);
}
	
