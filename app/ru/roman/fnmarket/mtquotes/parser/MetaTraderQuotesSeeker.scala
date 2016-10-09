package ru.roman.fnmarket.mtquotes.parser

import java.io.File

import ru.roman.fnmarket.filesystem.FileSystemWorker
import ru.roman.fnmarket.mtquotes._

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Roman on 06.10.2016.
  */
object MetaTraderQuotesSeeker extends FileSystemWorker {

  val userHome = System.getProperty("user.home")
  val mtTerminalsDirName = s"""$userHome/AppData/Roaming/MetaQuotes/Terminal/"""
  var ohlcDataDirName = "/MQL5/Files/DATA_OHLC/"

  def searchSymbolOhlcDataDirs: Map[String, Seq[File]] = {

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

  def searchFilesToLoad(ohlcDataDirs: Map[String, Seq[File]]): Seq[File] = {

    val files = ArrayBuffer[File]()
    val fileNames = ArrayBuffer[String]()

    val periods = Period.identityMap.keySet.mkString("|")
    val fileNamePattern = s"([A-z0-9 _]*)__($periods)".r

    for (entry <- ohlcDataDirs;
         ohlcDataDir <- entry._2;
         dataFile <- ohlcDataDir.listFiles()) {

      if (fileNames.contains(dataFile.getName)) {
        println(s"Duplicated file found in terminal directory: ${entry._1} and ignored ")

      } else {
        fileNamePattern.findFirstMatchIn(dataFile.getName) match {
          case Some(m) if Symbol.identityMap.contains(m.group(1)) => {
            fileNames += dataFile.getName
            files += dataFile
          }
          case None => println(s"Incorrect file ${dataFile.getName} found")
          case _ => // unknown ignored
        }
      }
    }
    files
  }
}
