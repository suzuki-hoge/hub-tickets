package attrs.validator

import attrs.domain._
import ujson.{Null, Num, Str, Value}

object Validator {
  def number(v: Value): Either[String, IssueNumber] = v match {
    //@formatter:off
    case Num(n) => Right(IssueNumber(n.toInt))
    case Null   => Left("no number specified")
    case _      => Left("invalid number format")
    //@formatter:on
  }

  def title(v: Value): Either[String, Title] = v match {
    //@formatter:off
    case Str(s) => Right(Title(s))
    case Null   => Left("no title specified")
    case _      => Left("invalid title format")
    //@formatter:on
  }

  def bodyOpt(v: Value): Either[String, Option[Body]] = v match {
    //@formatter:off
    case Str(s) => Right(Some(Body(s)))
    case Null   => Right(None)
    case _      => Left("invalid body format")
    //@formatter:on
  }

  def labelName(v: Value, ls: Seq[Label]): Either[String, LabelName] = v match {
    //@formatter:off
    case Str(s) if ls.exists(_.name.v == s) => Right(LabelName(s))
    case Null                               => Left("no label specified")
    case Str(_)                             => Left("no such label")
    case _                                  => Left("invalid label format")
    //@formatter:on
  }

  def labelNameOpt(v: Value, ls: Seq[Label]): Either[String, Option[LabelName]] = v match {
    //@formatter:off
    case Str(s) if ls.exists(_.name.v == s) => Right(Some(LabelName(s)))
    case Null                               => Right(None)
    case Str(_)                             => Left("no such label")
    case _                                  => Left("invalid label format")
    //@formatter:on
  }

  def assigneeNameOpt(v: Value, as: Seq[Assignee]): Either[String, Option[AssigneeName]] = v match {
    //@formatter:off
    case Str(s) if as.exists(_.name.v == s) => Right(Some(AssigneeName(s)))
    case Null                               => Right(None)
    case Str(_)                             => Left("no such assignee")
    case _                                  => Left("invalid assignee format")
    //@formatter:on
  }

  def pipelineIdOpt(v: Value, ps: Seq[Pipeline]): Either[String, Option[PipelineId]] = v match {
    //@formatter:off
    case Str(s) if ps.exists(_.name.v == s) => Right(ps.find(_.name.v == s).map(_.id))
    case Null                               => Right(None)
    case Str(_)                             => Left("no such pipeline")
    case _                                  => Left("invalid pipeline format")
    //@formatter:on
  }

  def estimate(v: Value): Either[String, Estimate] = v match {
    //@formatter:off
    case Num(n) => Right(Estimate(n.toFloat))
    case Null   => Left("no estimate specified")
    case _      => Left("invalid estimate format")
    //@formatter:on
  }

  def estimateOpt(v: Value): Either[String, Option[Estimate]] = v match {
    //@formatter:off
    case Num(n) => Right(Some(Estimate(n.toFloat)))
    case Null   => Right(None)
    case _      => Left("invalid estimate format")
    //@formatter:on
  }
}
