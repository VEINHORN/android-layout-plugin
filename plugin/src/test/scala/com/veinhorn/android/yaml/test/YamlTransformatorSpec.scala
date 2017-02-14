package com.veinhorn.android.yaml.test

import com.veinhorn.android.yaml.YamlTransformator

import scala.xml.XML

/**
  * Created by veinhorn on 14.2.17.
  */
class YamlTransformatorSpec extends LayoutSpec {
  it should "test YAML to XML android view transformation" in {
    val yaml = loadResource("activity_main.yaml")
    val xmlView = new YamlTransformator().transform(yaml)

    val xmlElm = XML.loadString(xmlView)
    (xmlElm \ "@layout_width").text should equal("match_parent")
    (xmlElm \ "@layout_height").text should equal("match_parent")
  }
}
