package command.domain.issue

sealed trait Error

object CopyOnClosedIssueWithoutPipelineSpecification extends Error

object CutOnClosedIssue extends Error

object OriginEstimateIsLessThanNewEstimate extends Error
