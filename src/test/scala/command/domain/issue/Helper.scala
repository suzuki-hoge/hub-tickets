package command.domain.issue

import attrs.domain._

import scala.language.implicitConversions

object Helper {
  implicit def intToIssueNumber(n: Int): IssueNumber = IssueNumber(n)

  implicit def strToTitle(s: String): Title = Title(s)

  implicit def strToBody(s: String): Body = Body(s)

  implicit def strToBodyOpt(s: String): Option[Body] = Some(Body(s))

  implicit def strToLabelName(s: String): LabelName = LabelName(s)

  implicit def strToLabelNameOpt(s: String): Option[LabelName] = Some(s)

  implicit def strToAssigneeNames(s: String): Option[AssigneeName] = Some(AssigneeName(s))

  implicit def strToPipelineId(s: String): PipelineId = PipelineId(s)

  implicit def strToPipelineIdOpt(s: String): Option[PipelineId] = Some(PipelineId(s))

  implicit def intToCurrentMilestoneNumber(n: Int): CurrentMilestoneNumber = CurrentMilestoneNumber(n)

  implicit def floatToEstimate(n: Float): Estimate = Estimate(n)

  implicit def floatToEstimateOpt(n: Float): Option[Estimate] = Some(Estimate(n))
}
