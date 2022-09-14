package weiss_mark

import scala.collection.mutable
import scala.xml.{Elem, Node, NodeSeq}

abstract class TaxNode {
  var tag: String
  var name = ""
  var features: Array[String] = Array()

  def loadFile(node: Node): Unit = {
    val tax = node.attribute("name")
    val name = node.text
    features = tax.getOrElse("").toString.split(",")
    this.name = name
  }

  def saveFile(): Array[Elem] = {

    val attr: mutable.HashMap[String, String] = mutable.HashMap(("name", name))
    val elm = XMLHelper.makeNode(tag, attr)
    Array(elm)
  }

  def displayInfo(): String = {
    val out = ""
    out
  }

  def find(): Unit = {

  }

}
