package eu.monnetproject.ontology.owlapi;

import eu.monnetproject.ontology.*;
import eu.monnetproject.ontology.Class;
import org.semanticweb.owlapi.model.*;

import java.util.*;
import java.net.URI;

public class OWLAPIDatatype implements Datatype {
	final OWLDatatype datatype;
	
	public OWLAPIDatatype(OWLDatatype datatype,OWLOntology onto) {
		this.datatype = datatype;
	}
	
	public java.net.URI getURI() {
		return datatype.getIRI().toURI();
	}

	 public Collection<DatatypeProperty> getIsRangeOf() {
	 	 throw new UnsupportedOperationException("TODO");
	 }
	 
	 
	@Override
	public String toString() {
		return datatype.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof OWLAPIDatatype) {
			return datatype.equals(((OWLAPIDatatype)o).datatype);
		} else if(o != null && o instanceof Datatype) {
			return getURI().equals(((Datatype)o).getURI());
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() { return datatype.hashCode(); }
}
