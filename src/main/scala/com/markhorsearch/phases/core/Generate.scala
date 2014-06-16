package com.markhorsearch.phases.core

import com.markhorsearch.phases.cleaner.HtmlCleaner
import com.markhorsearch.phases.collector.google.GoogleEngine
import com.markhorsearch.phases.decorator.Mixer
import com.markhorsearch.phases.cleaner.HtmlCleaner
import com.markhorsearch.phases.collector.SiteCollector

class Generate {
  def main(args: Array[String]) {
  generate
}
  def generate: PhaseCommon = {
	  val common = new PhaseCommon("Mordecai")
	  
	  new GoogleEngine process common
	  
	  new SiteCollector process common
	  new HtmlCleaner process common
	  new SiteCollector process common
	  new HtmlCleaner process common
	  new Mixer process common
	  common
  }

  def printa(pc: PhaseCommon)={
	  pc.documents.foreach(d => println("content="+d.content))
  }
}

trait Processor {
  
  def process(c: PhaseCommon)
  
}

