package com.cj.sshubin.webdb.domain

class CommandLineArgumentsConfiguration(
    private val args: Array<String>,
    private val defaultPort: Int) : ConfigurationFactory {
  override fun createConfiguration(): Configuration {
    val portString = args.getOrNull(0)
    val port = if (portString == null) defaultPort else Integer.parseInt(portString)
    val configuration = Configuration(port = port)
    return configuration
  }
}
