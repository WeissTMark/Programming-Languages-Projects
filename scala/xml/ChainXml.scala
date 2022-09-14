package XmlExamples

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.xml.{Elem, Node, Text}
import scala.xml.XML

trait XmlReadWrite {
    def loadXml(node: Node): Unit

    def writeXml(): Elem //Element in the XML
}

/**
  * a struct based class to save a pet
  *
  * @param species the species of the pet
  * @param name    the name of the  pet
  */
case class Pet(private var species: String = "", private var name: String = "") extends XmlReadWrite {



    /**
      * loads in a pet node
      *
      * @param node a pet node
      */
    def loadXml(node: Node): Unit = {
        val animal = node.attribute("species")
        val name = node.text
        species = animal.getOrElse("").toString
        this.name = name
    }

    /**
      * Make a pet node
      *
      * @return the new pet node
      */
    def writeXml(): Elem = {
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("species", species))
        val text = Text(name) //ending node
        return XMLHelper.makeNode( Pet.TAG, attr, text)
    }

    override def toString: String = {
        return "Species: " + species + " Name: " + name
    }
}

object Pet{
    val TAG = "pet"
}

/**
  * a basic class to hold a owner with their pets
  */
class Pets() extends XmlReadWrite {
    val TAG = "pets"
    private var pets: ListBuffer[Pet] = _
    private var owner: String = _

    override def toString: String = {
        var str = "Owner: " + owner + "\n"
        for(pet <- pets){ 
            str += pet + "\n"
        }
        str
    }

    /**
     * fills in the contents of this class
     *
     * @param node node that contains the owner information and pets
     */
    def addOwner(node: Node): Unit = {
        //to do full streaming method, I would get the attributes and then do a for-each
        val name = node.attribute("name")
        owner = name.getOrElse("").toString
    }

    def addPet(pet: Pet): Unit = {
        pets += pet
    }

    def loadXml(node: Node): Unit = {

        pets = ListBuffer[Pet]()

        /* general pattern
            grab attributes
            foreach childNode
                if the tag is something we care about
                     make child class
                     child.readXML(childNode)
         */

        //would grab attributes here if they were available


        val children = node.child //grab all children
        for(child <- children) {
            val tag = child.label
            tag match {
                case "owner" =>
                    println("owner")
                    addOwner(child)
                case Pet.TAG =>
                    println("pet")
                    //if pet tag, make a new pet and have it load the info it wants, then add it to the list
                    val pet = Pet() //full functional would give the node to the constructor
                    pet.loadXml(child)
                    pets += pet
                case _ => null
            }
        }
    }


    /**
      * Make a pets node
      *
      * @return the new pets node
      */
    def writeXml(): Elem = {

        /* general pattern
            grab attributes
            make attribute

            foreach childClass
                childeNode = childClass.WriteXML()
                node.append(childNode)

            return node

            Aside: full functional makes the children nodes first (recursion)
            and then make the parent node with attributes and children in one go
         */

        //make owner node
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("name", owner))
        var ownerXml = XMLHelper.makeNode("owner", attr)

        //make pet nodes
        var petXml = pets.map(x => x.writeXml())
        val children = ownerXml ++ petXml //need ALL siblings at one time
        XMLHelper.makeNode(TAG, null, children)
    }
}

/**
  * static part of Pets
  */
object Pets {
    def apply(): Pets = {
       new Pets()
    }


}


//to import xml, open the module setting-->then global libraries, then find the following
//C:\Users\ToshibaQ\.ivy2\cache\org.scala-lang.modules\scala-xml_2.12\bundles\scala-xml_2.12-1.0.6.jar
//add it to standard libraries
object ChainXml extends App {

    val owner = loadXml("petsB.xml") //by default scala look at the same level as the source files (not inside)
    print(owner)

    //add a pet and write to file
    val pet = Pet("hamster", "Hammy")
    owner.addPet(pet)
    val newPets = owner.writeXml() //build XML tree


    /**
      * starts up loading a xml file for pets
      *
      * @param name the name of the pets xml file to load
      * @return the pets object created fro teh xml
      */
    def loadXml(name: String): Pets = {
        var owner: Pets = Pets()
        val topNode = XML.loadFile(name) //XML.loadFile will read in the DOM tree
        if (topNode.label != "pets") { //.label is the "tag"
            println("invalid xml file")
        } else {
            owner.loadXml(topNode)
        }
        return owner
    }

    XML.save("anotherPet.xml", newPets, "UTF-8", true, null)


}


