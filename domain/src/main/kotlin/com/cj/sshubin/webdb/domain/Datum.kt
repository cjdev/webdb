package com.cj.sshubin.webdb.domain

data class Datum(val content: Any?) {
  fun withId(id: String): Datum = Datum(MapUtil.merge(content, mapOf(Pair("id", id))))
}
