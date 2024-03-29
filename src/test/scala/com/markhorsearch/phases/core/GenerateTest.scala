package com.markhorsearch.phases.core

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.specs2.runner.JUnitRunner
import com.markhorsearch.phases.core.document.Document
import com.markhorsearch.phases.collector.SiteCollector
import com.markhorsearch.phases.cleaner.HtmlCleaner
import com.markhorsearch.phases.decorator.Mixer


@RunWith(classOf[JUnitRunner])
class GenerateTest extends FlatSpec {
	"Generate" should "create phases" in {
	  val common = new PhaseCommon("")
	  
	  common.sites = 
	    new Site("""C:\Users\kaiser\Desktop\markhorTest\sitesTest\site1.htm""","site1", Option("test1")) ::
	    new Site("""C:\Users\kaiser\Desktop\markhorTest\sitesTest\site2.htm""","site2", Option("test2")) ::
	    Nil
	    
	  new SiteCollector process common
	  new HtmlCleaner process common
	  new SiteCollector process common
	  new HtmlCleaner process common
	  new Mixer process common
	}
	
	
	"Document" should "have 3 relevant links" in {
	  val document = new Document(List(page), "teste", true, Document.siteEmpty)
	  println(document.relevantSites.size)
	}
	
	val page = 
	  <body>
			<form>
			<a href="www.teste.com.br">a</a>
			<a href="www.teste.com.br">b</a>
			<a href="www.teste.com.br">c</a>
			<a href="www.teste.com.br">d</a>
			<a href="www.teste.com.br">e</a>
			<a href="www.teste.com.br">f</a>
			<a href="www.teste.com.br">g</a>
			<a href="www.teste.com.br">h</a>
			<a href="www.teste.com.br">i</a>
			</form>
	  </body>
}