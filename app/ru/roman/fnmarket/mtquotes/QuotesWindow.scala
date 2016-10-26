package ru.roman.fnmarket.mtquotes

/**
  * Created by Roman on 10.10.2016.
  */
abstract class QuotesWindow {

  def iterateWith(onNext: ((BigInt, Quote) => Unit)): Unit
}
