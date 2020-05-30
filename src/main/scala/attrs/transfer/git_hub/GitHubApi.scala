package attrs.transfer.git_hub

import attrs.domain._

object GitHubApi extends GitHub {
  override def labels: String = ???

  override def assignees: String = ???

  override def milestones: String = ???

  override def issue: String = ???

  override def create(t: Title, b: Body, l: LabelName, a: Option[AssigneeName]): String = ???
}
