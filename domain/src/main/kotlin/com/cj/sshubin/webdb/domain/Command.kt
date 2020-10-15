package com.cj.sshubin.webdb.domain

interface Command {
  fun execute(api: Api): ResponseValue

  companion object {
    fun fromRequest(request: RequestValue): Command {
      val path = splitPath(request.path)
      return when (path.size) {
        0 -> DisplayNamespaces
        1 -> fromNamespace(request, path[0])
        2 -> fromNamespaceId(request, path[0], path[1])
        else -> Unsupported(request)
      }
    }

    fun splitPath(path: String): List<String> {
      val toSplit = if (path.endsWith("/")) path.substring(0, path.length - 1) else path
      val parts = toSplit.split("/")
      return parts.drop(1)
    }

    fun fromNamespace(request: RequestValue, namespace: String): Command = when (request.method) {
      "GET" -> ListEntries(namespace)
      "POST" -> CreateEntry(namespace, request.body)
      else -> Unsupported(request)
    }

    fun fromNamespaceId(request: RequestValue, namespace: String, id: String): Command = when (request.method) {
      "GET" -> GetEntry(namespace, id)
      "DELETE" -> DeleteEntry(namespace, id)
      "POST" -> UpdateEntry(namespace, id, request.body)
      else -> Unsupported(request)
    }
  }

  object DisplayNamespaces : Command {
    override fun execute(api: Api): ResponseValue = api.displayNamespaces()
  }

  data class CreateEntry(val namespace: String, val body: String) : Command {
    override fun execute(api: Api): ResponseValue = api.createEntry(namespace, body)
  }

  data class ListEntries(val namespace: String) : Command {
    override fun execute(api: Api): ResponseValue = api.listEntries(namespace)
  }

  data class GetEntry(val namespace: String, val id: String) : Command {
    override fun execute(api: Api): ResponseValue = api.getEntry(namespace, id)
  }

  data class DeleteEntry(val namespace: String, val id: String) : Command {
    override fun execute(api: Api): ResponseValue = api.deleteEntry(namespace, id)
  }

  data class UpdateEntry(val namespace: String, val id: String, val body: String) : Command {
    override fun execute(api: Api): ResponseValue = api.updateEntry(namespace, id, body)
  }

  data class Unsupported(val request: RequestValue) : Command {
    override fun execute(api: Api): ResponseValue = api.unsupported(request)
  }
}
