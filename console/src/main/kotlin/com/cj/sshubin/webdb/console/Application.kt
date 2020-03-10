package com.cj.sshubin.webdb.console

import com.cj.sshubin.webdb.domain.Datum
import com.cj.sshubin.webdb.domain.DbRoutes.IdRoute
import com.cj.sshubin.webdb.domain.DbRoutes.NamespaceRoute
import com.cj.sshubin.webdb.domain.DbRoutes.Root
import com.cj.sshubin.webdb.domain.Id
import com.cj.sshubin.webdb.domain.InMemoryDatabase
import com.cj.sshubin.webdb.domain.JsonUtil.fromJson
import com.cj.sshubin.webdb.domain.JsonUtil.toJson
import com.cj.sshubin.webdb.domain.NamespaceId
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Locations
import io.ktor.locations.delete
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.response.respondText
import io.ktor.routing.routing
import io.ktor.util.pipeline.PipelineContext
import io.ktor.utils.io.ByteReadChannel
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets

fun Application.mainModule() {
  val db = InMemoryDatabase()
  install(Locations)
  routing {
    get<Root> {
      handleError {
        respondText("OK")
      }
    }
    get<NamespaceRoute> { (namespace) ->
      handleError {
        val allInNamespace = db.getAllInNamespace(NamespaceId(namespace))
        respondText(toJson(allInNamespace.unbox()))
      }
    }
    get<IdRoute> { (namespace, id) ->
      handleError {
        val datum = db[NamespaceId(namespace), Id(id)]
        respondText(toJson(datum.content))
      }
    }
    post<NamespaceRoute> { (namespace) ->
      handleError {
        val body = readBodyUtf8(call.request.receiveChannel())
        val id = db.create(NamespaceId(namespace), Datum(fromJson(body, Object::class.java)))
        respondText(id.value)
      }
    }
    post<IdRoute> { (namespace, id) ->
      handleError {
        val body = readBodyUtf8(call.request.receiveChannel())
        db[NamespaceId(namespace), Id(id)] = Datum(fromJson(body, Object::class.java))
        respondText(id)
      }
    }
    delete<IdRoute> { (namespace, id) ->
      handleError {
        db.delete(NamespaceId(namespace), Id(id))
        respondText(id)
      }
    }
  }

}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleError(block: suspend () -> Unit) {
  try {
    block()
  } catch (throwable: Throwable) {
    val outStream = ByteArrayOutputStream()
    val printStream = PrintStream(outStream)
    throwable.printStackTrace(printStream)
    val stackTraceAsString = String(outStream.toByteArray())
    respondText(stackTraceAsString, ContentType.Text.Plain, HttpStatusCode.BadRequest)
    throwable.printStackTrace()
  }

}

private suspend fun PipelineContext<Unit, ApplicationCall>.respondText(body: String) {
  respondText(body, ContentType.Text.Plain, HttpStatusCode.OK)
}

private suspend fun PipelineContext<Unit, ApplicationCall>.respondText(body: String, contentType: ContentType? = null, status: HttpStatusCode? = null) {
  call.respondText(body + "\n", contentType, status)
}

fun List<Datum>.unbox(): List<Any?> = this.map { it.content }

suspend fun readBodyUtf8(receiveChannel: ByteReadChannel): String {
  val bytes = mutableListOf<Byte>()
  while (!receiveChannel.isClosedForRead) {
    bytes.add(receiveChannel.readByte())
  }
  return String(bytes.toByteArray(), StandardCharsets.UTF_8)
}
