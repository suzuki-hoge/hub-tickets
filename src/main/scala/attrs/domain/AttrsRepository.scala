package attrs.domain

trait AttrsRepository {
  // refer

  def labels: Seq[Label]

  def assignees: Seq[Assignee]

  def pipelines: Seq[Pipeline]

  def currentMilestoneName: CurrentMilestoneName

  // create

  def create(milestone: Milestone): Unit
}
