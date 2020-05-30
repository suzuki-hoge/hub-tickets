package attrs.domain

case class Pipeline(id: PipelineId, name: PipelineName)

case class PipelineId(v: String) {
}

case class PipelineName(v: String)

case class DefaultPipelineId(v: String) {
  def useThis: PipelineId = PipelineId(v)
}
