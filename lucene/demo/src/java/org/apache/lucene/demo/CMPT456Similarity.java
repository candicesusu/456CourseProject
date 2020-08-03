package org.apache.lucene.demo;

import java.lang.Math; 
import org.apache.lucene.search.similarities.ClassicSimilarity;


public class CMPT456Similarity extends ClassicSimilarity {
  
  /**
   * Sole constructor. (For invocation by subclass 
   * constructors, typically implicit.)
   */
  public CMPT456Similarity() {
      super();
  }
  
  @Override
  public float tf(float freq) {
      return (float)Math.sqrt(1+freq);
  }
  
  @Override
  public float idf(long docFreq, long docCount) {
    return (float)(Math.log((docCount+2)/(double)(docFreq+2)) + 1.0);
  }

  
  
}
