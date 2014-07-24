package com.markhorsearch.phases.core.document

import scala.xml.parsing.NoBindingFactoryAdapter
import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
import java.net.URL
import scala.xml.XML
import org.xml.sax.InputSource
import scala.xml.parsing.NoBindingFactoryAdapter
import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
import java.net.HttpURLConnection
import scala.xml.Node
import scala.io.Source
import java.io.FileInputStream
import java.io.File
import java.io.InputStream
import java.io.Reader
import java.io.InputStreamReader
import scala.util.control.Exception
import java.lang.Exception

class HtmlParserMarkhor {
    lazy val adapter = new NoBindingFactoryAdapter
	lazy val parser = (new SAXFactoryImpl).newSAXParser
	
	def load(url: String, headers: Map[String, String] = Map.empty): Node = {
      if(url.startsWith("http") || url.startsWith("www"))
        loadWww(new URL(url))
      else
        loadLocal(url)
    }
    
	def loadWww(url: URL, headers: Map[String, String] = Map.empty): Node = {
      var node: Node = <html></html>
      try {
		val conn = url.openConnection().asInstanceOf[HttpURLConnection]
		for ((k, v) <- headers)
		conn.setRequestProperty(k, v)
		val source = new InputSource(conn.getInputStream)
		source.setEncoding("UTF-8")
		node = adapter.loadXML(source, parser)
      } catch {
      	case _ => println("")
      	}
      node
    }
    
    def loadLocal(url: String): Node =
    {
      val file= new File(url);
      val inputStream= new FileInputStream(file);
	  val reader = new InputStreamReader(inputStream,"UTF-8");
	  val is = new InputSource(reader);
	  is.setEncoding("UTF-8");
	  
      adapter.loadXML( is, parser)
    }
}