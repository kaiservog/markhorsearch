//package com.markhorsearch.phases.core.document
//
//import java.io.File
//import java.io.FileDescriptor
//import java.io.FileInputStream
//import java.io.InputStream
//import java.io.InputStreamReader
//import java.io.Reader
//import java.io.Reader
//import scala.xml.Elem
//import scala.xml.MetaData
//import scala.xml.NamespaceBinding
//import scala.xml.Node
//import scala.xml.Node
//import scala.xml.Text
//import scala.xml.factory.NodeFactory
//import scala.xml.parsing.FactoryAdapter
//import scala.xml.parsing.FactoryAdapter
//import org.xml.sax.InputSource
//import com.sun.org.apache.xalan.internal.xsltc.trax.DOM2SAX
//import _root_.scala.xml.{Node,TopScope}
//import org.xml.sax.XMLReader
//import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
//
//class TagSoupFactoryAdapter extends SAXFactoryAdapter
//                            with HtmlFactoryAdapter {
//	
//	private val parserFactory = new SAXFactoryImpl
//	parserFactory.setNamespaceAware(true)
//    def getReader() = parserFactory.newSAXParser().getXMLReader()
//}
//
//
//
//trait NonBindingFactoryAdapter extends FactoryAdapter
//                               with NodeFactory[Elem] {
//
//  def nodeContainsText(localName: String) = true
//
//  protected def create(pre: String, label: String,
//                       attrs: MetaData, scpe: NamespaceBinding,
//		       children: Seq[Node]): Elem =
//    Elem( pre, label, attrs, scpe, children:_* )
//  
//	
//  def createNode(pre: String, label: String,
//                 attrs: MetaData, scpe: NamespaceBinding,
//                 children: List[Node] ): Elem =
//    Elem( pre, label, attrs, scpe, children:_* )
//	 
//  def createText(text: String) = Text(text)
//	
//  def createProcInstr(target: String, data: String) =
//    makeProcInstr(target, data)
//}
//
//trait HtmlFactoryAdapter extends FactoryAdapter {
//  val emptyElements = Set("area", "base", "br", "col", "hr", "img", 
//      "input", "link", "meta", "param")
//      
//  def nodeContainsText(localName: String) =
//    !(emptyElements contains localName)
//}
//
//
//trait DOMFactoryAdapter extends NonBindingFactoryAdapter {
//
//  def getDOM(reader: Reader) : _root_.org.w3c.dom.Node
//
//  override def loadXML(unused : InputSource) : Node = {
//    throw(new Exception("Not Implemented"))
//  }
//	  
//  def loadXML(dom: _root_.org.w3c.dom.Node) : Node = {
//    val dom2sax = new DOM2SAX(dom)
//    dom2sax.setContentHandler(this)
//    scopeStack.push(TopScope)
//    dom2sax.parse()
//    scopeStack.pop
//    return rootElem
//  }
//
//  /** loads XML from given file */
//  override def loadFile(file: File): Node = {
//    val is = new FileInputStream(file)
//    val elem = load(is)
//    is.close
//    elem
//  }
// 
//  /** loads XML from given file descriptor */
//  override def loadFile(fileDesc: FileDescriptor): Node = {
//    val is = new FileInputStream(fileDesc)
//    val elem = load(is)
//    is.close
//    elem
//  }
//
//  /** loads XML from given file */
//  override def loadFile(fileName: String): Node = {
//    val is = new FileInputStream(fileName)
//    val elem = load(is)
//    is.close
//    elem
//  }
//
//  /** loads XML from given InputStream */
//  override def load(is: InputStream): Node =
//    load(new InputStreamReader(is))
//
//  /** loads XML from given Reader */
//  override def load(reader: Reader): Node =
//    loadXML(getDOM(reader))
//
//  /** loads XML from given sysID */
//  override def load(sysID: String): Node = {
//    val is = new java.net.URL(sysID).openStream()
//    val elem = load(is)
//    is.close
//    elem
//  }
//}
//
//
//trait SAXFactoryAdapter extends NonBindingFactoryAdapter {
//
//  /** The method [getReader] has to implemented by
//      concrete subclasses */
//  def getReader() : XMLReader;
//
//  override def loadXML(source : InputSource) : Node = {
//    val reader = getReader()
//    reader.setContentHandler(this)
//    scopeStack.push(TopScope)
//    reader.parse(source)
//    scopeStack.pop
//    return rootElem
//  }
//}