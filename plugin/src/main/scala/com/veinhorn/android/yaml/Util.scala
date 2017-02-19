package com.veinhorn.android.yaml

import scala.xml.Elem

object Util {
  def newElm(title: String): Elem = newElm.copy(label = title)

  private def newElm: Elem = <Empty></Empty>.copy(minimizeEmpty = true)
}