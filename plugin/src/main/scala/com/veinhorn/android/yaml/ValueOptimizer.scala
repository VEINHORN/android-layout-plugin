package com.veinhorn.android.yaml

/**
  * Created by veinhorn on 14.2.17.
  */
object ValueOptimizer {
  val IdPrefix: String = "@+id/"

  val IdAttr: String = "id"

  def optimize(key: String, value: String): String = key match {
    case IdAttr => IdOptimizer(value).optimize()
    case _      => value
  }

  trait Optimizer {
    def optimize(): String
  }

  case class IdOptimizer(value: String) extends Optimizer {
    override def optimize(): String = if (value.contains(IdPrefix)) value else IdPrefix + value
  }
}
