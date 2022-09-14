package weiss_mark

import scala.collection.mutable
import scala.xml.{Elem, NodeSeq, Text}

class Order extends TaxNode {
  override var tag: String =  "order"
  var child: Map[String, Family] = _
  var parent: Map[String, Order] = _

  override def saveFile(): Array[Elem] = {
    val kid = child.toArray
    if (kid != null) {
      val ret = kid.map({ case (key, value) =>
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("name", key))
        XMLHelper.makeNode("family", attr, value.saveFile())
      })
      return ret
    } else {
      return null
    }
  }

  override def displayInfo(): String = {
    var out = "\n--Order: " + name + "\n--Feature: " + features.mkString("", ", ", "")
    if (child != null) {
      child.foreach({ case (key, value) =>
        out += value.displayInfo()
      })
    }
    out
  }

  def hasFamily(fam: String): Boolean = {
    var has = false
    if (child != null) {
      child.foreach({ case (key, value) =>
        if (fam == key) {
          has = true
        }
      })
    }
    has
  }


}
