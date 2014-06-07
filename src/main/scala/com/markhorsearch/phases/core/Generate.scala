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
	  
	  println("1"+" "+common.sites.size+"/"+common.documents.size)
	  new SiteCollector process common
	  println("2"+" "+common.sites.size+"/"+common.documents.size)
	  new HtmlCleaner process common
	  println("3"+" "+common.sites.size+"/"+common.documents.size)
	  new SiteCollector process common
	  println("4"+" "+common.sites.size+"/"+common.documents.size)
	  new HtmlCleaner process common
	  println("5"+" "+common.documents.size+"/"+common.documents.size)
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

