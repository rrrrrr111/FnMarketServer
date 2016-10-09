package ru.roman.fnmarket.mtquotes

import scala.collection.mutable


/**
  *
  */
object Symbol {

  val SBRF_Splice = Symbol("SBRF Splice")
  val USDRUB_TOD = Symbol("USDRUB_TOD")
  val USDRUB_TOM = Symbol("USDRUB_TOM")
  val EURRUB_TOD = Symbol("EURRUB_TOD")
  val EURRUB_TOM = Symbol("EURRUB_TOM")

  val IDENTITY_MAP = mutable.LinkedHashMap(
    SBRF_Splice.name -> SBRF_Splice,
    USDRUB_TOD.name -> USDRUB_TOD,
    USDRUB_TOM.name -> USDRUB_TOM,
    EURRUB_TOD.name -> EURRUB_TOD,
    EURRUB_TOM.name -> EURRUB_TOM
  )

  def apply(name: String): Symbol =
    new Symbol(name)
}

class Symbol(val name: String) {}
