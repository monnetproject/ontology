/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.monnetproject.label.xbrl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jmccrae
 */
public class XBRLLabelExtractionPolicyTest {

    public XBRLLabelExtractionPolicyTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testExtract() throws Exception {
//        System.err.println("extract");
//        final XBRLLabelExtractionPolicy xbrlExtractor = new XBRLLabelExtractionPolicy();
//        OntologySerializer os = new OWLAPISerializer();
//        final Ontology ontology = os.read(new FileReader("/home/jmccrae/projects/MultilingualOntologies/ifrs-cor_2009-04-01.rdf"));
//        final HashMap<Language,Integer> counter = new HashMap<Language,Integer>();
//        for (Entity entity : ontology.getEntities()) {
//            final Map<Language, Collection<String>> labels = xbrlExtractor.getLabels(entity);
//            
//            for (Map.Entry<Language, Collection<String>> entry : labels.entrySet()) {
//                System.err.print(entry.getKey() + " -> ");
//                if(!counter.containsKey(entry.getKey())) {
//                    counter.put(entry.getKey(), entry.getValue().size());
//                } else {
//                    counter.put(entry.getKey(), entry.getValue().size() + counter.get(entry.getKey()));
//                }
//                for(String label : entry.getValue()) {
//                    System.err.print(label + ", ");
//                }
//                System.err.println();
//            }
//        }
//        
//        for (Map.Entry<Language, Integer> entry : counter.entrySet()) {
//            System.err.println(entry.getKey() + ": " + entry.getValue());
//        }
    }
}
