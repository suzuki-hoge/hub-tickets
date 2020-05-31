package attrs.domain

case class Pipeline(id: PipelineId, name: PipelineName) {
  def asDefault: DefaultPipelineId = DefaultPipelineId(id.v)
}

case class PipelineId(v: String)

case class PipelineName(v: String)

case class DefaultPipelineId(v: String) {
  def useThis: PipelineId = PipelineId(v)
}
