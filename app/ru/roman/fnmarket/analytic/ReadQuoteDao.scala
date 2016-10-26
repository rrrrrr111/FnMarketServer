package ru.roman.fnmarket.analytic

import java.sql.ResultSet

import org.joda.time.{DateTime, Interval}
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.RowCallbackHandler
import ru.roman.fnmarket.db.DesktopDao
import ru.roman.fnmarket.mtquotes.{Quote, QuotePeriod, QuoteSymbol, QuotesWindow}

/**
  * Created by Roman on 27.10.2016.
  */
object ReadQuoteDao extends DesktopDao {
  private val log = LoggerFactory.getLogger(getClass)


  val selectInIntervalSql =
    """
      |SELECT * FROM SBRF_SPLICE__D1 WHERE
      |? <= CLOSE_DATETIME AND CLOSE_DATETIME < ?
    """.stripMargin

  def prepareSelectInInterval(s: QuoteSymbol, p: QuotePeriod, interval: Interval): QuotesWindow = {

    new QuotesWindow {
      override def iterateWith(onNext: (BigInt, Quote) => Unit): Unit = {

        val sql = selectInIntervalSql.format(s.id, p)
        var rowNum: BigInt = 0

        jt.query(sql, new RowCallbackHandler() {
          override def processRow(rs: ResultSet): Unit = {

            rowNum += 1
            val q = new Quote(rs.getLong("ID"),
              s,
              p,
              new DateTime(rs.getTimestamp("CLOSE_DATETIME").getTime),
              rs.getBigDecimal("OPEN_PRICE"),
              rs.getBigDecimal("HIGHT_PRICE"),
              rs.getBigDecimal("LOW_PRICE"),
              rs.getBigDecimal("CLOSE_PRICE"),
              rs.getBigDecimal("VOLUME")
            )
            onNext(rowNum, q)
          }
        },
          dateTimeToTimestamp(interval.getStart), dateTimeToTimestamp(interval.getEnd)
        )
      }
    }
  }
}
