package com.veinhorn.android.yaml.test

import org.apache.commons.io.IOUtils
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by veinhorn on 14.2.17.
  */
class LayoutSpec extends FlatSpec with Matchers {
  def loadYamlResource(resource: String, encoding: String = "UTF-8"): String =
    loadResource(resource + ".yaml", encoding)

  def loadResource(resource: String, encoding: String = "UTF-8"): String =
    IOUtils.toString(getClass.getClassLoader.getResourceAsStream(resource), encoding)
}
