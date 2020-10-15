package com.cj.sshubin.webdb.logging

import com.cj.sshubin.webdb.contract.FilesContract
import com.cj.sshubin.webdb.contract.FilesDelegate
import java.nio.file.Path
import java.time.Clock
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class LoggerFactory(
    private val clock: Clock,
    private val files: FilesContract,
    private val emit: (String) -> Unit
) {
    fun createLogger(path: Path, name: String): Logger {
        val now = clock.instant()
        val zone = clock.zone
        val zonedDateTime = ZonedDateTime.ofInstant(now, zone)
        val formattedDateTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime)
        val fileName = formattedDateTime.replace(':', '-').replace('.', '-') + "-$name"
        val logFile = path.resolve(fileName)
        val initialize: () -> Unit = {
            files.createDirectories(path)
        }
        return LineEmittingAndFileLogger(initialize, emit, files, logFile)
    }

    fun createLogGroup(baseDir: Path): LogGroup {
        val now = clock.instant()
        val zone = clock.zone
        val zonedDateTime = ZonedDateTime.ofInstant(now, zone)
        val formattedDateTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime)
        val logDir = baseDir.resolve(formattedDateTime.replace(':', '-').replace('.', '-'))
        return LogGroup(emit, files, logDir)
    }

    companion object {
        val instanceDefaultZone: LoggerFactory by lazy {
            val clock: Clock = Clock.systemDefaultZone()
            val files: FilesContract = FilesDelegate
            val emit: (String) -> Unit = ::println
            LoggerFactory(clock, files, emit)
        }
    }
}
