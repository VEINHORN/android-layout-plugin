package com.veinhorn.android.view.yaml.test

import com.veinhorn.android.view.yaml.YamlTransformator
import org.apache.commons.io.IOUtils
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

/**
  * Created by Boris Korogvich on 08.02.2017.
  */
class YamlConverterSpec extends FlatSpec with Matchers {
  it should "convert complex YAML Android view into proper XML" in {
    val yaml = loadFile("activity_main.yaml")
    val xmlView = new YamlTransformator().transform(yaml)

    val xmlElm = XML.loadString(xmlView)
    (xmlElm \ "@layout_width").text should equal("match_parent")
    (xmlElm \ "@layout_height").text should equal("match_parent")
  }

  private def loadFile(resource: String): String =
    IOUtils.toString(getClass.getClassLoader.getResourceAsStream(resource), "UTF-8")
}
