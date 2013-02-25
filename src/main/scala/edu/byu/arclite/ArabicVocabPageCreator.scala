package edu.byu.arclite

import arabVocab.Page
import excel.ExcelReader
import java.io.{FileWriter, File}

object ArabicVocabPageCreator {

  val resourceRoot = "src/main/resources/"

  def main(args: Array[String]) {
    println("Arabic Vocab Page Creator")
    println("Written by Joshua Monson\n")

    println("Arguments: [inputFile [columnOrder]]")
    println("  inputFile: The excel file")
    println("  columnOrder: The default column order is \"category, arabic, english, url\". You can redefine this.\n")

    // Get the input file
    val inputFilename = if (args.isEmpty) "test.xlsx" else args(0)
    println("Using input file: " + inputFilename)

    // Get the column order
    val columnOrder = (if (args.length >= 2) args(1) else "category, arabic, english, url").split("\\s*,\\s*").toList
    println("Using column order of: " + columnOrder.mkString(", "))

    // Load the excel file and create the pages
    println("Loading pages from excel file...")
    val file = new File(resourceRoot + inputFilename)
    val pages = ExcelReader.read(file, columnOrder)
    println("Done. " + pages.size + " pages loaded.")

    // Write the pages out
    println("Writing .html pages.")
    writePages(pages)
    println("Done.")
  }

  def writePages(pages: List[Page]) {
    pages.foreach(page => {
      // Create the file
      val file = new File(resourceRoot + page.name.replaceAll(" ", "") + ".html")

      // Write the html out
      val writer = new FileWriter(file)
      writer.write(page.toHtml)
      writer.close()
    })
  }
}
