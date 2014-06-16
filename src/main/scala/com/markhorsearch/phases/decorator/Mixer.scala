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
        val c = d.content.text + links.mkString(" X ") 
        
    	content = content + c + " " + d.favicon.getOrElse("FUCKKKK") + "<hr/>"
      })
      
    
    new FileWriter("C:\\Users\\kaiser\\Desktop\\markhorTest", content).write
  }
}