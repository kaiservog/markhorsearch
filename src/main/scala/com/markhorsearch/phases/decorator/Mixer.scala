package com.markhorsearch.phases.decorator

import com.markhorsearch.phases.core.FileWriter
import com.markhorsearch.phases.core.PhaseCommon
import com.markhorsearch.phases.core.Processor
import com.markhorsearch.phases.collector.UrlNormalizer

class Mixer extends Processor {

  def process(common: PhaseCommon) = {
    var content = ""
    
    common.documents.foreach(d => {
        val links = for(sites <- d.relevantSites) yield sites.url
        val imgs = d.images
        
        val c = d.content.text + links.mkString(" X ") 
        if(c.trim().length() > 50) 
        	content = content + c + " " + d.favicon.getOrElse("FUCKKKK") + "<hr/>"
      })
      
      common.content = content
  }
}