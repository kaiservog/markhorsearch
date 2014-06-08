package com.markhorsearch.phases.core.document

import scala.xml.Node
import scala.xml.NodeSeq
import com.markhorsearch.phases.core.Site

class Document(val content: List[Node], val title: String, var cleaned: Boolean, val site: Site, val empty: Boolean = false) {
    val images: NodeSeq = content \\ "img"
    val links: NodeSeq = content \\ "a"
    
    def relevantSites: List[Site] = {
      val links = content \\ "a"
      val trueLinks = links.filter(l => {
    	  val href: String = l.attribute("href").getOrElse("").toString
          href.size > 5 && "#".r.findFirstIn(href).isEmpty
      })
      
      var newLinks: NodeSeq = NodeSeq.Empty
      
      if(trueLinks.size > 3)
    	  newLinks = trueLinks.slice(0, 3)
      else
          newLinks = trueLinks
      
      val sites = for(link <- newLinks) 
        yield new Site(link.attribute("href").getOrElse("").toString,
        				link.attribute("href").getOrElse("").toString,
        				Option.empty[String])
      sites toList
	}
}
 object Document {
   val siteEmpty = new Site("", "", Option.empty)
   val empty = { new Document(List.empty[Node], "", false, siteEmpty, true) }
 }