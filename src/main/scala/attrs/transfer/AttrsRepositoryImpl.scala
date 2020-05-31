package attrs.transfer

import attrs.domain._
import attrs.transfer.git_hub.{$Assignee, $Label, $Milestone, GitHub}
import attrs.transfer.zen_hub.{$Pipeline, ZenHub}
import play.api.libs.json.Json

case class AttrsRepositoryImpl(gitHub: GitHub, zenHub: ZenHub) extends AttrsRepository {
  override def create(milestone: Milestone): Unit = ???

  override def labels: Seq[Label] = Json.parse(gitHub.labels)
    .validate[Seq[$Label]].get
    .filter(_.using)
    .map(_.toAttrs)

  override def assignees: Seq[Assignee] = Json.parse(gitHub.assignees)
    .validate[Seq[$Assignee]].get
    .map(_.toAttrs)

  override def pipelines: Seq[Pipeline] = (Json.parse(zenHub.pipelines) \ "pipelines")
    .validate[Seq[$Pipeline]].get
    .map(_.toAttrs)

  override def currentMilestoneNumber: CurrentMilestoneNumber = Json.parse(gitHub.milestones)
    .validate[Seq[$Milestone]]
    .get.sortBy(_.due_on).reverse
    .map(_.toAttrs).head
}
