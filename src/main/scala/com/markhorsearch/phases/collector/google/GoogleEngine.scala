package com.markhorsearch.phases.collector.google

import java.net.URL
import java.net.URLEncoder
import scala.io.Source
import scala.util.parsing.json.JSON
import com.markhorsearch.phases.core.Processor
import com.markhorsearch.phases.core.Site
import com.markhorsearch.phases.core.PhaseCommon

class GoogleEngine extends Processor {
	val address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q="
	val charset = "UTF-8"

	def process(common: PhaseCommon) = {
		val url: URL = new URL(address + URLEncoder.encode(common query, charset))

		val lines = Source fromURL(url) getLines
		var text = ""
		lines foreach(line => { text += line })
		
		val json = JSON parseFull(text)
		var resultList = List[Map[String, String]]()
		json match {
		  case Some(resp: Map[String, Any]) => resp("responseData") match {
		      case results: Map[String, Any] => results("results") match {
		      	case list: List[Map[String,String]] => resultList = list  
		    }
		  }
		}
		
	    val sites = for(map <- resultList) yield new Site(map.get("url").getOrElse(""),
		             map.get("visibleUrl").getOrElse(""),
		             map.get("title"))
	    
	    common sites = sites
	}
}