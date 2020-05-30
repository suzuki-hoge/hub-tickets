package attrs.transfer.zen_hub

import attrs.domain.{Estimate, IssueNumber, PipelineId}
import requests.{get, post, put}
import store.Config

object ZenHubApi extends ZenHub {
  private val config = Config.read

  override def pipelines: String = get(
    s"https://api.zenhub.io/p1/repositories/${config.rId}/board",
    headers = Map("X-Authentication-Token" -> config.zToken)
  ).text

  override def setPipeline(n: IssueNumber, p: PipelineId): Unit = post(
    s"https://api.zenhub.io/p1/repositories/${config.rId}/issues/${n.v}/moves",
    data = Seq("pipeline_id" -> p.v, "position" -> "bottom"),
    headers = Map("X-Authentication-Token" -> config.zToken)
  )

  override def setEstimate(n: IssueNumber, e: Estimate): Unit = put(
    s"https://api.zenhub.io/p1/repositories/${config.rId}/issues/${n.v}/estimate",
    data = Seq("estimate" -> e.v.toString),
    headers = Map("X-Authentication-Token" -> config.zToken)
  )

  override def setPipeline(n: IssueNumber): String = ???

  override def setEstimate(n: IssueNumber): String = ???
}
