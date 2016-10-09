package ru.roman.fnmarket.mtquotes

import scala.collection.immutable.ListMap


/**
  *
  */
object Symbol {
  val SBRF_Splice = Symbol("SBRF Splice",
    Period.M1)
  val USDRUB_TOD = Symbol("USDRUB_TOD")
  val USDRUB_TOM = Symbol("USDRUB_TOM")
  val EURRUB_TOD = Symbol("EURRUB_TOD")
  val EURRUB_TOM = Symbol("EURRUB_TOM")

  val items = Seq(SBRF_Splice, USDRUB_TOD, USDRUB_TOM, EURRUB_TOD, EURRUB_TOM)
  val identityMap = ListMap[String, Symbol]() ++ items.map(s => s.name -> s).toMap

  def apply(name: String, periods: Period*): Symbol =
    new Symbol(name, periods)

  def byName(name: String): Symbol = {
    val item = identityMap(name)
    if (item != null) item else throw new RuntimeException(s"Symbol not found by name $name")
  }
}

class Symbol(
              val name: String,
              val periods: Seq[Period]
            ) {
  def supports(period: Period): Boolean = periods.contains(period)

  def canEqual(other: Any): Boolean = other.isInstanceOf[Symbol]

  override def equals(other: Any): Boolean = other match {
    case that: Symbol =>
      (that canEqual this) &&
        name == that.name
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(name)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
