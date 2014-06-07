package com.markhorsearch.phases.cleaner

import scala.xml.Node

class ElementClassifier {
  val trashWords = List("menu", "nav", "navigation","ads", "commercial", "propaganda", "buy", "sell")
  val contentPattern = """[\w\W].*?\s{4}""".r
  val minimal = 15
  
  val rankingList: List[((Node, List[String]) => Boolean, Int)] = List(
      ((elem: Node,l: List[String]) =>  elem.size > 2, -2),
      ((elem: Node,l: List[String]) =>  elem.text.contains(trashWords), -8),
      ((elem: Node,l: List[String]) =>  contentPattern.findFirstIn(elem.text).isDefined, 20),
      ((elem: Node,l: List[String]) =>  (elem \\ "ul").size > 0, -3),
      ((elem: Node,l: List[String]) =>  (elem \\ "a").size >= 4, -5),
      ((elem: Node,l: List[String]) =>  elem.text.contains(l), 35))
  
  
  def remove(elem: Node, superWords: List[String]): Boolean = {
      var total = 0
      
	  rankingList.foreach(rl => {
	    try {
		    if(rl._1(elem,superWords) == true) 
		      total += rl._2
	    }catch {
	      case e:Exception => println("nada")
	    }
	  })
	  println("total "+total)
	  total < minimal
  }
}

