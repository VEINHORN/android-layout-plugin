package com.veinhorn.android.yaml

/**
  * Created by veinhorn on 14.2.17.
  */
object ValueOptimizer {
  val IdPrefix: String = "@+id/"
  val SrcPrefix: String = "@drawable/"

  val IdAttr: String = "id"
  val SrcAttr: String = "src"

  def optimize(key: String, value: String): String = key match {
    case IdAttr  => IdOptimizer(value).optimize()
    case SrcAttr => SrcOptimizer(value).optimize()
    case _       => value
  }

  trait Optimizer {
    def optimize(): String
  }

  case class IdOptimizer(value: String) extends Optimizer {
    override def optimize(): String = addPrefix(value, IdPrefix)
  }

  // TODO: Improve for "@drawable/ and @android:drawable/"
  case class SrcOptimizer(value: String) extends Optimizer {
    override def optimize(): String = addPrefix(value, SrcPrefix)
  }

  private def addPrefix(value: String, prefix: String) =
    if (value.contains(prefix)) value else prefix + value
}
