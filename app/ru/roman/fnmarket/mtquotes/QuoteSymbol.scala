package ru.roman.fnmarket.mtquotes

import ru.roman.fnmarket.mtquotes.QuotePeriod.{D1, M1}

import scala.collection.immutable.ListMap


/**
  *
  */
object QuoteSymbol {
  val SBRF_Splice = QuoteSymbol("SBRF Splice", M1, D1)
  val USDRUB_TOD = QuoteSymbol("USDRUB_TOD")
  val USDRUB_TOM = QuoteSymbol("USDRUB_TOM")
  val EURRUB_TOD = QuoteSymbol("EURRUB_TOD")
  val EURRUB_TOM = QuoteSymbol("EURRUB_TOM")

  val items = Seq(SBRF_Splice, USDRUB_TOD, USDRUB_TOM, EURRUB_TOD, EURRUB_TOM)
  val identityMap = ListMap[String, QuoteSymbol]() ++ items.map(s => s.name -> s).toMap

  def apply(name: String, periods: QuotePeriod*): QuoteSymbol =
    new QuoteSymbol(name, periods)

  def byName(name: String): QuoteSymbol = {
    val item = identityMap(name)
    if (item != null) item else throw new RuntimeException(s"Symbol not found by name $name")
  }
}

sealed class QuoteSymbol(
              val name: String,
              val periods: Seq[QuotePeriod]
            ) {
  val id: String = name.replace(' ', '_')

  def supports(period: QuotePeriod): Boolean = periods.contains(period)

  def canEqual(other: Any): Boolean = other.isInstanceOf[QuoteSymbol]

  override def equals(other: Any): Boolean = other match {
    case that: QuoteSymbol =>
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
