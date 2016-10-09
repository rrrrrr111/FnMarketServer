package ru.roman.fnmarket

import java.io.File

import ru.roman.fnmarket.mtquotes.parser.{CsvParser, FileFormat, MetaTraderQuotesSeeker, QuotesIterator}
import ru.roman.fnmarket.mtquotes.{Period, Symbol}


/**
  * Created by Roman on 04.10.2016.
  */
object Main extends App {

  val filesToLoad: Seq[(FileFormat[Symbol, Period], File)] = MetaTraderQuotesSeeker.searchFilesToLoad
  val quotesIterator: QuotesIterator = CsvParser.createQuotesIterator(filesToLoad)

  println(filesToLoad)

}
