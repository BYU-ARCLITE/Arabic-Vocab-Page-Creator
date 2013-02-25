package edu.byu.arclite.excel

import java.io.{FileInputStream, File}
import org.apache.poi.xssf.usermodel.{XSSFSheet, XSSFWorkbook}
import edu.byu.arclite.arabVocab.{Entry, Category, Page}

/**
 * This takes an excel file where each page represents a different exercise and converts it into internal page objects
 * @author Joshua Monson
 */
object ExcelReader {

  /**
   * Read in an .xslx file and output a list of Pages
   * @param file The excel file
   * @return A list of pages
   */
  def read(file: File, columnOrder: List[String]): List[Page] = {

    // Load the workbook
    val workbook = new XSSFWorkbook(new FileInputStream(file))

    // Get the page for each sheet
    var pages = List[Page]()
    for (sheetIndex <- 0 until workbook.getNumberOfSheets) {
      val sheet = workbook.getSheetAt(sheetIndex)

      // Save the page
      pages = readSheet(sheet, columnOrder) :: pages
    }

    // Return the pages
    pages
  }

  /**
   * This takes a sheet, reads each entry, groups by category, and outputs a Page object
   * @param sheet The excel sheet
   * @return The resulting Page object
   */
  def readSheet(sheet: XSSFSheet, columnOrder: List[String]): Page = {
    // Read each row in as a product of strings
    var stringEntries = List[(String, String, String, String)]()
    val rows = sheet.rowIterator()

    // The first cell of the first row is the page name
    val pageName = rows.next().getCell(0).getStringCellValue

    while (rows.hasNext) {

      // Get the row
      val row = rows.next()

      // Allow for missing category
      val lastCell =
        if (row.getPhysicalNumberOfCells < 4) "" else row.getCell(3).getStringCellValue

      // Save the cell values. Append this so as to
      stringEntries = stringEntries ::: List(
        (row.getCell(0).getStringCellValue, row.getCell(1).getStringCellValue, row.getCell(2).getStringCellValue,
          lastCell)
      )
    }

    // Group by categories
    val categories = stringEntries.groupBy(_.productElement(columnOrder.indexOf("category")).asInstanceOf[String])
      .map(e =>
        Category(
          e._1,
          e._2.map(tuple => Entry(
            tuple.productElement(columnOrder.indexOf("arabic")).asInstanceOf[String],
            tuple.productElement(columnOrder.indexOf("english")).asInstanceOf[String],
            tuple.productElement(columnOrder.indexOf("url")).asInstanceOf[String])
          )
        )
      ).toList

    // Return the page, using the sheet name
    Page(pageName, categories)
  }
}
