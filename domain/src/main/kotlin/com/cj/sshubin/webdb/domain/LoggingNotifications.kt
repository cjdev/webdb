package com.cj.sshubin.webdb.domain

import com.cj.sshubin.webdb.json.JsonUtil.toJson
import com.cj.sshubin.webdb.logging.Logger
import java.io.PrintWriter
import java.io.StringWriter

class LoggingNotifications(private val logger: Logger) : Notifications {
  override fun requestValueEvent(request: RequestValue) {
    logger.log(request.toJson())
    logger.log(request.body)
  }

  override fun responseValueEvent(requestResponse: RequestResponse) {
    logger.log(requestResponse.toJson())
    logger.log(requestResponse.response.body)
  }

  override fun exceptionEvent(throwable: Throwable) {
    val stringWriter = StringWriter()
    val printWriter = PrintWriter(stringWriter)
    throwable.printStackTrace(printWriter)
    logger.log(stringWriter.buffer.toString())
  }
}
