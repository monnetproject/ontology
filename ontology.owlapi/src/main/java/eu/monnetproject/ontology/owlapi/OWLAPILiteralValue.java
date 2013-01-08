package eu.monnetproject.ontology.owlapi;

import eu.monnetproject.ontology.*;
import eu.monnetproject.lang.*;
import org.semanticweb.owlapi.model.*;

public class OWLAPILiteralValue implements LiteralValue {
	final OWLLiteral lit;
	protected final OWLOntology onto;
	protected final OWLDataFactory factory;
	protected final OWLOntologyManager manager;
	
	public OWLAPILiteralValue(OWLLiteral wrapped,OWLOntology onto) {
		this.onto = onto;
		this.manager = onto.getOWLOntologyManager();
		this.factory = manager.getOWLDataFactory();
		lit = wrapped;
	}
	
	
  public String getValue() {
  	return lit.getLiteral();
  }
  public Language getLanguage() {
  	if(lit.getLang() != null) {
  		try {
                    return Language.get(lit.getLang());
                } catch(LanguageCodeFormatException x) {
                    return null;
                }
  	} else {
  		return null;
  	}
  }
  public Datatype getDatatype() {
  	if(lit.getDatatype() != null) {
  		return new OWLAPIDatatype(lit.getDatatype(),onto);
  	} else {
  		return null;
  	}
  }
  
   
	@Override
	public String toString() {
		return lit.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof OWLAPILiteralValue) {
			return lit.equals(((OWLAPILiteralValue)o).lit);
		} else if(o != null && o instanceof LiteralValue) {
			LiteralValue lv = (LiteralValue)o;
			return lit.getLiteral().equals(lv.getValue()) &&
			 ((lit.getLang() == null && lv.getLanguage() == null) ||
			 	(lit.getLang() != null && lv.getLanguage() != null 
			 		&& lit.getLang().equals(lv.getLanguage().toString()))) &&
			 ((lit.getDatatype() == null && lv.getDatatype() == null) ||
			 	 (lit.getDatatype() != null && lit.getDatatype().getIRI().toURI().equals(lv.getDatatype().getURI())));
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() { return lit.hashCode(); }
}
