package eu.monnetproject.label.impl;

import aQute.bnd.annotation.component.*;
import eu.monnetproject.label.*;
import eu.monnetproject.label.custom.*;
import eu.monnetproject.label.rdf.*;
import eu.monnetproject.label.skos.*;
import eu.monnetproject.label.textcat.*;
import eu.monnetproject.label.uri.*;
import eu.monnetproject.label.xbrl.XBRLLabelExtractionPolicy;
import eu.monnetproject.lang.*;
import eu.monnetproject.ontology.*;
import eu.monnetproject.util.Logging;

import java.net.*;
import java.util.*;
import eu.monnetproject.util.Logger;

@Component(provide = LabelExtractorFactory.class)
public class LabelExtractorFactoryImpl implements LabelExtractorFactory {

    private Logger log = Logging.getLogger(this);

    @Override
    public LabelExtractor getExtractor(Collection<URI> extraURIs, boolean fallback, boolean inferLang) {
        List<LabelExtractor> extractors = new LinkedList<LabelExtractor>();
        if (extraURIs != null) {
            for (URI uri : extraURIs) {
                extractors.add(new CustomLabelExtractionPolicy(uri));
            }
        }
        extractors.add(new RDFLabelExtractionPolicy());
        extractors.add(new SKOSLabelExtractionPolicy());
        extractors.add(new XBRLLabelExtractionPolicy());
        final TextCatInferrer inferrer = inferLang ? new TextCatInferrer() : null;
        if (fallback) {
            return new AggregateLabelExtractor(Arrays.asList(new AggregateLabelExtractor(extractors, inferrer, true), new URILabelExtractionPolicy()), inferrer);
        } else {
            return new AggregateLabelExtractor(extractors, inferrer, true);
        }
    }

    @Override
    public LabelExtractor getExtractor(List<LabelExtractor> extractors, LanguageInferrer inferrer) {
        return new AggregateLabelExtractor(extractors, inferrer);
    }
}
