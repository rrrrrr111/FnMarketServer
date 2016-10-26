package ru.roman.fnmarket.mtquotes.parser

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import ru.roman.fnmarket.mtquotes.{Quote, QuotesWindow}

import scala.io.Source

/**
  * Created by Roman on 08.10.2016.
  */
object CsvParser {

  def createQuotesWindow(filesToLoad: Seq[FileMetaInfo]): QuotesWindow = {

    new QuotesWindow {
      override def iterateWith(onNext: ((BigInt, Quote) => Unit)): Unit = {

        filesToLoad.foreach { fileMetaInfo =>

          var rowNum: BigInt = 0
          Source.fromFile(fileMetaInfo.file).getLines().foreach { l =>
            rowNum += 1

            if (rowNum != 1) { // skipping the header

              val parts: Array[String] = l.split(';')

              onNext(rowNum,
                new Quote(
                  fileMetaInfo.symbol,
                  fileMetaInfo.period,
                  DateTime.parse(parts(0), DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")),
                  BigDecimal(parts(1)),
                  BigDecimal(parts(2)),
                  BigDecimal(parts(3)),
                  BigDecimal(parts(4)),
                  BigDecimal(parts(5))
                ))
            }
          }
        }
      }
    }
  }
}
