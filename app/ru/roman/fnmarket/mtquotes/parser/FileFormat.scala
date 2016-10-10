package ru.roman.fnmarket.mtquotes.parser

import ru.roman.fnmarket.mtquotes.{Period, Symbol}

import scala.util.matching.Regex
import scala.util.matching.Regex.Match


/**
  *
  */
object FileFormat {
  val SYMBOL__PERIOD = {
    val periods = Period.identityMap.keySet.mkString("|")

    new FileFormat(s"([A-z0-9 _]*)__($periods)".r) {

      override def extractSymbol(fileName: String): Symbol = {
        val m = fileNameRegex.findFirstMatchIn(fileName).getOrElse {
          throw new IllegalStateException(s"incorrect file name $fileName")
        }
        Symbol.byName(m.group(1))
      }

      override def extractPeriod(fileName: String): Period = {
        val m = fileNameRegex.findFirstMatchIn(fileName).getOrElse {
          throw new IllegalStateException(s"incorrect file name $fileName")
        }
        Period.byName(m.group(2))
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
        if Symbol.identityMap.contains(m.group(1))
          && Symbol.identityMap(m.group(1)).supports(Period.byName(m.group(2))) => Some(format)

      case Some(_)
        if Symbol.identityMap.contains(m.group(1))
          && Period.identityMap.contains(m.group(2)) => None // unsupported file skipped

      case _ =>
        println(s"Incorrect file $fileName found")
        None
    }
  }
}

abstract class FileFormat(
                           val fileNameRegex: Regex
                         ) {
  def extractSymbol(fileName: String): (Symbol)

  def extractPeriod(fileName: String): (Period)

  override def toString = s"FileFormat($fileNameRegex)"
}
