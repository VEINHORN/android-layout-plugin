package com.veinhorn.android.yaml.test

import org.apache.commons.io.IOUtils
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by veinhorn on 14.2.17.
  */
class LayoutSpec extends FlatSpec with Matchers {
  def loadResource(resource: String, encoding: String = "UTF-8") =
    IOUtils.toString(getClass.getClassLoader.getResourceAsStream(resource), encoding)
}
