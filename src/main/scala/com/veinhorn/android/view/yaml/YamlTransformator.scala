package com.veinhorn.android.view.yaml

import net.jcazevedo.moultingyaml.DefaultYamlProtocol._
import net.jcazevedo.moultingyaml.{YamlObject, _}

import scala.xml._


/**
  * Created by Boris Korogvich on 08.02.2017.
  */
object YamlTransformator {
  val Empty: String = "Empty"
  val DefaultNamespace = "android"
}

class YamlTransformator extends Transformator[String, String] {
  import YamlTransformator._

  override def transform(yaml: String): String = {
    val yamlView = yaml.parseYaml.asYamlObject
    new PrettyPrinter(200, 4).format(generate(yamlView))
  }

  @throws(classOf[Exception])
  private def generate(viewAst: YamlObject): Elem = viewAst.fields.keys.toList match {
    case fields: List[YamlValue] if fields.length == 1 => generateXml(newElm.copy(label = fields.head.convertTo[String]), viewAst.fields(fields.head).asYamlObject)
    case _ => throw new Exception("Root view doesn't exist")
  }

  /** Recursively converts YAML view to the XML */
  private def generateXml(r: Elem, yamlView: YamlObject): Elem = {
    yamlView.fields.keys.foldLeft(r) { (root, key) =>
      val elmTitle = key.convertTo[String] // YAML element title
      yamlView.fields(key) match {
        case array: YamlArray => fromArray(root, elmTitle, array)
        case _                => fromObject(root, elmTitle, key, yamlView)
      }
    }
  }

  /** It's some kind of YamlArray -> Elem recursive conversion */
  private def fromArray(root: Elem, title: String, array: YamlArray) =
    array.elements.foldLeft(root)((r, elm) => createElement(r, title, elm.asYamlObject))

  /** It's some kind of YamlObject -> Elem recursive conversion */
  private def fromObject(root: Elem, title: String, key: YamlValue, yamlView: YamlObject): Elem = Character.isLowerCase(title.head) match {
    case true  => createAttribute(root, title, yamlView.fields(key))
    case false => createElement(root, title, yamlView.fields(key).asYamlObject)
  }

  private def createElement(root: Elem, name: String, yamlView: YamlObject): Elem =
    root.copy(child = root.child :+ generateXml(newElm.copy(label = name), yamlView))

  /** Creates new XML element with new attribute based on root element */
  private def createAttribute(root: Elem, name: String, value: YamlValue): Elem =
    root % Attribute(None, createNamespace(name), Text(uniformType(value)), Null)

  /** Creates XML namespace based on title, or "android" as the default */
  private def createNamespace(name: String): String = name.split(":") match {
    case Array(namespace, title) => name
    case _                       => s"$DefaultNamespace:$name"
  }

  /** Converts specific YAML lib types to the uniform string type that's used in Scala XML */
  private def uniformType(value: YamlValue): String = value match {
    case v: YamlNumber => v.convertTo[Int].toString
    case default => default.convertTo[String]
  }

  /** Just creates new empty element */
  private def newElm: Elem = <x></x>.copy(label = Empty)
}
