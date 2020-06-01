import attrs.domain.Milestone
import attrs.transfer.AttrsRepositoryImpl
import attrs.transfer.git_hub.GitHub
import attrs.transfer.zen_hub.ZenHub
import attrs.validator.{Validator => V}
import cask.endpoints.postJson
import cask.{MainRoutes, get}
import command.domain.issue._
import command.transfer.issue.IssueRepositoryImpl
import store.{Config, Store}
import ujson.{Arr, Obj, Value}

object Main extends MainRoutes {

  Config.initialize("~/Documents/tmp")

  private val gitHub = GitHub.di(true)
  private val zenHub = ZenHub.di(true)

  private val issues = IssueRepositoryImpl(gitHub, zenHub)
  private val attrs = AttrsRepositoryImpl(gitHub, zenHub)

  Store.initialize(attrs)

  @get("/attrs/labels")
  def labels(): Arr = Arr(
    Store.read.ls.map(label => Obj("name" -> label.name.v, "color" -> label.color.v)): _*
  )

  @get("/attrs/assignees")
  def assignees(): Arr = Arr(
    Store.read.as.map(assignee => Obj("name" -> assignee.name.v, "icon" -> assignee.icon.v)): _*
  )

  @get("/attrs/pipelines")
  def pipelines(): Arr = Arr(
    Store.read.ps.map(pipeline => Obj("name" -> pipeline.name.v)): _*
  )

  @postJson("/command/issue/create")
  def create(title: Value, body: Value, label: Value, assignee: Value, pipeline: Value, estimate: Value): Obj = {
    val store = Store.read

    (for {
      t <- V.title(title)
      b <- V.bodyOpt(body)
      l <- V.labelName(label, store.ls)
      a <- V.assigneeNameOpt(assignee, store.as)
      p <- V.pipelineIdOpt(pipeline, store.ps)
      e <- V.estimate(estimate)
    } yield CreateRequest.of(t, b, l, a, p, e, store.defP, attrs.currentMilestoneNumber))
      .map(issues.create)
      .fold(s => Obj("error" -> s), n => Obj("number" -> n.v))
  }

  @postJson("/command/issue/copy")
  def copy(number: Value, title: Value, body: Value, label: Value, assignee: Value, pipeline: Value, estimate: Value): Obj = {
    val store = Store.read

    (for {
      n <- V.number(number)
      t <- V.title(title)
      b <- V.bodyOpt(body)
      l <- V.labelNameOpt(label, store.ls)
      a <- V.assigneeNameOpt(assignee, store.as)
      p <- V.pipelineIdOpt(pipeline, store.ps)
      e <- V.estimateOpt(estimate)
      req <- issues.findOne(n).copy(t, b, l, a, p, e, attrs.currentMilestoneNumber).left.map {
        case CopyOnClosedIssueWithoutPipelineSpecification => "copy on closed issue without pipeline specification"
      }
    } yield req)
      .map(issues.copy)
      .fold(s => Obj("error" -> s), n => Obj("number" -> n.v))
  }

  @postJson("/command/issue/cut")
  def cut(number: Value, title: Value, body: Value, label: Value, assignee: Value, pipeline: Value, estimate: Value): Obj = {
    val store = Store.read

    (for {
      n <- V.number(number)
      t <- V.title(title)
      b <- V.bodyOpt(body)
      l <- V.labelNameOpt(label, store.ls)
      a <- V.assigneeNameOpt(assignee, store.as)
      p <- V.pipelineIdOpt(pipeline, store.ps)
      e <- V.estimate(estimate)
      req <- issues.findOne(n).cut(t, b, l, a, p, e, attrs.currentMilestoneNumber).left.map {
        case CutOnClosedIssue => "cut on closed issue"
        case OriginEstimateIsLessThanNewEstimate => "origin estimate is less than new estimate"
      }
    } yield req)
      .map(issues.cut)
      .fold(s => Obj("error" -> s), n => Obj("number" -> n.v))
  }

  @postJson("/command/issue/assign")
  def assign(number: Value, assignee: Value): Obj = {
    val store = Store.read

    (for {
      n <- V.number(number)
      a <- V.assigneeName(assignee, store.as)
    } yield AssignRequest(n, a))
      .map(issues.assign)
      .fold(s => Obj("error" -> s), _ => Obj())
  }

  @postJson("/command/milestone/create")
  def create(name: Value, start: Value, end: Value): Obj = {
    (for {
      n <- V.milestoneName(name)
      s <- V.milestoneStart(start)
      e <- V.milestoneEnd(end)
    } yield Milestone(n, s, e))
      .map(attrs.create)
      .fold(s => Obj("error" -> s), n => Obj("result" -> n.map(_ => "created").getOrElse("already created ( no change )")))
  }

  initialize()
}
