package com.markhorsearch.phases.core

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.specs2.runner.JUnitRunner
import scala.io.Source
import com.markhorsearch.phases.cleaner.types.WikiCleaner
import com.markhorsearch.phases.core.document.HtmlParserMarkhor
import com.markhorsearch.phases.core.document.Document

@RunWith(classOf[JUnitRunner])
class WikiCleanerTest extends FlatSpec {
    "WikiCleaner" should " cleaner" in {
    	val page = new HtmlParserMarkhor().load(ler).toList
    	val wCleaner = new WikiCleaner
    	
    	val cleaned = wCleaner.cleanSite(new Document(page, "", false, Document.siteEmpty))
    }
    
    def ler(): String = {
      val content = Source.fromFile("C:\\Users\\kaiser\\Desktop\\markhorTest\\lua.htm", "UTF-8").getLines
      var texto = ""
        content foreach(f => {
          texto += f})
      texto
    }
}