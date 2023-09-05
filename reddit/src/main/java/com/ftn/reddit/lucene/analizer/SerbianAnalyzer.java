package com.ftn.reddit.lucene.analizer;

import com.ftn.reddit.lucene.filter.CirilicaToLatinicaFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ReusableAnalyzerBase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;


import java.io.Reader;

public class SerbianAnalyzer extends Analyzer {

    @Override
    public TokenStream tokenStream(String s, Reader reader) {
        return null;
    }
}
