package com.cj.sshubin.webdb.domain

import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.Server

class ServerDelegate(private val delegate: Server) : ServerContract {
  override var handler: Handler
    get() = delegate.handler
    set(value) {
      delegate.handler = value
    }

  override fun start() {
    delegate.start()
  }

  override fun join() {
    delegate.join()
  }
}
