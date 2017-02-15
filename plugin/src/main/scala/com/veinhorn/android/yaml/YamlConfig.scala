package com.veinhorn.android.yaml

import net.jcazevedo.moultingyaml.DefaultYamlProtocol._
import net.jcazevedo.moultingyaml._
import net.jcazevedo.moultingyaml.{YamlObject, YamlString}

object YamlConfig {
  def init(yaml: YamlObject): YamlConfig = {
    var usePrefixes = false
    yaml.fields(yaml.fields.keys.toList.head).asInstanceOf[YamlObject].getFields(YamlString("prefixes")).foreach { p =>
      usePrefixes = p.convertTo[Boolean]
    }
    val ok = "ok"
    YamlConfig(usePrefixes)
  }
}

case class YamlConfig(usePrefixes: Boolean) {

}