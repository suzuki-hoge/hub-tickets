package store

import play.api.libs.json.{Json, Reads}

import scala.io.Source

case class Config(owner: String, repo: String, rId: String, gToken: String, zToken: String, defaultPipelineName: String, presetAssignees: Seq[String])

object Config {
  private var dir: Option[String] = None

  def initialize(dir: String): Unit = this.dir = Some(dir.replace("~", System.getProperty("user.home")))

  def read: Config = {
    val dir = if (this.dir.isEmpty) throw new RuntimeException("config dir missing.") else this.dir.get

    val s1 = Source.fromFile(s"$dir/board.json")
    val s2 = Source.fromFile(s"$dir/personal.json")

    val board = Json.parse(s1.getLines.mkString).validate[Board].get
    val personal = Json.parse(s2.getLines.mkString).validate[Personal].get

    s1.close()
    s2.close()

    Config(
      board.owner,
      board.repository,
      board.repositoryId,
      personal.githubToken,
      personal.zenhubToken,
      board.defaultPipelineName,
      board.presetAssignees
    )
  }

  // parsers

  case class Board(owner: String, repository: String, repositoryId: String, defaultPipelineName: String, presetAssignees: Seq[String])

  object Board {
    implicit val jsonReads: Reads[Board] = Json.reads[Board]
  }

  case class Personal(githubToken: String, zenhubToken: String)

  object Personal {
    implicit val jsonReads: Reads[Personal] = Json.reads[Personal]
  }

}
