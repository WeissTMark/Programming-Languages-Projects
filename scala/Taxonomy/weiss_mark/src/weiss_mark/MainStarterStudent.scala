package weiss_mark

import scala.io.StdIn
import scala.xml.XML

//0. Got it running						______✓
//1.	Add + Display*	34
//Prompts correct (-1 pt each missed)		______✓
//Adds a one of each item and displays 	______✓
//Adds multiples 							______✓
//Above displays correctly formatted 		______✓
//
//
//2A) Remove + Display*	10
//Prompts correct							______✓
//Removes and displays correctly 			______✓
//
//
//2B) Add + XML save*	16
//Console added items saved correctly 		______✓
//Console added multiples is saved correctly 	______✓
//
//
//2C) XML load + XML save*	16
//1 XML file loaded and saved correctly 	______X
//2+ XML file loaded and saved correctly	______X
//
//
//2D) XML load + Display*	16
//1 XML file loaded and displays correctly 	______X
//2+ XML file loaded and displays correctly	______X
//
//
//2E) XML+ Display with bad file handing	10
//Calling display on empty, and after “messy” load works	______✓
//Handles files not found correctly						______✓
//Handle “not taxonomy” correctly							______✓
//Handles extra tag, text, and attributes correctly		______X
//Handles missing attributes correctly					______X
//
//
//3.	Stress test for above*	15
//Loads in file, adds data, and displays/saves correctly		______X
//Appends a file and displays/saves correctly 				______X
//Removes animal after edits, and displays/saves correctly 	______X
//
//
//4. Find feature*	16
//CoR format at least there						______X
//Prompts correct									______X
//First item found and output formatted correctly	_____X
//Handles “not found case”						______X
//
//
//5.	Total species count*	12
//Prompts correct				______X
//Calculated correctly		______X
//Parallelized*				______X
//
//Every Line with a * has its grading tag: ______X
object MainStarterStudent extends App {
  var choice = -1

  val menu: String =
    """
      |1) Add data
      |2) Display data
      |3) Remove class
      |4) Load XML
      |5) Write XML
      |6) Find feature
      |7) Calculate species
      |0) Quit
      |Choice:> """.stripMargin

  var temp = ""
  var tree = Tax
  while (choice != 0) {
    try {
      print(menu)
      //something to strip out empty lines
      choice = getInput.toInt

      choice match {
        case 0 => quit()
        case 1 => addData()
        case 2 => displayData()
        case 3 => deleteClass()
        case 4 => loadXML()
        case 5 => writeXML()
        case 6 => println("TODO")
        case 7 => println("TODO")
        case _ => println("Invalid option")
      }
    } catch {
      case e: Throwable => print(e)
    }
  }

  def getInput: String = {
    temp = StdIn.readLine()
    while (temp.isEmpty)
      temp = StdIn.readLine()
    temp
  }

  def continue(): Boolean = {
    var bool = false
    println("continue (y/n):>")
    val cont = getInput.toLowerCase
    if (cont == "y") {
      bool = true
    }
    bool
  }

  def quit(): Unit = {
    tree.clear()
  }
  //GRADING: ADD
  def addData(): Unit = {
    var choice = "y";
    println("what class:> ")
    val clas = getInput.toLowerCase
    if (!tree.hasClass(clas)) {
      tree.addClass(clas)
      println("added class")
      if (!continue()) {
        return;
      }
    }
    println("what order:> ")
    val ord = getInput.toLowerCase
    if (!tree.hasOrder(ord)) {
      tree.addOrder(clas, ord)
      println("added order")
      if (!continue()) {
        return;
      }
    }
    println("what family:> ")
    var fam = getInput.toLowerCase
    while (choice == "y") {
      if (tree.hasFam(clas, ord, fam)) {
        println("add feature (y/n):>")
        choice = getInput.toLowerCase
        if (choice != "n") {
          println("what feature:> ")
          val feat = getInput.toLowerCase
          tree.addFeature(clas, ord, fam, feat)
        }
      }
      else {
        tree.addFamily(clas, ord, fam)
        println("added family")
        if (!continue()) {
          return;
        }
      }
    }
    choice = "y"
    while (choice == "y") {
      println("add summary (y/n):> ")
      choice = getInput.toLowerCase
      val gen = tree.getGenus(clas, ord, fam)
      val spec = tree.getSpec(clas, ord, fam)
      if (choice == "y") {
        println("update genus count (" + gen + "):> ")
        val newGen = getInput.toInt
        println("update species count (" + spec + "):> ")
        val newSpec = getInput.toInt
        var newEx = tree.getSummary(clas, ord, fam)
        while (choice != "n") {
          println("add example (y/n):> ")
          choice = getInput.toLowerCase
          if (choice == "y") {
            println("what example:> ")
            newEx = newEx ++ Array(getInput.toLowerCase)
          }
        }
        tree.addSummary(clas, ord, fam, newSpec, newGen, newEx)
      }
    }

  }

  def deleteClass(): Unit = {
    println("what class:> ")
    var clas = getInput.toLowerCase
    var ret = tree.removeClass(clas)
    println(ret)
  }

  def loadXML(): Unit = {
    println("file name:> ")
    val inFile = getInput.toLowerCase
    try {
      val file = XML.loadFile(inFile)
      if (file.label != "taxonomy") {
        println("invalid xml file. needs to be an taxonomy xml file")
      }
      else {
        tree.loadFile(file)
      }
    } catch {
      case _:Throwable => println("could not open file: " + inFile + " (the system cannot find the file specified)")
    }

  }

  //GRADING: WRITE
  def writeXML(): Unit = {
    println("file name:> ")
    val outFile = getInput.toLowerCase
    XML.save(outFile, tree.writeFile())
  }

  //GRADING: PRINT
  def displayData(): Unit = {
    print(tree.toString)
  }
}
