package attrs.transfer.zen_hub

import attrs.domain._
import play.api.libs.json.{Json, Reads}

trait ZenHub {
  def pipelines: String

  def pipeline(n: IssueNumber): String

  def estimate(n: IssueNumber): String

  def setPipeline(n: IssueNumber, p: PipelineId): Unit

  def setEstimate(n: IssueNumber, e: Estimate): Unit
}

case class $PipelineId(pipeline_id: String) {
  def toAttrs: PipelineId = PipelineId(pipeline_id)
}

object $PipelineId {
  implicit val jsonReads: Reads[$PipelineId] = Json.reads[$PipelineId]
}

case class $Estimate(value: Int) {
  def toAttrs: Estimate = Estimate(value.toFloat)
}

object $Estimate {
  implicit val jsonReads: Reads[$Estimate] = Json.reads[$Estimate]
}

case class $Pipeline(id: String, name: String) {
  def toAttrs: Pipeline = Pipeline(PipelineId(id), PipelineName(name))

  def asDefaultId: DefaultPipelineId = DefaultPipelineId(id)
}

object $Pipeline {
  implicit val jsonReads: Reads[$Pipeline] = Json.reads[$Pipeline]
}
