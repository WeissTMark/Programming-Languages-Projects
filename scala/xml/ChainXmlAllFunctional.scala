package XmlExamples

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.xml._



/**
  * a struct based class to save a pet
  *
  * @param species the species of the pet
  * @param name    the name of the  pet
  */
case class PetFunctional(private var species: String = "", private var name: String = "") {


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

object PetFunctional{
    val TAG = "pet"

    def apply(node : Node) : PetFunctional = {
        val n = node.text
        val s = node.attribute("species").getOrElse("").toString
        new PetFunctional(s, n)
    }
}

/**
  * a basic class to hold a owner with their pets
  */
class PetsFunctional(private var pets: ListBuffer[PetFunctional],  private var owner: String)  {
    val TAG = "pets"
    override def toString: String = {
        var str = "Owner: " + owner + "\n"
        for(pet <- pets){ 
            str += pet + "\n"
        }
        str
    }

    def addPet(pet: PetFunctional): Unit = {
        pets += pet
    }


    /**
      * Make a pets node
      *
      * @return the new pets node
      */
    def writeXml(): Elem = {

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
object PetsFunctional {
    def apply(): PetsFunctional = {
       new PetsFunctional(ListBuffer[PetFunctional](), "owner")
    }
    
    def apply(node: Node):PetsFunctional = {
        var owner : String = ""
        val pets : ListBuffer[PetFunctional] = ListBuffer[PetFunctional]()

        val children = node.child //grab all children
        for(child <- children) {
            val tag = child.label
            println(tag)
            tag match {
                case "owner" =>
                    owner = child.attribute("name").getOrElse("").toString
                case Pet.TAG =>
                    pets += PetFunctional(child)
                case _ => null
            }
        }
        new PetsFunctional(pets, owner)
    }


}


//to import xml, open the module setting-->then global libraries, then find the following
//C:\Users\ToshibaQ\.ivy2\cache\org.scala-lang.modules\scala-xml_2.12\bundles\scala-xml_2.12-1.0.6.jar
//add it to standard libraries
object ChainXmlFunctional extends App {

    val owner = loadXml("petsB.xml") //by default scala look at the same level as the source files (not inside)
    print(owner)

    //add a pet and write to file
    val pet = PetFunctional("hamster", "Hammy")
    owner.addPet(pet)
    val newPets = owner.writeXml() //build XML tree


    /**
      * starts up loading a xml file for pets
      *
      * @param name the name of the pets xml file to load
      * @return the pets object created fro teh xml
      */
    def loadXml(name: String): PetsFunctional = {
        var owner: PetsFunctional = null
        val topNode = XML.loadFile(name) //XML.loadFile will read in the DOM tree
        if (topNode.label != "pets") { //.label is the "tag"
            println("invalid xml file")
        } else {
            owner = PetsFunctional(topNode)
        }
        return owner
    }

    XML.save("anotherPet.xml", newPets, "UTF-8", true, null)


}


