package ru.roman.fnmarket.mtquotes.parser

import ru.roman.fnmarket.mtquotes.Quote

/**
  * Created by Roman on 10.10.2016.
  */
abstract class QuotesIterator {

  def iterate(onNext: Quote => {}): Unit
}
