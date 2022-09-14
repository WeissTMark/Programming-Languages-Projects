package weiss_mark

import scala.collection.mutable
import scala.xml.{Elem, NodeSeq, Text}

class Family extends TaxNode {
  case class summary(species: Int,
                    genus: Int,
                    examples: Array[String])
  override var tag: String =  "family"
  var parent: Map[String, Order] = _
  var child: Map[String, TaxNode] = _

  var info: summary = summary(0, 0, Array())

  override def saveFile(): Array[Elem] = {
    val ret = features.map(x => {
      XMLHelper.makeNode("feature", null, Text(x))})
    ret
  }

  def getGen: Int = {
    val ret = info.genus
    ret
  }
  def getSpec: Int = {
    val ret = info.species
    ret
  }
  def getEx: Array[String] = {
    val ret = info.examples
    ret
  }

  override def displayInfo(): String = {

    var out = "\n----Family: " + name + "\n----Feature: " + features.mkString("", ", ", "")
    out += "\n------Genus: " + info.genus + "  Species: " + info.species + "  examples: " + info.examples.mkString("", ", ", "")
    out
  }

  def setSum(spec: Int, genus: Int, ex: Array[String]): Unit = {
    var sum = summary(spec, genus, ex)
    info = sum
  }
}
