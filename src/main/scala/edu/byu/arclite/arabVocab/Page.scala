package edu.byu.arclite.arabVocab

import mustache.Mustache

/**
 * This holds the information concerning a vocab page
 * @author Joshua Monson
 * @param name The name of the page
 * @param categories The list of categories
 */
case class Page(name: String, categories: List[Category]) {

  /**
   * This generates and returns the HTML of the page
   * @return The HTML code
   */
  def toHtml: String = {
    val data = Map(
      "name" -> name,
      "categories" -> org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4(categories.map(_.toHtml).mkString)
    )
    org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4(Page.mustache.render(data))
  }
}

object Page {
  val template =
    """
      |<!DOCTYPE html>
      |<html>
      |<head>
      |	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      |	<title>{{name}}</title>
      |
      |	<script src="http://arclite.byu.edu/arabic/vocab/mediaelement/jquery.js"></script>
      |	<script src="http://arclite.byu.edu/arabic/vocab/mediaelement/mediaelement-and-player.min.js"></script>
      |
      |	<link rel="stylesheet" href="http://arclite.byu.edu/arabic/vocab/mediaelement/mediaelementplayer.min.css" />
      |	<link rel="stylesheet" href="http://arclite.byu.edu/arabic/vocab/bootstrap.min.css" />
      |
      |	<style type="text/css">
      |		.word {font-size: 15pt;}
      |   .arabic {direction:rtl;}
      |	</style>
      |</head>
      |<body>
      |<div class="container">
      |	<h1>{{name}}</h1>
      |
      |	{{categories}}
      |</div>
      |
      |<script type="text/javascript">
      |	$(function() {$('audio').mediaelementplayer();});
      |</script>
      |
      |</body>
      |</html>
    """.stripMargin
  val mustache = new Mustache(template)
}