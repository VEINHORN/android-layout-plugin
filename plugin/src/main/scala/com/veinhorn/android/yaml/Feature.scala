package com.veinhorn.android.yaml

import net.jcazevedo.moultingyaml.YamlObject

/**
  * Feature works as transformator from YamlObject to more complex YamlObject
  */
trait Feature extends Transformator[YamlObject, YamlObject] {

}