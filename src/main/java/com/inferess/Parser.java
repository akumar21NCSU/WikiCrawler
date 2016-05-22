package com.inferess;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.*;
import com.inferess.Calculator;

public class Parser {

  private String urls[];
  private ArrayList<String> titles;
  
  public Parser(){
    urls = new String[5];
    urls[0] = "https://en.wikipedia.org/wiki/Coldplay";
		urls[1] = "https://en.wikipedia.org/wiki/Apache_Spark";
		urls[2] = "https://en.wikipedia.org/wiki/Apache_Hive";
		urls[3] = "https://en.wikipedia.org/wiki/Roger_Federer";
		urls[4] = "https://en.wikipedia.org/wiki/Apache_HBase";
    
    titles = new ArrayList<String>();
    titles.add("Coldplay");
    titles.add("Apache Spark");
    titles.add("Apache Hive");
    titles.add("Roger Federer");
    titles.add("Apache HBase");
  }
  
  public String[] getUrls(){
    return urls;
  }
  public ArrayList<String> getTitles(){
    return titles;    
  }

	public HashMap<String,Integer> getCount(Elements paragraphs, ArrayList<Integer> lengths){
		
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		for(Element p : paragraphs){
      String text = p.text();
      text =  text.replace(',', ' ' );
      text = text.replace('.', ' ' );      
     
      String[] arr = text.split(" +");
      lengths.add(arr.length);     
      for(String word: arr){
        word = word.toLowerCase();
        if(map.containsKey(word)){
          int count = map.get(word);
          count = count+1;
          map.put(word, count);
        }else{
          map.put(word,1);
        }
      }
    }		
		return map;
	}
	
  public  int getDocCount(ArrayList<HashMap<String,Integer>> docs, String word){
    int count=0;
    for(HashMap<String,Integer> doc : docs){
      if(doc.get(word) != null)
        count++;
    }
    return count;
  }
  
  public  void printResults(String word, ArrayList<Double> results, ArrayList<String> titles, String[] urls){
    System.out.println("For query- '"+word +"' list of Wikipedia page Titles with Urls (with highest score first) - ");
    
    for(int itr=0;itr<results.size();itr++){
      
      int maxIndex =itr;
      double maxResult =-1.0;      
      for(int i=0;i<results.size();i++){
        if(results.get(i)> maxResult){
          maxIndex = i;
          maxResult = results.get(i);
        }
        
      }
      System.out.println("Wiki Title - "+titles.get(maxIndex)+  "     "+urls[maxIndex] );
      results.set(maxIndex, -1.0);
    }
    
    
  }
  
  public void process(String[] urls, String[] queries,ArrayList<HashMap<String,Integer>> docs, ArrayList<String> titles) throws IOException{
    ArrayList<Integer> lengths = new ArrayList<Integer>();
    
		for(String url: urls){
			  Document doc = Jsoup.connect(url).get();
		    Elements paragraphs = doc.select("body");
		    HashMap<String,Integer> map = getCount(paragraphs,lengths);		    
		    docs.add(map);
		}
		
	  for(String q : queries){
      double maxTfIdf = -1.0;
      int maxTfIdfIndex = -1;
      ArrayList<Double> results = new ArrayList<Double>();
      
      for(int i=0;i<docs.size();i++){
        HashMap<String,Integer> m = docs.get(i);
        int freq=0;
        if(m.get(q) != null)
          freq = m.get(q);
        int totalWords = lengths.get(i);
        int noOfDocs = getDocCount(docs,q);        
        double result = new Calculator().tfIdf(freq, totalWords, urls.length, noOfDocs);
        //System.out.println("For title "+ titles.get(i)+" result =  "+result);
        results.add(result);               
      }
      printResults(q,results,titles,urls);
      System.out.println();
    }
    
  }
  
	public static void main(String[] args) throws IOException{
		    
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));       
    System.out.println("Enter your query words ");
    String query = br.readLine().trim();
    if(query.equals("")){
      System.out.println("Enter atleast one query word");
      return;
    }
    String[] queries = query.split(" +");    
    if(queries.length > 5){
      System.out.println("You can query only upto 5 words.");
      return;
    }
    
    Parser newParser = new Parser();		
		ArrayList<HashMap<String,Integer>> docs = new ArrayList<HashMap<String,Integer>>();     
    newParser.process(newParser.getUrls(),queries,docs,newParser.getTitles());    
           
	}

}
