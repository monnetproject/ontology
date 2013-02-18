package eu.monnetproject.ontology.owlapi;

import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;

import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.ReaderDocumentSource;
import org.semanticweb.owlapi.io.WriterDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import aQute.bnd.annotation.component.Component;
import eu.monnetproject.data.DataSource;
import eu.monnetproject.data.DataTarget;
import eu.monnetproject.ontology.Ontology;
import eu.monnetproject.ontology.OntologyFormat;
import eu.monnetproject.ontology.OntologySerializer;

@Component(provide=OntologySerializer.class)
public class OWLAPISerializer implements OntologySerializer {

    private final OWLOntologyManager manager;
    private final OWLDataFactory factory;

    public OWLAPISerializer() {
       this.manager = OWLManager.createOWLOntologyManager();
       manager.setSilentMissingImportsHandling(true);
       this.factory = manager.getOWLDataFactory();
    }

    
    
    @Override
    public Ontology create(URI uri) {
        try {
            return new OWLAPIOntology(manager.createOntology(IRI.create(uri)), factory);
        } catch (OWLOntologyCreationException x) {
            throw new RuntimeException(x);
        }
    }

    @Override
    public Ontology read(DataSource source) {
        OWLOntology onto = null;
        OWLAPIOntology owlapiOnto = null;

        try {
            for (OWLOntology onto2 : manager.getOntologies()) {
                if (manager.getOntologyDocumentIRI(onto2).equals(IRI.create(source.asURL()))) {
                    onto = onto2;
                }
            }
            if (onto != null) {
                owlapiOnto = new OWLAPIOntology(onto, factory);
                //owlapiOnto.setDataSource(source);
                return owlapiOnto;
            }
        } catch (UnsupportedOperationException e) {
//            log.warning(e.getMessage());
        } catch (URISyntaxException e) {
//            log.warning(e.getMessage());
        }

        try {
            owlapiOnto = new OWLAPIOntology(manager.loadOntology(IRI.create(source.asURL())), factory);
        } catch (OWLOntologyAlreadyExistsException x) {
            owlapiOnto = new OWLAPIOntology(manager.getOntology(x.getOntologyID()), factory);
        } catch (Exception x) {
            try {
                owlapiOnto = new OWLAPIOntology(manager.loadOntologyFromOntologyDocument(source.asFile()), factory);
            } catch (OWLOntologyAlreadyExistsException x2) {
                owlapiOnto = new OWLAPIOntology(manager.getOntology(x2.getOntologyID()), factory);
            } catch (Exception x2) {
                try {
                    owlapiOnto = new OWLAPIOntology(manager.loadOntologyFromOntologyDocument(source.asInputStream()), factory);
                } catch (OWLOntologyAlreadyExistsException x3) {
                    owlapiOnto = new OWLAPIOntology(manager.getOntology(x3.getOntologyID()), factory);
                } catch (Exception x3) {
//                    log.stackTrace(x);
  //                  log.stackTrace(x2);
    //                log.stackTrace(x3);
                    throw new RuntimeException("Could not read OWL ontology");
                }
            }
        }

        //owlapiOnto.setDataSource(source);
        return owlapiOnto;
    }

    @Override
    public Ontology read(Reader source) {
        return read(source, null);
    }

    @Override
    public Ontology read(Reader source, URI graph) {

        OWLOntology onto = null;
        OWLAPIOntology owlapiOnto = null;

        try {
            for (OWLOntology onto2 : manager.getOntologies()) {
                if (graph != null && manager.getOntologyDocumentIRI(onto2).equals(IRI.create(graph))) {
                    onto = onto2;
                }
            }
            if (onto != null) {
                owlapiOnto = new OWLAPIOntology(onto, factory);
                //owlapiOnto.setDataSource(source);
                return owlapiOnto;
            }
        } catch (UnsupportedOperationException e) {
//            log.warning(e.getMessage());
        }

        try {
            owlapiOnto = new OWLAPIOntology(manager.loadOntologyFromOntologyDocument(new ReaderDocumentSource(source)), factory);
        } catch (OWLOntologyAlreadyExistsException x) {
            owlapiOnto = new OWLAPIOntology(manager.getOntology(x.getOntologyID()), factory);
        } catch (Exception x3) {
            throw new RuntimeException("Could not read OWL ontology");
        }

        //owlapiOnto.setDataSource(source);
        return owlapiOnto;
    }

    @Override
    public void write(Ontology ontology, DataTarget target, String format) {
        if (ontology instanceof OWLAPIOntology) {
            try {
                manager.saveOntology(((OWLAPIOntology) ontology).onto, IRI.create(target.asURL()));
            } catch (Exception x) {
                try {
                    if (format.equals("RDFXML")) {
                        manager.saveOntology(((OWLAPIOntology) ontology).onto, new RDFXMLOntologyFormat(), target.asOutputStream());
                    } else if (format.equals("TURTLE")) {
                        manager.saveOntology(((OWLAPIOntology) ontology).onto, new TurtleOntologyFormat(), target.asOutputStream());
                    } else {
                        manager.saveOntology(((OWLAPIOntology) ontology).onto, new OWLXMLOntologyFormat(), target.asOutputStream());
                    }
                } catch (Exception x2) {
//                    log.stackTrace(x);
//                    log.stackTrace(x);
                    throw new RuntimeException("Could not write OWL ontology");
                }
            }
        } else {
            throw new IllegalArgumentException("Cannot serializer an ontology I did not create!");
        }
    }

    @Override
    public void write(Ontology ontology, DataTarget target) {
        write(ontology, target, "OWLXML");
    }

    @Override
    public void write(Ontology ontology, Writer target, OntologyFormat format) {

        if (ontology instanceof OWLAPIOntology) {
            try {
                if (format.equals(OntologyFormat.RDFXML)) {
                    manager.saveOntology(((OWLAPIOntology) ontology).onto, new RDFXMLOntologyFormat(), new WriterDocumentTarget(target));
                } else if (format.equals(OntologyFormat.TURTLE)) {
                    manager.saveOntology(((OWLAPIOntology) ontology).onto, new TurtleOntologyFormat(), new WriterDocumentTarget(target));
                } else if (format.equals(OntologyFormat.OWLXML)) {
                    manager.saveOntology(((OWLAPIOntology) ontology).onto, new OWLXMLOntologyFormat(), new WriterDocumentTarget(target));
                } else {
                    throw new IllegalArgumentException("Unsupported format " + format);
                }
            } catch (Exception x2) {
//                log.stackTrace(x2);
                throw new RuntimeException("Could not write OWL ontology");
            }
        } else {
            throw new IllegalArgumentException("Cannot serializer an ontology I did not create!");
        }
    }

    @Override
    public void write(Ontology ontology, Writer target) {
        write(ontology, target, OntologyFormat.RDFXML);
    }
}
