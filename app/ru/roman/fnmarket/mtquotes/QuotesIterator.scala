package ru.roman.fnmarket.mtquotes

/**
  * Created by Roman on 10.10.2016.
  */
abstract class QuotesIterator {

  def startIterateWith(onNext: ((BigInt, Quote) => Unit)): Unit
}
