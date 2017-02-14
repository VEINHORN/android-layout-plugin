package com.veinhorn.android.yaml.test

import com.veinhorn.android.yaml.ValueOptimizer.{IdOptimizer, SrcOptimizer}
import com.veinhorn.android.yaml.YamlTransformator

import scala.xml.XML

/**
  * Created by veinhorn on 14.2.17.
  */
class ValueOptimizerSpec extends LayoutSpec {
  it should "test id attribute optimization" in {
    IdOptimizer("nameTextView").optimize() should equal("@+id/nameTextView")
    IdOptimizer("@+id/nameTextView").optimize() should equal("@+id/nameTextView")
  }

  it should "test src attribute optimization" in {
    SrcOptimizer("image.png").optimize() should equal("@drawable/image.png")
    SrcOptimizer("@drawable/image.png").optimize() should equal("@drawable/image.png")
  }

  it should "test prefixes optimization in generated XML layout" in {
    val yaml = loadResource("view_without_prefixes.yaml")
    val xmlView = new YamlTransformator().transform(yaml)

    val xmlElm = XML.loadString(xmlView)
    (xmlElm \ "@id").text should equal("@+id/parentView")
    (xmlElm \ "ImageView" \ "@src").text should equal("@drawable/image.png")
  }
}
