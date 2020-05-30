package attrs.transfer

import attrs.domain._
import attrs.transfer.git_hub.{$Assignee, $Label, $Milestone, GitHub}
import attrs.transfer.zen_hub.{$Pipeline, ZenHub}
import play.api.libs.json.Json

case class AttrsRepositoryImpl(gitHub: GitHub, zenHub: ZenHub) extends AttrsRepository {
  override def create(milestone: Milestone): Unit = ???

  override def labels: Seq[Label] = Json.parse(gitHub.fetchLabels)
    .validate[Seq[$Label]].get
    .filter(_.using)
    .map(_.toAttrs)

  override def assignees: Seq[Assignee] = Json.parse(gitHub.fetchAssignees)
    .validate[Seq[$Assignee]].get
    .map(_.toAttrs)

  override def pipelines: Seq[Pipeline] = (Json.parse(zenHub.fetchPipelines) \ "pipelines")
    .validate[Seq[$Pipeline]].get
    .map(_.toAttrs)

  override def currentMilestoneName: CurrentMilestoneName = Json.parse(gitHub.fetchMilestones)
    .validate[Seq[$Milestone]]
    .get.sortBy(_.due_on).reverse
    .map($m => CurrentMilestoneName($m.title)).head
}
