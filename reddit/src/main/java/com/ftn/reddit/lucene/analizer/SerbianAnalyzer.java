package com.ftn.reddit.lucene.analizer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;


import java.io.Reader;

public class SerbianAnalyzer extends Analyzer {

    @Override
    public TokenStream tokenStream(String s, Reader reader) {
        return null;
    }
}
