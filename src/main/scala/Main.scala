import attrs.transfer.AttrsRepositoryImpl
import attrs.transfer.git_hub.GitHubMock
import attrs.transfer.zen_hub.ZenHubMock
import cask.{MainRoutes, Request, post}
import command.transfer.issue.IssueRepositoryImpl
import store.{Config, Store}

object Main extends MainRoutes {

  private val gitHub = GitHubMock
  private val zenHub = ZenHubMock

  private val issues = IssueRepositoryImpl(gitHub, zenHub)
  private val attrs = AttrsRepositoryImpl(gitHub, zenHub)

  Config.initialize("~/Documents/tmp")
  Store.initialize(attrs)

  @post("/command/issue/create")
  def create(request: Request): String = {
    println(Config.read)
    println(Store.read)

    "config"
  }

  initialize()
}
