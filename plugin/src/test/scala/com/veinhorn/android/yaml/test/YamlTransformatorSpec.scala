package com.veinhorn.android.yaml.test

import com.veinhorn.android.yaml.YamlTransformator

import scala.xml.XML

/**
  * Created by veinhorn on 14.2.17.
  */
class YamlTransformatorSpec extends LayoutSpec {
  it should "test YAML to XML android view transformation" in {
    val yaml = loadYamlResource("activity_main")
    val xmlView = new YamlTransformator().transform(yaml)

    val xmlElm = XML.loadString(xmlView)
    (xmlElm \ "@layout_width").text should equal("match_parent")
    (xmlElm \ "@layout_height").text should equal("match_parent")
  }

  it should "test multi ids feature" in {
    val yaml = loadYamlResource("multi_id_view")
    val xmlView = new YamlTransformator().transform(yaml)

    // TODO: Implement tags checking
    val xmlElm = XML.loadString(xmlView)
  }

  it should "test prefixes generation in attributes" in {
    val yaml = loadYamlResource("with_prefixes")
    val xmlView = new YamlTransformator().transform(yaml)

    val xmlElm = XML.loadString(xmlView)
  }

  it should "test attribute features" in {
    val yaml = loadYamlResource("attribute_features")
    val xmlView = new YamlTransformator().transform(yaml)

    val xmlElm = XML.loadString(xmlView)
  }
}
