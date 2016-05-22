package com.inferess;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.inferess.Parser;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Unit test for simple App.
 */
public class ParserTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ParserTest( String testName )
    {
        super( testName ); 
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ParserTest.class );
    }

    
    public void testGetDocCount(){
      Parser p = new Parser();
      ArrayList<HashMap<String,Integer>> docs = new ArrayList<HashMap<String,Integer>>();
      String word ="testword";
      
      HashMap<String,Integer> map1 = new HashMap<String,Integer>();
      map1.put("testword",2);
      
      HashMap<String,Integer> map2 = new HashMap<String,Integer>();
      map2.put("test",20);
      map2.put("program",5);
      
      HashMap<String,Integer> map3 = new HashMap<String,Integer>();
      map3.put("testing",2);
      map3.put("program",5);
      map3.put("computer",5);
      
      HashMap<String,Integer> map4 = new HashMap<String,Integer>();
      map4.put("program",5);
      map4.put("testword",5);
      
      HashMap<String,Integer> map5 = new HashMap<String,Integer>();
      map5.put("computer",3);
      map5.put("program",5);
      
      docs.add(map1);
      docs.add(map2);
      docs.add(map3);
      docs.add(map4);
      docs.add(map5);
      
      int output = 2;
      int actual_output = p.getDocCount(docs,word);
     // System.out.println("################## Actual output - "+actual_output);
      assert(output == actual_output);
    }
}
