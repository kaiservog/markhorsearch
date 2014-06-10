package com.markhorsearch.phases.decorator

import com.markhorsearch.phases.core.FileWriter
import com.markhorsearch.phases.core.PhaseCommon
import com.markhorsearch.phases.core.Processor

class Mixer extends Processor {

  def process(common: PhaseCommon) = {
    var content = ""
    
    common.documents.foreach(d => { 
        val c = d.content.text 
        
    	content = content + c + "<hr />"+d.site.provider + "\n" + d.links+"<hr />"
      })
      
    
    new FileWriter("C:\\Users\\kaiser\\Desktop\\markhorTest", content).write
  }
}