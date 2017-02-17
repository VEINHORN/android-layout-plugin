package com.veinhorn.android.yaml

import com.veinhorn.android.yaml.feature.{AttributeFeature, ElementFeature}
import net.jcazevedo.moultingyaml.DefaultYamlProtocol._
import net.jcazevedo.moultingyaml._

import scala.xml._


/**
  * Created by Boris Korogvich on 08.02.2017.
  */
object YamlTransformator {
  val DefaultNamespace: String = "android"
}

class YamlTransformator(var config: Option[YamlConfig] = None) extends Transformator[String, String] {
  import Util._
  import YamlTransformator._

  /** Used mostly for tests */
  def this(config: YamlConfig) = {
    this(Option(config))
  }

  override def transform(yaml: String): String = {
    val yamlView = yaml.parseYaml.asYamlObject
    new PrettyPrinter(200, 4).format(generate(yamlView))
  }

  @throws(classOf[Exception])
  private def generate(viewAst: YamlObject): Elem = viewAst.fields.keys.toList match {
    case fields: List[YamlValue] if fields.length == 1 =>
      if (config.isEmpty) config = Some(YamlConfig.init(viewAst))
      // TODO: Remove config elements from YAML
      generateXml(newElm.copy(label = fields.head.convertTo[String]), viewAst.fields(fields.head).asYamlObject)
    case _ => throw new Exception("Root view doesn't exist")
  }

  private def generateXml(r: Elem, yamlView: YamlObject): Elem = {
    yamlView.fields.keys.foldLeft(r) { (root, key) =>
      val elmTitle = key.convertTo[String] // YAML element title
      yamlView.fields(key) match {
        case array: YamlArray => fromArray(root, elmTitle, array)
        case _                => fromObject(root, elmTitle, yamlView)
      }
    }
  }

  private def fromArray(root: Elem, title: String, array: YamlArray) =
    array.elements.foldLeft(root) { (r, elm) =>
      fromObject(r, title, YamlObject(YamlString(title) -> elm))
    }

  private def fromObject(root: Elem, title: String, yamlView: YamlObject): Elem = isAttribute(title) match {
    case true  =>
      createAttribute(root, title, yamlView.fields(YamlString(title)))
    case false =>
      val yaml = yamlView.fields(YamlString(title)).asYamlObject
      ElementFeature.apply(title, yaml)(generateXml(root, _))(createElement(root, title, _))
  }

  private def isAttribute(title: String): Boolean =
    if (Character.isLowerCase(title.head)) true else false

  private def createElement(root: Elem, name: String, yamlView: YamlObject): Elem =
    root.copy(child = root.child :+ generateXml(newElm.copy(label = name), yamlView))

  private def createAttribute(root: Elem, name: String, value: YamlValue): Elem = {
    // TODO: Remove prefixes element from YAML on config init stage
    // Filter configuration elements
    if (name == "prefixes") root
    else {
      val optimizedValue = AttributeFeature.apply(root.label, name, uniformType(value))// ValueOptimizer.optimize(name, uniformType(value))
      root % Attribute(None, createNamespace(name), Text(optimizedValue), Null)
    }
  }

  private def createNamespace(name: String): String = name.split(":") match {
    case Array(namespace, title) => name
    case _                       => if (config.map(_.usePrefixes).getOrElse(false)) s"$DefaultNamespace:$name" else name
  }

  private def uniformType(value: YamlValue): String = value match {
    case v: YamlNumber => v.convertTo[Int].toString
    case default => default.convertTo[String]
  }
}
