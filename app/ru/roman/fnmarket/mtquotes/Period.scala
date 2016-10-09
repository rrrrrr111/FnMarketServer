package ru.roman.fnmarket.mtquotes

import scala.collection.mutable


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

  val IDENTITY_MAP = mutable.LinkedHashMap(
    M1.name -> M1,
    M5.name -> M5,
    M15.name -> M15,
    H1.name -> H1,
    H4.name -> H4,
    D1.name -> D1,
    W1.name -> W1,
    MN1.name -> MN1
  )

  def apply(name: String): Period =
    new Period(name)
}

class Period(val name: String) {}
