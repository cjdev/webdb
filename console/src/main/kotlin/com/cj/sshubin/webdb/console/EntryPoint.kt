package com.cj.sshubin.webdb.console

object EntryPoint {
  @JvmStatic
  fun main(args: Array<String>) {
    DependencyInjection(args).runner.run()
  }
}
