package attrs.transfer.zen_hub

import attrs.domain._
import play.api.libs.json.{Json, Reads}

trait ZenHub {
  def fetchPipelines: String

  def setPipeline(n: IssueNumber, p: PipelineId): Unit

  def setEstimate(n: IssueNumber, e: Estimate): Unit
}

case class $Pipeline(id: String, name: String) {
  def toAttrs: Pipeline = Pipeline(PipelineId(id), PipelineName(name))

  def asDefaultId: DefaultPipelineId = DefaultPipelineId(id)
}

object $Pipeline {
  implicit val jsonReads: Reads[$Pipeline] = Json.reads[$Pipeline]
}
