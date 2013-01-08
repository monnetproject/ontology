package eu.monnetproject.label.textcat;

import aQute.bnd.annotation.component.*;
import eu.monnetproject.label.*;
import eu.monnetproject.lang.*;
import org.knallgrau.utils.textcat.*;

@Component(provide=LanguageInferrer.class)
public class TextCatInferrer implements LanguageInferrer {
	private final TextCategorizer tc = new TextCategorizer();
	
	public Language getLang(String label) {
		String l = tc.categorize(label);
		if(l.equals("unknown")) {
			return null;
		} else if(l.equals("albanian")) {
			return Language.ALBANIAN;
		} else if(l.equals("danish")) {
			return Language.DANISH;
		} else if(l.equals("dutch")) {
			return Language.DUTCH;
		} else if(l.equals("english")) {
			return Language.ENGLISH;
		} else if(l.equals("finnish")) {
			return Language.FINNISH;
		} else if(l.equals("french")) {
			return Language.FRENCH;
		} else if(l.equals("german")) {
			return Language.GERMAN;
		} else if(l.equals("hungarian")) {
			return Language.HUNGARIAN;
		} else if(l.equals("italian")) {
			return Language.ITALIAN;
		} else if(l.equals("norwegian")) {
			return Language.NORWEGIAN;
		} else if(l.equals("polish")) {
			return Language.POLISH;
		} else if(l.equals("slovenian")) {
			return Language.SLOVENE;
		} else if(l.equals("slovakian")) {
			return Language.SLOVAK;
		} else if(l.equals("spanish")) {
			return Language.SPANISH;
		} else if(l.equals("swedish")) {
			return Language.SWEDISH;
		} else {
			throw new RuntimeException("Unexpected return from TextCat " + l + " treating as unknown");
		}
	}
}

