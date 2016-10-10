package ru.roman.fnmarket.mtquotes

import java.time.LocalDateTime

/**
  * Created by Roman on 09.10.2016.
  */
class Quote(
             val id: Long,
             val symbol: Symbol,
             val period: Period,
             val localDateTime: LocalDateTime,
             val openPrice: BigDecimal,
             val hightPrice: BigDecimal,
             val lowPrice: BigDecimal,
             val closePrice: BigDecimal,
             val volume: BigDecimal) {

  def this(symbol: Symbol,
           period: Period,
           localDateTime: LocalDateTime,
           openPrice: BigDecimal,
           hightPrice: BigDecimal,
           lowPrice: BigDecimal,
           closePrice: BigDecimal,
           volume: BigDecimal) {
    this(null.asInstanceOf[Long],
      symbol,
      period,
      localDateTime,
      openPrice,
      hightPrice,
      lowPrice,
      closePrice,
      volume)
  }

  override def toString = s"Quote($id, $symbol, $period, $localDateTime, $openPrice, $hightPrice, $lowPrice, $closePrice, $volume)"
}
