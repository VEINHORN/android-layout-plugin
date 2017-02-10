package com.veinhorn.android.view.yaml

import net.jcazevedo.moultingyaml.DefaultYamlProtocol._
import net.jcazevedo.moultingyaml.{YamlObject, _}

import scala.xml._


/**
  * Created by Boris Korogvich on 08.02.2017.
  */
object YamlTransformator {
  val Unknown: String = "Unknown"
  val DefaultNamespace = "android"
}

class YamlTransformator extends Transformator[String, String] {
  import YamlTransformator._

  override def transform(yaml: String): String = {
    val yamlView = yaml.parseYaml.asYamlObject
    new PrettyPrinter(100, 4).format(generate(yamlView))
  }

  @throws(classOf[Exception])
  private def generate(viewAst: YamlObject): Elem = viewAst.fields.keys.toList match {
    case fields: List[YamlValue] if fields.length == 1 => generateXml(unknown.copy(label = fields.head.convertTo[String]), viewAst.fields(fields.head).asYamlObject)
    case _ => throw new Exception("Root view doesn't exist")
  }

  private def generateXml(r: Elem, yamlView: YamlObject): Elem = {
    var root = r
    for (key <- yamlView.fields.keys) {
      val elmTitle = key.convertTo[String] // YAML element title

      yamlView.fields(key) match {
        case arr: YamlArray =>
          for (k <- arr.elements) {
            root = root.copy(child = root.child :+ generateXml(unknown.copy(label = elmTitle), k.asYamlObject))
          }
        case _ =>
          Character.isLowerCase(elmTitle.head) match {
            case true  => root = createAttribute(root, elmTitle, yamlView.fields(key)) // root % Attribute(None, elmTitle, Text(uniformType(yamlView.fields(key))), Null)
            case false => root = root.copy(child = root.child :+ generateXml(unknown.copy(label = elmTitle), yamlView.fields(key).asYamlObject))
          }
      }
    }
    root
  }

  /** Creates new XML element with new attribute based on root element */
  private def createAttribute(root: Elem, name: String, value: YamlValue): Elem =
    root % Attribute(None, createNamespace(name), Text(uniformType(value)), Null)

  private def createNamespace(name: String): String = name.split(":") match {
    case Array(namespace, title) => name
    case _                       => s"$DefaultNamespace:name"
  }

  /** Converts specific YAML lib types to the uniform string type that's used in Scala XML */
  private def uniformType(value: YamlValue): String = value match {
    case v: YamlNumber => v.convertTo[Int].toString
    case default => default.convertTo[String]
  }

  /** Creates unknown element */
  private def unknown: Elem = <a></a>.copy(label = Unknown)
}
