package attrs.transfer

import attrs.domain._
import attrs.transfer.git_hub.GitHubMock
import attrs.transfer.zen_hub.ZenHubMock
import org.scalatest.FunSuite

class AttrsRepositoryImplTest extends FunSuite {
  val sut = AttrsRepositoryImpl(GitHubMock, ZenHubMock)

  test("labels") {
    assert(
      sut.labels == Seq(Label(LabelName("dev - feature"), LabelColor("c5def5")), Label(LabelName("dev - refactoring"), LabelColor("ededed")))
    )
  }

  test("assignees") {
    assert(
      sut.assignees == Seq(Assignee(AssigneeName("suzuki-hoge"), AssigneeIcon("https://avatars3.githubusercontent.com/u/18749992")))
    )
  }

  test("pipelines") {
    assert(
      sut.pipelines == Seq(Pipeline(PipelineId("1"), PipelineName("backlog")), Pipeline(PipelineId("2"), PipelineName("sprint backlog")), Pipeline(PipelineId("3"), PipelineName("reviewing")))
    )
  }

  test("currentMilestone") {
    assert(
      sut.currentMilestoneNumber == CurrentMilestoneNumber(2)
    )
  }
}
