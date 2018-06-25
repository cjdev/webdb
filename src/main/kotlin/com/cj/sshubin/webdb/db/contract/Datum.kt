package com.cj.sshubin.webdb.db.contract

import com.cj.sshubin.webdb.map.MapUtil

data class Datum(val content: Any?) {
    fun withId(id: String): Datum = Datum(MapUtil.merge(content, mapOf(Pair("id", id))))
}
