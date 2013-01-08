/**********************************************************************************
 * Copyright (c) 2011, Monnet Project
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Monnet Project nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE MONNET PROJECT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *********************************************************************************/
package eu.monnetproject.label.impl;

import eu.monnetproject.label.LabelExtractor;
import eu.monnetproject.label.LanguageInferrer;
import eu.monnetproject.lang.Language;
import eu.monnetproject.ontology.Entity;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author John McCrae
 */
public class AggregateLabelExtractor implements LabelExtractor {
    private final List<LabelExtractor> leps;
    private final LanguageInferrer inferrer;
    private final boolean union;
    
    public AggregateLabelExtractor(List<LabelExtractor> leps, boolean union) {
        this.leps = leps;
        this.inferrer = null;
        this.union = union;
    }

    
    
    public AggregateLabelExtractor(List<LabelExtractor> leps, LanguageInferrer inferrer) {
        this.leps = leps;
        this.inferrer = inferrer;
        this.union = false;
    }

    public AggregateLabelExtractor(List<LabelExtractor> leps, LanguageInferrer inferrer, boolean union) {
        this.leps = leps;
        this.inferrer = inferrer;
        this.union = union;
    }
    
    

    @Override
    public Map<Language, Collection<String>> getLabels(Entity entity) {
        Map<Language,Collection<String>> rv = null;
        if(union) {
            rv = new HashMap<Language, Collection<String>>();
        }
        for (LabelExtractor lep : leps) {
            Map<Language, Collection<String>> result = lep.getLabels(entity);
            if (result != null && !result.isEmpty()) {
                if (inferrer != null && result.containsKey(LabelExtractor.NO_LANGUAGE)) {
                    Map<Language, Collection<String>> result2 = new HashMap<Language, Collection<String>>();
                    for (Map.Entry<Language, Collection<String>> e : result.entrySet()) {
                        if (e.getKey().equals(LabelExtractor.NO_LANGUAGE)) {
                            for (String label : e.getValue()) {
                                Language newLang = inferrer.getLang(label);
                                if (newLang == null) {
                                    newLang = LabelExtractor.NO_LANGUAGE;
                                }
                                if (!result2.containsKey(newLang)) {
                                    result2.put(newLang, new LinkedList<String>());
                                }
                                result2.get(newLang).add(label);
                            }
                        } else {
                            if (!result2.containsKey(e.getKey())) {
                                result2.put(e.getKey(), e.getValue());
                            } else {
                                result2.get(e.getKey()).addAll(e.getValue());
                            }
                        }
                    }
                    if(union) {
                        rv.putAll(result2);
                    } else {
                        return result2;
                    }
                } else {
                    if(union) {
                        rv.putAll(result);
                    } else {
                        return result;
                    }
                }
            }
        }
        if(union) {
            return rv;
        } else {
            return Collections.EMPTY_MAP;
        }
    }
    
}
