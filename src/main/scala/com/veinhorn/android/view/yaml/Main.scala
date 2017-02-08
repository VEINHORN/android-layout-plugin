package com.veinhorn.android.view.yaml

import net.jcazevedo.moultingyaml.DefaultYamlProtocol._
import net.jcazevedo.moultingyaml._

import scala.xml._

object Main extends App {
  val source =
    """
      | LinearLayout:
      |   layout_weight: match_parent
      |   layout_width: match_parent
      |
      |   ListView:
      |     id: myListView
      |
      |   TextView:
      |     id: myTextView
      |     text: "some text"
      |
      |   RelativeLayout:
      |     layout_weight: match_parent
      |     layout_width: match_parent
      |
      |     TextView:
      |       id: nameTextView
      |       text: 21
      |
    """.stripMargin

  val layoutAst = source.parseYaml.asYamlObject

  // for (key <- layoutAst.fields.keys) println(layoutAst.fields(key))

  val xmlView = generateXml(layoutAst)
  val printer = new PrettyPrinter(80, 4)
  val res = printer.format(xmlView)
  println(res)

  @throws(classOf[Exception])
  def generateXml(layoutAst: YamlObject): Elem = {
    val keys = layoutAst.fields.keys.toList
    if (keys.length != 1) throw new Exception("Cannot convert YAML into XML")
    val parent = <root></root>.copy(label = keys.head.convertTo[String])

    generateXml(parent, layoutAst.fields(keys.head).asYamlObject)
  }

  /** Recursivly generate xml from  */
  def generateXml(r: Elem, layot: YamlObject): Elem = {
    var root = r
    for (key <- layot.fields.keys) {
      val title = key.convertTo[String].head
      println(title)
      if (Character.isLowerCase(title))
        root = root % Attribute(None, key.convertTo[String], Text("11"), Null)
      else
        root = root.copy(child = root.child :+ generateXml(<yo></yo>.copy(label = key.convertTo[String]), layot.fields(key).asYamlObject))
    }
    root
  }

  println("test")
}
