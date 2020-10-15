package com.cj.sshubin.webdb.domain

import org.eclipse.jetty.server.Handler

class Runner(private val serverFactory: ServerFactory,
             private val handler: Handler,
             private val configurationFactory: ConfigurationFactory) : Runnable {
  override fun run() {
    val configuration = configurationFactory.createConfiguration()
    val server = serverFactory.createWithPort(configuration.port)
    server.handler = handler
    server.start()
    server.join()
  }
}
