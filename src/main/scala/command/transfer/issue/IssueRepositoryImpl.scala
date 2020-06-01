package command.transfer.issue

import attrs.domain._
import attrs.transfer.git_hub.{$GIssue, $IssueNumber, GitHub}
import attrs.transfer.zen_hub.{$Estimate, $PipelineId, ZenHub}
import command.domain.issue._
import play.api.libs.json.Json

case class IssueRepositoryImpl(gitHub: GitHub, zenHub: ZenHub) extends IssueRepository {
  override def findOne(n: IssueNumber): Issue = {
    val g = Json.parse(gitHub.issue(n))
      .validate[$GIssue].get

    val p = (Json.parse(zenHub.pipeline(n)) \ "pipeline")
      .validate[$PipelineId].get

    val e = (Json.parse(zenHub.estimate(n)) \ "estimate")
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

  override def create(req: CreateRequest): IssueNumber = create(req.creation)

  override def copy(req: CopyRequest): IssueNumber = create(req.creation)

  override def cut(req: CutRequest): IssueNumber = {
    val dstN = create(req.creation)

    gitHub.comment(req.comment.srcN, req.comment.to(dstN))
    gitHub.comment(dstN, req.comment.from)
    zenHub.subtraction(req.sub)
    req.closing.foreach(gitHub.close)

    dstN
  }

  private def create(c: Creation): IssueNumber = {
    val n = Json.parse(
      gitHub.create(c.t, c.b.getOrElse(Body("")), c.l, c.a, c.m)
    ).validate[$IssueNumber].get.toAttrs

    zenHub.setPipeline(n, c.p)
    zenHub.setEstimate(n, c.e)

    n
  }

  override def assign(req: AssignRequest): Unit = gitHub.assign(req.n, req.a)
}
