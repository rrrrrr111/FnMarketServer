package ru.roman.fnmarket.mtquotes.uploader

import org.apache.commons.lang3.time.{DurationFormatUtils, StopWatch}
import org.slf4j.LoggerFactory
import ru.roman.fnmarket.mtquotes.QuotesIterator
import ru.roman.fnmarket.mtquotes.parser.{CsvParser, FileMetaInfo, MetaTraderQuotesSeeker}

/**
  * Created by Roman on 12.10.2016.
  */
object QuotesUploadService {
  private val log = LoggerFactory.getLogger(getClass)

  def uploadQuotesFromMtToDb = {

    val sw = new StopWatch()

    val filesToLoad: Seq[FileMetaInfo] = MetaTraderQuotesSeeker.searchFilesToLoad
    log.info(s"Files found for upload: $filesToLoad, for: ${DurationFormatUtils.formatDurationHMS(sw.getTime)}")

    sw.reset()
    sw.start()

    val quotesIterator: QuotesIterator = CsvParser.createQuotesIterator(filesToLoad)
    val count = QuotesUploadDao.upload(quotesIterator)
    log.info(s"Quotes uploaded to DB: $count, for: ${DurationFormatUtils.formatDurationHMS(sw.getTime)}")
  }

}
