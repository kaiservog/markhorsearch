package com.markhorsearch.phases.decorator

import com.markhorsearch.phases.core.FileWriter
import com.markhorsearch.phases.core.PhaseCommon
import com.markhorsearch.phases.core.Processor

class Mixer extends Processor {

  def process(common: PhaseCommon) = {
    var content = ""
    
    common.documents.foreach(d => content = content + d.content.mkString)
    new FileWriter("C:\\Users\\kaiser\\Desktop\\markhorTest", content).write
  }
}