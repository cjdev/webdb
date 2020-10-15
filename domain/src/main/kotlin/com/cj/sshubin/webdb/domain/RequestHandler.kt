package com.cj.sshubin.webdb.domain

interface RequestHandler {
  fun handle(request: RequestValue): ResponseValue
}
