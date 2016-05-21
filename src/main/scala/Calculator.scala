package com.inferess

class Calculator {  

  def tfIdf(freq: Int, totalWords: Int,totalDocs: Int, nDocs: Int ): Double ={
    
    def tf(): Double = {
           
      return freq.toDouble/totalWords
    }
    def itf():Double = {
           
      return Math.log((totalDocs.toDouble/nDocs))
    }
    
    var t = tf()
    var i = itf()
    //println("t = "+t +" and i = "+i)
    return t*i    
  }
}

