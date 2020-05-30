package command.transfer.issue

import attrs.domain._
import attrs.transfer.git_hub.{$GIssue, $IssueNumber, GitHub}
import attrs.transfer.zen_hub.{$Estimate, $PipelineId, ZenHub}
import command.domain.issue._
import play.api.libs.json.Json

case class IssueRepositoryImpl(gitHub: GitHub, zenHub: ZenHub) extends IssueRepository {
  override def findOne(n: IssueNumber): Issue = {
    val g = Json.parse(gitHub.issue)
      .validate[$GIssue].get

    val p = (Json.parse(zenHub.setPipeline(n)) \ "pipeline")
      .validate[$PipelineId].get

    val e = (Json.parse(zenHub.setEstimate(n)) \ "estimate")
      .validate[$Estimate].get

    Issue(
      IssueNumber(g.number),
      Title(g.title),
      Body(g.body),
      g.labels.head.toAttrs.name,
      g.assignees.headOption.map(_.toAttrs.name),
      p.toAttrs,
      e.toAttrs
    )
  }

  override def create(req: CreateRequest): IssueNumber = {
    val n = Json.parse(
      gitHub.create(req.creation.t, req.creation.b.getOrElse(Body("")), req.creation.l, req.creation.a.getOrElse(AssigneeName("")))
    ).validate[$IssueNumber].get.toAttrs

    zenHub.setPipeline(n, req.creation.p)
    zenHub.setEstimate(n, req.creation.e)

    n
  }

  override def copy(req: CopyRequest): IssueNumber = {
    println(req)

    IssueNumber(1)
  }

  override def cut(req: CutRequest): IssueNumber = {
    println(req)

    IssueNumber(1)
  }
}
