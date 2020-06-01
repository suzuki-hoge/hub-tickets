package attrs.domain

case class Pipeline(id: PipelineId, name: PipelineName) {
  def asDefault: DefaultPipelineId = DefaultPipelineId(id.v)
}

case class PipelineId(v: String) {
  def isClosed: Boolean = v == "closed"
}

object PipelineId {
  def closed: PipelineId = PipelineId("closed")
}

case class PipelineName(v: String)

case class DefaultPipelineId(v: String) {
  def useThis: PipelineId = PipelineId(v)
}
