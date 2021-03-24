// Nusaiba Radi 260672575

import java.util.*;
import java.io.*;

public class DocumentFrequency {
  // needed to make newline since "\n" isn't working for me
  public static String newline = System.getProperty("line.separator");
  
  public static void main(String[] args) {
    String dir = args[0]; // name of directory with input files
    HashMap<String, Integer> dfs;
    dfs = extractDocumentFrequencies(dir, 40);
    writeDocumentFrequencies(dfs, "freqs.txt");
  }
  
  public static HashMap<String, Integer> extractDocumentFrequencies(String directory, int nDocs) {
    HashMap<String, Integer> frequency = new HashMap<String, Integer>();
    
    HashSet<String> keys ;
    
    // a for loop to process each file in the directory
    for (int i = 1; i <= nDocs; i++){
      keys = extractWordsFromDocument(directory+"/"+i+".txt");
      
      // foe each loop to through the hashSet elements
      for(String key: keys){   
        
        // if the word already there increase the value
        if(frequency.containsKey(key)){
          int value = frequency.get(key);
          value++;
          frequency.put(key, value);
        }
        // word appers for first time
        else{
          frequency.put(key,1);
        }
      }  
    }
    return frequency;
  }
  
  
  public static HashSet<String> extractWordsFromDocument(String filename) {
    HashSet<String> words = new HashSet<String>();
    // reading the file
    try{
      FileReader fr = new FileReader(filename);
      BufferedReader br = new BufferedReader(fr);
      String line = br.readLine(); 
      
      while(line!=null){
        
        // spliting the word 
        String[] parts = line.split(" ");
        
        String normline = null;
        
        for(int i=0; i<parts.length; i++){
          
          //get rid of the whitespace and converting into lowercase
          normline = normalize(parts[i]);
          
          // adding the word the hashset if it's not  empty 
          if( normline.length() != 0 ){
            words.add(normline);
          }
        }
        line = br.readLine();
      }
      br.close();
      fr.close();
      
    }catch(IOException e){
      System.out.println(e);
    }
    return words;
  }
  
  
  public static void writeDocumentFrequencies(HashMap<String, Integer> dfs, String filename) {
    
    HashMap<String,Integer> words = new HashMap<String,Integer>();
    // writing the file
    try{
      FileWriter fw = new FileWriter(filename);
      BufferedWriter bw = new BufferedWriter(fw);
      
      //convert the hashmap keyes to arrayList
      // sorting the keyes
      List<String> sortedKeys = new ArrayList<String>(dfs.keySet());
      // List sortedKeys=new ArrayList(freq.keySet());
      Collections.sort(sortedKeys);
      
      // foe each loop to go through the sorted keys /
      // writing each key with it's own value 
      for (String key : sortedKeys) {
        bw.write(key + " " + dfs.get(key) + newline  );
      }
      bw.close();
      fw.close();
    }catch(IOException e){
      System.out.println(e);
    }  
  }
  
  /*
   * This method "normalizes" a word, stripping extra whitespace and punctuation.
   * Do not modify.
   */
  public static String normalize(String word) {
    return word.replaceAll("[^a-zA-Z ']", "").toLowerCase();
  }
  
}