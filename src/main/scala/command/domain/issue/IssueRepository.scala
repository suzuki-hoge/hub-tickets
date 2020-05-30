package command.domain.issue

import attrs.domain.IssueNumber

trait IssueRepository {
  def findOne(n: IssueNumber): Issue

  def create(req: CreateRequest): IssueNumber

  def copy(req: CopyRequest): IssueNumber

  def cut(req: CutRequest): IssueNumber
}
