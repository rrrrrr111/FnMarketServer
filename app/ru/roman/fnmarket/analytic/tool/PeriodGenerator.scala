package ru.roman.fnmarket.analytic.tool

import org.joda.time.{DateTime, Interval, LocalDate}


/**
  * Created by Roman on 16.10.2016.
  */
object PeriodGenerator {

  def generateMonthIntervals(months: Int): Array[Interval] = {

    val nextMonth = LocalDate.now().plusMonths(1)
    val startOfNextMonth = new DateTime(nextMonth.getYear, nextMonth.getMonthOfYear, 1, 0, 0)

    val periods = new Array[Interval](months)
    1.to(months).foreach { num =>

      periods(periods.length - num) = new Interval(startOfNextMonth.minusMonths(num), startOfNextMonth.minusMonths(num - 1))
    }
    periods
  }
}
