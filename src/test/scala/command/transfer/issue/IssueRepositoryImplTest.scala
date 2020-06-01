package command.transfer.issue

import attrs.transfer.git_hub.GitHubMock
import attrs.transfer.zen_hub.ZenHubMock
import command.domain.issue.Helper._
import command.domain.issue.Issue
import org.scalatest.FunSuite

class IssueRepositoryImplTest extends FunSuite {
  val sut = IssueRepositoryImpl(GitHubMock, ZenHubMock)

  test("findOne") {
    assert(
      sut.findOne(1) == Issue(1, "first issue", "some body", "dev - feature", "suzuki-hoge", "1", 0.5f)
    )
  }
}
