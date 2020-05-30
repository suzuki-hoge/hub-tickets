package attrs.transfer.git_hub

import attrs.domain._
import requests.{get, post}
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

  override def issue: String = ???

  override def create(t: Title, b: Body, l: LabelName, a: AssigneeName): String = post(
    s"https://api.github.com/repos/${config.owner}/${config.repo}/issues",
    data = Obj("title" -> t.v, "body" -> b.v, "labels" -> Arr(l.v), "assignee" -> a.v).render(),
    headers = Map("Authorization" -> s"token ${config.gToken}")
  ).text
}
