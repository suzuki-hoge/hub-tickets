package attrs.validator

import attrs.domain._

object Validator {
  def number(n: Int): Either[String, IssueNumber] = Right(IssueNumber(n))

  def title(s: String): Either[String, Title] = s match {
    case "" => Left("no title specified")
    case _s => Right(Title(_s))
  }

  def bodyOpt(s: String): Either[String, Option[Body]] = s match {
    case "" => Right(None)
    case _s => Right(Some(Body(_s)))
  }

  def labelName(s: String, ls: Seq[Label]): Either[String, LabelName] = s match {
    //@formatter:off
    case ""                              => Left("no label specified")
    case _s if ls.exists(_.name.v == _s) => Right(LabelName(_s))
    case _                               => Left("no such label")
    //@formatter:on
  }

  def labelNameOpt(s: String, ls: Seq[Label]): Either[String, Option[LabelName]] = s match {
    //@formatter:off
    case ""                              => Right(None)
    case _s if ls.exists(_.name.v == _s) => Right(Some(LabelName(_s)))
    case _                               => Left("no such label")
    //@formatter:on
  }

  def assigneeNameOpt(s: String, as: Seq[Assignee]): Either[String, Option[AssigneeName]] = s match {
    //@formatter:off
    case ""                              => Right(None)
    case _s if as.exists(_.name.v == _s) => Right(Some(AssigneeName(_s)))
    case _                               => Left("no such assignee")
    //@formatter:on
  }

  def pipelineIdOpt(s: String, ps: Seq[Pipeline]): Either[String, Option[PipelineId]] = s match {
    //@formatter:off
    case ""                              => Right(None)
    case _s if ps.exists(_.name.v == _s) => Right(ps.find(_.name.v == _s).map(_.id))
    case _                               => Left("no such pipeline")
    //@formatter:on
  }

  def estimate(n: Float): Either[String, Estimate] = Right(Estimate(n))
}
