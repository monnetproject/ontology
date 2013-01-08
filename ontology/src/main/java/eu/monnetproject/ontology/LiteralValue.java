package eu.monnetproject.ontology;

import eu.monnetproject.lang.Language;

public interface LiteralValue extends LiteralOrIndividual, AnnotationValue {
  String getValue();
  Language getLanguage();
  Datatype getDatatype();
}
