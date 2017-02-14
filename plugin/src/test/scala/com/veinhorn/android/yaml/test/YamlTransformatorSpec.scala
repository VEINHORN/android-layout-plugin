package com.veinhorn.android.yaml.test

import com.veinhorn.android.yaml.YamlTransformator
import org.apache.commons.io.IOUtils
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

/**
  * Created by veinhorn on 14.2.17.
  */
class YamlTransformatorSpec extends FlatSpec with Matchers {
  it should "test YAML to XML android view transformation" in {
    val yaml = loadResource("activity_main.yaml")
    val xmlView = new YamlTransformator().transform(yaml)

    val xmlElm = XML.loadString(xmlView)
    (xmlElm \ "@layout_width").text should equal("match_parent")
    (xmlElm \ "@layout_height").text should equal("match_parent")
  }

  private def loadResource(resource: String, encoding: String = "UTF-8") =
    IOUtils.toString(getClass.getClassLoader.getResourceAsStream(resource), encoding)
}
