package ru.roman.fnmarket.mtquotes.parser

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import ru.roman.fnmarket.mtquotes.{Period, Quote, Symbol}

import scala.io.Source

/**
  * Created by Roman on 08.10.2016.
  */
object CsvParser {

  def createQuotesIterator(filesToLoad: Seq[(FileFormat[Symbol, Period], File)]): QuotesIterator = {

    new QuotesIterator {
      def iterate(onNext: Quote => {}): Unit = {

        filesToLoad.foreach { entry =>

          val tuple = entry._1.extractIdentity(entry._2.getName)
          val symbol = tuple._1
          val period = tuple._2
          val df: DateTimeFormatter = DateTimeFormatter.ofPattern("")

          Source.fromFile(entry._2).getLines().foreach { l =>
            val parts: Array[String] = l.split(';')

            onNext(
              new Quote(
                symbol,
                period,
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
