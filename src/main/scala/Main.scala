import attrs.transfer.AttrsRepositoryImpl
import attrs.transfer.git_hub.GitHubMock
import attrs.transfer.zen_hub.ZenHubMock
import cask.{MainRoutes, Request, get, post}
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

  @get("/attrs/labels") def labels(request: Request): String = Arr(
    attrs.labels.map(label => Obj("name" -> label.name.v, "color" -> label.color.v)): _*
  ).render()

  @get("/attrs/assignees") def assignees(request: Request): String = Arr(
    attrs.assignees.map(assignee => Obj("name" -> assignee.name.v, "icon" -> assignee.icon.v)): _*
  ).render()

  @get("/attrs/pipelines") def pipelines(request: Request): String = Arr(
    attrs.pipelines.map(pipeline => Obj("name" -> pipeline.name.v)): _*
  ).render()

  @post("/command/issue/create")
  def create(request: Request): String = {
    println(Config.read)
    println(Store.read)

    "config"
  }

  initialize()
}
