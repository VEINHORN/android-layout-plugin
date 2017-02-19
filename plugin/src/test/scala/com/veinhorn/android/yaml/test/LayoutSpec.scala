package com.veinhorn.android.yaml.test

import org.apache.commons.io.IOUtils
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by veinhorn on 14.2.17.
  */
object LayoutSpec {
  val Yaml: String = ".yaml"
}

class LayoutSpec extends FlatSpec with Matchers {
  import LayoutSpec._

  def loadYamlResource(resource: String, encoding: String = "UTF-8"): String =
    loadResource(resource + Yaml, encoding)

  def loadResource(resource: String, encoding: String = "UTF-8"): String =
    IOUtils.toString(getClass.getClassLoader.getResourceAsStream(resource), encoding)
}
