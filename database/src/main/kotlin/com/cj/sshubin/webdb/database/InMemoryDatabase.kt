package com.cj.sshubin.webdb.database

class InMemoryDatabase() : Database {
  private val keyOrder: MutableList<NamespaceId> = mutableListOf()
  private val namespaces: MutableMap<NamespaceId, InMemoryNamespaceData> = mutableMapOf()
  override fun create(namespace: NamespaceId, datum: Datum): Id = synchronized(this) {
    getOrCreateNamespaceData(namespace).create(datum)
  }

  override fun get(namespace: NamespaceId, id: Id): Datum? = synchronized(this) {
    getOrCreateNamespaceData(namespace)[id]
  }

  override fun getAllInNamespace(namespace: NamespaceId): List<Datum> = synchronized(this) {
    getOrCreateNamespaceData(namespace).all()
  }

  override fun set(namespace: NamespaceId, id: Id, datum: Datum) = synchronized(this) {
    getOrCreateNamespaceData(namespace)[id] = datum
  }

  override fun delete(namespace: NamespaceId, id: Id) = synchronized(this) {
    getOrCreateNamespaceData(namespace).delete(id)
  }

  override fun listNamespaces(): List<NamespaceId> = keyOrder

  private fun getOrCreateNamespaceData(namespace: NamespaceId): InMemoryNamespaceData {
    val maybeValue = namespaces[namespace]
    return if (maybeValue == null) {
      val value = InMemoryNamespaceData(namespace)
      keyOrder.add(namespace)
      namespaces[namespace] = value
      value
    } else {
      maybeValue
    }
  }
}
