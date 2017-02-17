package com.veinhorn.android.yaml.test

import com.veinhorn.android.yaml.YamlTransformator
import com.veinhorn.android.yaml.feature.AttributeFeature.{IdFeature, SrcFeature}

import scala.xml.XML

/**
  * Created by veinhorn on 14.2.17.
  */
class ValueOptimizerSpec extends LayoutSpec {
  it should "test id attribute optimization" in {
    new IdFeature().feature("nameTextView") should equal("@+id/nameTextView")
    new IdFeature().feature("@+id/nameTextView") should equal("@+id/nameTextView")
  }

  it should "test src attribute optimization" in {
    new SrcFeature().feature("image.png") should equal("@drawable/image.png")
    new SrcFeature().feature("@drawable/image.png") should equal("@drawable/image.png")
  }

  it should "test prefixes optimization in generated XML layout" in {
    val yaml = loadResource("view_without_prefixes.yaml")
    val xmlView = new YamlTransformator().transform(yaml)

    val xmlElm = XML.loadString(xmlView)
    (xmlElm \ "@id").text should equal("@+id/parentView")
    (xmlElm \ "ImageView" \ "@src").text should equal("@drawable/image.png")
  }
}
