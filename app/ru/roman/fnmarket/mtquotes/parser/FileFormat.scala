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

    new FileFormat[Symbol, Period](s"([A-z0-9 _]*)__($periods)".r) {

      def extractIdentity(fileName: String): (Symbol, Period) = {
        fileNameRegex.findFirstMatchIn(fileName) match {
          case Some(m) => (Symbol.byName(m.group(1)), Period.byName(m.group(2)))
          case _ => throw new IllegalStateException(s"incorrect file name $fileName")
        }
      }
    }
  }

  val items: Seq[FileFormat[_ <: AnyRef, _ <: AnyRef]] = Seq(SYMBOL__PERIOD)

  def determine[Symbol, Period](fileName: String): Option[FileFormat[Symbol, Period]] = {
    var m: Match = null
    items.find { ff =>
      m = ff.fileNameRegex.findFirstMatchIn(fileName).orNull
      m != null
    } match {
      case Some(format)
        if Symbol.identityMap.contains(m.group(1))
          && Symbol.identityMap(m.group(1)).supports(Period.byName(m.group(2))) =>

        format.asInstanceOf[Option[FileFormat[Symbol, Period]]]
      case Some(_)
        if Symbol.identityMap.contains(m.group(1))
          && Period.identityMap.contains(m.group(2)) => None // unsupported file skipped

      case _ =>
        println(s"Incorrect file $fileName found")
        None
    }
  }
}

abstract class FileFormat[A <: AnyRef, B <: AnyRef](
                                                     val fileNameRegex: Regex
                                                   ) {
  def extractIdentity(fileName: String): (A, B)
}
