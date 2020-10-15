package com.cj.sshubin.webdb.domain

import org.eclipse.jetty.server.Handler

interface ServerContract {
  var handler: Handler
  fun start()
  fun join()
}
