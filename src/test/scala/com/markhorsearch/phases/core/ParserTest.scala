package com.markhorsearch.phases.core

import scala.io.Source
import scala.xml.Node
import scala.xml.NodeSeq
import scala.xml.Text
import scala.xml.transform.RewriteRule
import scala.xml.transform.RuleTransformer
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.specs2.runner.JUnitRunner
import com.markhorsearch.phases.cleaner.ElementClassifier
import com.markhorsearch.phases.core.document.Document
import com.markhorsearch.phases.core.document.HtmlParserMarkhor
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Seq
import scala.collection.mutable.Set
import scala.collection.mutable.ListBuffer

//@RunWith(classOf[JUnitRunner])
class ParserTest extends FlatSpec  {
    "Parser" should " only remove menu" in {
     val page = """<html>
    		<body>
    			<div id="a">
    				<div id="b"> <a>menu1</a> <a>menu2</a> <a>menu3</a></div>
    				This cannot be removed never
    			</div>
    		</body>
    	</html>"""
       
     val p = """<a> teste 123 <b> nooo </b> hehehe</a>"""
       
     //val document = new Document
     //document.process(new HtmlParserMarkhor().load(ler()))

     val ec = new ElementClassifier
     
     val remove = new RewriteRule {
       override def transform(n: Node): NodeSeq = {
         n.label.trim match {
           case "script" => NodeSeq.Empty
           case "style" => NodeSeq.Empty
           case _ => n
         }
       }
     }
     
     //val newNode = new RuleTransformer(remove)(document.node)
     //processNode(newNode, Option.empty[Node])
     val lista = defineds.distinct
     val lista2 = lista.filter(element => {
       val links = (element \\ "a").size
       val total = element.child.size
       val b = links < (total/2)
       b
     })
     new FileWriter("C:\\Users\\kaiser\\Desktop\\markhorTest", lista2 mkString).write
  }
    val r = """(\w*?\s.*?){5,}""".r
    val lista = new ListBuffer[Node]()
    val defineds = ListBuffer[Node]()
    def processNode(node: Node, parent: Option[Node]) {
        if(node.label.trim() != "script")
        {
        	if(node.label.trim() != "style")
        	{
        		if (node.isInstanceOf[Text] && !r.findFirstIn(node.text).isEmpty)
        		{
        			if(node.text.trim() != "")
        			{
        				if(parent.isDefined) {
        				   defineds += parent.get
        				}
        				
        			}	
        		}
        	}
        }
        
    	node.child.foreach(c => processNode(c, Option(node)))
    }
    
    def ler(): String = {
      val content = Source.fromFile("C:\\Users\\kaiser\\Documents\\stack.htm", "UTF-8").getLines
      var texto = ""
        content foreach(f => {
          texto += f})
      texto
    }
}