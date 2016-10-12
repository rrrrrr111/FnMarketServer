package ru.roman.fnmarket.mtquotes.parser

import java.io.File

import ru.roman.fnmarket.mtquotes.{Period, Symbol}

/**
  * Created by Roman on 11.10.2016.
  */
class FileMetaInfo(
                    val file: File,
                    val fileFormat: FileFormat,
                    val symbol: Symbol,
                    val period: Period) {
  def this(
            file: File,
            fileFormat: FileFormat
          ) = {
    this(
      file,
      fileFormat,
      fileFormat.extractSymbol(file.getName),
      fileFormat.extractPeriod(file.getName)
    )
  }

  override def toString = s"FileMetaInfo(${file.getName}, $fileFormat, $symbol, $period)"
}
