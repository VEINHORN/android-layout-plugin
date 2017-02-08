package com.veinhorn.android.view.yaml.test

import com.veinhorn.android.view.yaml.YamlTransformator
import org.apache.commons.io.IOUtils
import org.scalatest.FlatSpec

/**
  * Created by Boris Korogvich on 08.02.2017.
  */
class YamlConverterSpec extends FlatSpec {
  it should "test android view convertion from YAML to XML" in {
    val transformator = new YamlTransformator()
    val input = getClass.getClassLoader.getResourceAsStream("activity_main.yaml")
    val yaml = loadFile("activity_main.yaml")
    println("test")
    // transformator.transform()
  }

  private def loadFile(resource: String): String =
    IOUtils.toString(getClass.getClassLoader.getResourceAsStream(resource), "UTF-8")
}
