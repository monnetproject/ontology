package eu.monnetproject.label.uri;

import aQute.bnd.annotation.component.*;
import eu.monnetproject.lang.Language;
import eu.monnetproject.label.*;
import eu.monnetproject.ontology.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

//@Component(provide=LabelExtractor.class,properties="strategy=fallback")
public class URILabelExtractionPolicy implements LabelExtractor {

	public Map<Language,Collection<String>> getLabels(final Entity entity) {
		URI uri = entity.getURI();
		if(uri == null) {
			return Collections.EMPTY_MAP;
		} else {
			String uriStr = uri.toString();
			if(uriStr.lastIndexOf('#') > 0) {
				return processFrag(uriStr.substring(uriStr.lastIndexOf('#')+1));
			} else if(uriStr.lastIndexOf('/') > 0) {
				return processFrag(uriStr.substring(uriStr.lastIndexOf('/')+1));
			} else {
				return Collections.EMPTY_MAP;
			}
		}
	}
	
	private Map<Language,Collection<String>> processFrag(final String frag) {
		try {
			// First decode the URI, to get rid of any "+" or "%XX" strings 
			String cleanFrag = URLDecoder.decode(frag,"UTF-8");
			
			// If it contains _ assume this is the separator
			if(cleanFrag.contains("_")) {
				cleanFrag = cleanFrag.replaceAll("_"," ");
			// Otherwise it is probably camel-capped
			} else {
				cleanFrag = cleanFrag.replaceAll("([a-z])([A-Z])","$1 $2");
				StringBuffer sb = new StringBuffer(cleanFrag);
				Matcher m = Pattern.compile("[a-z] ([A-Z])").matcher(sb);
				while(m.find()) {
					sb.replace(m.start(1),m.end(1),m.group(1).toLowerCase());
				}
				cleanFrag = sb.toString();
			}
			return Collections.singletonMap(LabelExtractor.NO_LANGUAGE,
				(Collection<String>)Collections.singleton(cleanFrag));
		} catch(java.io.UnsupportedEncodingException x) {
			x.printStackTrace();
			throw new RuntimeException(x);
		}
	}
}
