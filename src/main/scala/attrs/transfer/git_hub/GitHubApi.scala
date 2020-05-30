package attrs.transfer.git_hub

import attrs.domain._

object GitHubApi extends GitHub {
  override def fetchLabels: String = ???

  override def fetchAssignees: String = ???

  override def fetchMilestones: String = ???

  override def issue: String = ???

  override def create(t: Title, b: Body, l: LabelName, a: Option[AssigneeName]): String = ???
}
