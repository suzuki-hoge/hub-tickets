package attrs.transfer.git_hub

import attrs.domain._
import command.domain.issue.OriginIssueClosing
import requests.{get, patch, post}
import store.Config
import ujson.{Arr, Obj}

object GitHubApi extends GitHub {
  private val config = Config.read

  override def labels: String = get(
    s"https://api.github.com/repos/${config.owner}/${config.repo}/labels",
    headers = Map("Authorization" -> s"token ${config.gToken}")
  ).text

  override def assignees: String = get(
    s"https://api.github.com/repos/${config.owner}/${config.repo}/assignees",
    headers = Map("Authorization" -> s"token ${config.gToken}")
  ).text

  override def milestones: String = get(
    s"https://api.github.com/repos/${config.owner}/${config.repo}/milestones",
    headers = Map("Authorization" -> s"token ${config.gToken}")
  ).text

  override def issue(n: IssueNumber): String = get(
    s"https://api.github.com/repos/${config.owner}/${config.repo}/issues/${n.v}",
    headers = Map("Authorization" -> s"token ${config.gToken}")
  ).text

  override def create(t: Title, b: Body, l: LabelName, a: Option[AssigneeName], m: CurrentMilestoneNumber): String = post(
    s"https://api.github.com/repos/${config.owner}/${config.repo}/issues",
    data = Obj("title" -> t.v, "body" -> b.v, "labels" -> Arr(l.v), "assignee" -> a.map(_.v).getOrElse("").toString, "milestone" -> m.v.toString),
    headers = Map("Authorization" -> s"token ${config.gToken}")
  ).text

  override def comment(n: IssueNumber, message: String): Unit = post(
    s"https://api.github.com/repos/${config.owner}/${config.repo}/issues/${n.v}/comments",
    data = Obj("body" -> message),
    headers = Map("Authorization" -> s"token ${config.gToken}")
  )

  override def close(oic: OriginIssueClosing): Unit = update(oic.n, Obj("state" -> "closed"))

  override def assign(n: IssueNumber, a: AssigneeName): Unit = update(n, Obj("assignee" -> a.v))

  private def update(n: IssueNumber, obj: Obj): Unit = patch(
    s"https://api.github.com/repos/${config.owner}/${config.repo}/issues/${n.v}",
    data = obj,
    headers = Map("Authorization" -> s"token ${config.gToken}")
  )

  override def create(m: Milestone): String = post(
    s"https://api.github.com/repos/${config.owner}/${config.repo}/milestones",
    data = Obj("title" -> m.name.v, "due_on" -> m.end.v),
    headers = Map("Authorization" -> s"token ${config.gToken}")
  ).text
}
