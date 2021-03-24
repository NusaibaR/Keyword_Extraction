// Nusaiba Radi 260672575

import java.util.*;
import java.io.*;

public class KeywordExtractor {
  public static void main(String[] args) {
    String dir = args[0]; // name of directory with input files
    HashMap<String, Integer> dfs;
    dfs = readDocumentFrequencies("freqs.txt");
    // for loop to go through the 40 files
    for(int i=1;i<=40;i++){
      HashMap<String, Integer> tfs;
      tfs = computeTermFrequencies(dir +"/"+i+".txt");
      HashMap<String, Double> tf_idf;
      // calling the compute tfidf method with the hashmap from the two other method
      tf_idf = computeTFIDF(tfs,dfs,40);
      // printing the file name
      System.out.println(i+".txt");
      // printing the top 5 words
      printTopKeywords(tf_idf,5);
    }
  }
  
  public static HashMap<String, Integer> computeTermFrequencies(String filename) {
    HashMap<String,Integer> wordFreq = new HashMap<String, Integer>();
    //reading the documents
    try{
      FileReader fr = new FileReader(filename);
      BufferedReader br = new BufferedReader(fr);
      String line = br.readLine();
      
      while(line!=null){
        // spliting
        String[] parts = line.split(" ");
        
        String normline = null;
        int value = 1;
        
        for(int i=0; i<parts.length;i++){
          // calling the normalize method from the other file
          normline = DocumentFrequency.normalize(parts[i]);
          // if the String is not empty will add it to the hashmap
          if(!normline.equals("")){
            // if the world already in the hashmap we increse the frequncy
            if(wordFreq.containsKey(normline)){
              value = wordFreq.get(normline)+1;
              wordFreq.put(normline,value);
            }
            // if it appear for the first time we add it with frequency one
            else{
              wordFreq.put(normline,1);
            }
          }
        }
        line = br.readLine();
      }
      
      br.close();
      fr.close();
      
    }catch(IOException e){
      System.out.println(e);
    }
    
    
    return wordFreq;
  }
  
  public static HashMap<String, Integer> readDocumentFrequencies(String filename) {
    HashMap<String,Integer> wordFreq = new HashMap<String, Integer>();
    // reading the frec file that we wrote
    try{
      FileReader fr = new FileReader(filename);
      BufferedReader br = new BufferedReader(fr);
      String line = br.readLine(); 
      
      while(line!=null){
        
        // spliting the string 
        String[] parts = line.split(" ");
        String key = parts[0];
        String value =  parts[1];
        int freq = Integer.parseInt(value);
        
        // adding the key and it's value to the hashmap 
        wordFreq.put(key,freq);  
        
        line = br.readLine();
      }
      
      br.close();
      fr.close();
    }catch(IOException e){
      System.out.println(e);
    }
    return wordFreq;
  }
  
  public static HashMap<String, Double> computeTFIDF(HashMap<String, Integer> tfs, HashMap<String, Integer> dfs, 
                                                     double nDocs) {
    
    HashMap<String, Double> tf_idf = new HashMap<String, Double>();
    
    //looping through the hashmap with sorted key 
    // using map.entery which give us the (key,value) pairs
    for(Map.Entry<String,Integer> pairs : tfs.entrySet()){
      //  find tf from term frequncies(tfs)
      int tf = pairs.getValue();
      String  word =pairs.getKey();
      // finding idf from document frequency(dfs)     
      double idf = Math.log(nDocs/dfs.get(pairs.getKey()));
      
      double tfidf = (tf)*(idf);
      // adding each key with it's tfidf score(in order)
      tf_idf.put(pairs.getKey(),tfidf);
    }
    return tf_idf;
  }
  
  /**
   * This method prints the top K keywords by TF-IDF in descending order.
   */
  public static void printTopKeywords(HashMap<String, Double> tfidfs, int k) {
    ValueComparator vc =  new ValueComparator(tfidfs);
    TreeMap<String, Double> sortedMap = new TreeMap<String, Double>(vc);
    sortedMap.putAll(tfidfs);
    
    int i = 0;
    for(Map.Entry<String, Double> entry: sortedMap.entrySet()) {
      String key = entry.getKey();
      Double value = entry.getValue();
      
      System.out.println(key + " " + value);
      i++;
      if (i >= k) {
        break;
      }
    }
  }  
}

/*
 * This class makes printTopKeywords work. Do not modify.
 */
class ValueComparator implements Comparator<String> {
  
  Map<String, Double> map;
  
  public ValueComparator(Map<String, Double> base) {
    this.map = base;
  }
  
  public int compare(String a, String b) {
    if (map.get(a) >= map.get(b)) {
      return -1;
    } else {
      return 1;
    } // returning 0 would merge keys 
  }
}