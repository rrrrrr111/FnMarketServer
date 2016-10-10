package ru.roman.fnmarket.mtquotes.parser

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import ru.roman.fnmarket.mtquotes.{Quote, QuotesIterator}

import scala.io.Source

/**
  * Created by Roman on 08.10.2016.
  */
object CsvParser {

  def createQuotesIterator(filesToLoad: Seq[FileMetaInfo]): QuotesIterator = {

    new QuotesIterator {
      override def startIterateWith(onNext: ((BigInt, Quote) => Unit)): Unit = {

        val df: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

        filesToLoad.foreach { fileMetaInfo =>

          var rowNum: BigInt = 0
          Source.fromFile(fileMetaInfo.file).getLines().foreach { l =>
            rowNum += 1
            if (rowNum != 1) {  // skipping the header

              val parts: Array[String] = l.split(';')

              onNext(rowNum,
                new Quote(
                  fileMetaInfo.symbol,
                  fileMetaInfo.period,
                  LocalDateTime.from(df.parse(parts(0))),
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
