/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.lucene.demo;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Paths;
import java.io.InputStream;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.standard.StandardFilter;

public final class CMPT456Analyzer extends StopwordAnalyzerBase {

  /** Default maximum allowed token length */
  public static final int DEFAULT_MAX_TOKEN_LENGTH = 255;
  private static String FILE_PATH = "lucene/demo/src/java/org/apache/lucene/demo/stopwords.txt";
  private int maxTokenLength = DEFAULT_MAX_TOKEN_LENGTH;

  public CMPT456Analyzer(CharArraySet stopWords) {
    super(stopWords);
  }

  public CMPT456Analyzer() throws IOException {
      this(loadStopwordSet(Paths.get(FILE_PATH)));
  }

  public void setMaxTokenLength(int length) {
    maxTokenLength = length;
  }

  public int getMaxTokenLength() {
    return maxTokenLength;
  }

  @Override
  protected TokenStreamComponents createComponents(final String fieldName) {
    final StandardTokenizer src = new StandardTokenizer();
    src.setMaxTokenLength(maxTokenLength);
    TokenStream tok = new StandardFilter(src);
    tok = new LowerCaseFilter(tok);
    tok = new StopFilter(tok, stopwords);
    tok = new PorterStemFilter(tok);
    return new TokenStreamComponents(src, tok) {
      @Override
      protected void setReader(final Reader reader) {
        src.setMaxTokenLength(CMPT456Analyzer.this.maxTokenLength);
        super.setReader(reader);
      }
    };
  }

  @Override
  protected TokenStream normalize(String fieldName, TokenStream in) {
      TokenStream result = new StandardFilter(in);
      return new LowerCaseFilter(result);
  }
}
