package com.cj.sshubin.webdb.console

import com.cj.sshubin.webdb.database.Database
import com.cj.sshubin.webdb.database.InMemoryDatabase
import com.cj.sshubin.webdb.domain.*
import com.cj.sshubin.webdb.logging.Logger
import com.cj.sshubin.webdb.logging.LoggerFactory
import org.eclipse.jetty.server.Handler
import java.nio.file.Path
import java.nio.file.Paths

class DependencyInjection(args: Array<String>) {
  private val serverFactory: ServerFactory = JettyServerFactory()
  private val database: Database = InMemoryDatabase()
  private val api: Api = ApiDelegateToDatabase(database)
  private val handler: RequestHandler = ApiHandler(api)
  private val logPath: Path = Paths.get("out/log")
  private val logName: String = "request-response"
  private val logger: Logger = LoggerFactory.instanceDefaultZone.createLogger(logPath, logName)
  private val notifications: Notifications = LoggingNotifications(logger)
  private val requestValueEvent: (RequestValue) -> Unit = notifications::requestValueEvent
  private val responseValueEvent: (RequestResponse) -> Unit = notifications::responseValueEvent
  private val exceptionEvent = notifications::exceptionEvent
  private val handlerAdapter: Handler = HandlerAdapter(handler, requestValueEvent, responseValueEvent, exceptionEvent)
  private val defaultPort: Int = 8080
  private val configurationFactory: ConfigurationFactory = CommandLineArgumentsConfiguration(args, defaultPort)
  val runner: Runnable = Runner(serverFactory, handlerAdapter, configurationFactory)
}
