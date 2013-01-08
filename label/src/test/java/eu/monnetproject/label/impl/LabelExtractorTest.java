/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.monnetproject.label.impl;

import eu.monnetproject.ontology.Entity;
import java.util.LinkedList;
import java.util.List;
import eu.monnetproject.data.FileDataSource;
import eu.monnetproject.label.LabelExtractor;
import eu.monnetproject.lang.Language;
import eu.monnetproject.ontology.Class;
import eu.monnetproject.ontology.Ontology;
import eu.monnetproject.ontology.OntologyFactory;
import eu.monnetproject.ontology.OntologySerializer;
import eu.monnetproject.framework.services.Services;
import eu.monnetproject.ontology.Individual;
import java.io.StringReader;
import java.net.URI;
import java.util.Collection;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jmccrae
 */
public class LabelExtractorTest {
    
    private static final String BASE = "http://www.semanticweb.org/ontologies/2011/7/test_nonstd_labels.owl#";
    
    public LabelExtractorTest() {
    }

    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    private LabelExtractorFactoryImpl lefi = new LabelExtractorFactoryImpl();
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test public void testLabelExtraction() throws Exception {
        final LabelExtractor extractor = lefi.getExtractor(null, false, false);
        final OntologySerializer serializer = Services.get(OntologySerializer.class);
        final Ontology ontology = serializer.read(new FileDataSource("src/test/resources/data/test/test_nonstd_labels.owl"));
        Entity clazz = null;
        for(Entity entity : ontology.getEntities()) {
            if(entity.getURI().toString().equals(BASE+"Standard")) {
                clazz = entity;
            }
        }
        assertNotNull(clazz);
        final Map<Language, Collection<String>> labels = extractor.getLabels(clazz);
        assertTrue(labels.containsKey(Language.ENGLISH));
        assertTrue(labels.get(Language.ENGLISH).contains("standard"));
    }
    
    @Test public void testDefaultAndInferredLabelExtraction() throws Exception {
        final LabelExtractor extractor = lefi.getExtractor(null, true, true);
        final OntologySerializer serializer = Services.get(OntologySerializer.class);
        final Ontology ontology = serializer.read(new FileDataSource("src/test/resources/data/test/test_nonstd_labels.owl"));
        final OntologyFactory factory = ontology.getFactory();
        final Class clazz = factory.makeClass(URI.create(BASE+"ThisIsFromTheDefault"));
        final Map<Language, Collection<String>> labels = extractor.getLabels(clazz);
        assertTrue(labels.containsKey(Language.ENGLISH));
        assertTrue(labels.get(Language.ENGLISH).contains("This is from the default"));
    }
    
    @Test public void testCustomLabelExtraction() throws Exception {
        List<URI> extras = new LinkedList<URI>();
        extras.add(URI.create(BASE+"testProperty"));
        final LabelExtractor extractor = lefi.getExtractor(extras, true, true);
        final OntologySerializer serializer = Services.get(OntologySerializer.class);
        final Ontology ontology = serializer.read(new FileDataSource("src/test/resources/data/test/test_nonstd_labels.owl"));
        final OntologyFactory factory = ontology.getFactory();
        final Class clazz = factory.makeClass(URI.create(BASE+"ThisIsFromTheDefault"));
        final Map<Language, Collection<String>> labels = extractor.getLabels(clazz);
        assertTrue(labels.containsKey(Language.ENGLISH));
        assertTrue(labels.get(Language.ENGLISH).contains("This is from custom label extraction"));
    }
    
    @Test public void testSKOSXLLabelExtraction() throws Exception {
        final LabelExtractor extractor = lefi.getExtractor(null, false, false);
        String ontologyFile = "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:skos-xl=\"http://www.w3.org/2008/05/skos-xl#\">"
                + " <owl:NamedIndividual rdf:about=\"http://Export/default.model#Case\">"
                + "   <skos-xl:prefLabel>"
                + "     <owl:NamedIndividual rdf:about=\"http://Export/default.model#Case_label\"/>"
                + "   </skos-xl:prefLabel>"
                + "  </owl:NamedIndividual>"
                + "  <owl:NamedIndividual rdf:about=\"http://Export/default.model#Case_label\">"
                + "    <skos-xl:literalForm xml:lang=\"en\">case</skos-xl:literalForm>"
                + "  </owl:NamedIndividual>"
                + "  <owl:AnnotationProperty rdf:about=\"http://www.w3.org/2008/05/skos-xl#literalForm\"/>"
                + "</rdf:RDF>";
        final OntologySerializer serializer = Services.get(OntologySerializer.class);
        final Ontology ontology = serializer.read(new StringReader(ontologyFile));
        final OntologyFactory factory = ontology.getFactory();
        final Individual indiv = factory.makeIndividual(URI.create("http://Export/default.model#Case_label"));
        assertTrue(!indiv.getAnnotations().isEmpty() || !indiv.getProperties().isEmpty());
        final Individual clazz = factory.makeIndividual(URI.create("http://Export/default.model#Case"));
        final Map<Language, Collection<String>> labels = extractor.getLabels(clazz);
        assertTrue(labels.containsKey(Language.ENGLISH));
        assertTrue(labels.get(Language.ENGLISH).contains("case"));
    }
        
}
