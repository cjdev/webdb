package com.cj.sshubin.webdb.domain

class ApiHandler(private val api: Api) : RequestHandler {
  override fun handle(request: RequestValue): ResponseValue {
    val command = Command.fromRequest(request)
    return command.execute(api)
  }
}
