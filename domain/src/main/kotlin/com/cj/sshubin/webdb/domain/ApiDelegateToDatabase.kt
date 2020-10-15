package com.cj.sshubin.webdb.domain

import com.cj.sshubin.webdb.database.Database
import com.cj.sshubin.webdb.database.Datum
import com.cj.sshubin.webdb.database.Id
import com.cj.sshubin.webdb.database.NamespaceId
import com.cj.sshubin.webdb.json.JsonUtil.jsonToClass
import com.cj.sshubin.webdb.json.JsonUtil.toJson
import com.cj.sshubin.webdb.json.MapUtil
import javax.servlet.http.HttpServletResponse

class ApiDelegateToDatabase(private val database: Database) : Api {
  override fun displayNamespaces(): ResponseValue {
    val allNamespaces = database.listNamespaces().map { it.name }
    val body = allNamespaces.toJson()
    return jsonResponse(body)
  }

  override fun createEntry(namespace: String, body: String): ResponseValue {
    val jsonObject = body.jsonToClass(Object::class.java)
    val datum = Datum(jsonObject)
    val id = database.create(NamespaceId(namespace), datum)
    return idResponse(id)
  }

  override fun listEntries(namespace: String): ResponseValue {
    val allInNamespace = database.getAllInNamespace(NamespaceId(namespace))
    val body = allInNamespace.map { it.content }.toJson()
    return jsonResponse(body)
  }

  override fun getEntry(namespace: String, id: String): ResponseValue {
    val datum = database[NamespaceId(namespace), Id(id)]
    return if (datum == null) {
      val status = HttpServletResponse.SC_NOT_FOUND
      val body = "value with id '$id' not found in namespace '$namespace'"
      ResponseValue(status, ContentTypes.TEXT, body)
    } else {
      val status = HttpServletResponse.SC_OK
      val body = datum.content.toJson()
      ResponseValue(status, ContentTypes.JSON, body)
    }
  }

  override fun deleteEntry(namespace: String, id: String): ResponseValue {
    database.delete(NamespaceId(namespace), Id(id))
    return idResponse(Id(id))
  }

  override fun updateEntry(namespace: String, id: String, body: String): ResponseValue {
    val oldValue = database[NamespaceId(namespace), Id(id)]
    return if (oldValue == null) {
      createEntry(namespace, body)
    } else {
      val content = body.jsonToClass(Object::class.java)
      val newContent = MapUtil.merge(oldValue.content, content)
      val newValue = Datum(newContent)
      database[NamespaceId(namespace), Id(id)] = newValue
      return idResponse(Id(id))
    }
  }

  override fun unsupported(request: RequestValue): ResponseValue {
    throw RuntimeException("Unsupported request with method ${request.method} and path '${request.path}'")
  }

  private fun idResponse(id: Id): ResponseValue = ResponseValue(HttpServletResponse.SC_OK, ContentTypes.TEXT, id.value)
  private fun jsonResponse(json: String): ResponseValue = ResponseValue(HttpServletResponse.SC_OK, ContentTypes.JSON, json)
}
