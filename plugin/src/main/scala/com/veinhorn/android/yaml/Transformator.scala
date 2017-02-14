package com.veinhorn.android.yaml

/**
  * Created by Boris Korogvich on 08.02.2017.
  */
trait Transformator[I, O] {
  def transform(input: I): O
}
