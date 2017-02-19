package com.veinhorn.android.yaml.feature

trait AttributeFeature extends Feature {
  def feature(value: String): String
}

object AttributeFeature {
  /** Attribute feature signs */
  val AndroidResourcePrefix: String = "^"
  /** */

  val IdPrefix: String = "@+id/"

  val DrawablePrefix: String = "@drawable/"
  val AndroidDrawablePrefix: String = "@android:drawable/"

  val IdAttr: String = "id"
  val SrcAttr: String = "src"

  // TODO: Improve feature detection algorithm
  def apply(title: String, key: String, value: String): String = key match {
    case IdAttr  => new IdFeature().feature(value)
    case SrcAttr => new SrcFeature().feature(value)
    case _      => value
  }

  class IdFeature extends AttributeFeature {
    override def feature(value: String): String =
      if (value.startsWith(IdPrefix)) value else IdPrefix + value
  }

  class SrcFeature extends AttributeFeature {
    override def feature(value: String): String =
      if (value.startsWith(DrawablePrefix)) value
      else if (value.startsWith(AndroidResourcePrefix)) addPrefix(AndroidDrawablePrefix, value)(AndroidResourcePrefix)
      else DrawablePrefix + value
  }

  private def addPrefix(prefix: String, value: String)(sign: String): String =
    prefix + value.replace(sign, "")
}