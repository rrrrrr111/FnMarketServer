package ru.roman.fnmarket.mtquotes

import scala.collection.immutable.ListMap


/**
  *
  */
object Symbol {

  val SBRF_Splice = Symbol("SBRF Splice")
  val USDRUB_TOD = Symbol("USDRUB_TOD")
  val USDRUB_TOM = Symbol("USDRUB_TOM")
  val EURRUB_TOD = Symbol("EURRUB_TOD")
  val EURRUB_TOM = Symbol("EURRUB_TOM")

  val items = SBRF_Splice :: USDRUB_TOD :: USDRUB_TOM :: EURRUB_TOD :: EURRUB_TOM :: Nil
  val identityMap = ListMap[String, Symbol]() ++ items.map(s => s.name -> s).toMap

  def apply(name: String): Symbol =
    new Symbol(name)
}

class Symbol(val name: String) {}
