package attrs.transfer.git_hub

import attrs.domain._
import command.domain.issue.OriginIssueClosing

object GitHubMock extends GitHub {
  override def labels: String =
    """[
      |  { "name": "Epic",              "color": "3E4B9E" },
      |  { "name": "dev - feature",     "color": "c5def5" },
      |  { "name": "dev - refactoring", "color": "ededed" }
      |]""".stripMargin

  override def assignees: String =
    """[
      |  { "login": "suzuki-hoge", "avatar_url": "https://avatars3.githubusercontent.com/u/18749992?v=4" }
      |]""".stripMargin

  override def milestones: String =
    """[
      |  { "number": 1, "title": "sprint 1", "due_on": "2020-04-07T00:00:00Z" },
      |  { "number": 2, "title": "sprint 2", "due_on": "2020-04-14T00:00:00Z" }
      |]""".stripMargin

  override def issue(n: IssueNumber): String =
    """{
      |  "number"   : 1,
      |  "title"    : "first issue",
      |  "body"     : "some body",
      |  "labels"   : [
      |    { "name" : "dev - feature", "color": "c5def5" }
      |  ],
      |  "assignees": [
      |    { "login": "suzuki-hoge", "avatar_url": "https://avatars3.githubusercontent.com/u/18749992?v=4" }
      |  ]
      |}""".stripMargin

  override def create(t: Title, b: Body, l: LabelName, a: Option[AssigneeName], m: CurrentMilestoneNumber): String = {
    """{
      |  "number": 1
      |}""".stripMargin
  }

  override def comment(n: IssueNumber, message: String): Unit = ()

  override def close(oic: OriginIssueClosing): Unit = ()

  override def assign(n: IssueNumber, a: AssigneeName): Unit = ()

  override def create(m: Milestone): String =
    """{
      |  "number": 1
      |}""".stripMargin
}
