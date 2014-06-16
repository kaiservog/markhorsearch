package com.markhorsearch.phases.cleaner.types

import scala.xml.transform.RuleTransformer
import scala.xml.transform.RewriteRule
import scala.xml.NodeSeq
import scala.collection.mutable.ListBuffer
import com.markhorsearch.phases.core.document.Document
import scala.xml.Node
import scala.xml.Text

class Cleaner {
  val phraseRegex = """(\w*?\s.*?){5,}""".r

  def cleanSite(document: Document): Document = {
     val remove = new RewriteRule {
       override def transform(n: Node): NodeSeq = {
         n.label.trim match {
           case "script" => NodeSeq.Empty
           case "style" => NodeSeq.Empty
           case _ => n
         }
       }
     }

     val newNode = new RuleTransformer(remove)(document.content.head)
     val stay = ListBuffer[Node]()
     populeText(newNode, Option.empty, stay)

     val neo = document.newDocument(stay toList, true)
     neo.favicon = favicon(document)
     neo
  }
  
  private def populeText(node: Node, parent: Option[Node], stay: ListBuffer[Node]) {
        node.label.trim match {
          case "script" => return
          case "style" => return
          case "img" => stay += node; return
          case _ => 
        }
		
        val nodeText = node.text
        val nodeWords = nodeText.split(" ").toList
        
        if (node.isInstanceOf[Text] &&
            phraseRegex.findFirstIn(nodeText).isDefined &&
		    similarity(nodeWords) > 1) {
			if(parent.isDefined) stay += parent.get
		}
        
    	node.child.foreach(c => populeText(c, Option(node), stay))
  }
  
  private def similarity(words: List[String]) = {
    val en = enWords.filter(w => words.contains(w))
    val pt = ptWords.filter(w => words.contains(w))
    
    en.size + pt.size
  }
 
  def favicon(d: Document): Option[String] = {
      val node: NodeSeq = d.content \\ "link"
      val rightNode = node filter( n=> {
        val rel = n.attribute("rel").getOrElse("").toString
        
        rel == "shortcut icon" || rel == "icon" 
      })
      
      if(rightNode.size > 0) 
      {
        var src = rightNode(0).attribute("href").getOrElse("").toString
        if(src.substring(0, 2) == "//") src = "http://"+src.substring(2)
        else if(src.substring(0, 1) == "/") src = "http://"+d.site.provider+src
        Option("""<img src="%s" />""".format(src))
      }
      else
      {
        Option.empty[String]
      }
  }
  
  def enWords = List("is", "and", "or", "was", "it", "were", "when", "the")
  def ptWords = List("e", "ou", "era", "isso", "isto", "a", "o")
}