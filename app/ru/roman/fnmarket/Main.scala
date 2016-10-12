package ru.roman.fnmarket

import play.api.db.Databases


/**
  * Created by Roman on 04.10.2016.
  */
object Main extends App {

  //val filesToLoad: Seq[FileMetaInfo] = MetaTraderQuotesSeeker.searchFilesToLoad
  //println(s"Files found for upload: $filesToLoad")

  //val quotesIterator: QuotesIterator = CsvParser.createQuotesIterator(filesToLoad)
  //quotesIterator.startIterateWith(println(_, " ", _))

  Databases.withDatabase(
    driver = "oracle.jdbc.OracleDriver",
    url = "jdbc:oracle:thin:@localhost:1521:ORCL",
    name= "fn_market_1_0",
    Map(
      "username" -> "fn_market_1_0",
      "password" -> "fn_market_1_0"
    )
  )(db => {

    db.

    println( db)
  })

}
