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

  it should "test YAML to XML android view transformation (multi ids)" in {
    val yaml = loadResource("multi_id_view.yaml")
    val xmlView = new YamlTransformator().transform(yaml)

    // TODO: Implement tags checking
    val xmlElm = XML.loadString(xmlView)
  }

  it should "test prefixes generation in attributes" in {
    val yaml = loadResource("with_prefixes.yaml")
    val xmlView = new YamlTransformator().transform(yaml)

    val xmlElm = XML.loadString(xmlView)
  }
}
