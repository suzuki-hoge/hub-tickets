package command.domain.issue

import attrs.domain.{IssueNumber, Milestone}

trait IssueRepository {
  def findOne(n: IssueNumber): Issue

  def create(req: CreateRequest): IssueNumber

  def copy(req: CopyRequest): IssueNumber

  def cut(req: CutRequest): IssueNumber

  def assign(req: AssignRequest): Unit
}
