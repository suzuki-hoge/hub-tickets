package attrs.transfer.git_hub

import attrs.domain._
import play.api.libs.json.{Json, Reads}

trait GitHub {
  def fetchLabels: String

  def fetchAssignees: String

  def fetchMilestones: String

  def create(t: Title, b: Body, l: LabelName, a: Option[AssigneeName]): String
}

case class $IssueNumber(number: Int) {
  def toAttrs: IssueNumber = IssueNumber(number)
}

object $IssueNumber {
  implicit val jsonReads: Reads[$IssueNumber] = Json.reads[$IssueNumber]
}

case class $Label(name: String, color: String) {
  def using: Boolean = name.contains(" - ")

  def toAttrs: Label = Label(LabelName(name), LabelColor(color))
}

object $Label {
  implicit val jsonReads: Reads[$Label] = Json.reads[$Label]
}

case class $Assignee(login: String, avatar_url: String) {
  def toAttrs: Assignee = Assignee(AssigneeName(login), AssigneeIcon(avatar_url.split("\\?")(0)))
}

object $Assignee {
  implicit val jsonReads: Reads[$Assignee] = Json.reads[$Assignee]
}

case class $Milestone(title: String, due_on: String) {
  def toAttrs: CurrentMilestoneName = CurrentMilestoneName(title)
}

object $Milestone {
  implicit val jsonReads: Reads[$Milestone] = Json.reads[$Milestone]
}
