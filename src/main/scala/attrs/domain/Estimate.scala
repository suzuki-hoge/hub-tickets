package attrs.domain

case class Estimate(v: Float) {
  def <(other: Estimate): Boolean = v < other.v

  def +(other: Estimate): Estimate = Estimate(other.v + v)
}

object Estimate {
  def zero: Estimate = Estimate(0)
}
