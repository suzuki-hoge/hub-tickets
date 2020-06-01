package command.domain.issue

import attrs.domain._

case class Issue(orgN: IssueNumber, orgT: Title, orgB: Body, orgL: LabelName, orgA: Option[AssigneeName], orgP: PipelineId, orgE: Estimate) {
  def copy(reqT: Title, reqB: Option[Body], reqL: Option[LabelName], reqA: Option[AssigneeName], reqP: Option[PipelineId], reqE: Option[Estimate], cmn: CurrentMilestoneNumber): Either[Error, CopyRequest] = {
    if (orgP.isClosed && reqP.isEmpty)
      Left(CopyOnClosedIssueWithoutPipelineSpecification)
    else
      Right(
        CopyRequest(
          Creation(reqT, reqB orElse Some(orgB), reqL getOrElse orgL, reqA orElse orgA, reqP getOrElse orgP, cmn, reqE getOrElse orgE)
        )
      )
  }

  def cut(reqT: Title, reqB: Option[Body], reqL: Option[LabelName], reqA: Option[AssigneeName], reqP: Option[PipelineId], reqE: Estimate, cmn: CurrentMilestoneNumber): Either[Error, CutRequest] = {
    if (orgP.isClosed)
      Left(CutOnClosedIssue)
    else if (orgE < reqE)
      Left(OriginEstimateIsLessThanNewEstimate)
    else
      Right(
        CutRequest(
          Creation(reqT, reqB, reqL getOrElse orgL, reqA orElse orgA, reqP getOrElse orgP, cmn, reqE),
          Comment(orgN, "cut", reqE),
          EstimateSubtraction(orgN, reqE),
          if (orgE == reqE) Some(OriginIssueClosing(orgN)) else None
        )
      )
  }
}
