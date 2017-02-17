package com.veinhorn.android.yaml.feature

import com.veinhorn.android.yaml.Transformator
import net.jcazevedo.moultingyaml.DefaultYamlProtocol._
import net.jcazevedo.moultingyaml._

/**
  * ElementFeature works as a transformator from YamlObject to more complex YamlObject
  */
trait ElementFeature extends Feature with Transformator[YamlObject, YamlObject]

/** For now it's element feature, based on element title */
object ElementFeature {
  /**
    * Try to apply feature to the element
    * @param title is element title
    * @param yaml is an input YAML object
    * @param success is a generator function, based on transformed YAML object
    * @param otherwise is a basic generator function, non-feature variant
    * @tparam T generator result
    */
  // TODO: Improve readability
  def apply[T](title: String, yaml: YamlObject)(success: YamlObject => T)(otherwise: YamlObject => T): T = {
    val id = yaml.getFields(YamlString("id"))
    if (id.nonEmpty && id.head.convertTo[String].matches("[(].*[)]"))
      success(new MultiIdFeature(title).transform(yaml))
    else otherwise(yaml)
  }

  private class MultiIdFeature(elmTitle: String) extends ElementFeature {
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
}
