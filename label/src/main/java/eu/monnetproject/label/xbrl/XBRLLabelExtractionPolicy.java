package eu.monnetproject.label.xbrl;

import eu.monnetproject.label.LabelExtractor;
import eu.monnetproject.label.custom.CustomLabelExtractionPolicy;
import eu.monnetproject.label.impl.AggregateLabelExtractor;
import java.net.URI;
import java.util.Arrays;


/**
 * XBRL Label Extraction Policy to extract labels from XBRL-RDF files with the following label types
 * 
 *   - xbrl 2003: role label, verbose label, total label, terse label
 *   - xbrl iasb: role label negated, terse label negated, net label
 * 
 * @author Tobias Wunner
 *
 */
public class XBRLLabelExtractionPolicy extends AggregateLabelExtractor {
	
	private static final String XBRL_2003 = "http://www.xbrl.org/2003/role/";
	private static final String XBRL_IASB = "http://xbrl.iasb.org/role/";
	
	// label xbrl 2003
	private static final URI LABEL_ROLE = URI.create(XBRL_2003+"label");
	private static final URI LABEL_VERBOSE = URI.create(XBRL_2003+"verbose");	
	private static final URI LABEL_TOTAL = URI.create(XBRL_2003+"totalLabel");
	private static final URI LABEL_TERSE = URI.create(XBRL_2003+"terseLabel");
	
	// label xbrl iasb	
	private static final URI LABEL_ROLE_NEGATED = URI.create(XBRL_IASB+"label/labelNegated");
	private static final URI LABEL_TERSE_NEGATED = URI.create(XBRL_IASB+"label/terseLabelNegated");
	private static final URI LABEL_NET = URI.create(XBRL_IASB+"label/netLabel");
        
        public XBRLLabelExtractionPolicy() {
            super(Arrays.asList(new LabelExtractor[] {
                new CustomLabelExtractionPolicy(LABEL_ROLE),
                new CustomLabelExtractionPolicy(LABEL_VERBOSE),
                new CustomLabelExtractionPolicy(LABEL_TOTAL),
                new CustomLabelExtractionPolicy(LABEL_TERSE),
                new CustomLabelExtractionPolicy(LABEL_NET)
            }),true);
        }
        
	/*
	private boolean isXBRLLabel(AnnotationProperty annoProp) {
		URI uri = annoProp.getURI();
		if (
				uri.equals(LABEL_ROLE) ||
				uri.equals(LABEL_VERBOSE) ||
				uri.equals(LABEL_TOTAL) ||
				uri.equals(LABEL_TERSE) ||
				uri.equals(LABEL_ROLE_NEGATED) ||
				uri.equals(LABEL_TERSE_NEGATED) ||
				uri.equals(LABEL_NET)
		)
			return true;
		return false;
	}

	@Override
	public Map<Language, Collection<String>> getLabels(Entity entity) {
		Map<Language, Collection<String>> labelMap = new HashMap<Language, Collection<String>>();
		for (AnnotationProperty annoProp : entity.getAnnotations().keySet()) {
			if (isXBRLLabel(annoProp)) {
				for (AnnotationValue annoValue : entity.getAnnotationValues(annoProp)) {
                    if (annoValue instanceof LiteralValue) {
                        LiteralValue value = (LiteralValue) annoValue;
                        Language lang = value.getLanguage() == null ? LabelExtractor.NO_LANGUAGE : value.getLanguage();
                        Collection<String> labels;
                        if (!labelMap.containsKey(lang))
                        	labels = new HashSet<String>();
                        else
                        	labels = labelMap.get(lang);
                        // remove properties in label
                        String labelValue = value.getValue();
                        labelValue = labelValue.replaceAll("( ){0,1}\\[.*\\]$", "");
                        labels.add(labelValue);
                        labelMap.put(lang, labels);
                    }
                }
			}
		}
		if (entity instanceof Individual) {
			// TODO
		}
		return labelMap;
	}*/


}