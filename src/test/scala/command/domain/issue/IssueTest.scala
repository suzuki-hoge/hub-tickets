package command.domain.issue

import command.domain.issue.Helper._
import org.scalatest.FunSuite

class IssueTest extends FunSuite {

  val sut = Issue(1, "title_1", "body_2", "label_1", "assignee_1", "pipeline_1", 3f)

  test("copy - all specified") {
    val act = sut.copy("title_2", "body_2", "label_2", "assignee_2", "pipeline_2", 3f, 2)

    assert(
      act == CopyRequest(
        Creation("title_2", "body_2", "label_2", "assignee_2", "pipeline_2", 2, 3f)
      )
    )
  }

  test("copy - no specified") {
    val act = sut.copy("title_2", None, None, None, None, None, 2)

    assert(
      act == CopyRequest(
        Creation("title_2", "body_2", "label_1", "assignee_1", "pipeline_1", 2, 3f)
      )
    )
  }

  test("cut - all specified") {
    val act = sut.cut("title_2", "body_2", "label_2", "assignee_2", "pipeline_2", 3f, 2)

    assert(
      act.right.get == CutRequest(
        Creation("title_2", "body_2", "label_2", "assignee_2", "pipeline_2", 2, 3f),
        Comment(1, "cut", 3f),
        EstimateSubtraction(1, 3f),
        Some(OriginIssueClosing(1))
      )
    )
  }

  test("cut - no specified") {
    val act = sut.cut("title_2", None, None, None, None, 2f, 2)

    assert(
      act.right.get == CutRequest(
        Creation("title_2", None, "label_1", "assignee_1", "pipeline_1", 2, 2f),
        Comment(1, "cut", 2f),
        EstimateSubtraction(1, 2f),
        None
      )
    )
  }

  test("cut - failure") {
    val act = sut.cut("title_2", None, None, None, None, 4f, 2)

    assert(
      act.left.get == OriginEstimateIsLessThanNewEstimate
    )
  }
}

