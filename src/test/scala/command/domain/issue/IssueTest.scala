package command.domain.issue

import command.domain.issue.Helper._
import org.scalatest.FunSuite

class IssueTest extends FunSuite {

  val sut = Issue(1, "title_1", "body_2", "label_1", "assignee_1", "pipeline_1", 3)

  test("copy - all specified") {
    val act = sut.copy("title_2", "body_2", "label_2", "assignee_2", "pipeline_2", 3, "milestone_2")

    assert(
      act == CopyRequest(
        Creation("title_2", "body_2", "label_2", "assignee_2", "pipeline_2", "milestone_2", 3)
      )
    )
  }

  test("copy - no specified") {
    val act = sut.copy("title_2", None, None, None, None, None, "milestone_2")

    assert(
      act == CopyRequest(
        Creation("title_2", None, "label_1", "assignee_1", "pipeline_1", "milestone_2", 3)
      )
    )
  }

  test("cut - all specified") {
    val act = sut.cut("title_2", "body_2", "label_2", "assignee_2", "pipeline_2", 3, "milestone_2")

    assert(
      act.right.get == CutRequest(
        Creation("title_2", "body_2", "label_2", "assignee_2", "pipeline_2", "milestone_2", 3),
        CutComment(1),
        EstimateSubtraction(1, 3),
        Some(OriginIssueClosing(1))
      )
    )
  }

  test("cut - no specified") {
    val act = sut.cut("title_2", None, None, None, None, 2, "milestone_2")

    assert(
      act.right.get == CutRequest(
        Creation("title_2", None, "label_1", "assignee_1", "pipeline_1", "milestone_2", 2),
        CutComment(1),
        EstimateSubtraction(1, 2),
        None
      )
    )
  }

  test("cut - failure") {
    val act = sut.cut("title_2", None, None, None, None, 4, "milestone_2")

    assert(
      act.left.get == OriginEstimateIsLessThanNewEstimate
    )
  }
}

