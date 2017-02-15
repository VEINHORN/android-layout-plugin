package com.veinhorn.android.yaml.features

import com.veinhorn.android.yaml.Feature
import net.jcazevedo.moultingyaml.DefaultYamlProtocol._
import net.jcazevedo.moultingyaml._

class MultiIdFeature(elmTitle: String) extends Feature {
  override def transform(yaml: YamlObject): YamlObject = {
    val ids: Array[String] = yaml.getFields(YamlString("id")).head.convertTo[String].replaceAll("[()]", "").split("[ ,]+")
    // if (ids.length == 0) // TODO: Remove braces in case of length = 0

    YamlObject(
      YamlString(elmTitle) -> YamlArray(
        ids.map { id =>
          YamlObject(
            yaml.fields.filter(_._1 != YamlString("id")) + (YamlString("id") -> YamlString(id))
          )
        }.toVector
      )
    )
  }
}
