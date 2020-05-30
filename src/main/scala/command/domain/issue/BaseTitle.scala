package command.domain.issue

import attrs.domain.Title

case class BaseTitle(v: String) {
  def join(t: Title): Title = Title(s"$v - ${t.v}")
}
