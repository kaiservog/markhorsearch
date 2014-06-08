package com.markhorsearch.phases.cleaner

import scala.collection.mutable.ListBuffer
import scala.xml.Node
import scala.xml.NodeSeq
import scala.xml.transform.RewriteRule
import scala.xml.transform.RuleTransformer
import com.markhorsearch.phases.core.document.Document
import com.markhorsearch.phases.core.document.Document
import scala.xml.Text
import com.markhorsearch.phases.core.Processor
import com.markhorsearch.phases.core.PhaseCommon
 
class HtmlCleaner extends Processor {
  val phraseRegex = """(\w*?\s.*?){5,}""".r
  val ec = new ElementClassifier
  

  def process(common: PhaseCommon) = {
    val docsToClean = common.documents.filter(shouldProcess)
    val docsCleaned = common.documents.filter(d => d.cleaned == true)

    val newDocs = for(site <- docsToClean) yield cleanSite(site)

    common documents = docsCleaned ::: newDocs
    common sites = List.empty
  }
  
  def shouldProcess(d: Document) = d.content.size > 0 && d.cleaned == false
  
  private def cleanSite(site: Document): Document = {
     val remove = new RewriteRule {
       override def transform(n: Node): NodeSeq = {
         n.label.trim match {
           case "script" => NodeSeq.Empty
           case "style" => NodeSeq.Empty
           case _ => n
         }
       }
     }

     val newNode = new RuleTransformer(remove)(site.content.head)
     val stay = ListBuffer[Node]()
     populeText(newNode, Option.empty, stay)
     
     new Document(stay toList, site.title, true, site.site)
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
  
  def enWords = List("is", "and", "or", "was", "it", "were", "when", "the")
  def ptWords = List("e", "ou", "era", "isso", "isto", "a", "o")
}