package com.veinhorn.android.yaml

import net.jcazevedo.moultingyaml.DefaultYamlProtocol._
import net.jcazevedo.moultingyaml._
import net.jcazevedo.moultingyaml.{YamlObject, YamlString}

// TODO: Add "namespaces" default option, if not specify, add default android namespaces
object YamlConfig {
  def initialize(yaml: YamlObject): YamlConfig = {
    var usePrefixes = false
    yaml.fields(yaml.fields.keys.toList.head).asInstanceOf[YamlObject].getFields(YamlString("prefixes")).foreach { p =>
      usePrefixes = p.convertTo[Boolean]
    }
    YamlConfig(usePrefixes)
  }
}

case class YamlConfig(usePrefixes: Boolean) {

}