package attrs.transfer.zen_hub

import attrs.domain.{Estimate, IssueNumber, Milestone, MilestoneNumber, PipelineId}
import command.domain.issue.EstimateSubtraction

object ZenHubMock extends ZenHub {
  override def pipelines: String =
    """{
      |  "pipelines": [
      |    { "id": "1", "name": "backlog" },
      |    { "id": "2", "name": "sprint backlog" },
      |    { "id": "3", "name": "reviewing" }
      |  ]
      |}""".stripMargin

  override def pipeline(n: IssueNumber): String =
    """{
      |  "pipeline": { "pipeline_id": "1" }
      |}""".stripMargin

  override def estimate(n: IssueNumber): String =
    """{
      |  "estimate": { "value": 0.5 }
      |}""".stripMargin

  override def setPipeline(n: IssueNumber, p: PipelineId): Unit = ()

  override def setEstimate(n: IssueNumber, e: Estimate): Unit = ()

  override def subtraction(s: EstimateSubtraction): Unit = ()

  override def setStart(n: MilestoneNumber, m: Milestone): Unit = ()
}
