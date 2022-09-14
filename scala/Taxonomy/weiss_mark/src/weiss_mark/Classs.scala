package weiss_mark

import scala.collection.mutable
import scala.xml.{Elem, NodeSeq}

class Classs extends TaxNode {
  override var tag: String =  "class"
  var parent: Map[String, TaxNode] = _
  var child: Map[String, Order] = _

  override def displayInfo(): String = {
    var out = "Class: " + name + "\nFeature: " + features.mkString("", ", ", "")
    if (child != null) {
      child.foreach({ case (key, value) =>
        out += value.displayInfo()
      })
    }
    out
  }

  override def saveFile(): Array[Elem] = {
    val kid = child.toArray
    if (kid != null) {
      val ret = kid.map({ case (key, value) =>
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("name", key))
        XMLHelper.makeNode("order", attr, value.saveFile())
      })
      return ret
    }
    return null
  }

  def hasOrder(ord: String): Boolean = {
    var has = false
    if (child != null) {
      child.foreach({ case (key, value) =>
        if (ord == key) {
          has = true
        }
      })
    }
    has
  }

  def hasFam(ord: String, fam: String): Boolean = {
    var has = false
    if (child != null) {
      child.foreach({ case (key, value) =>
        if (ord == key) {
          value.child.foreach({ case (key, value) =>

          })
        }
      })
    }
    has
  }


}
