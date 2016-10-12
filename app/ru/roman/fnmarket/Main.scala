package ru.roman.fnmarket

import ru.roman.fnmarket.mtquotes.uploader.QuotesUploadService


/**
  * Created by Roman on 04.10.2016.
  */
object Main extends App {

  QuotesUploadService.uploadQuotesFromMtToDb

}
