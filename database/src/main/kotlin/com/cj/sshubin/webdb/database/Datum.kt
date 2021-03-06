package com.cj.sshubin.webdb.database

import com.cj.sshubin.webdb.json.MapUtil

data class Datum(val content: Any?) {
  fun withId(id: String): Datum = Datum(MapUtil.merge(content, mapOf(Pair("id", id))))
}
