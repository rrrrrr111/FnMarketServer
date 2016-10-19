package ru.roman.fnmarket.mtquotes.parser

import ru.roman.fnmarket.mtquotes.{QuotePeriod, QuoteSymbol}

import scala.util.matching.Regex
import scala.util.matching.Regex.Match


/**
  *
  */
object FileFormat {
  val SYMBOL__PERIOD = {
    val periods = QuotePeriod.identityMap.keySet.mkString("|")

    new FileFormat(s"([A-z0-9 _]*)__($periods)".r) {

      override def extractSymbol(fileName: String): QuoteSymbol = {
        val m = fileNameRegex.findFirstMatchIn(fileName).getOrElse {
          throw new IllegalStateException(s"incorrect file name $fileName")
        }
        QuoteSymbol.byName(m.group(1))
      }

      override def extractPeriod(fileName: String): QuotePeriod = {
        val m = fileNameRegex.findFirstMatchIn(fileName).getOrElse {
          throw new IllegalStateException(s"incorrect file name $fileName")
        }
        QuotePeriod.byName(m.group(2))
      }
    }
  }

  val items: Seq[FileFormat] = Seq(SYMBOL__PERIOD)

  def determine(fileName: String): Option[FileFormat] = {
    var m: Match = null
    items.find { ff =>
      m = ff.fileNameRegex.findFirstMatchIn(fileName).orNull
      m != null
    } match {
      case Some(format)
        if QuoteSymbol.identityMap.contains(m.group(1))
          && QuoteSymbol.identityMap(m.group(1)).supports(QuotePeriod.byName(m.group(2))) => Some(format)

      case Some(_)
        if QuoteSymbol.identityMap.contains(m.group(1))
          && QuotePeriod.identityMap.contains(m.group(2)) => None // unsupported file skipped

      case _ =>
        println(s"Incorrect file $fileName found")
        None
    }
  }
}

abstract class FileFormat(
                           val fileNameRegex: Regex
                         ) {
  def extractSymbol(fileName: String): (QuoteSymbol)

  def extractPeriod(fileName: String): (QuotePeriod)

  override def toString = s"FileFormat($fileNameRegex)"
}
