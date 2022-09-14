package weiss_mark

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.xml.{Elem, Text}

object Tax {

  var trees = Map("" -> new Classs)

  def clear(): Unit = {
    trees = trees.empty
  }

  def hasClass(clas: String): Boolean = {
    var has = false
    trees.foreach({ node =>
      if (node._1 == clas) {
        has = true
      }
    })
    has
  }

  def hasOrder(ord: String): Boolean = {
    var has = false
    trees.foreach({ case (key, value) =>
      if (value.hasOrder(ord)) {
        has = true
      }
    })
    has
  }

  def hasFam(clas: String, ord: String, fam: String): Boolean = {
    var has = false
    if (trees(clas) != null) {
      if (trees(clas).child(ord).child != null) {
        trees(clas).child(ord).child.foreach({ case (key, value) =>
          if (key == fam) {
            has = true
          }
        })
      }
    }

    has
  }

  def addClass(name: String): Unit = {
    val top = new Classs
    top.name = name
    trees = trees + (name -> top)
  }

  def addOrder(clas: String, name: String): Unit = {
    val ord = new Order
    ord.name = name
    if (trees(clas).child == null) {
      trees(clas).child = Map(name -> ord)
    }
    else {
      trees(clas).child = trees(clas).child + (name -> ord)
    }
  }

  def addFamily(clas: String, Order: String, name: String): Unit = {
    val fam = new Family
    fam.name = name
    val ord = trees(clas).child
    if (ord(Order).child == null) {
      ord(Order).child = Map(name -> fam)
    }
    else {
      ord(Order).child = ord(Order).child + (name -> fam)
    }
  }

  def addFeature(clas: String, Order: String, name: String, feature: String): Unit = {
    val fam = new Family
    fam.name = name
    val ord = trees(clas).child
    val feats = ord(Order).child(name).features
    ord(Order).child(name).features = feats ++ Array(feature)
  }

  def addSummary(clas: String, Order: String, name: String, spec: Int, genus: Int, ex: Array[String]): Unit = {
    val fam = new Family
    fam.name = name
    val ord = trees(clas).child
    ord(Order).child(name).setSum(spec, genus, ex)
  }

  def removeClass(clas: String): String = {
    var ret = "class not found"
    trees.foreach({ case (key, value) =>
      if (key == clas) {
        trees = trees - key
        ret = "removed " + key
      }
    })
    ret
  }

  def getGenus(clas: String, Order: String, name: String): Int = {
    var gen = 0
    val fam = new Family
    fam.name = name
    val ord = trees(clas).child
    gen = ord(Order).child(name).getGen
    gen
  }

  def getSpec(clas: String, Order: String, name: String): Int = {
    var spec = 0
    val fam = new Family
    fam.name = name
    val ord = trees(clas).child
    spec = ord(Order).child(name).getSpec
    spec
  }

  def getSummary(clas: String, Order: String, fami: String): Array[String] = {
    val fam = new Family
    fam.name = fami
    val ord = trees(clas).child
    val spec = ord(Order).child(fami).getEx
    spec
  }

  def loadFile(file: Elem): Unit = {
    val children = file.child //grab all children
    for (child <- children) {
      val tag = child.label
      var clas = ""
      var ord = ""
      var fam = ""
      tag match {
        case "class" =>
          val name = child.attribute("name")
          clas = name.getOrElse("").toString
          addClass(clas)
        case "order" =>
          val name = child.attribute("name")
          ord = name.getOrElse("").toString
          addOrder(clas, ord)
        case "family" =>
          val name = child.attribute("name")
          fam = name.getOrElse("").toString
          addFamily(clas, ord, fam)
        case _ => null

      }
    }
  }

  def writeFile(): Elem = {
    val branches = trees.toArray
    if (branches != null) {
      val ret = branches.map({ case (key, value) =>
        if (key != "") {
          val attr: mutable.HashMap[String, String] = mutable.HashMap(("name", key))
          XMLHelper.makeNode("class", attr, value.saveFile())
        } else {
          XMLHelper.makeNode("class", null, value.saveFile())
        }
      })
      XMLHelper.makeNode("taxonomy", null, ret)
    } else {
      XMLHelper.makeNode("taxonomy", null)
    }
  }

  def sumSpecies(): Unit = {

  }

  @Override
  override def toString: String = {
    var out = ""
    trees.foreach({ case (key, value) =>
      if (key != "") {
        out += value.displayInfo()
        out += "\n"
      }
    })
    out
  }
}
