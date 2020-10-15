package com.cj.sshubin.webdb.domain

interface ServerFactory {
  fun createWithPort(port: Int): ServerContract
}
