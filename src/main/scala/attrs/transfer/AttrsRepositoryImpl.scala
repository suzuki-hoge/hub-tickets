package attrs.transfer

import attrs.domain._
import attrs.transfer.git_hub._
import attrs.transfer.zen_hub.{$Pipeline, ZenHub}
import play.api.libs.json.Json

case class AttrsRepositoryImpl(gitHub: GitHub, zenHub: ZenHub) extends AttrsRepository {
  override def labels: Seq[Label] = Json.parse(gitHub.labels)
    .validate[Array[$Label]].get
    .filter(_.using)
    .map(_.toAttrs)

  override def assignees: Seq[Assignee] = Json.parse(gitHub.assignees)
    .validate[Array[$Assignee]].get
    .map(_.toAttrs)

  override def pipelines: Seq[Pipeline] = (Json.parse(zenHub.pipelines) \ "pipelines")
    .validate[Array[$Pipeline]].get
    .map(_.toAttrs)

  override def currentMilestoneNumber: CurrentMilestoneNumber = Json.parse(gitHub.milestones)
    .validate[Array[$Milestone]]
    .get.sortBy(_.due_on).reverse
    .map(_.toCurrent).head

  override def create(m: Milestone): Option[MilestoneNumber] = {
    val mn = Json.parse(gitHub.milestones)
      .validate[Array[$Milestone]].get
      .find(_.title == m.name.v)
      .map(_.toNumber)

    if (mn.isEmpty) {
      val mn = Json.parse(gitHub.create(m)).validate[$MilestoneNumber].get.toAttrs
      zenHub.setStart(mn, m)
      Some(mn)
    }
    else
      None
  }
}
