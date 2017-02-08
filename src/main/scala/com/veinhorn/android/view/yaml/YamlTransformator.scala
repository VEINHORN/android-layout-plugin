package com.veinhorn.android.view.yaml

import net.jcazevedo.moultingyaml.DefaultYamlProtocol._
import net.jcazevedo.moultingyaml.{YamlObject, _}

import scala.xml._


/**
  * Created by Boris Korogvich on 08.02.2017.
  */
object YamlTransformator {
  val Unknown: String = "Unknown"
}

class YamlTransformator extends Transformator[String, String] {
  import YamlTransformator.Unknown

  override def transform(yaml: String): String = {
    val yamlView = yaml.parseYaml.asYamlObject
    new PrettyPrinter(100, 4).format(generateXml(yamlView))
  }

  @throws(classOf[Exception])
  private def generateXml(viewAst: YamlObject): Elem = {
    val keys = viewAst.fields.keys.toList
    if (keys.length != 1) throw new Exception("Root view doesn't exist")
    generateXml(unknown.copy(label = keys.head.convertTo[String]), viewAst.fields(keys.head).asYamlObject)
  }

  private def generateXml(r: Elem, yamlView: YamlObject): Elem = {
    var root = r
    // TODO: Reduce code size
    for (key <- yamlView.fields.keys) {
      val elmTitle = key.convertTo[String]
      Character.isLowerCase(elmTitle.head) match {
        // TODO: Fix bug with type transformation
        case true  => root = root % Attribute(None, elmTitle, Text(uniformType(yamlView.fields(key))), Null)
        case false => root = root.copy(child = root.child :+ generateXml(unknown.copy(label = elmTitle), yamlView.fields(key).asYamlObject))
      }
    }
    root
  }

  /** Converts specific YAML lib types to the uniform string type that's used in Scala XML */
  private def uniformType(value: YamlValue): String = value match {
    case v: YamlString => v.convertTo[String]
    case v: YamlNumber => v.convertTo[Int].toString
  }

  /** Creates unknown element */
  private def unknown: Elem = <a></a>.copy(label = Unknown)
}
