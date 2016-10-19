package ru.roman.fnmarket.mtquotes.uploader

import java.sql.{PreparedStatement, Timestamp}

import org.apache.commons.lang3.time.{DurationFormatUtils, StopWatch}
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.PreparedStatementSetter
import ru.roman.fnmarket.db.DesktopDao
import ru.roman.fnmarket.mtquotes.{Quote, QuotePeriod, QuoteSymbol, QuotesIterator}

/**
  * Created by Roman on 12.10.2016.
  */
object QuotesUploadDao extends DesktopDao {
  private val log = LoggerFactory.getLogger(getClass)

  val uploadSql =
    """
      |INSERT INTO %s__%s (ID, OPEN_PRICE, HIGHT_PRICE, LOW_PRICE, CLOSE_PRICE, CLOSE_DATETIME, VOLUME)
      |VALUES (ID_SEQ.nextval, ?, ?, ?, ?, ? ,?)
    """.stripMargin

  def upload(iterator: QuotesIterator): Int = {

    var counter: Int = 0
    val batchSize: Int = 10000
    val sw = new StopWatch()
    var symbol: QuoteSymbol = null
    var period: QuotePeriod = null
    var sql: String = null

    db.withTransaction(c => {
      val jt = newJdbcTemplate(c)
      sw.start()

      iterator.startIterateWith((n: BigInt, q: Quote) => {

        if (symbol != q.symbol || period != q.period) {
          symbol = q.symbol
          period = q.period
          sql = uploadSql.format(symbol.id, period)
        }

        jt.update(sql, new PreparedStatementSetter {
          // на 10% быстрее чем jt.update(String, AnyRef*)
          override def setValues(ps: PreparedStatement): Unit = {

            ps.setBigDecimal(1, q.openPrice.bigDecimal)
            ps.setBigDecimal(2, q.hightPrice.bigDecimal)
            ps.setBigDecimal(3, q.lowPrice.bigDecimal)
            ps.setBigDecimal(4, q.closePrice.bigDecimal)
            ps.setTimestamp(5, Timestamp.valueOf(q.closeDatetime))
            ps.setBigDecimal(6, q.volume.bigDecimal)
          }
        })

        counter += 1
        if (counter % batchSize == 0) {
          c.commit()
          sw.stop()
          log.info(s"$batchSize records commited, total: $counter, " +
            s"upload duration: ${DurationFormatUtils.formatDurationHMS(sw.getTime)}, " +
            s"speed: ${batchSize * 1000 / sw.getTime}q/s")
          sw.reset()
          sw.start()
        }

      })

    })

    counter
  }
}


//ps.setBigDecimal(1, q.openPrice.bigDecimal)
//ps.setBigDecimal(2, q.hightPrice.bigDecimal)
//ps.setBigDecimal(3, q.lowPrice.bigDecimal)
//ps.setBigDecimal(4, q.closePrice.bigDecimal)
//ps.setTimestamp(5, Timestamp.valueOf(q.closeDatetime))
//ps.setBigDecimal(6, q.volume.bigDecimal)

//q.openPrice.bigDecimal,
//q.hightPrice.bigDecimal,
//q.lowPrice.bigDecimal,
//q.closePrice.bigDecimal,
//Timestamp.valueOf(q.closeDatetime),
//q.volume.bigDecimal