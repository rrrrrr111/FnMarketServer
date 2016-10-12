package ru.roman.fnmarket.db

import java.sql.Connection

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.SingleConnectionDataSource

/**
  * Created by Roman on 12.10.2016.
  */
trait DesktopDao {

  protected lazy val db = DesktopDbSupport.db
  protected lazy val jt = new JdbcTemplate(db.dataSource, true)

  protected def newJdbcTemplate(c: Connection): JdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(c, false))

}
