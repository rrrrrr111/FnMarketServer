package ru.roman.fnmarket.db

import org.slf4j.LoggerFactory
import play.api.db.{Database, Databases}

/**
  * Created by Roman on 12.10.2016.
  */
object DesktopDbSupport {
  private val log = LoggerFactory.getLogger(getClass)

  val db: Database = Databases.apply(
    driver = "oracle.jdbc.OracleDriver",
    url = "jdbc:oracle:thin:@localhost:1521:ORCL",
    name = "fn_market_1_0",
    Map(
      "username" -> "fn_market_1_0",
      "password" -> "fn_market_1_0"
    )
  )
  log.info("Database pool initialised")

}
