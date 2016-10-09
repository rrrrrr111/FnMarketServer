package ru.roman.example

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

import org.apache.commons.lang3.time.DurationFormatUtils

import scala.io.Source

/**
  * Created by Roman on 09.10.2016.
  */
object AnalyseDbofiLogFiles extends App {

  var df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS")
  var previous: LocalDateTime = null
  var first: LocalDateTime = null
  var last: LocalDateTime = null
  var counter: BigInt = 0L
  var deltaCounter: BigInt = 0L

  val src = Source.fromFile("C:\\Documents and Settings\\Roman\\Downloads\\shed_logs_0000\\incom_0.txt")
  //val src = Source.fromFile("C:\\Documents and Settings\\Roman\\Downloads\\shed_logs_0000\\incom_1.txt")
  //val src = Source.fromFile("C:\\Documents and Settings\\Roman\\Downloads\\shed_logs_0000\\incom_2.txt")
  //val src = Source.fromFile("C:\\Documents and Settings\\Roman\\Downloads\\shed_logs_0500\\incom_10.txt")
  //val src = Source.fromFile("C:\\Documents and Settings\\Roman\\Downloads\\shed_logs_0500\\incom_11.txt")
  //val src = Source.fromFile("C:\\Documents and Settings\\Roman\\Downloads\\shed_logs_0500\\incom_12.txt")

  for (l <- src.getLines()) {

    val dateTimeStr = l.substring(0, 23)
    val dateTime = LocalDateTime.from(df.parse(dateTimeStr))
    if (previous == null) {
      previous = dateTime
      first = dateTime
    }

    val delta = ChronoUnit.MILLIS.between(previous, dateTime)
    counter = counter + 1
    deltaCounter = deltaCounter + delta

    println(DurationFormatUtils.formatDurationHMS(delta))

    previous = LocalDateTime.from(dateTime)
  }
  last = previous

  val averageDelta = deltaCounter / counter
  println(s"Average: $averageDelta in ${df.format(first)}  ->  ${df.format(last)}")

}
