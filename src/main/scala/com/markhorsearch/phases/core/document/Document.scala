package com.markhorsearch.phases.core.document

import scala.xml.Node
import scala.xml.NodeSeq
import com.markhorsearch.phases.core.Site

class Document(val content: List[Node]	, val title: String, var cleaned: Boolean, val empty: Boolean = false) {
    def relevantSites: List[Site] = {
      val links = for(elm <- content) yield (elm \\ "a")

      val trueLinks = links.filter(l => {
          if(l.size < 1) false
          else {
	    	  val href: String = l(0).attribute("href").getOrElse("").toString
	          href.size > 5 && "#".r.findFirstIn(href).isEmpty
          }
      })
      
      val newLinks = trueLinks.distinct.dropRight(3)
      
      for(link <- newLinks) 
        yield new Site(link(0).attribute("href").getOrElse("").toString,
        				link(0).attribute("href").getOrElse("").toString,
        				Option.empty[String])
	}

}
 object Document {
   val empty = { new Document(List.empty[Node], "", false, true) }
 }