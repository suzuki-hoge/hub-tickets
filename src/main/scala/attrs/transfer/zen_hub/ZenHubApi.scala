package attrs.transfer.zen_hub

import attrs.domain.{Estimate, IssueNumber, Milestone, MilestoneNumber, PipelineId}
import command.domain.issue.EstimateSubtraction
import requests.{get, post, put}
import store.Config

object ZenHubApi extends ZenHub {
  private val config = Config.read

  override def pipelines: String = get(
    s"https://api.zenhub.io/p1/repositories/${config.rId}/board",
    headers = Map("X-Authentication-Token" -> config.zToken)
  ).text

  override def pipeline(n: IssueNumber): String = issue(n)

  override def estimate(n: IssueNumber): String = issue(n)

  private def issue(n: IssueNumber): String = get(
    s"https://api.zenhub.io/p1/repositories/${config.rId}/issues/${n.v}",
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

  override def subtraction(s: EstimateSubtraction): Unit = setEstimate(s.n, s.e)

  override def setStart(n: MilestoneNumber, m: Milestone): Unit = post(
    s"https://api.zenhub.io/p1/repositories/${config.rId}/milestones/${n.v}/start_date",
    data = Seq("start_date" -> m.start.v),
    headers = Map("X-Authentication-Token" -> config.zToken)
  )
}
