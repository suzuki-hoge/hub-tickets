package store

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

import attrs.domain.{Assignee, DefaultPipelineId, Label, Pipeline}
import attrs.transfer.AttrsRepositoryImpl

case class Store(ls: Seq[Label], as: Seq[Assignee], ps: Seq[Pipeline], defP: DefaultPipelineId)

object Store {
  def initialize(attrs: AttrsRepositoryImpl): Unit = {
    val ls = attrs.labels
    val as = attrs.assignees
    val ps = attrs.pipelines

    val store = Store(ls, as, ps, ps.find(_.name.v == Config.read.defaultPipelineName).head.asDefault)

    val oos = new ObjectOutputStream(new FileOutputStream("/tmp/store"))
    try {
      oos.writeObject(store)
    } finally {
      oos.close()
    }
  }

  def read: Store = {
    val ois = new ObjectInputStream(new FileInputStream("/tmp/store"))
    try {
      ois.readObject().asInstanceOf[Store]
    } finally {
      ois.close()
    }
  }
}
