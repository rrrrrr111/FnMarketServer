package ru.roman.fnmarket

import ru.roman.fnmarket.mtquotes.QuotesIterator
import ru.roman.fnmarket.mtquotes.parser._


/**
  * Created by Roman on 04.10.2016.
  */
object Main extends App {

  val filesToLoad: Seq[FileMetaInfo] = MetaTraderQuotesSeeker.searchFilesToLoad
  println(s"Files found for upload: $filesToLoad")

  val quotesIterator: QuotesIterator = CsvParser.createQuotesIterator(filesToLoad)
  quotesIterator.startIterateWith(println(_, " ", _))

}
