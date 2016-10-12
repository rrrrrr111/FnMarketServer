package ru.roman.fnmarket.mtquotes

import scala.collection.immutable.ListMap


/**
  *
  */
object Period {
  val M1 = Period("M1")
  val M5 = Period("M5")
  val M15 = Period("M15")
  val H1 = Period("H1")
  val H4 = Period("H4")
  val D1 = Period("D1")
  val W1 = Period("W1")
  val MN1 = Period("MN1")
  val items = Seq(M1, M5, M15, H1, H4, D1, W1, MN1)
  val identityMap = ListMap[String, Period]() ++ items.map(s => s.name -> s).toMap

  def byName(name: String): Period = {
    val item = identityMap(name)
    if (item != null) item else throw new RuntimeException(s"Period not found by name $name")
  }
}

case class Period(name: String) {

  def canEqual(other: Any): Boolean = other.isInstanceOf[Period]

  override def equals(other: Any): Boolean = other match {
    case that: Period =>
      (that canEqual this) &&
        name == that.name
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(name)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString = name
}
