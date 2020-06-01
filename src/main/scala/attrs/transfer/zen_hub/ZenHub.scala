package attrs.transfer.zen_hub

import attrs.domain._
import command.domain.issue.EstimateSubtraction
import play.api.libs.json.{Json, Reads}

trait ZenHub {
  def pipelines: String

  def pipeline(n: IssueNumber): String

  def estimate(n: IssueNumber): String

  def setPipeline(n: IssueNumber, p: PipelineId): Unit

  def setEstimate(n: IssueNumber, e: Estimate): Unit

  def subtraction(s: EstimateSubtraction): Unit
}

object ZenHub {
  def di(b: Boolean): ZenHub = if (b) ZenHubApi else ZenHubMock
}

case class $PipelineId(pipeline_id: String) {
  def toAttrs: PipelineId = PipelineId(pipeline_id)
}

object $PipelineId {
  implicit val jsonReads: Reads[$PipelineId] = Json.reads[$PipelineId]
}

case class $Estimate(value: Float) {
  def toAttrs: Estimate = Estimate(value)
}

object $Estimate {
  implicit val jsonReads: Reads[$Estimate] = Json.reads[$Estimate]
}

case class $Pipeline(id: String, name: String) {
  def toAttrs: Pipeline = Pipeline(PipelineId(id), PipelineName(name))
}

object $Pipeline {
  implicit val jsonReads: Reads[$Pipeline] = Json.reads[$Pipeline]
}
