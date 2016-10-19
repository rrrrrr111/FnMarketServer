package ru.roman.fnmarket.mtquotes

import scala.collection.immutable.ListMap


/**
  *
  */
object QuotePeriod {
  val M1 = QuotePeriod("M1")
  val M5 = QuotePeriod("M5")
  val M15 = QuotePeriod("M15")
  val H1 = QuotePeriod("H1")
  val H4 = QuotePeriod("H4")
  val D1 = QuotePeriod("D1")
  val W1 = QuotePeriod("W1")
  val MN1 = QuotePeriod("MN1")
  val items = Seq(M1, M5, M15, H1, H4, D1, W1, MN1)
  val identityMap = ListMap[String, QuotePeriod]() ++ items.map(s => s.name -> s).toMap

  def byName(name: String): QuotePeriod = {
    val item = identityMap(name)
    if (item != null) item else throw new RuntimeException(s"Period not found by name $name")
  }
}

sealed case class QuotePeriod(name: String) {

  def canEqual(other: Any): Boolean = other.isInstanceOf[QuotePeriod]

  override def equals(other: Any): Boolean = other match {
    case that: QuotePeriod =>
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
