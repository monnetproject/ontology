package eu.monnetproject.ontology;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

import eu.monnetproject.data.DataSource;
import eu.monnetproject.data.FileDataSource;
import eu.monnetproject.ontology.owlapi.OWLAPISerializer;

import junit.framework.Assert;

public class TestOWLAPIOntology {

	private static final String TEST_ONTOLOGY = "src/test/resources/data/ttl/ontology.ttl";
	private static final String TEST_ONTOLOGY_URL = "http://www.monnet-project.eu/test/ex";
	private static final String[] TEST_CLASS_URIS = {TEST_ONTOLOGY_URL+"#Assets",TEST_ONTOLOGY_URL+"#FixedAssets"};

	private Ontology getOntology(DataSource datasource) {
		OWLAPISerializer owlapiSerializer = new OWLAPISerializer();
		return owlapiSerializer.read(datasource);
	}

	@Test
	public void TestLoadOntology() {
		Ontology instance = getOntology(new FileDataSource(new File(TEST_ONTOLOGY)));
		Assert.assertNotNull(instance);
	}

	@Test
	public void TestGetClasses() {
		Ontology ontology = getOntology(new FileDataSource(new File(TEST_ONTOLOGY)));
		Collection<Class> instance = ontology.getClasses();
		// check if ontology contains 2 classes
		Assert.assertEquals(2, instance.size());
		// check class uris
		HashSet<String> containedURIs = new HashSet<String>(Arrays.asList(TEST_CLASS_URIS));
		for(Class o:instance)
			Assert.assertTrue(containedURIs.contains(o.getURI().toString()));
	}

}
