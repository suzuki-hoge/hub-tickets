package command.domain.issue

import attrs.domain._

case class Issue(n: IssueNumber, orgT: Title, orgB: Body, orgL: LabelName, orgA: Option[AssigneeName], orgP: PipelineId, orgE: Estimate) {
  def copy(reqT: Title, reqB: Option[Body], reqL: Option[LabelName], reqA: Option[AssigneeName], reqP: Option[PipelineId], reqE: Option[Estimate], cmn: CurrentMilestoneName): CopyRequest = {
    CopyRequest(
      Creation(reqT, reqB, reqL getOrElse orgL, reqA orElse orgA, reqP getOrElse orgP, cmn.useThis, reqE getOrElse orgE)
    )
  }

  def cut(reqT: Title, reqB: Option[Body], reqL: Option[LabelName], reqA: Option[AssigneeName], reqP: Option[PipelineId], reqE: Estimate, cmn: CurrentMilestoneName): Either[Error, CutRequest] = {
    if (orgE < reqE)
      Left(OriginEstimateIsLessThanNewEstimate)
    else
      Right(
        CutRequest(
          Creation(reqT, reqB, reqL getOrElse orgL, reqA orElse orgA, reqP getOrElse orgP, cmn.useThis, reqE),
          CutComment(n),
          EstimateSubtraction(n, reqE),
          if (orgE == reqE) Some(OriginIssueClosing(n)) else None
        )
      )
  }
}
