package com.cj.sshubin.webdb.domain

interface Api {
  fun displayNamespaces(): ResponseValue
  fun createEntry(namespace: String, body: String): ResponseValue
  fun listEntries(namespace: String): ResponseValue
  fun getEntry(namespace: String, id: String): ResponseValue
  fun deleteEntry(namespace: String, id: String): ResponseValue
  fun updateEntry(namespace: String, id: String, body: String): ResponseValue
  fun unsupported(request: RequestValue): ResponseValue
}
