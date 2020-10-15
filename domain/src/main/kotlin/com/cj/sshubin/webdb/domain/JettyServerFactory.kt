package com.cj.sshubin.webdb.domain

import org.eclipse.jetty.server.Server

class JettyServerFactory : ServerFactory {
  override fun createWithPort(port: Int): ServerContract {
    val server = Server(port)
    val delegate = ServerDelegate(server)
    return delegate
  }
}