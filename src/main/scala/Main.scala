import attrs.domain._
import attrs.transfer.AttrsRepositoryImpl
import attrs.transfer.git_hub.GitHubMock
import attrs.transfer.zen_hub.ZenHubMock
import cask.endpoints.postJson
import cask.{MainRoutes, Request, get}
import command.domain.issue.CreateRequest
import command.transfer.issue.IssueRepositoryImpl
import store.{Config, Store}
import ujson.{Arr, Obj}

object Main extends MainRoutes {

  private val gitHub = GitHubMock
  private val zenHub = ZenHubMock

  private val issues = IssueRepositoryImpl(gitHub, zenHub)
  private val attrs = AttrsRepositoryImpl(gitHub, zenHub)

  Config.initialize("~/Documents/tmp")
  Store.initialize(attrs)

  @get("/attrs/labels")
  def labels(request: Request): Arr = Arr(
    attrs.labels.map(label => Obj("name" -> label.name.v, "color" -> label.color.v)): _*
  )

  @get("/attrs/assignees")
  def assignees(request: Request): Arr = Arr(
    attrs.assignees.map(assignee => Obj("name" -> assignee.name.v, "icon" -> assignee.icon.v)): _*
  )

  @get("/attrs/pipelines")
  def pipelines(request: Request): Arr = Arr(
    attrs.pipelines.map(pipeline => Obj("name" -> pipeline.name.v)): _*
  )

  @postJson("/command/issue/create")
  def create(title: String, body: String, label: String, assignee: String, pipeline: String, estimate: Int): Obj = {
    val store = Store.read

    val req = CreateRequest.of(
      Title(title),
      Some(body).filter(_.nonEmpty).map(Body),
      LabelName(label),
      Some(assignee).filter(_.nonEmpty).map(AssigneeName),
      store.ps.filter(_.name.v == pipeline).map(_.id).headOption,
      Estimate(estimate),
      store.defP,
      attrs.currentMilestoneName
    )

    Obj("number" -> issues.create(req).v)
  }

  initialize()
}
