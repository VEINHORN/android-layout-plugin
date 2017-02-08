package com.veinhorn.android.view.yaml

import net.jcazevedo.moultingyaml._

object Main extends App {
  val source =
    """
      | LinearLayout:
      |   layout_weight: match_parent
      |   layout_width: match_parent
      |
      |   ListView:
      |     id: myListView
      |
      |   TextView:
      |     id: myTextView
      |     text: "some text"
      |
      |   RelativeLayout:
      |     layout_weight: match_parent
      |     layout_width: match_parent
      |
      |     TextView:
      |       id: nameTextView
      |       text: 21
      |
    """.stripMargin

  val layoutAst = source.parseYaml.asYamlObject
}
