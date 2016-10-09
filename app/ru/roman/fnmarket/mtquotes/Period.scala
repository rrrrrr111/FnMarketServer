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

  val items = M1 :: M5 :: M15 :: H1 :: H4 :: D1 :: W1 :: MN1 :: Nil
  val identityMap = ListMap[String, Period]() ++ items.map(s => s.name -> s).toMap

  def apply(name: String): Period =
    new Period(name)
}

class Period(val name: String) {}
