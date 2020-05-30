package attrs.domain

case class Milestone(name: MilestoneName, start: MilestoneStart, end: MilestoneEnd)

case class MilestoneName(v: String)

case class MilestoneStart(v: String)

case class MilestoneEnd(v: String)

case class CurrentMilestoneName(v: String) {
  def useThis: MilestoneName = MilestoneName(v)
}
