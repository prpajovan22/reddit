package com.ftn.reddit.lucene.filter;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

public class CirilicaToLatinicaFilter extends TokenFilter{

    private final CharTermAttribute termAttribute;

    public CirilicaToLatinicaFilter(TokenStream input) {
        super(input);
        termAttribute = input.addAttribute(CharTermAttribute.class);
    }
    @Override
    public boolean incrementToken() throws IOException {
        if (input.incrementToken()) {
            String text=termAttribute.toString();
            termAttribute.setEmpty();
            termAttribute.append(CirilicaToLatinicaConverter.cir2lat(text));
            return true;
        }
        return false;
    }
}
