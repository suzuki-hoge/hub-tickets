package command.domain.issue

import attrs.domain._

object Helper {
  implicit def intToIssueNumber(n: Int): IssueNumber = IssueNumber(n)

  implicit def strToBaseTitleOpt(s: String): Option[BaseTitle] = Some(BaseTitle(s))

  implicit def strToTitle(s: String): Title = Title(s)

  implicit def strToBody(s: String): Body = Body(s)

  implicit def strToBodyOpt(s: String): Option[Body] = Some(Body(s))

  implicit def strToLabelName(s: String): LabelName = LabelName(s)

  implicit def strToLabelNameOpt(s: String): Option[LabelName] = Some(s)

  implicit def strToAssigneeNames(s: String): Option[AssigneeName] = Some(AssigneeName(s))

  implicit def strToPipelineId(v: String): PipelineId = PipelineId(v)

  implicit def strToPipelineIdOpt(v: String): Option[PipelineId] = Some(PipelineId(v))

  implicit def strToMilestoneName(v: String): MilestoneName = MilestoneName(v)

  implicit def strToCurrentMilestoneName(v: String): CurrentMilestoneName = CurrentMilestoneName(v)

  implicit def intToEstimate(n: Int): Estimate = Estimate(n)

  implicit def intToEstimateOpt(n: Int): Option[Estimate] = Some(Estimate(n))
}
