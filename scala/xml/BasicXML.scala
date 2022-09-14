package XmlExamples

import scala.collection.mutable
import scala.xml.{Elem, Node, Null, Text, UnprefixedAttribute}


//to import xml, open the module setting-->then global libraries, then find the following
//C:\Users\<username>\.ivy2\cache\org.scala-lang.modules\scala-xml_2.12\bundles\scala-xml_2.12-1.0.6.jar
//add it to standard libraries
object BasicXML extends App {

  //making a Node the text way----------------------------------------
  //Warning: will not work after Scala 2.12
  var pets = <pets>
    <owner name="Harold">
      <pet species="Cat">Patty</pet>
      <pet species="Dog">Snoopy</pet>
    </owner>
    <owner name="Bob">
      <pet species="Hamster">Hammy</pet>
    </owner>
  </pets>


  
  //changing the xml----------------------------------------------------------------------------------------

  //.child returns a NodeBuffer with each of the direct children
  //this is to CORRECT method to parse a node
  print("\n\nprint all children of pets")
  val children = pets.child
  for (child <- children) {
    print(child)
  }


  //note: .text returns ALL descendant Text nodes contents
  //note: #PCDATA is a place holder for empty nodes and occurs with nice formatting...
  println("\n\n\nPrinting the children's complete information")

  def printInfo(child: Node): Unit = {
    println("Tag:" + child.label)
    println("Text: " + child.text.strip)
    println("Attributes: " + child.attributes + "\n")
  }
  for (child <- children) {
    printInfo(child)
  }

  /*to make an node in code, use Elem or Text
  located here: https://www.scala-lang.org/api/2.12.12/scala-xml/scala/xml/Elem.html


  Elem(
    prefix: String,
    label: String,
    attributes1: MetaData,
    scope: NamespaceBinding,
    minimizeEmpty: Boolean,
    child: Node*)


    That's annoying, so I made a helper class XMLHelper.scala
  */

  //simplest new node
  val newPet0 = XMLHelper.makeNode("pet")
  println("\n\n\n\nEmpty node: " + newPet0 )

  //node with attribute
  val attr1 = mutable.HashMap[String, String](
    ("species", "hamster"),
    ("subtype", "syrian"))
  val newPet1 = XMLHelper.makeNode("pet", attr1)


  //node with attribute and child text
  val text = Text("Fluffy")   //Text requires a special node
  val attr2 = mutable.HashMap[String, String](("species", "hamster"))
  val newPet2 = XMLHelper.makeNode("pet", attr2, text)


  //add node with children
  val attrPerson = mutable.HashMap[String, String](("name", "Bobby"))
  val newPets = newPet1 ++ newPet2 //needs a sequence of nodes
  val newPerson = XMLHelper.makeNode("owner", attrPerson, newPets)
  println("\n\n\n\nNew owner: " + newPerson )

  //immutable behind the scenes, so I'm replacing the entire thing!
  pets = XMLHelper.makeNode("pets", null, pets.child ++ newPerson)
  println("\n\n\n\npets with new owner: " + pets)

  //to make the formatting nice, use PrettyPrinter
  val prettyPrinter = new scala.xml.PrettyPrinter(80, 2)
  val prettyXml = prettyPrinter.format(pets)
  println("\n\n\n\nNicely formatted: " + prettyXml )
}

