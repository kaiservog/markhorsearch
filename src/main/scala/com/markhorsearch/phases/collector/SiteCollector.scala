package com.markhorsearch.phases.collector

import java.net.URL
import scala.xml.Node
import com.markhorsearch.phases.core.PhaseCommon
import com.markhorsearch.phases.core.Processor
import com.markhorsearch.phases.core.Site
import com.markhorsearch.phases.core.document.Document
import com.markhorsearch.phases.core.document.Document
import com.markhorsearch.phases.core.document.HtmlParserMarkhor
import scala.collection.mutable.ListBuffer

class SiteCollector extends Processor {
	
  def process(common: PhaseCommon) = {
    var relevantSites = List[Site]()
    
    for(document <- common.documents) 
        relevantSites = relevantSites  ::: document.relevantSites
    
    val sites = common.sites ++ relevantSites
    val docs = for(site <- sites) yield getSite(site)
    common.documents = common.documents ::: (docs filter hasContent)
    
    println("docs"+common.documents)
  }
  
  private def hasContent(d: Document) ={
	  d.content.size > 0 && d.empty == false
  }
  
  private def getSite(site: Site): Document = {
    var doc = Document.empty
    
    try {
    	val page: Node = new HtmlParserMarkhor().load(site.url)

    	doc = new Document(List(page), 
    			site.title.getOrElse((page \\ "title") mkString), false, site)
    }catch {
    	case e: Exception => println(e.getMessage())
    }
    doc
  }
}