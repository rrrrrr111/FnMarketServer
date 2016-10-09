package ru.roman.fnmarket

import java.io.File

import ru.roman.fnmarket.mtquotes.parser.MetaTraderQuotesSeeker


/**
  * Created by Roman on 04.10.2016.
  */
object Main extends App {

  val filesToLoad: Seq[File] = MetaTraderQuotesSeeker.searchFilesToLoad

  //CsvParser.readfile

  println(filesToLoad)

}
