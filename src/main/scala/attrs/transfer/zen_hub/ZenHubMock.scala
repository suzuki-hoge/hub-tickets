package attrs.transfer.zen_hub

import attrs.domain.{Estimate, IssueNumber, PipelineId}

object ZenHubMock extends ZenHub {
  override def setPipeline(n: IssueNumber): String =
    """{
      |  "pipeline": { "pipeline_id": "1" }
      |}""".stripMargin

  override def setEstimate(n: IssueNumber): String =
    """{
      |  "estimate": { "value": 5 }
      |}""".stripMargin

  override def pipelines: String =
    """{
      |  "pipelines": [
      |    { "id": "1", "name": "backlog" },
      |    { "id": "2", "name": "sprint backlog" },
      |    { "id": "3", "name": "reviewing" }
      |  ]
      |}""".stripMargin

  override def setPipeline(n: IssueNumber, p: PipelineId): Unit = ()

  override def setEstimate(n: IssueNumber, e: Estimate): Unit = ()
}
