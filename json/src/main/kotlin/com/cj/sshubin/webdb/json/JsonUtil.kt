package com.cj.sshubin.webdb.json

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

object JsonUtil {
  private val mapper = ObjectMapper()

  init {
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true)
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
  }

  fun <T> T.toJson(): String = mapper.writeValueAsString(this)

  fun <T> String.jsonToClass(theClass: Class<T>): T = mapper.readValue(this, theClass)

  fun String.normalizeJson(): String {
    val asObject = jsonToClass(Object::class.java)
    return asObject.toJson()
  }

  fun mergeJsonStrings(a: String, b: String): String {
    val aObject = a.jsonToClass(Object::class.java)
    val bObject = b.jsonToClass(Object::class.java)
    val cObject = MapUtil.merge(aObject, bObject)
    return cObject.toJson()
  }
}
