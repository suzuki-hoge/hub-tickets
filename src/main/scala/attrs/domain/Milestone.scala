package attrs.domain

case class CurrentMilestoneNumber(v: Int)

case class Milestone(name: MilestoneName, start: MilestoneStart, end: MilestoneEnd)

case class MilestoneNumber(v: Int)

case class MilestoneName(v: String)

case class MilestoneStart(v: String)

case class MilestoneEnd(v: String)
