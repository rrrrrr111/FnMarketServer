package ru.roman.fnmarket.analytic

import ru.roman.fnmarket.analytic.tool.PeriodGenerator
import ru.roman.fnmarket.mtquotes.{QuotePeriod, QuoteSymbol}

/**
  * Created by Roman on 16.10.2016.
  */
object ProbabilityCalculator {

  def probabilityOfNextDayInSameDirectionAsPrevious = {

    val intervals = PeriodGenerator.generateMonthIntervals(12)
    val window = ReadQuoteDao.prepareSelectInInterval(QuoteSymbol.SBRF_Splice, QuotePeriod.D1, intervals(0))

    window.iterateWith((num, q) => {

      println(s">>>>>>>>>>>>>>>>>>>>>>>>>>>$num $q")
    })
  }
}
