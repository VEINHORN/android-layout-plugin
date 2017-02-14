package com.veinhorn.android.yaml.test

import com.veinhorn.android.yaml.ValueOptimizer.IdOptimizer
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by veinhorn on 14.2.17.
  */
class ValueOptimizerSpec extends FlatSpec with Matchers {
  it should "test id attribute optimization" in {
    IdOptimizer("nameTextView").optimize() should equal("@+id/nameTextView")
    IdOptimizer("@+id/nameTextView").optimize() should equal("@+id/nameTextView")
  }
}
