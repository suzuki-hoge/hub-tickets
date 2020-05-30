package attrs.transfer.zen_hub

import attrs.domain.{Estimate, IssueNumber, PipelineId}

object ZenHubApi extends ZenHub {
  override def fetchPipelines: String = ???

  override def setPipeline(n: IssueNumber, p: PipelineId): Unit = ???

  override def setEstimate(n: IssueNumber, e: Estimate): Unit = ???
}
