package com.veinhorn.android.yaml

import scala.xml.Elem

object Util {
  private val Empty: String = "Empty"

  def newElm: Elem = <x></x>.copy(label = Empty)
}