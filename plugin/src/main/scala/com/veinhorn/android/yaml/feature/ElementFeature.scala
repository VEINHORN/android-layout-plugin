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
  private val Id: YamlString = YamlString("id")

  /** Tries to apply feature or just call otherwise function */
  def apply[T](title: String, yaml: YamlObject)(success: YamlObject => T)(otherwise: YamlObject => T): T = {
    if (yaml.fields.contains(Id) && yaml.getFields(Id).nonEmpty) {
      def newFeature(fromArray: Boolean) = new MultiIdFeature(title, fromArray).transform(yaml)
      def isStringIds(id: YamlString) = id.convertTo[String].matches("[(].*[)]")

      yaml.getFields(Id).head match {
        case _:   YamlArray                      => return success(newFeature(true))
        case sId: YamlString if isStringIds(sId) => return success(newFeature(false))
        case _                                   =>
      }
    }
    otherwise(yaml)
  }

  private class MultiIdFeature(elmTitle: String, fromArray: Boolean) extends ElementFeature {
    override def transform(yaml: YamlObject): YamlObject = {
      val ids = getIds(yaml)
      YamlObject(
        YamlString(elmTitle) -> YamlArray(
          ids.map { id =>
            YamlObject(
              yaml.fields.filter(_._1 != Id) + (Id -> YamlString(id))
            )
          }.toVector
        )
      )
    }

    private def getIds(yaml: YamlObject): Array[String] = fromArray match {
      case true =>
        yaml.getFields(Id).head.convertTo[Array[String]]
      case _    =>
        yaml.getFields(Id).head.convertTo[String].replaceAll("[()]", "").split("[ ,]+")
    }
  }
}
