package attrs.transfer.zen_hub

import attrs.domain.{Estimate, IssueNumber, PipelineId}

object ZenHubMock extends ZenHub {
  override def fetchPipelines: String =
    """{
      |  "pipelines": [
      |    { "id": "1", "name": "backlog" },
      |    { "id": "2", "name": "sprint backlog" },
      |    { "id": "3", "name": "reviewing" }
      |  ]
      |}""".stripMargin

  override def setPipeline(n: IssueNumber, p: PipelineId): Unit = println(p)

  override def setEstimate(n: IssueNumber, e: Estimate): Unit = println(e)
}
