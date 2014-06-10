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
import com.markhorsearch.phases.cleaner.types.Cleaner
import com.markhorsearch.phases.core.Site
import com.markhorsearch.phases.cleaner.types.WikiCleaner
import com.markhorsearch.phases.cleaner.types.WikiCleaner

class HtmlCleaner extends Processor {
  val ec = new ElementClassifier

  def process(common: PhaseCommon) = {
    val docsToClean = common.documents.filter(shouldProcess)
    val docsCleaned = common.documents.filter(d => d.cleaned == true)

    val newDocs = for(site <- docsToClean) yield cleanSite(site)

    common documents = docsCleaned ::: newDocs
    common sites = List.empty
  }

  def shouldProcess(d: Document) = d.content.size > 0 && d.cleaned == false
  
  private def cleanSite(document: Document): Document = {
    val wiki = "\\w\\w\\.wikipedia\\.org"
    var cleaner: Cleaner = null
    if(document.site.provider.matches(wiki))
    {
    	cleaner = new WikiCleaner
    	println("wiki cleaner")
    }
    else
    {
    	cleaner = new Cleaner
    }	
    cleaner.cleanSite(document)
  }
  
}