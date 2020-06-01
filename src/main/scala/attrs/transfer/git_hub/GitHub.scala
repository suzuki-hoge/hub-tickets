package attrs.transfer.git_hub

import attrs.domain._
import command.domain.issue.OriginIssueClosing
import play.api.libs.json.{Json, Reads}

trait GitHub {
  def labels: String

  def assignees: String

  def milestones: String

  def issue(n: IssueNumber): String

  def create(t: Title, b: Body, l: LabelName, a: Option[AssigneeName], m: CurrentMilestoneNumber): String

  def comment(n: IssueNumber, message: String): Unit

  def close(oic: OriginIssueClosing): Unit

  def assign(n: IssueNumber, a: AssigneeName): Unit

  def create(m: Milestone): String
}

object GitHub {
  def di(b: Boolean): GitHub = if (b) GitHubApi else GitHubMock
}

case class $IssueNumber(number: Int) {
  def toAttrs: IssueNumber = IssueNumber(number)
}

object $IssueNumber {
  implicit val jsonReads: Reads[$IssueNumber] = Json.reads[$IssueNumber]
}

case class $GIssue(number: Int, title: String, body: String, labels: Seq[$Label], assignees: Seq[$Assignee])

object $GIssue {
  implicit val jsonReads: Reads[$GIssue] = Json.reads[$GIssue]
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

case class $Milestone(number: Int, title: String, due_on: String) {
  def toCurrent: CurrentMilestoneNumber = CurrentMilestoneNumber(number)

  def toNumber: MilestoneNumber = MilestoneNumber(number)
}

object $Milestone {
  implicit val jsonReads: Reads[$Milestone] = Json.reads[$Milestone]
}

case class $MilestoneNumber(number: Int) {
  def toAttrs: MilestoneNumber = MilestoneNumber(number)
}

object $MilestoneNumber {
  implicit val jsonReads: Reads[$MilestoneNumber] = Json.reads[$MilestoneNumber]
}
