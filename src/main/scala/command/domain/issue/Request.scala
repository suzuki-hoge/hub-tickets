package command.domain.issue

import attrs.domain._

case class CreateRequest(creation: Creation)

object CreateRequest {
  def of(reqT: Title, reqB: Option[Body], reqL: LabelName, reqA: Option[AssigneeName], reqP: Option[PipelineId], reqE: Estimate, defP: DefaultPipelineId, cmn: CurrentMilestoneName): CreateRequest = CreateRequest(
    Creation(reqT, reqB, reqL, reqA, reqP getOrElse defP.useThis, cmn.useThis, reqE)
  )
}

case class CopyRequest(creation: Creation)

case class CutRequest(creation: Creation, comment: CutComment, sub: EstimateSubtraction, closing: Option[OriginIssueClosing])

case class Creation(t: Title, b: Option[Body], l: LabelName, a: Option[AssigneeName], p: PipelineId, m: MilestoneName, e: Estimate)

case class CutComment(n: IssueNumber)

case class EstimateSubtraction(n: IssueNumber, e: Estimate)

case class OriginIssueClosing(n: IssueNumber)
