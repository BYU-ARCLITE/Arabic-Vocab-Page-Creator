package edu.byu.arclite.arabVocab

import mustache.Mustache
import org.apache.commons.lang3.StringEscapeUtils

/**
 * This holds the information concerning an entry within a category
 * @author Joshua Monson
 * @param arabic The arabic text
 * @param english The english text
 * @param url The URL of the audio
 */
case class Entry(arabic: String, english: String, url: String) {

  /**
   * This generates and returns the HTML of the entry
   * @return The HTML code
   */
  def toHtml: String = {
    val data = Map(
      "arabic" -> arabic,
      "english" -> english,
      "url" -> url
    )
    StringEscapeUtils.unescapeHtml4(Entry.mustache.render(data))
  }
}

object Entry {
  val template =
    """
      |<div class="row">
      |	<div class="span8">
      |		<div class="well">
      |			<div class="word">{{english}}</div>
      |			<div class="pull-right word arabic">{{arabic}}</div>
      |			<div class="clearfix"></div>
      |			<audio src="{{url}}" type="audio/mp3" controls="controls">
      |			</audio>
      |		</div>
      |	</div>
      |</div>
    """.stripMargin
  val mustache = new Mustache(template)
}