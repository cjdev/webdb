package com.cj.sshubin.webdb.logging

import com.cj.sshubin.webdb.contract.FilesContract
import java.nio.file.Path
import java.nio.file.StandardOpenOption

class FileLogger(
    private val initialize: () -> Unit,
    private val files: FilesContract,
    private val logFile: Path
) : Logger {
  private val initializeResult = lazy {
    initialize()
  }

  override fun log(lines: List<String>) {
    initializeResult.value
    files.write(logFile, lines, StandardOpenOption.APPEND, StandardOpenOption.CREATE)
  }
}
