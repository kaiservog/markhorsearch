package com.markhorsearch.phases.core.document

import scala.xml.Node
import scala.xml.NodeSeq
import com.markhorsearch.phases.core.Site
import com.markhorsearch.phases.collector.UrlNormalizer

class Document(val content: List[Node], val title: String, var cleaned: Boolean, val site: Site, val empty: Boolean = false) {
    val images: NodeSeq = content \\ "img"
    val links: NodeSeq = content \\ "a"
    var tables: NodeSeq = content \\ "table"
    var favicon: Option[String] = Option.empty[String]
    
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
        yield new Site(UrlNormalizer(link.attribute("href").getOrElse("").toString, site.provider),
        				link.attribute("href").getOrElse("").toString,
        				Option.empty[String])
      sites toList
	}
    
    def newDocument(content: List[Node], cleaned: Boolean): Document = {
      val neo = new Document(content, this.title, cleaned, this.site)
      neo.tables = this.tables
      neo
    }
}
 object Document {
   val siteEmpty = new Site("", "", Option.empty)
   val empty = { new Document(List.empty[Node], "", false, siteEmpty, true) }
 }