package attrs.domain

case class Label(name: LabelName, color: LabelColor)

case class LabelName(v: String) {
  def group: String = v.split("-")(0).trim

  def detail: String = v.split("-")(1).trim
}

case class LabelColor(v: String)
