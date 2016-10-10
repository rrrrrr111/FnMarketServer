package ru.roman.fnmarket.mtquotes.parser

import java.io.File

import ru.roman.fnmarket.filesystem.FileSystemWorker

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Roman on 06.10.2016.
  */
object MetaTraderQuotesSeeker extends FileSystemWorker {

  val userHome = System.getProperty("user.home")
  val mtTerminalsDirName = s"""$userHome/AppData/Roaming/MetaQuotes/Terminal/"""
  var ohlcDataDirName = "/MQL5/Files/DATA_OHLC/"

  def searchFilesToLoad: Seq[FileMetaInfo] = {
    val ohlcDataDirs = searchSymbolOhlcDataDirs
    searchFiles(ohlcDataDirs)
  }

  private def searchSymbolOhlcDataDirs: Map[String, Seq[File]] = {

    println(s"Starting the MetaTrader quotes parser, user.home=$userHome")
    checkDirNameForRead(mtTerminalsDirName)

    val terminalDirs = newFile(mtTerminalsDirName) listFiles {
      terminalDir: File =>
        val idTerminalDataDir = terminalDir.isDirectory && terminalDir.getName.length == 32
        lazy val isOhlcDataDirExist = checkDirNameForRead(
          terminalDir.getPath + ohlcDataDirName, throwException = false).isEmpty
        idTerminalDataDir && isOhlcDataDirExist
    }

    terminalDirs map { terminalDir => terminalDir.getName -> {
      newFile(terminalDir.getPath + ohlcDataDirName).listFiles {
        (_: File).isDirectory
      }.toList
    }
    } toMap
  }

  private def searchFiles(ohlcDataDirs: Map[String, Seq[File]]): ArrayBuffer[FileMetaInfo] = {
    val filesMetaInfos = ArrayBuffer[FileMetaInfo]()
    val fileNames = ArrayBuffer[String]()

    for (entry <- ohlcDataDirs;
         ohlcDataDir <- entry._2;
         dataFile <- ohlcDataDir.listFiles()) {

      val fileName = dataFile.getName

      if (fileNames.contains(fileName)) {
        println(s"Duplicated file found in terminal directory: ${entry._1} and ignored ")

      } else {
        val ff = FileFormat.determine(fileName)
        ff match {
          case Some(format) =>
            fileNames += fileName
            filesMetaInfos += new FileMetaInfo(dataFile, ff.get)
          case _ => // unknown filesMetaInfos ignored
        }
      }
    }
    filesMetaInfos
  }
}
