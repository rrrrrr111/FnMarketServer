package ru.roman.fnmarket.mtquotes

import java.time.LocalDateTime

/**
  * Created by Roman on 09.10.2016.
  */
class Quote(
             val id: Long,
             val symbol: MSymbol,
             val period: Period,
             val closeDatetime: LocalDateTime,
             val openPrice: BigDecimal,
             val hightPrice: BigDecimal,
             val lowPrice: BigDecimal,
             val closePrice: BigDecimal,
             val volume: BigDecimal) {

  def this(symbol: MSymbol,
           period: Period,
           closeDatetime: LocalDateTime,
           openPrice: BigDecimal,
           hightPrice: BigDecimal,
           lowPrice: BigDecimal,
           closePrice: BigDecimal,
           volume: BigDecimal) {
    this(null.asInstanceOf[Long],
      symbol,
      period,
      closeDatetime,
      openPrice,
      hightPrice,
      lowPrice,
      closePrice,
      volume)
  }

  override def toString = s"Quote($id, $symbol, $period, $closeDatetime, $openPrice, $hightPrice, $lowPrice, $closePrice, $volume)"
}
