package com.cj.sshubin.webdb.domain

import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.handler.AbstractHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class HandlerAdapter(private val delegate: RequestHandler,
                     private val requestValueEvent: (RequestValue) -> Unit,
                     private val responseValueEvent: (RequestResponse) -> Unit,
                     private val exceptionEvent: (Throwable) -> Unit) : AbstractHandler() {
  override fun handle(target: String, baseRequest: Request, request: HttpServletRequest, response: HttpServletResponse) {
    try {
      handleTypical(baseRequest, request, response)
    } catch (ex: Exception) {
      handleException(baseRequest, request, response, ex)
    }
  }

  private fun handleTypical(baseRequest: Request, request: HttpServletRequest, response: HttpServletResponse) {
    val path = request.pathInfo
    val query = request.queryString ?: ""
    val method = request.method
    val body = request.reader.consumeString()
    val requestValue = RequestValue(method, path, query, body)
    requestValueEvent(requestValue)
    val responseValue = delegate.handle(requestValue)
    responseValueEvent(RequestResponse(requestValue, responseValue))
    response.contentType = responseValue.contentType
    response.status = response.status
    response.writer.print(responseValue.body)
    baseRequest.isHandled = true
  }

  private fun handleException(baseRequest: Request, request: HttpServletRequest, response: HttpServletResponse, ex: Exception) {
    exceptionEvent(ex)
    response.contentType = ContentTypes.TEXT
    response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    ex.printStackTrace(response.writer)
    baseRequest.isHandled = true
  }
}
