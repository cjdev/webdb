package com.cj.sshubin.webdb.domain

interface Notifications {
  fun requestValueEvent(request: RequestValue)
  fun responseValueEvent(requestResponse: RequestResponse)
  fun exceptionEvent(throwable: Throwable)
}