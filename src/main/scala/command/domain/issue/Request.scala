package command.domain.issue

import attrs.domain._

case class CreateRequest(creation: Creation)

object CreateRequest {
  def of(reqT: Title, reqB: Option[Body], reqL: LabelName, reqA: Option[AssigneeName], reqP: Option[PipelineId], reqE: Estimate, defP: DefaultPipelineId, cmn: CurrentMilestoneNumber): CreateRequest = CreateRequest(
    Creation(reqT, reqB, reqL, reqA, reqP getOrElse defP.useThis, cmn, reqE)
  )
}

case class CopyRequest(creation: Creation)

case class CutRequest(creation: Creation, comment: Comment, sub: EstimateSubtraction, closing: Option[OriginIssueClosing])

case class Creation(t: Title, b: Option[Body], l: LabelName, a: Option[AssigneeName], p: PipelineId, m: CurrentMilestoneNumber, e: Estimate)

case class Comment(srcN: IssueNumber, action: String, dstE: Estimate) {
  def from: String = s"$action from #${srcN.v}"

  def to(dstN: IssueNumber): String = s"$action to #${dstN.v} ( ${dstE.v} sp )"
}

case class EstimateSubtraction(n: IssueNumber, e: Estimate)

case class OriginIssueClosing(n: IssueNumber)
