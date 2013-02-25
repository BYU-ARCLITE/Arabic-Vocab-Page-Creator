package edu.byu.arclite.arabVocab

import mustache.Mustache

/**
 * This holds the information concerning a category within a page
   * @author Joshua Monson
 * @param name The name of the category
 * @param entries The list of entries
 */
case class Category(name: String, entries: List[Entry]) {

  /**
   * This generates and returns the HTML of the category
   * @return The HTML code
   */
  val toHtml: String = {
    val data = Map(
      "name" -> name,
      "entries" -> entries.map(_.toHtml).mkString
    )
    Category.mustache.render(data)
  }
}

object Category {
  val template =
    """
      |<h2>{{name}}</h2>
      |{{entries}}
    """.stripMargin
  val mustache = new Mustache(template)
}
