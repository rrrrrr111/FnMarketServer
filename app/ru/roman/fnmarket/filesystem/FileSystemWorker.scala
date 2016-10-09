package ru.roman.fnmarket.filesystem

import java.io.{File, FileFilter, FileNotFoundException}

import org.apache.commons.io.FilenameUtils

import scala.language.implicitConversions


/**
  *
  */
trait FileSystemWorker {

  protected implicit def funcToFileFilter(filter: File => Boolean): FileFilter =
    new FileFilter() {
      override def accept(file: File): Boolean = filter(file)
    }

  /**
    * Проверяет существует ли файл и доступен ли для чтения
    */
  def checkFileForRead(file: File, throwException: Boolean = true): Option[String] =
    returnOrRethrow(throwException, () => {
      if (!file.exists) throw new FileNotFoundException(s"File ${file.getPath} not exists")
      if (file.isDirectory) throw new FileNotFoundException(s"File ${file.getPath} is directory")
      if (!file.canRead) throw new FileNotFoundException(s"File ${file.getPath} cannot be read")
    })

  def checkFileNameForRead(dir: String, throwException: Boolean = true): Option[String] =
    checkFileForRead(newFile(dir), throwException)

  /**
    * Проверяет существует ли папка и доступна ли для чтения
    */
  def checkDirForRead(dir: File, throwException: Boolean = true): Option[String] =
    returnOrRethrow(throwException, () => {
      if (!dir.exists) throw new FileNotFoundException(s"Directory ${dir.getPath} not exists")
      if (!dir.isDirectory) throw new FileNotFoundException(s"Directory ${dir.getPath} is file")
      if (!dir.canRead) throw new FileNotFoundException(s"Directory ${dir.getPath} cannot be read")
    })

  def checkDirNameForRead(dir: String, throwException: Boolean = true): Option[String] =
    checkDirForRead(newFile(dir), throwException)

  def newFile(fileName: String): File =
    new File(FilenameUtils.normalize(fileName))

  private def returnOrRethrow(throwException: Boolean, check: (() => Unit)): Option[String] = {
    try {
      check()
      None
    } catch {
      case e: Throwable if throwException => throw e
      case e: Throwable => Some(e.getMessage)
    }
  }
}
